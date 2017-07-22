package com.android.androiddatabinding.adapters;

import android.content.Context;
import android.databinding.ViewDataBinding;

import com.android.androiddatabinding.R;
import com.android.androiddatabinding.common.BaseAdapter;
import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.model.People;
import com.android.androiddatabinding.viewmodel.FooterViewModel;
import com.android.androiddatabinding.viewmodel.PeopleViewModel;
import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;

/**
 * Created by shreesha on 14/2/17.
 */

public class PeopleAdapter extends BaseAdapter<People> {

    private ArrayList<People> people;
    private Context mContext;

    public PeopleAdapter(Context context, ArrayList<People> people) {
        super(people);
        this.people = people;
        this.mContext = context;
    }

    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.people_item;
    }

    @Override
    protected int getFooterLayoutId() {
        return R.layout.footer_item;
    }

    @Override
    protected Object getHeaderViewModel(int position) {
        return null;
    }

    @Override
    protected Object getItemViewModel(ViewDataBinding viewDataBinding, int viewType, int position) {
        return new PeopleViewModel(mContext, getItem(position), viewDataBinding);
    }

    @Override
    protected Object getFooterViewModel(int position) {
        return new FooterViewModel();
    }

    @Override
    protected void displayLoadMoreFooter() {

    }

    @Override
    protected void displayErrorFooter() {

    }

    @Override
    public void addFooter() {
        isFooterAdded = true;
        add(new People());
    }

    @Override
    protected int getViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ? FOOTER : PEOPLE;
    }

    @Override
    protected int getVariableForPosition(int position) {
        return (isLastPosition(position) && isFooterAdded) ? com.android.androiddatabinding.BR.footer : BR.people;
    }

    @Override
    protected void onVieItemRecycled(BaseViewHolder holder) {

    }
}
