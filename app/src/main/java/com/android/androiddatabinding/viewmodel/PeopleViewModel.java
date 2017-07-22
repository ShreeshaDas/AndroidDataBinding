package com.android.androiddatabinding.viewmodel;

import android.content.Context;
import android.databinding.Bindable;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.android.androiddatabinding.bus.RxBus;
import com.android.androiddatabinding.bus.events.KnowMoreClickEvent;
import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.PeopleItemBinding;
import com.android.androiddatabinding.model.People;
import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by shreesha on 17/7/17.
 */

public class PeopleViewModel extends BaseViewModel {

    private People people;
    private Context mContext;
    private String mPosterPath;
    private String mTitle;
    private PeopleItemBinding mPeopleItemBinding;

    public PeopleViewModel(Context context, People item, ViewDataBinding viewDataBinding) {
        super();
        this.mContext = context;
        this.people = item;
        this.mPeopleItemBinding = (PeopleItemBinding) viewDataBinding;
    }

    @Bindable
    public String getPosterPath() {
        return people.getProfilePath();
    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
    }

    @Bindable
    public String getTitle() {
        return people.getName();
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    @Bindable
    public boolean isClicked() {
        return people.isClicked();
    }

    public void setIsClicked(boolean isClicked) {
        people.setIsClicked(isClicked);
        notifyChange();
        RxBus.getInstance().send(new KnowMoreClickEvent(people));
    }

    public void onKnowMoreClick(View view) {
        if (isClicked()) {
            setIsClicked(false);
        } else {
            setIsClicked(true);
        }
    }
}
