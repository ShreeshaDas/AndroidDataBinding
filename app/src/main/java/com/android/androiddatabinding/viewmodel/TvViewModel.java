package com.android.androiddatabinding.viewmodel;

import android.content.Context;

import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.TvItemBinding;
import com.android.androiddatabinding.model.Movie;
import com.android.androiddatabinding.model.Tvs;

/**
 * Created by shreesha on 17/7/17.
 */

public class TvViewModel extends BaseViewModel {

    private Tvs mTv;
    private Context mContext;
    private String mPosterPath;
    private String mTitle;

    public TvViewModel(Context context, Tvs item) {
        super();
        this.mContext = context;
        this.mTv = item;
    }

    public String getPosterPath() {
        return mTv.getPosterPath();
    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
    }

    public String getTitle() {
        return mTv.getName();
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

}
