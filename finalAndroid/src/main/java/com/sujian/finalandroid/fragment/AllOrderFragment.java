package com.sujian.finalandroid.fragment;

import android.content.Intent;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sujian.finalandroid.activity.OrderDetailActivity;
import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.adapter.DefauListViewAdapter;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.base.BaseHolder;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.OrderInfoCallbackEntity;
import com.sujian.finalandroid.entity.OrderInfo;
import com.sujian.finalandroid.ui.LoadingPage;
import com.sujian.finalandroid.uitls.MyUitls;
import com.sujian.finalandroid.uitls.ToastUitls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 全部订单
 *
 * @author 12111
 */
public class AllOrderFragment extends BaseFragment {

    // 刷新控件
    @ViewInject(R.id.ptrlv_allorder)
    private PullToRefreshListView mPullRefreshListView;
    //判断是下拉，还是上拉的标记
    private boolean isPullDownRefresh = true;
    //listview
    private ListView lv_allorder;
    //listview 适配器
    private OrderAllAdapter orderAllAdapter;
    // 装载数据集合
    List<OrderInfo> dataLists;


    /**
     * 创建了成功的页面
     *
     * @return view
     */
    public View createSuccessView() {
        View view = View.inflate(getActivity(), R.layout.allorder_fragment, null);
        return view;
    }


    public LoadingPage.LoadResult load() {

        return LoadingPage.LoadResult.success;
    }

    @Override
    public void initDatas(View view) {
        super.initDatas(view);
        //第一个界面 需要手动请求数据展示
        show();
        getDataFromService();
        initPullToRefresh();

    }

    /**
     * 从服务器得到数据
     */

    private void getDataFromService() {
        String url = Constants.SERVICEADDRESS + "order/order_returnAllOrder.cake";
        OkHttpUtils.get()
                .url(url)
                .addParams("user_id", MyUitls.getUid() + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtil.e(response);
                        OrderInfoCallbackEntity o = new Gson().fromJson(response, OrderInfoCallbackEntity.class);
                        LogUtil.e(o.toString());
                        if (o.isSuccess()) {
                            dataLists.addAll(o.getList());
                            orderAllAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }



    /**
     * 初始化 刷新控件
     */
    private void initPullToRefresh() {
        // 设置刷新的模式
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);

        //滑动监听
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            //下拉
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isPullDownRefresh = true;
                refreshEvent(refreshView);
            }

            //上拉
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                isPullDownRefresh = false;
                refreshEvent(refreshView);
            }
        });



        //得到listview视图
        lv_allorder = mPullRefreshListView.getRefreshableView();

        dataLists = new ArrayList<>();

        orderAllAdapter = new OrderAllAdapter(dataLists);
        lv_allorder.setAdapter(orderAllAdapter);

        lv_allorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), OrderDetailActivity.class));
            }
        });
    }


    /**
     * 下拉与上拉的事件处理
     *
     * @param refreshView
     */
    private void refreshEvent(PullToRefreshBase<ListView> refreshView) {
        String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

        // 更新刷新的时间
        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

        //获取数据
        // 这里就偷懒了 模拟刷新
        new Thread(new Runnable() {
            @Override
            public void run() {
                //睡眠两秒
                SystemClock.sleep(2000);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 通知刷新已完成
                        mPullRefreshListView.onRefreshComplete();
                        ToastUitls.show("没有更多的数据了");
                    }
                });

            }
        }).start();
    }


    /**
     * 全部订单的适配器
     */
    class OrderAllAdapter extends DefauListViewAdapter<OrderInfo> {

        private ImageOptions options;

        public OrderAllAdapter(List<OrderInfo> data) {
            super(data);
            options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.ic_launcher)
                    .setFailureDrawableId(R.drawable.ic_launcher).setUseMemCache(true).build();
        }

        @Override
        public BaseHolder getHolder() {
            return new OrderViewHolder();
        }


        class OrderViewHolder extends BaseHolder<OrderInfo> {

            @ViewInject(R.id.iv_order_item_icon)
            private ImageView imageView;
            @ViewInject(R.id.tv_order_item_title)
            private TextView title;
            @ViewInject(R.id.tv_order_item_price)
            private TextView price;
            @ViewInject(R.id.tv_order_item_state)
            private TextView state;
            @ViewInject(R.id.tv_order_item_time)
            private TextView tv_order_item_time;

            @Override
            protected void refreshView() {
                x.image().bind(imageView, Constants.SERVICEADDRESS + data.getPic_url(), options);
                title.setText(data.getCommodity_name());
                price.setText("共" + data.getCommodity_num() + "件商品  ￥" + data.getCommodity_price() * data.getCommodity_num() + "元");
                state.setText(MyUitls.getState(data.getOrder_state()));
                tv_order_item_time.setText(data.getOrder_time());
            }

            @Override
            public View initView() {
                View inflate = View.inflate(getActivity(), R.layout.order_list_item, null);
                x.view().inject(this, inflate);
                return inflate;
            }
        }

    }
}
