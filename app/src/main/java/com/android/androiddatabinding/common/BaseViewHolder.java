package com.android.androiddatabinding.common;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;


/**
 * Created by shreesha on 11/7/17.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final ViewDataBinding binding;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(int variable, Object obj) {
        binding.setVariable(variable, obj);
        binding.executePendingBindings();
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

}
