package com.android.androiddatabinding.common;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.android.androiddatabinding.BR;


/**
 * Created by shreesha on 11/7/17.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final ViewDataBinding binding;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object obj) {
        binding.setVariable(BR.obj, obj);
        binding.executePendingBindings();
    }


}
