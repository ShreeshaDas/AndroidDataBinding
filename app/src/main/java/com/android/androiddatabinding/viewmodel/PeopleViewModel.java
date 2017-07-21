package com.android.androiddatabinding.viewmodel;

import android.content.Context;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.PeopleItemBinding;
import com.android.androiddatabinding.model.PeopleList;

/**
 * Created by shreesha on 17/7/17.
 */

public class PeopleViewModel extends BaseViewModel {

    private PeopleList peopleList;
    private Context mContext;
    private String mPosterPath;
    private String mTitle;
    private PeopleItemBinding mPeopleItemBinding;

    public PeopleViewModel(Context context, PeopleList item, ViewDataBinding viewDataBinding) {
        super();
        this.mContext = context;
        this.peopleList = item;
        this.mPeopleItemBinding = (PeopleItemBinding) viewDataBinding;
    }

    @Bindable
    public String getPosterPath() {
        return peopleList.getProfilePath();
    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
    }

    @Bindable
    public String getTitle() {
        return peopleList.getName();
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    @Bindable
    public boolean isClicked() {
        return peopleList.isClicked();
    }

    public void setIsClicked(boolean isClicked) {
        peopleList.setIsClicked(isClicked);
        notifyChange();
    }

    public void onKnowMoreClick(View view) {
        if (isClicked()) {
            setIsClicked(false);
        } else {
            setIsClicked(true);
        }
    }
}
