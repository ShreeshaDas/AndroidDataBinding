package com.android.androiddatabinding.viewmodel;

import android.content.Context;

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

    public MovieViewModel(Context context, Movie movie) {
        this.mMovie = movie;
        this.mContext = context;
    }

    public String getPosterPath() {
        return mMovie.getPosterPath();
    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
    }

    public String getTitle() {
        return mMovie.getTitle();
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void updateList(MovieItemBinding movieItemBinding) {
        movieItemBinding.setMovie(this);
        movieItemBinding.executePendingBindings();
    }
}
