package com.android.androiddatabinding.viewmodel;

import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.model.MediaCategory;

/**
 * Created by shreesha on 12/7/17.
 */

public class MovieTitleViewModel extends BaseViewModel {

    public MediaCategory mMovieCategory;
    public String mTitle;

    public MovieTitleViewModel(MediaCategory mediaCategory) {
        this.mMovieCategory = mediaCategory;
    }

    public String getTitle() {
        return mMovieCategory.getMediaCategory();
    }

}

