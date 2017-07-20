package com.android.androiddatabinding.viewmodel;

import android.content.Context;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;

import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.MovieItemBinding;
import com.android.androiddatabinding.model.Movie;

/**
 * Created by shreesha on 17/7/17.
 */

public class MovieViewModel extends BaseViewModel {

    private Movie mMovie;
    private Context mContext;
    private String mPosterPath;
    private String mTitle;
    private MovieItemBinding mMovieItemBinding;

    public MovieViewModel(Context context, Movie movie, ViewDataBinding viewDataBinding) {
        this.mMovie = movie;
        this.mContext = context;
        this.mMovieItemBinding = (MovieItemBinding) viewDataBinding;
    }

    @Bindable
    public String getPosterPath() {
        return mMovie.getPosterPath();
    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
    }

    @Bindable
    public String getTitle() {
        return mMovie.getTitle();
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }
}
