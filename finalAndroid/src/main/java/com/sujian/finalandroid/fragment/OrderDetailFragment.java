package com.sujian.finalandroid.fragment;

import android.view.View;

import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.ui.LoadingPage;

/**
 * 订单详情页面
 */
public class OrderDetailFragment extends BaseFragment {

    @Override
    protected View createSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_order_detail, null);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void initDatas(View view) {
        super.initDatas(view);
        show();
    }
}
