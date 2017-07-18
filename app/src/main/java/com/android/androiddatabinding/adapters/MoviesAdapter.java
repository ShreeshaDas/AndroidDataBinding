package com.android.androiddatabinding.adapters;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.androiddatabinding.R;
import com.android.androiddatabinding.common.BaseAdapter;
import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.databinding.MovieItemBinding;
import com.android.androiddatabinding.model.Movie;
import com.android.androiddatabinding.viewholder.FooterViewHolder;
import com.android.androiddatabinding.viewholder.MovieViewHolder;
import com.android.androiddatabinding.viewmodel.MovieViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

/**
 * Created by shreesha on 14/2/17.
 */

public class MoviesAdapter extends BaseAdapter<Movie> {

    private ArrayList<Movie> mMovies;
    private Context mContext;

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        super(movies);
        this.mMovies = movies;
        this.mContext = context;
    }

    @Override
    protected RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MOVIE:
                MovieItemBinding movieItemBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.movie_item,
                        parent,
                        false);
                return new MovieViewHolder(movieItemBinding);
        }
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_item, parent, false);
        return new FooterViewHolder(v);
    }

    @Override
    protected void bindHeaderViewHolder(BaseViewHolder viewHolder, int position) {

    }

    @Override
    protected void bindItemViewHolder(BaseViewHolder viewHolder, int position) {
        if (viewHolder instanceof MovieViewHolder) {
            viewHolder.onBind(viewHolder, new MovieViewModel(mContext, getItem(position)), position);
        }
    }

    @Override
    protected void bindFooterViewHolder(BaseViewHolder viewHolder, int position) {
        viewHolder.onBind(viewHolder, null, position);
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
        add(new Movie());
    }

    @Override
    public void onViewRecycled(BaseViewHolder viewHolder) {

    }

    @Override
    public int getItemViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ? FOOTER : MOVIE;
    }
}
