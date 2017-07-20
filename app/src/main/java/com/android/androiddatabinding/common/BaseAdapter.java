package com.android.androiddatabinding.common;

import android.databinding.ViewDataBinding;

import java.util.List;

/**
 * Created by Shreesha on 19-07-2017.
 */

public abstract class BaseAdapter<T> extends BaseDataBindingAdapter {


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

    public BaseAdapter(List<T> items) {
        this.items = items;
    }

    protected abstract int getHeaderLayoutId();

    protected abstract int getItemLayoutId(int viewType);

    protected abstract int getFooterLayoutId();

    protected abstract Object getHeaderViewModel(int position);

    protected abstract Object getItemViewModel(ViewDataBinding viewDataBinding, int viewType, int position);

    protected abstract Object getFooterViewModel(int position);

    protected abstract void displayLoadMoreFooter();

    protected abstract void displayErrorFooter();

    public abstract void addFooter();

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
    protected int getLayoutForViewTpe(int position) {
        switch (getViewType(position)) {
            case HEADER:
                return getHeaderLayoutId();
            case MOVIE_TITLE:
            case MOVIE_LIST:
            case TV_TITLE:
            case TV_LIST:
            case PEOPLE_TITLE:
            case PEOPLE_LIST:
            case MOVIE:
            case TV:
            case PEOPLE:
                return getItemLayoutId(getViewType(position));
            case FOOTER:
                return getFooterLayoutId();
            default:
                break;
        }
        return 0;
    }

    @Override
    protected Object getObjForPosition(ViewDataBinding viewDataBinding, int position) {
        switch (getViewType(position)) {
            case HEADER:
                return getHeaderViewModel(position);
            case MOVIE_TITLE:
            case MOVIE_LIST:
            case TV_TITLE:
            case TV_LIST:
            case PEOPLE_TITLE:
            case PEOPLE_LIST:
            case MOVIE:
            case TV:
            case PEOPLE:
                return getItemViewModel(viewDataBinding, getViewType(position), position);
            case FOOTER:
                return getFooterViewModel(position);
            default:
                break;
        }
        return null;
    }

    protected abstract int getViewType(int position);

}
