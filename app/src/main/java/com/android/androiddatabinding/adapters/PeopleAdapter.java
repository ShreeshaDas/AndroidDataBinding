package com.android.androiddatabinding.adapters;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.android.androiddatabinding.R;
import com.android.androiddatabinding.common.BaseAdapter;
import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.model.PeopleList;
import com.android.androiddatabinding.viewmodel.PeopleViewModel;
import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;

/**
 * Created by shreesha on 14/2/17.
 */

public class PeopleAdapter extends BaseAdapter<PeopleList> {

    private ArrayList<PeopleList> peopleList;
    private Context mContext;

    public PeopleAdapter(Context context, ArrayList<PeopleList> peopleList) {
        super(peopleList);
        this.peopleList = peopleList;
        this.mContext = context;
    }

    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return  R.layout.people_item;
    }

    @Override
    protected int getFooterLayoutId() {
        return 0;
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
        return null;
    }

    @Override
    protected void displayLoadMoreFooter() {

    }

    @Override
    protected void displayErrorFooter() {

    }

    @Override
    public void addFooter() {

    }

    @Override
    protected int getViewType(int position) {
        return PEOPLE;
    }

    @Override
    protected int getVariableForPosition(int position) {
        return BR.people;
    }
}
