package com.android.androiddatabinding.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;

import com.android.androiddatabinding.adapters.MoviesAdapter;
import com.android.androiddatabinding.bus.RxBus;
import com.android.androiddatabinding.bus.events.Events;
import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.MovieListLayoutBinding;
import com.android.androiddatabinding.model.MediaCategory;
import com.android.androiddatabinding.model.Movie;
import com.android.androiddatabinding.viewmodel.MovieListViewModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by shreesha on 10/7/17.
 */

public class MovieListViewHolder extends BaseViewHolder {


    private static final String TAG = MovieListViewHolder.class.getSimpleName();

    private MoviesAdapter mMoviesAdapter;
    private Context mContext;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private String mMediaCategory;
    private MovieListLayoutBinding mMovieListLayoutBinding;
    private SparseIntArray listPosition = new SparseIntArray();


    public MovieListViewHolder(MovieListLayoutBinding movieListLayoutBinding) {
        super(movieListLayoutBinding.getRoot());
        this.mContext = movieListLayoutBinding.getRoot().getContext();
        this.mMovieListLayoutBinding = movieListLayoutBinding;
        subscribe();
    }


    private void initAdapter() {
        mMoviesAdapter = new MoviesAdapter(mContext, new ArrayList<Movie>());
        mMovieListLayoutBinding.movieList.setAdapter(mMoviesAdapter);
        mMovieListLayoutBinding.movieList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, BaseViewModel baseViewModel, int position) {
        MovieListViewModel movieListViewModel = (MovieListViewModel) baseViewModel;
        mMediaCategory = movieListViewModel.getCategoryTitle();
        mMovieListLayoutBinding.setMovielist(movieListViewModel);
        initAdapter();
        handelScrollPosition(position);
        movieListViewModel.getItemList(mContext, (MovieListViewModel) baseViewModel);
    }

    private void handelScrollPosition(int position) {
        int lastSeenFirstPosition = listPosition.get(position, 0);
        if (lastSeenFirstPosition >= 0) {
            mMovieListLayoutBinding.movieList.scrollToPosition(lastSeenFirstPosition);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        reset();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        final int position = viewHolder.getAdapterPosition();
        if (viewHolder instanceof MovieListViewHolder) {
            LinearLayoutManager layoutManager = ((LinearLayoutManager) mMovieListLayoutBinding.movieList.getLayoutManager());
            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            listPosition.put(position, firstVisiblePosition);
        }
    }

    public void subscribe() {
        Disposable disposable = RxBus.getInstance().getBus()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        if (o instanceof MediaCategory) {
                            updateMovieAdapter((MediaCategory) o);
                        } else if (o instanceof Events) {
                            Events busEvent = (Events) o;
                            if (busEvent == Events.RECYCLER_VIEW_DETACHED) {
                                reset();
                            }
                        }
                    }
                });
        mCompositeDisposable.add(disposable);
    }

    private void updateMovieAdapter(MediaCategory mediaCategory) {
        if (mediaCategory != null && mediaCategory.getMovies() != null && mediaCategory.getMovies().size() > 0) {
            if (mMediaCategory.equals(mediaCategory.getMediaCategory())) {
                mMoviesAdapter.addAll(mediaCategory.getMovies());
            }
        }
    }

    private void unSubscribeFromObservable() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        mCompositeDisposable = null;
    }

}
