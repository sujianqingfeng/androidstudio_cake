package com.sujian.finalandroid.fragment;


import android.view.View;

import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.ui.LoadingPage;

import org.xutils.x;

/**
 * 系统消息fragment
 */
public class SystemNewFragment extends BaseFragment {


    @Override
    protected View createSuccessView() {
        View view = View.inflate(x.app(), R.layout.fragment_system_new, null);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.empty;
    }
}
