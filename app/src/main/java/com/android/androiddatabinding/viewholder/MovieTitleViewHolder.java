package com.android.androiddatabinding.viewholder;

import android.support.v7.widget.RecyclerView;

import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.MovieTitleLayoutBinding;
import com.android.androiddatabinding.model.MovieCategory;
import com.android.androiddatabinding.viewmodel.MovieTitleViewModel;

import java.util.ArrayList;

/**
 * Created by shreesha on 10/7/17.
 */

public class MovieTitleViewHolder extends BaseViewHolder {

    private MovieTitleLayoutBinding mMovieTitleLayoutBinding;

    public MovieTitleViewHolder(MovieTitleLayoutBinding movieTitleLayoutBinding) {
        super(movieTitleLayoutBinding.getRoot());
        this.mMovieTitleLayoutBinding = movieTitleLayoutBinding;
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, BaseViewModel baseViewModel, int position) {
        mMovieTitleLayoutBinding.setMovietitle((MovieTitleViewModel) baseViewModel);
        mMovieTitleLayoutBinding.executePendingBindings();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {

    }
}
