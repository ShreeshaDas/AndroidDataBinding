package com.android.androiddatabinding.viewholder;

import android.support.v7.widget.RecyclerView;

import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.TvItemBinding;
import com.android.androiddatabinding.model.Movie;
import com.android.androiddatabinding.model.Tvs;
import com.android.androiddatabinding.viewmodel.TvViewModel;

/**
 * Created by shreesha on 17/7/17.
 */

public class TvViewHolder extends BaseViewHolder {

    TvItemBinding tvItemBinding;

    public TvViewHolder(TvItemBinding tvItemBinding) {
        super(tvItemBinding.getRoot());
        this.tvItemBinding = tvItemBinding;
    }


    @Override
    public void onBind(RecyclerView.ViewHolder holder, BaseViewModel baseViewModel, int position) {
        TvViewModel tvViewModel = (TvViewModel) baseViewModel;
        tvViewModel.updateList(tvItemBinding);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {

    }
}