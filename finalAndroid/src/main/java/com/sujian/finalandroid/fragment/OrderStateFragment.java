package com.sujian.finalandroid.fragment;

import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.ui.LoadingPage;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单状态页面
 */
public class OrderStateFragment extends BaseFragment {

    // 刷新控件
    @ViewInject(R.id.ptrlv_orderstate)
    private PullToRefreshListView mPullRefreshListView;


    private ListView lv_orderStatelistview;

    // 装载数据集合
    List<Map<String, Object>> dataLists;
    Map<String, Object> map;

    private OrderStateListViewAdapter orderStateListViewAdapter;

    @Override
    protected View createSuccessView() {
        View view = View.inflate(getActivity(), R.layout.fragment_order_state, null);
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
        initPullToRefresh();
    }


    /**
     * 初始化 刷新控件
     */
    private void initPullToRefresh() {
        // 设置刷新的模式
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                refreshEvent(refreshView);
            }
        });


        //得到listview视图
        lv_orderStatelistview = mPullRefreshListView.getRefreshableView();

        dataLists = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            map = new HashMap<String, Object>();
            map.put("title", "标题" + i);
            map.put("time", "2016-6-6 10:1" + i);
            map.put("content", "内容测试内容测试内容测试内容测试内容测试" + i);
            dataLists.add(map);
        }
        orderStateListViewAdapter = new OrderStateListViewAdapter();
        lv_orderStatelistview.setAdapter(orderStateListViewAdapter);
        lv_orderStatelistview.setCacheColorHint(ContextCompat.getColor(getActivity(), R.color.transparent));
        lv_orderStatelistview.setSelector(R.color.transparent);
        lv_orderStatelistview.setVerticalScrollBarEnabled(true);
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
        // new GetDataTask().execute();
    }


    /**
     * 商品列表的适配器
     *
     * @author 12111
     */
    class OrderStateListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataLists.size();
        }

        @Override
        public Object getItem(int position) {
            return dataLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(getActivity(), R.layout.order_state_listview_item, null);
                x.view().inject(viewHolder, convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Map<String, Object> map2 = dataLists.get(position);

            viewHolder.title.setText((String) map2.get("title"));
            viewHolder.time.setText((String) map2.get("time"));
            viewHolder.content.setText((String) map2.get("content"));

            if (position == getCount() - 1) {
                viewHolder.v.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }

        class ViewHolder {
            @ViewInject(R.id.tv_order_state_item_title)
            private TextView title;
            @ViewInject(R.id.tv_order_state_item_time)
            private TextView time;
            @ViewInject(R.id.tv_order_state_item_content)
            private TextView content;
            @ViewInject(R.id.v_order_state_line)
            private View v;
        }
    }
}
