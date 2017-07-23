package com.android.androiddatabinding.adapters;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

import com.android.androiddatabinding.BR;
import com.android.androiddatabinding.R;
import com.android.androiddatabinding.common.BaseAdapter;
import com.android.androiddatabinding.common.BaseViewHolder;
import com.android.androiddatabinding.model.Tvs;
import com.android.androiddatabinding.viewmodel.FooterViewModel;
import com.android.androiddatabinding.viewmodel.TvViewModel;

import java.util.ArrayList;

/**
 * Created by shreesha on 14/2/17.
 */

public class TvAdapter extends BaseAdapter<Tvs> {

    private ArrayList<Tvs> mTv;
    private Context mContext;

    public TvAdapter(Context context, ArrayList<Tvs> tv) {
        super(tv);
        this.mTv = tv;
        this.mContext = context;
    }

    @Override
    protected int getHeaderLayoutId() {
        return 0;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.tv_item;
    }

    @Override
    protected int getFooterLayoutId() {
        return 0;
    }

    @Override
    protected Object getHeaderViewModel(int position) {
        return null;
    }

    @Override
    protected Object getItemViewModel(ViewDataBinding viewDataBinding, int viewType, int position) {
        return new TvViewModel(mContext, getItem(position), viewDataBinding);
    }

    @Override
    protected Object getFooterViewModel(ViewDataBinding viewDataBinding, int position) {
        return new FooterViewModel(viewDataBinding);
    }

    @Override
    protected void displayLoadMoreFooter() {

    }

    @Override
    protected void displayErrorFooter() {

    }

    @Override
    protected void displayRetryFooter() {

    }

    @Override
    public void addFooter() {

    }

    @Override
    protected int getViewType(int position) {
        return TV;
    }


    @Override
    protected int getVariableForPosition(int position) {
        return BR.tv;
    }

    @Override
    protected void onVieItemRecycled(BaseViewHolder holder) {

    }
}
