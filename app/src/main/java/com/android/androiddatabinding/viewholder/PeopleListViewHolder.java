package com.android.androiddatabinding.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;

import com.android.androiddatabinding.adapters.PeopleAdapter;
import com.android.androiddatabinding.bus.RxBus;
import com.android.androiddatabinding.bus.events.Events;
import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.common.BaseViewModel;
import com.android.androiddatabinding.databinding.PeopleListLayoutBinding;
import com.android.androiddatabinding.model.MediaCategory;
import com.android.androiddatabinding.model.Movie;
import com.android.androiddatabinding.model.PeopleList;
import com.android.androiddatabinding.viewmodel.PeopleListViewModel;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by shreesha on 10/7/17.
 */

public class PeopleListViewHolder extends BaseViewHolder {


    private static final String TAG = PeopleListViewHolder.class.getSimpleName();

    private PeopleAdapter mPeopleAdapter;
    private Context mContext;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private String mMediaCategory;
    private PeopleListLayoutBinding peopleListLayoutBinding;
    private SparseIntArray listPosition = new SparseIntArray();


    public PeopleListViewHolder(PeopleListLayoutBinding movieListLayoutBinding) {
        super(movieListLayoutBinding.getRoot());
        this.mContext = movieListLayoutBinding.getRoot().getContext();
        this.peopleListLayoutBinding = movieListLayoutBinding;
        subscribe();
    }


    private void initAdapter() {
        mPeopleAdapter = new PeopleAdapter(mContext, new ArrayList<PeopleList>());
        peopleListLayoutBinding.movieList.setAdapter(mPeopleAdapter);
        peopleListLayoutBinding.movieList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onBind(RecyclerView.ViewHolder holder, BaseViewModel baseViewModel, int position) {
        PeopleListViewModel peopleListViewModel = (PeopleListViewModel) baseViewModel;
        mMediaCategory = peopleListViewModel.getCategoryTitle();
        peopleListLayoutBinding.setPeoplelist(peopleListViewModel);
        initAdapter();
        handelScrollPosition(position);
        peopleListViewModel.getItemList(mContext, peopleListViewModel);
    }

    private void handelScrollPosition(int position) {
        int lastSeenFirstPosition = listPosition.get(position, 0);
        if (lastSeenFirstPosition >= 0) {
            peopleListLayoutBinding.movieList.scrollToPosition(lastSeenFirstPosition);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        reset();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder viewHolder) {
        final int position = viewHolder.getAdapterPosition();
        if (viewHolder instanceof PeopleListViewHolder) {
            LinearLayoutManager layoutManager = ((LinearLayoutManager) peopleListLayoutBinding.movieList.getLayoutManager());
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
        if (mediaCategory != null && mediaCategory.getPeople() != null && mediaCategory.getPeople().size() > 0) {
            if (mMediaCategory.equals(mediaCategory.getMediaCategory())) {
                mPeopleAdapter.addAll(mediaCategory.getPeople());
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
