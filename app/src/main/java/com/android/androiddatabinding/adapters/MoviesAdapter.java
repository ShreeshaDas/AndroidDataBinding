package com.android.androiddatabinding.adapters;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.android.androiddatabinding.BR;
import com.android.androiddatabinding.R;
import com.android.androiddatabinding.common.BaseAdapter;
import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.model.Movie;
import com.android.androiddatabinding.viewmodel.FooterViewModel;
import com.android.androiddatabinding.viewmodel.MovieViewModel;

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
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.movie_item;
    }

    @Override
    protected int getFooterLayoutId() {
        return R.layout.footer_item;
    }

    @Override
    protected Object getHeaderViewModel(int position) {
        return null;
    }

    @Override
    protected Object getItemViewModel(ViewDataBinding viewDataBinding, int viewType, int position) {
        return new MovieViewModel(mContext, getItem(position), viewDataBinding);
    }

    @Override
    protected Object getFooterViewModel(int position) {
        return new FooterViewModel();
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
    protected int getViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ? FOOTER : MOVIE;
    }


    @Override
    protected int getVariableForPosition(int position) {
        return BR.movie;
    }

}
