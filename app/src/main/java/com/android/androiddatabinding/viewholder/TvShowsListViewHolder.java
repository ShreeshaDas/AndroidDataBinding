package com.android.androiddatabinding.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;

import com.android.androiddatabinding.adapters.TvAdapter;
import com.android.androiddatabinding.bus.RxBus;
import com.android.androiddatabinding.bus.events.Events;
import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.TvShowsListLayoutBinding;
import com.android.androiddatabinding.model.MediaCategory;
import com.android.androiddatabinding.model.Tvs;
import com.android.androiddatabinding.viewmodel.TvShowsViewModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by shreesha on 10/7/17.
 */

public class TvShowsListViewHolder extends BaseViewHolder {


    private static final String TAG = TvShowsListViewHolder.class.getSimpleName();

    private TvAdapter mTvAdapter;
    private Context mContext;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private String mMediaCategory;
    private TvShowsListLayoutBinding mTvShowsListLayoutBinding;
    private SparseIntArray listPosition = new SparseIntArray();


    public TvShowsListViewHolder(TvShowsListLayoutBinding tvShowsListLayoutBinding) {
        super(tvShowsListLayoutBinding.getRoot());
        this.mContext = tvShowsListLayoutBinding.getRoot().getContext();
        this.mTvShowsListLayoutBinding = tvShowsListLayoutBinding;
        subscribe();
    }


    private void initAdapter() {
        mTvAdapter = new TvAdapter(mContext, new ArrayList<Tvs>());
        mTvShowsListLayoutBinding.movieList.setAdapter(mTvAdapter);
        mTvShowsListLayoutBinding.movieList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, BaseViewModel baseViewModel, int position) {
        TvShowsViewModel tvShowsViewModel = (TvShowsViewModel) baseViewModel;
        mMediaCategory = tvShowsViewModel.getCategoryTitle();
        mTvShowsListLayoutBinding.setTvShowsList(tvShowsViewModel);
        initAdapter();
        handelScrollPosition(position);
        tvShowsViewModel.getItemList(mContext, (TvShowsViewModel) baseViewModel);
    }

    private void handelScrollPosition(int position) {
        int lastSeenFirstPosition = listPosition.get(position, 0);
        if (lastSeenFirstPosition >= 0) {
            mTvShowsListLayoutBinding.movieList.scrollToPosition(lastSeenFirstPosition);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        reset();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        final int position = viewHolder.getAdapterPosition();
        if (viewHolder instanceof TvShowsListViewHolder) {
            LinearLayoutManager layoutManager = ((LinearLayoutManager) mTvShowsListLayoutBinding.movieList.getLayoutManager());
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
        if (mediaCategory != null && mediaCategory.getTvShows() != null && mediaCategory.getTvShows().getResults().size() > 0) {
            if (mMediaCategory.equals(mediaCategory.getMediaCategory())) {
                mTvAdapter.addAll(mediaCategory.getTvShows().getResults());
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
