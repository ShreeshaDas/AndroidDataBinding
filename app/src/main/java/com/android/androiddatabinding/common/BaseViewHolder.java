package com.android.androiddatabinding.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by shreesha on 11/7/17.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(final RecyclerView.ViewHolder holder, BaseViewModel baseViewModel, int position);

    public abstract void onDetachedFromRecyclerView(RecyclerView recyclerView);

    public abstract void onViewRecycled(RecyclerView.ViewHolder viewHolder);
}
