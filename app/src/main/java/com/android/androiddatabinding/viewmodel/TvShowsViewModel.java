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
import com.android.androiddatabinding.adapters.TvAdapter;
import com.android.androiddatabinding.bus.RxBus;
import com.android.androiddatabinding.bus.events.Events;
import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.custom.EndlessRecyclerOnScrollListener;
import com.android.androiddatabinding.data.fetcher.TvFetcher;
import com.android.androiddatabinding.databinding.TvShowsListLayoutBinding;
import com.android.androiddatabinding.internal.Constants;
import com.android.androiddatabinding.model.GenericResponse;
import com.android.androiddatabinding.model.MediaCategory;
import com.android.androiddatabinding.model.NetworkError;
import com.android.androiddatabinding.model.Tvs;

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
 * Created by Shreesha on 16-07-2017.
 */

public class TvShowsViewModel extends BaseViewModel {

    private static final String TAG = TvShowsViewModel.class.getSimpleName();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MediaCategory mMediaCategory;
    private Context mContext;
    public ObservableField<String> errorMessageLabel;
    public ObservableInt errorLabel;
    public ObservableInt mediaRecyclerView;
    private TvAdapter mTvAdapter;
    private TvShowsListLayoutBinding mTvShowsListLayoutBinding;
    private int mScrollPosition;
    private EndlessRecyclerOnScrollListener mEndlessRecyclerOnScrollListener;

    public TvShowsViewModel(Context context, MediaCategory mediaCategory,
                            ViewDataBinding viewDataBinding, int scrollPosition) {
        this.mMediaCategory = mediaCategory;
        this.mContext = context;
        this.mScrollPosition = scrollPosition;
        mTvShowsListLayoutBinding = (TvShowsListLayoutBinding) viewDataBinding;
        errorMessageLabel = new ObservableField<>(context.getString(R.string.default_error_message));
        errorLabel = new ObservableInt(View.GONE);
        mediaRecyclerView = new ObservableInt(View.VISIBLE);
        initView();
        initAdapter();
        getItemList();
        subscribe();
        handelScrollPosition();
        initPagination();
    }

    public void initView() {
        mediaRecyclerView.set(View.VISIBLE);
        errorLabel.set(View.GONE);
    }

    public String getCategoryTitle() {
        return mMediaCategory.getMediaCategory();
    }

    public GenericResponse<ArrayList<Tvs>> getTvShows() {
        return mMediaCategory.getTvShows();
    }

    public String getMediaType() {
        return mMediaCategory.getMediaType();
    }

    public String getQueryType() {
        return mMediaCategory.getQueryType();
    }

    public void initAdapter() {
        if (mTvAdapter == null) {
            mTvAdapter = new TvAdapter(mContext, new ArrayList<Tvs>());
            mTvShowsListLayoutBinding.tvList.setAdapter(mTvAdapter);
            mTvShowsListLayoutBinding.tvList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        }
    }

    private void initPagination() {
        mEndlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener((LinearLayoutManager) mTvShowsListLayoutBinding.tvList.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                getTvShows(getQueryType(), getMediaType(), 1);
            }
        };
        mTvShowsListLayoutBinding.tvList.addOnScrollListener(mEndlessRecyclerOnScrollListener);
    }


    public void getItemList() {
        if (getTvShows() == null) {
            getTvShows(getQueryType(), getMediaType(), 1);
        } else {
            if (getTvShows() != null && getTvShows().getResults().size() == 0) {
                if (mMediaCategory.getNetworkError() != null) {
                    showError();
                } else {
                    Log.d(TAG, getCategoryTitle() + " " + getQueryType());
                    getTvShows(getQueryType(), getMediaType(), 1);
                }
            } else {
                setMedia(getTvShows());
            }
        }
    }

    private void setMedia(GenericResponse<ArrayList<Tvs>> tvShows) {
        updateData(tvShows);
    }

    private void updateData(GenericResponse<ArrayList<Tvs>> tvShows) {
        mediaRecyclerView.set(View.VISIBLE);
        errorLabel.set(View.GONE);
        setTvShows(tvShows);
        updateMovieAdapter(tvShows);
    }

    public void getTvShows(String type, String mediaType, int page) {
        AndroidDataBindingApplication dataBindingApplication = AndroidDataBindingApplication.getApplication(mContext);
        Disposable disposable = new TvFetcher().getTvsList(dataBindingApplication.getRetrofit(), mediaType, type, Constants.API_KEY, page)
                .subscribeOn(dataBindingApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getMovieListSuccess(), getMovieListFailure());
        mCompositeDisposable.add(disposable);
    }

    private Consumer<? super Throwable> getMovieListFailure() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                handelGetMoviesErrorCase(throwable);
            }
        };
    }

    private Consumer<? super GenericResponse<ArrayList<Tvs>>> getMovieListSuccess() {
        return new Consumer<GenericResponse<ArrayList<Tvs>>>() {
            @Override
            public void accept(GenericResponse<ArrayList<Tvs>> tvResponse) throws Exception {
                updateTvShowsList(tvResponse);
            }
        };
    }


    private void updateTvShowsList(GenericResponse<ArrayList<Tvs>> tv) {
        if (tv != null && tv.getResults() != null && tv.getResults().size() > 0) {
            upTvShowData(tv);
        }
    }

    private void upTvShowData(GenericResponse<ArrayList<Tvs>> tvShows) {
        mediaRecyclerView.set(View.VISIBLE);
        errorLabel.set(View.GONE);
        setTvShows(tvShows);
        updateMovieAdapter(tvShows);
    }

    private void setTvShows(GenericResponse<ArrayList<Tvs>> tvShows) {
        this.mMediaCategory.setTv(tvShows);
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

    private void showError() {
        errorLabel.set(View.VISIBLE);
        mediaRecyclerView.set(View.GONE);
        errorMessageLabel.set(mContext.getString(R.string.error_loading_people));
        //RxBus.getInstance().send(mMediaCategory);
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

    private void updateMovieAdapter(GenericResponse<ArrayList<Tvs>> tvShows) {
        if (tvShows != null && tvShows.getResults() != null && tvShows.getResults().size() > 0) {
            mTvAdapter.addAll(mMediaCategory.getTvShows().getResults());
            if (tvShows.hasNextPage()) {
                mTvAdapter.addFooter();
            } else {
                mTvAdapter.removeFooter();
            }
        }
    }

    private void handelScrollPosition() {
        if (mScrollPosition > 0) {
            mTvShowsListLayoutBinding.tvList.scrollToPosition(mScrollPosition);
        }
    }


}
