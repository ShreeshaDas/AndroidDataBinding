package com.android.androiddatabinding.viewholder;

import android.support.v7.widget.RecyclerView;

import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.PeopleItemBinding;
import com.android.androiddatabinding.model.PeopleList;
import com.android.androiddatabinding.viewmodel.PeopleViewModel;

/**
 * Created by shreesha on 17/7/17.
 */

public class PeopleViewHolder extends BaseViewHolder {

    PeopleItemBinding peopleItemBinding;

    public PeopleViewHolder(PeopleItemBinding movieItemBinding) {
        super(movieItemBinding.getRoot());
        this.peopleItemBinding = movieItemBinding;
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, BaseViewModel baseViewModel, int position) {
        PeopleViewModel peopleViewModel = (PeopleViewModel) baseViewModel;
        peopleViewModel.updateList(peopleItemBinding);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {

    }
}
