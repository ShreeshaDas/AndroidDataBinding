package com.android.androiddatabinding.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.android.androiddatabinding.AndroidDataBindingApplication;
import com.android.androiddatabinding.R;
import com.android.androiddatabinding.adapters.PeopleAdapter;
import com.android.androiddatabinding.bus.RxBus;
import com.android.androiddatabinding.bus.events.Events;
import com.android.androiddatabinding.bus.events.KnowMoreClickEvent;
import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.custom.EndlessRecyclerOnScrollListener;
import com.android.androiddatabinding.data.fetcher.PeopleFetcher;
import com.android.androiddatabinding.databinding.PeopleListLayoutBinding;
import com.android.androiddatabinding.internal.Constants;
import com.android.androiddatabinding.model.GenericResponse;
import com.android.androiddatabinding.model.MediaCategory;
import com.android.androiddatabinding.model.NetworkError;
import com.android.androiddatabinding.model.People;

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

    public ObservableField<String> errorMessageLabel;
    public ObservableInt errorLabel;
    public ObservableInt mediaRecyclerView;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MediaCategory mMediaCategory;
    private Context mContext;
    private PeopleAdapter mPeopleAdapter;
    private PeopleListLayoutBinding mPeopleListLayoutBinding;
    private int mScrollPosition;
    private EndlessRecyclerOnScrollListener mEndlessRecyclerOnScrollListener;

    public PeopleListViewModel(Context context, MediaCategory mediaCategory,
                               ViewDataBinding viewDataBinding, int scrollPosition) {
        this.mMediaCategory = mediaCategory;
        this.mPeopleListLayoutBinding = (PeopleListLayoutBinding) viewDataBinding;
        this.mContext = context;
        this.mScrollPosition = scrollPosition;
        errorMessageLabel = new ObservableField<>(context.getString(R.string.default_people_error_message));
        errorLabel = new ObservableInt(View.GONE);
        mediaRecyclerView = new ObservableInt(View.VISIBLE);
        initView();
        initAdapter();
        getItemList();
        subscribe();
        handelScrollPosition();
        initPagination();
    }


    private void initView() {
        mediaRecyclerView.set(View.VISIBLE);
        errorLabel.set(View.GONE);
    }

    private void initPagination() {
        mEndlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener((LinearLayoutManager) mPeopleListLayoutBinding.movieList.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                getPeople(getQueryType(), getMediaType(), 1);
            }
        };
        mPeopleListLayoutBinding.movieList.addOnScrollListener(mEndlessRecyclerOnScrollListener);
    }


    public String getCategoryTitle() {
        return mMediaCategory.getMediaCategory();
    }

    public GenericResponse<ArrayList<People>> getPeople() {
        return mMediaCategory.getPeople();
    }

    public void setPeople(GenericResponse<ArrayList<People>> peopleLists) {
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
            mPeopleAdapter = new PeopleAdapter(mContext, new ArrayList<People>());
            mPeopleListLayoutBinding.movieList.setAdapter(mPeopleAdapter);
            mPeopleListLayoutBinding.movieList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        }
    }

    public void getItemList() {
        if (getPeople() == null) {
            getPeople(getQueryType(), getMediaType(), 1);
        } else {
            if (getPeople() != null && getPeople().getResults().size() == 0) {
                if (mMediaCategory.getNetworkError() != null) {
                    showError();
                } else {
                    Log.d(TAG, getCategoryTitle() + " " + getQueryType());
                    getPeople(getQueryType(), getMediaType(), 1);
                }
            } else {
                setPeopleList(getPeople());
            }
        }
    }

    private void setPeopleList(GenericResponse<ArrayList<People>> peopleList) {
        updateData(peopleList);
    }


    private void getPeople(String type, String mediaType, int page) {
        AndroidDataBindingApplication dataBindingApplication = AndroidDataBindingApplication.getApplication(mContext);
        Disposable disposable = new PeopleFetcher().getPeopleList(dataBindingApplication.getRetrofit(), mediaType, type, Constants.API_KEY, page)
                .subscribeOn(dataBindingApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GenericResponse<ArrayList<People>>>() {
                    @Override
                    public void accept(GenericResponse<ArrayList<People>> people) throws Exception {
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

    private void updatePeopleList(GenericResponse<ArrayList<People>> people) {
        if (people != null && people.getResults() != null && people.getResults().size() > 0) {
            updateData(people);
        }
    }

    private void showError() {
        errorLabel.set(View.VISIBLE);
        mediaRecyclerView.set(View.GONE);
        errorMessageLabel.set(mContext.getString(R.string.error_loading_people));
    }

    private void updateData(GenericResponse<ArrayList<People>> people) {
        mediaRecyclerView.set(View.VISIBLE);
        errorLabel.set(View.GONE);
        setPeople(people);
        setDataToPeopleAdapter(people);
    }

    private void setDataToPeopleAdapter(GenericResponse<ArrayList<People>> people) {
        if (people != null && people.getResults() != null && people.getResults().size() > 0) {
            mPeopleAdapter.addAll(people.getResults());
            if (people.hasNextPage()) {
                mPeopleAdapter.addFooter();
            } else {
                mPeopleAdapter.removeFooter();
            }
        }
    }

    private void updateDataToMovieAdapter() {
        if (mMediaCategory != null && mMediaCategory.getPeople() != null && mMediaCategory.getPeople().getResults().size() > 0) {
            mPeopleAdapter.updateAll(mMediaCategory.getPeople().getResults());
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
                        } else if (o instanceof KnowMoreClickEvent) {
                            KnowMoreClickEvent knowMoreClickEvent = (KnowMoreClickEvent) o;
                            People people = knowMoreClickEvent.getPeople();
                            int itemPosition = mPeopleAdapter.getData().indexOf(people);
                            getPeople().getResults().set(itemPosition, people);
                            setPeople(getPeople());
                            updateDataToMovieAdapter();
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


    private void handelScrollPosition() {
        if (mScrollPosition > 0) {
            mPeopleListLayoutBinding.movieList.scrollToPosition(mScrollPosition);
        }
    }
}
