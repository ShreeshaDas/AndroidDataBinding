package com.android.androiddatabinding.viewmodel;

import com.android.androiddatabinding.common.BaseViewModel;

/**
 * Created by shreesha on 12/7/17.
 */

public class MovieTitleViewModel extends BaseViewModel {

    public String mMovieTitle;

    public MovieTitleViewModel(String mMovieTitle) {
        this.mMovieTitle = mMovieTitle;
    }


    public String getMovieTitle() {
        return mMovieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.mMovieTitle = movieTitle;
    }

}

