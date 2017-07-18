package com.android.androiddatabinding.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.androiddatabinding.R;
import com.android.androiddatabinding.bus.RxBus;
import com.android.androiddatabinding.bus.events.Events;
import com.android.androiddatabinding.common.BaseAdapter;
import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.databinding.MovieListLayoutBinding;
import com.android.androiddatabinding.databinding.MovieTitleLayoutBinding;
import com.android.androiddatabinding.databinding.PeopleListLayoutBinding;
import com.android.androiddatabinding.databinding.TvShowsListLayoutBinding;
import com.android.androiddatabinding.model.MediaCategory;
import com.android.androiddatabinding.viewholder.MovieListViewHolder;
import com.android.androiddatabinding.viewholder.MovieTitleViewHolder;
import com.android.androiddatabinding.viewholder.PeopleListViewHolder;
import com.android.androiddatabinding.viewholder.TvShowsListViewHolder;
import com.android.androiddatabinding.viewmodel.MovieListViewModel;
import com.android.androiddatabinding.viewmodel.MovieTitleViewModel;
import com.android.androiddatabinding.viewmodel.PeopleListViewModel;
import com.android.androiddatabinding.viewmodel.TvShowsViewModel;

import java.util.List;

/**
 * Created by shreesha on 10/7/17.
 */

//https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4
//https://medium.com/@etiennelawlor/pagination-with-recyclerview-1cb7e66a502b

public class MovieCategoryAdapter extends BaseAdapter<MediaCategory> {

    private Context mContext;

    public MovieCategoryAdapter(Context context, List<MediaCategory> items) {
        super(items);
        this.mContext = context;
    }

    @Override
    protected RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MOVIE_TITLE:
            case TV_TITLE:
            case PEOPLE_TITLE:
                MovieTitleLayoutBinding movieTitleLayoutBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.movie_title_layout,
                        parent,
                        false);
                return new MovieTitleViewHolder(movieTitleLayoutBinding);
            case MOVIE_LIST:
                MovieListLayoutBinding movieListLayoutBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.movie_list_layout,
                        parent,
                        false);
                return new MovieListViewHolder(movieListLayoutBinding);
            case TV_LIST:
                TvShowsListLayoutBinding tvShowsListLayoutBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.tv_shows_list_layout,
                        parent,
                        false);
                return new TvShowsListViewHolder(tvShowsListLayoutBinding);
            case PEOPLE_LIST:
                PeopleListLayoutBinding peopleListLayoutBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.people_list_layout,
                        parent,
                        false);
                return new PeopleListViewHolder(peopleListLayoutBinding);
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
    protected void bindItemViewHolder(BaseViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case MOVIE_TITLE:
            case TV_TITLE:
            case PEOPLE_TITLE:
                viewHolder.onBind(viewHolder, new MovieTitleViewModel(getItem(position).getMediaCategory()), position);
                break;
            case MOVIE_LIST:
                viewHolder.onBind(viewHolder, new MovieListViewModel(mContext, getItem(position)), position);
                break;
            case TV_LIST:
                viewHolder.onBind(viewHolder, new TvShowsViewModel(mContext, getItem(position)), position);
                break;
            case PEOPLE_LIST:
                viewHolder.onBind(viewHolder, new PeopleListViewModel(mContext, getItem(position)), position);
                break;
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
    public int getItemViewType(int position) {
        return getItem(position).getGetViewType();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        RxBus.getInstance().send(Events.RECYCLER_VIEW_DETACHED);
        super.onDetachedFromRecyclerView(recyclerView);
    }


    @Override
    public void onViewRecycled(BaseViewHolder viewHolder) {
        viewHolder.onViewRecycled(viewHolder);
    }
}
