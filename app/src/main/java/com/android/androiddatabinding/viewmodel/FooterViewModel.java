package com.android.androiddatabinding.viewmodel;

import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;

import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.FooterItemBinding;

/**
 * Created by Shreesha on 19-07-2017.
 */

public class FooterViewModel extends BaseViewModel {

    private FooterItemBinding mFooterItemBinding;
    public ObservableInt rotate;

    public FooterViewModel(ViewDataBinding viewDataBinding) {
        this.mFooterItemBinding = (FooterItemBinding) viewDataBinding;
    }


    public void displayLoadMoreFooter() {

    }

    public void displayErrorFooter() {

    }

    public void displayRetryFooter() {

    }
}
