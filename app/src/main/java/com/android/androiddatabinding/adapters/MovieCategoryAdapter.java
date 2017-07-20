package com.android.androiddatabinding.adapters;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.android.androiddatabinding.BR;
import com.android.androiddatabinding.R;
import com.android.androiddatabinding.bus.RxBus;
import com.android.androiddatabinding.bus.events.Events;
import com.android.androiddatabinding.common.BaseAdapter;
import com.android.androiddatabinding.model.MediaCategory;
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
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        switch (viewType) {
            case MOVIE_TITLE:
            case TV_TITLE:
            case PEOPLE_TITLE:
                return R.layout.movie_title_layout;
            case MOVIE_LIST:
                return R.layout.movie_list_layout;
            case TV_LIST:
                return R.layout.tv_shows_list_layout;
            case PEOPLE_LIST:
                return R.layout.people_list_layout;
            default:
                break;
        }
        return 0;
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
        switch (viewType) {
            case MOVIE_TITLE:
            case TV_TITLE:
            case PEOPLE_TITLE:
                return new MovieTitleViewModel(getItem(position), viewDataBinding);
            case MOVIE_LIST:
                return new MovieListViewModel(mContext, getItem(position), viewDataBinding);
            case TV_LIST:
                return new TvShowsViewModel(mContext, getItem(position), viewDataBinding);
            case PEOPLE_LIST:
                return new PeopleListViewModel(mContext, getItem(position), viewDataBinding);
        }
        return null;
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
        return getItem(position).getGetViewType();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        RxBus.getInstance().send(Events.RECYCLER_VIEW_DETACHED);
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    protected int getVariableForPosition(int position) {
        switch (getViewType(position)) {
            case HEADER:
                break;
            case MOVIE_TITLE:
            case TV_TITLE:
            case PEOPLE_TITLE:
                return BR.title;
            case TV_LIST:
                return BR.tvshow;
            case MOVIE_LIST:
                return BR.movielist;
            case PEOPLE_LIST:
                return BR.peoplelist;
            case FOOTER:
                return BR.footer;
            default:
                break;
        }
        return 0;
    }
}
