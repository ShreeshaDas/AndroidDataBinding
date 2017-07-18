package com.android.androiddatabinding.adapters;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.android.androiddatabinding.R;
import com.android.androiddatabinding.common.BaseAdapter;
import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.databinding.MovieItemBinding;
import com.android.androiddatabinding.databinding.PeopleItemBinding;
import com.android.androiddatabinding.model.Movie;
import com.android.androiddatabinding.model.PeopleList;
import com.android.androiddatabinding.viewholder.PeopleListViewHolder;
import com.android.androiddatabinding.viewholder.PeopleViewHolder;
import com.android.androiddatabinding.viewmodel.PeopleListViewModel;
import com.android.androiddatabinding.viewmodel.PeopleViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;

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
    protected RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case PEOPLE:
                PeopleItemBinding peopleItemBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.people_item,
                        parent,
                        false);
                return new PeopleViewHolder(peopleItemBinding);
        }
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected void bindHeaderViewHolder(BaseViewHolder viewHolder, int position) {

    }

    @Override
    protected void bindItemViewHolder(BaseViewHolder holder, int position) {
        if (holder instanceof PeopleViewHolder) {
            holder.onBind(holder, new PeopleViewModel(mContext, getItem(position)), position);
        }
    }

    @Override
    protected void bindFooterViewHolder(BaseViewHolder viewHolder, int position) {

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
    public void onViewRecycled(BaseViewHolder viewHolder) {

    }

    @Override
    public int getItemViewType(int position) {
        return PEOPLE;
    }
}
