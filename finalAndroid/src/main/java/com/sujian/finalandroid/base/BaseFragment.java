package com.sujian.finalandroid.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sujian.finalandroid.ui.LoadingPage;
import com.sujian.finalandroid.uitls.ViewUtils;

import org.xutils.x;

import java.lang.reflect.Field;

/**
 * @author Sujian  121116111@QQ.COM
 *         BaseFragment
 *         TODO(fragment的基类)
 *         2016年4月10日 下午6:32:50
 */
public abstract class BaseFragment extends Fragment {

    private LoadingPage loadingPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化布局视图
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView();
        x.view().inject(this, view);
        return view;
    }

    public View initView() {
        if (loadingPage == null) {
            loadingPage = new LoadingPage(getActivity()) {
                @Override
                protected View createSuccessView() {
                    return BaseFragment.this.createSuccessView();
                }

                @Override
                protected LoadResult load() {
                    return BaseFragment.this.load();
                }
            };

        } else {
            ViewUtils.removeParent(loadingPage);
        }

        return loadingPage;
    }


    /**
     * 创建成功的view
     *
     * @return view
     */
    protected abstract View createSuccessView();

    /**
     * 请求服务器
     *
     * @return LoadResult
     */
    protected abstract LoadingPage.LoadResult load();

    /**
     * 显示页面
     */
    public void show() {
        if (loadingPage != null) {
            loadingPage.show();
        }
    }

    /**
     * 视图初始化完成之后调用本方法
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initDatas(view);
    }

    /**
     * 初始化数据
     */
    public void initDatas(View view) {

    }


    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
