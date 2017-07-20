package com.android.androiddatabinding.viewmodel;

import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.util.Log;

import com.android.androiddatabinding.BR;
import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.MovieTitleLayoutBinding;
import com.android.androiddatabinding.model.MediaCategory;

/**
 * Created by shreesha on 12/7/17.
 */

public class MovieTitleViewModel extends BaseViewModel {

    private static final String TAG = MovieTitleViewModel.class.getSimpleName();
    private MediaCategory mMovieCategory;
    public String mTitle;
    private MovieTitleLayoutBinding mMovieTitleLayoutBinding;

    public MovieTitleViewModel(MediaCategory mediaCategory, ViewDataBinding viewDataBinding) {
        super();
        this.mMovieCategory = mediaCategory;
        this.mMovieTitleLayoutBinding = (MovieTitleLayoutBinding) viewDataBinding;
        initBindingData();
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }


    @Bindable
    public String getTitle() {
        return mMovieCategory.getMediaCategory();
    }

    public void initBindingData() {
        setTitle(mMovieCategory.getMediaCategory());
    }
}

