package com.android.androiddatabinding.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.androiddatabinding.R;
import com.android.androiddatabinding.common.BaseAdapter;
import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.databinding.TvItemBinding;
import com.android.androiddatabinding.model.Tvs;
import com.android.androiddatabinding.viewholder.TvViewHolder;
import com.android.androiddatabinding.viewmodel.TvViewModel;

import java.util.ArrayList;

/**
 * Created by shreesha on 14/2/17.
 */

public class TvAdapter extends BaseAdapter<Tvs> {

    private ArrayList<Tvs> mTv;
    private Context mContext;

    public TvAdapter(Context context, ArrayList<Tvs> tv) {
        super(tv);
        this.mTv = tv;
        this.mContext = context;
    }

    @Override
    protected RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TV:
                TvItemBinding tvItemBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.tv_item,
                        parent,
                        false);
                return new TvViewHolder(tvItemBinding);
        }
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected void bindHeaderViewHolder(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    protected void bindItemViewHolder(BaseViewHolder viewHolder, int position) {
        if (viewHolder instanceof TvViewHolder) {
            viewHolder.onBind(viewHolder, new TvViewModel(mContext, getItem(position)), position);
        }
    }

    @Override
    protected void bindFooterViewHolder(RecyclerView.ViewHolder viewHolder) {

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
        return TV;
    }
}
