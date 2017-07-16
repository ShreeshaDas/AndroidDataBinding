package com.android.androiddatabinding.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.android.androiddatabinding.AndroidDataBindingApplication;
import com.android.androiddatabinding.R;
import com.android.androiddatabinding.adapters.MovieCategoryAdapter;
import com.android.androiddatabinding.bus.RxBus;
import com.android.androiddatabinding.bus.events.Events;
import com.android.androiddatabinding.common.BaseAdapter;
import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.data.fetcher.MoviesFetcher;
import com.android.androiddatabinding.internal.Constants;
import com.android.androiddatabinding.model.Movie;
import com.android.androiddatabinding.model.MovieCategory;
import com.android.androiddatabinding.model.MoviesResponse;
import com.android.androiddatabinding.model.NetworkError;

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

public class MovieListViewModel extends BaseViewModel {

    private static final String TAG = MovieListViewModel.class.getSimpleName();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MovieCategory mMovieCategory;
    private Context mContext;
    public ObservableField<String> errorMessageLabel;
    public ObservableInt errorLabel;
    public ObservableInt mediaRecyclerView;

    public MovieListViewModel(Context context, MovieCategory movieCategory) {
        this.mMovieCategory = movieCategory;
        errorMessageLabel = new ObservableField<>(context.getString(R.string.default_error_message));
        errorLabel = new ObservableInt(View.GONE);
        mediaRecyclerView = new ObservableInt(View.VISIBLE);
        mContext = context;
        initView();
        subscribe();
    }

    private void initView() {
        mediaRecyclerView.set(View.VISIBLE);
        errorLabel.set(View.GONE);
    }

    public String getCategoryTitle() {
        return mMovieCategory.getMediaCategory();
    }

    public ArrayList<Movie> getMovies() {
        return mMovieCategory.getMovies();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.mMovieCategory.setMovies(movies);
    }

    public String getMediaType() {
        return mMovieCategory.getMediaType();
    }

    public String getQueryType() {
        return mMovieCategory.getQueryType();
    }


    public void getItemList(Context context, MovieListViewModel movieListViewModel) {
        if (movieListViewModel.getMovies() != null && movieListViewModel.getMovies().size() == 0) {
            if (mMovieCategory.getNetworkError() != null) {
                showError();
            } else {
                Log.d(TAG, movieListViewModel.getCategoryTitle() + " " + movieListViewModel.getQueryType());
                getMovies(context, movieListViewModel.getQueryType(), movieListViewModel.getMediaType());
            }
        } else {
            setMedia(movieListViewModel.getMovies());
        }
    }

    private void setMedia(ArrayList<Movie> movies) {
        updateData(movies);
    }


    private void getMovies(Context context, String type, String mediaType) {
        AndroidDataBindingApplication dataBindingApplication = AndroidDataBindingApplication.getApplication(context);
        Disposable disposable = new MoviesFetcher().getMoviesList(dataBindingApplication.getRetrofit(), mediaType, type, Constants.API_KEY, 1)
                .subscribeOn(dataBindingApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MoviesResponse>() {
                    @Override
                    public void accept(MoviesResponse moviesResponse) throws Exception {
                        updateMovieList(moviesResponse);
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
        mMovieCategory.setNetworkError(error);
        showError();
    }

    private void updateMovieList(MoviesResponse moviesResponse) {
        if (moviesResponse != null && moviesResponse.getResults() != null && moviesResponse.getResults().size() > 0) {
            updateData(moviesResponse.getResults());
        }
    }

    private void showError() {
        errorLabel.set(View.VISIBLE);
        mediaRecyclerView.set(View.GONE);
        errorMessageLabel.set(mContext.getString(R.string.error_loading_people));
        //RxBus.getInstance().send(mMovieCategory);
    }

    private void updateData(ArrayList<Movie> movies) {
        mediaRecyclerView.set(View.VISIBLE);
        errorLabel.set(View.GONE);
        setMovies(movies);
        RxBus.getInstance().send(mMovieCategory);
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
}