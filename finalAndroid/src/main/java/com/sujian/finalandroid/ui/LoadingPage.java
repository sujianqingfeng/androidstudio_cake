package com.sujian.finalandroid.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.uitls.ThreadManager;
import com.sujian.finalandroid.uitls.UIUtils;

/**
 * 封装加载页面 把basefragment 部分抽取到本类中 让上层代码简明
 * Created by sujian on 2016/5/15.
 */
public abstract class LoadingPage extends FrameLayout {

    public static final int STATE_UNKOWN = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_SUCCESS = 4;
    public int state = STATE_LOADING;

    private View loadingView;// 加载中的界面
    private View errorView;// 错误界面
    private View emptyView;// 空界面
    private View successView;// 加载成功的界面

    public LoadingPage(Context context) {
        super(context);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 加载不同的页面
     */
    private void init() {
        loadingView = createLoadingView(); // 创建了加载中的界面
        if (loadingView != null) {
            this.addView(loadingView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        errorView = createErrorView(); // 加载错误界面
        if (errorView != null) {
            this.addView(errorView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        emptyView = createEmptyView(); // 加载空的界面
        if (emptyView != null) {
            this.addView(emptyView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        successView = createSuccessView();
        if (successView != null) {
            this.addView(successView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        }
        showPager();
    }


    /**
     * 根据不同的状态显示不同的界面
     */
    private void showPager() {
        if (loadingView != null) {
            loadingView.setVisibility(state == STATE_UNKOWN
                    || state == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        }
        if (errorView != null) {
            errorView.setVisibility(state == STATE_ERROR ? View.VISIBLE
                    : View.INVISIBLE);
        }
        if (emptyView != null) {
            emptyView.setVisibility(state == STATE_EMPTY ? View.VISIBLE
                    : View.INVISIBLE);
        }
        if (successView != null) {
            successView.setVisibility(state == STATE_SUCCESS ? View.VISIBLE
                    : View.INVISIBLE);
        }
    }

    /**
     * 根据服务器的数据 切换状态
     */
    public void show() {
        if (state == STATE_ERROR || state == STATE_EMPTY) {
            state = STATE_LOADING;
        }
        // 请求服务器 获取服务器上数据 进行判断
        // 请求服务器 返回一个结果
        ThreadManager.getInstance().createLongPool().execute(new Runnable() {

            @Override
            public void run() {

                final LoadResult result = load();
                UIUtils.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (result != null) {
                            state = result.getValue();
                            showPager(); // 状态改变了,重新判断当前应该显示哪个界面
                        }
                    }
                });
            }
        });
        showPager();


    }

    public enum LoadResult {
        loading(1), error(2), empty(3), success(4);

        int value;

        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }


    /**
     * 创建了空的界面
     *
     * @return view
     */
    private View createEmptyView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.loadpage_empty, null);
        return view;
    }

    /**
     * 创建了错误界面
     *
     * @return view
     */
    private View createErrorView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.loadpage_error, null);
        Button page_bt = (Button) view.findViewById(R.id.page_bt);
        page_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                show();
            }
        });
        return view;
    }

    /**
     * 创建加载中的界面
     *
     * @return view
     */
    private View createLoadingView() {
        View view = View
                .inflate(UIUtils.getContext(), R.layout.loadpage_loading, null);
        return view;
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
    protected abstract LoadResult load();

}
