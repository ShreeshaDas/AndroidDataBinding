package com.android.androiddatabinding.common;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.androiddatabinding.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by Shreesha on 16-07-2017.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int HEADER = 0;
    public static final int MOVIE_TITLE = 1;
    public static final int MOVIE_LIST = 2;
    public static final int TV_TITLE = 3;
    public static final int TV_LIST = 4;
    public static final int PEOPLE_TITLE = 5;
    public static final int PEOPLE_LIST = 6;
    public static final int FOOTER = 7;
    public static final int MOVIE = 8;
    public static final int TV = 9;
    public static final int PEOPLE = 10;

    public enum FooterType {
        LOAD_MORE,
        ERROR
    }

    protected List<T> items;
    protected boolean isFooterAdded = false;

    protected abstract RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent);

    protected abstract RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent, int viewType);

    protected abstract RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent);

    protected abstract void bindHeaderViewHolder(RecyclerView.ViewHolder viewHolder);

    protected abstract void bindItemViewHolder(BaseViewHolder viewHolder, int position);

    protected abstract void bindFooterViewHolder(RecyclerView.ViewHolder viewHolder);

    protected abstract void displayLoadMoreFooter();

    protected abstract void displayErrorFooter();

    public abstract void addFooter();

    public abstract void onViewRecycled(BaseViewHolder viewHolder);

    public BaseAdapter(List<T> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case HEADER:
                viewHolder = createHeaderViewHolder(parent);
                break;
            case MOVIE_TITLE:
            case MOVIE_LIST:
            case TV_TITLE:
            case TV_LIST:
            case PEOPLE_TITLE:
            case PEOPLE_LIST:
            case MOVIE:
            case TV:
            case PEOPLE:
                viewHolder = createItemViewHolder(parent, viewType);
                break;
            case FOOTER:
                viewHolder = createFooterViewHolder(parent);
                break;
            default:
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (getItemViewType(position)) {
            case HEADER:
                bindHeaderViewHolder(viewHolder);
                break;
            case MOVIE_TITLE:
            case MOVIE_LIST:
            case TV_TITLE:
            case TV_LIST:
            case PEOPLE_TITLE:
            case PEOPLE_LIST:
            case MOVIE:
            case TV:
            case PEOPLE:
                bindItemViewHolder((BaseViewHolder) viewHolder, position);
                break;
            case FOOTER:
                bindFooterViewHolder(viewHolder);
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public void add(T item) {
        items.add(item);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<T> items) {
        for (T item : items) {
            add(item);
        }
    }

    private void remove(T item) {
        int position = items.indexOf(item);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isFooterAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public boolean isLastPosition(int position) {
        return (position == items.size() - 1);
    }

    public void removeFooter() {
        isFooterAdded = false;

        int position = items.size() - 1;
        T item = getItem(position);

        if (item != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void updateFooter(FooterType footerType) {
        switch (footerType) {
            case LOAD_MORE:
                displayLoadMoreFooter();
                break;
            case ERROR:
                displayErrorFooter();
                break;
            default:
                break;
        }
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        onViewRecycled((BaseViewHolder) holder);
        super.onViewRecycled(holder);
    }
}
