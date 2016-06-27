package com.sujian.finalandroid.fragment;

import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.ui.LoadingPage;

import android.view.View;

/**
 * 待付款
 *
 * @author 12111
 */
public class PendingPayFragment extends BaseFragment {


    @Override
    protected View createSuccessView() {
        View view = View.inflate(getActivity(), R.layout.pendingpayment_fragment, null);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.empty;
    }

}
