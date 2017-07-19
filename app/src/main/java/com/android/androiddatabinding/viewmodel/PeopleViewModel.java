package com.android.androiddatabinding.viewmodel;

import android.content.Context;

import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.PeopleItemBinding;
import com.android.androiddatabinding.databinding.TvItemBinding;
import com.android.androiddatabinding.model.PeopleList;
import com.android.androiddatabinding.model.Tvs;

/**
 * Created by shreesha on 17/7/17.
 */

public class PeopleViewModel extends BaseViewModel {

    private PeopleList peopleList;
    private Context mContext;
    private String mPosterPath;
    private String mTitle;

    public PeopleViewModel(Context context, PeopleList item) {
        super();
        this.mContext = context;
        this.peopleList = item;
    }

    public String getPosterPath() {
        return peopleList.getProfilePath();
    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
    }

    public String getTitle() {
        return peopleList.getName();
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }
}
