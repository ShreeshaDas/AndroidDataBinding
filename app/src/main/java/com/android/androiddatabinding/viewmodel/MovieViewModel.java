package com.android.androiddatabinding.viewmodel;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.android.androiddatabinding.R;
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

    public MovieViewModel(Context context, Movie movie) {
        this.mMovie = movie;
        this.mContext = context;
        getBinding();
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

    private void getBinding() {
        LayoutInflater layoutInflater =
                LayoutInflater.from(mContext);
        View movieListView = layoutInflater.inflate(R.layout.movie_item, null, false);
        mMovieItemBinding = DataBindingUtil.getBinding(movieListView);
    }
}
