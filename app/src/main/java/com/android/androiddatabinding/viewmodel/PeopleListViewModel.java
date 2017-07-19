package com.android.androiddatabinding.viewmodel;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;

import com.android.androiddatabinding.AndroidDataBindingApplication;
import com.android.androiddatabinding.R;
import com.android.androiddatabinding.adapters.PeopleAdapter;
import com.android.androiddatabinding.bus.RxBus;
import com.android.androiddatabinding.bus.events.Events;
import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.data.fetcher.PeopleFetcher;
import com.android.androiddatabinding.databinding.PeopleListLayoutBinding;
import com.android.androiddatabinding.internal.Constants;
import com.android.androiddatabinding.model.GenericResponse;
import com.android.androiddatabinding.model.MediaCategory;
import com.android.androiddatabinding.model.NetworkError;
import com.android.androiddatabinding.model.PeopleList;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;

/**
 * Created by shreesha on 12/7/17.
 */

public class PeopleListViewModel extends BaseViewModel {

    private static final String TAG = PeopleListViewModel.class.getSimpleName();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MediaCategory mMediaCategory;
    private Context mContext;
    public ObservableField<String> errorMessageLabel;
    public ObservableInt errorLabel;
    public ObservableInt mediaRecyclerView;
    private PeopleAdapter mPeopleAdapter;
    private PeopleListLayoutBinding mPeopleListLayoutBinding;

    public PeopleListViewModel(Context context, MediaCategory mediaCategory) {
        this.mMediaCategory = mediaCategory;
        getBinding();
        errorMessageLabel = new ObservableField<>(context.getString(R.string.default_people_error_message));
        errorLabel = new ObservableInt(View.GONE);
        mediaRecyclerView = new ObservableInt(View.VISIBLE);
        mContext = context;
        initView();
        initAdapter();
        getItemList();
        subscribe();
    }

    private void initView() {
        mediaRecyclerView.set(View.VISIBLE);
        errorLabel.set(View.GONE);
    }

    public String getCategoryTitle() {
        return mMediaCategory.getMediaCategory();
    }

    public GenericResponse<ArrayList<PeopleList>> getPeople() {
        return mMediaCategory.getPeople();
    }

    public void setPeople(GenericResponse<ArrayList<PeopleList>> peopleLists) {
        this.mMediaCategory.setPeople(peopleLists);
    }

    public String getMediaType() {
        return mMediaCategory.getMediaType();
    }

    public String getQueryType() {
        return mMediaCategory.getQueryType();
    }


    public void initAdapter() {
        if (mPeopleAdapter == null) {
            mPeopleAdapter = new PeopleAdapter(mContext, new ArrayList<PeopleList>());
            mPeopleListLayoutBinding.movieList.setAdapter(mPeopleAdapter);
            mPeopleListLayoutBinding.movieList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        }
    }

    public void getItemList() {
        if (getPeople() == null) {
            getPeople(getQueryType(), getMediaType());
        } else {
            if (getPeople() != null && getPeople().getResults().size() == 0) {
                if (mMediaCategory.getNetworkError() != null) {
                    showError();
                } else {
                    Log.d(TAG, getCategoryTitle() + " " + getQueryType());
                    getPeople(getQueryType(), getMediaType());
                }
            } else {
                setPeopleList(getPeople());
            }
        }
    }

    public void handelScrollPosition(PeopleListLayoutBinding peopleListLayoutBinding, SparseIntArray listPosition, int position) {
        int lastSeenFirstPosition = listPosition.get(position, 0);
        if (lastSeenFirstPosition >= 0) {
            peopleListLayoutBinding.movieList.scrollToPosition(lastSeenFirstPosition);
        }
    }

    private void setPeopleList(GenericResponse<ArrayList<PeopleList>> peopleList) {
        updateData(peopleList);
    }


    private void getPeople(String type, String mediaType) {
        AndroidDataBindingApplication dataBindingApplication = AndroidDataBindingApplication.getApplication(mContext);
        Disposable disposable = new PeopleFetcher().getPeopleList(dataBindingApplication.getRetrofit(), mediaType, type, Constants.API_KEY, 1)
                .subscribeOn(dataBindingApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GenericResponse<ArrayList<PeopleList>>>() {
                    @Override
                    public void accept(GenericResponse<ArrayList<PeopleList>> people) throws Exception {
                        updatePeopleList(people);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        handelGetMoviesErrorCase(throwable);
                    }
                });
        mCompositeDisposable.add(disposable);
    }


    private void handelGetMoviesErrorCase(Throwable throwable) {
        NetworkError error = new NetworkError();
        if (throwable instanceof HttpException) {
            //we have a HTTP exception (HTTP status code is not 200-300)
            Converter<ResponseBody, NetworkError> errorConverter =
                    AndroidDataBindingApplication.getApplication(mContext)
                            .getRetrofit()
                            .responseBodyConverter(NetworkError.class, new Annotation[0]);
            //maybe check if ((HttpException) throwable).code() == 400 ??
            try {
                error = errorConverter.convert(((HttpException) throwable).response().errorBody());
                error.setErrorType(NetworkError.HTTP_EXCEPTION);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (throwable instanceof IOException) {
            error.setErrorType(NetworkError.IO_EXCEPTION);
        } else if (throwable instanceof SocketTimeoutException) {
            error.setErrorType(NetworkError.TIMEOUT_EXCEPTION);
        }
        mMediaCategory.setNetworkError(error);
        showError();
    }

    private void updatePeopleList(GenericResponse<ArrayList<PeopleList>> people) {
        if (people != null && people.getResults() != null && people.getResults().size() > 0) {
            updateData(people);
        }
    }

    private void showError() {
        errorLabel.set(View.VISIBLE);
        mediaRecyclerView.set(View.GONE);
        errorMessageLabel.set(mContext.getString(R.string.error_loading_people));
        //RxBus.getInstance().send(mMediaCategory);
    }

    private void updateData(GenericResponse<ArrayList<PeopleList>> people) {
        mediaRecyclerView.set(View.VISIBLE);
        errorLabel.set(View.GONE);
        setPeople(people);
        RxBus.getInstance().send(mMediaCategory);
    }

    private void updateMovieAdapter(MediaCategory mediaCategory) {
        if (mediaCategory != null && mediaCategory.getPeople() != null && mediaCategory.getPeople().getResults().size() > 0) {
            if (mMediaCategory.equals(mediaCategory.getMediaCategory())) {
                mPeopleAdapter.addAll(mediaCategory.getPeople().getResults());
            }
        }
    }

    public void subscribe() {
        Disposable disposable = RxBus.getInstance().getBus()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        if (o instanceof Events) {
                            switch ((Events) o) {
                                case RECYCLER_VIEW_DETACHED:
                                    reset();
                                    break;
                            }
                        }
                    }
                });
        mCompositeDisposable.add(disposable);
    }


    private void unSubscribeFromObservable() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        mCompositeDisposable = null;
    }

    private void getBinding() {
        LayoutInflater layoutInflater =
                LayoutInflater.from(mContext);
        View movieListView = layoutInflater.inflate(R.layout.people_list_layout, null, false);
        mPeopleListLayoutBinding = DataBindingUtil.getBinding(movieListView);
    }

    public void setPagination(Context context, PeopleListLayoutBinding peopleListLayoutBinding, PeopleListViewModel baseViewModel) {

    }
}
