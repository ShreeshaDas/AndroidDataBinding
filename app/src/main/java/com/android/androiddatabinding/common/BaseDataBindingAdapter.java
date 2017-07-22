package com.android.androiddatabinding.common;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Shreesha on 16-07-2017.
 */

public abstract class BaseDataBindingAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private OnItemClickListener<T> onItemClickListener;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(
                layoutInflater, viewType, parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder viewHolder, final int position) {
        Object obj = getObjForPosition(viewHolder.getBinding(), position);
        final T item = getItemForPosition(position);
        int variable = getVariableForPosition(position);
        viewHolder.getBinding().getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, item);
                }
            }
        });
        viewHolder.bind(variable, obj);
    }


    @Override
    public int getItemViewType(int position) {
        return getLayoutForViewTpe(position);
    }

    @Override
    public void onViewRecycled(BaseViewHolder holder) {
        onVieItemRecycled(holder);
        super.onViewRecycled(holder);
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected abstract Object getObjForPosition(ViewDataBinding viewDataBinding, int position);

    protected abstract int getVariableForPosition(int position);

    protected abstract int getLayoutForViewTpe(int viewType);

    protected abstract void onVieItemRecycled(BaseViewHolder holder);

    protected abstract T getItemForPosition(int position);

}
