package com.android.androiddatabinding.viewholder;

import android.support.v7.widget.RecyclerView;

import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.MovieItemBinding;
import com.android.androiddatabinding.model.Movie;
import com.android.androiddatabinding.viewmodel.MovieViewModel;

/**
 * Created by shreesha on 17/7/17.
 */

public class MovieViewHolder extends BaseViewHolder {

    MovieItemBinding movieItemBinding;

    public MovieViewHolder(MovieItemBinding movieItemBinding) {
        super(movieItemBinding.getRoot());
        this.movieItemBinding = movieItemBinding;
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, BaseViewModel baseViewModel, int position) {
        MovieViewModel movieViewModel = (MovieViewModel) baseViewModel;
        movieViewModel.updateList(movieItemBinding);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {

    }
}