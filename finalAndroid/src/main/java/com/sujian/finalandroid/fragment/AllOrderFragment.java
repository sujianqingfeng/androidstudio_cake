package com.sujian.finalandroid.fragment;

import android.content.Intent;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sujian.finalandroid.activity.OrderDetailActivity;
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

    private ListView lv_allorder;

    private AllOrderListViewAdapter allOrderListViewAdapter;
    // 装载数据集合
    List<Map<String, Object>> dataLists;
    Map<String, Object> map;

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
        initPullToRefresh();

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

        //滑动到底部的监听
        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();
            }
        });

        //得到listview视图
        lv_allorder = mPullRefreshListView.getRefreshableView();

        dataLists = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            map = new HashMap<String, Object>();
            map.put("picurl", "http://img0.imgtn.bdimg.com/it/u=2918690550,3711054886&fm=21&gp=0.jpg");
            map.put("title", "标题" + i);
            map.put("price", "价格" + i);
            map.put("num", i);
            map.put("state", "状态" + i);
            dataLists.add(map);
        }
        allOrderListViewAdapter = new AllOrderListViewAdapter();
        lv_allorder.setAdapter(allOrderListViewAdapter);

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
        // new GetDataTask().execute();
    }

    /**
     * 全部订单的适配器
     *
     * @author 12111
     */
    class AllOrderListViewAdapter extends BaseAdapter {

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
                convertView = View.inflate(getActivity(), R.layout.order_list_item, null);
                x.view().inject(viewHolder, convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Map<String, Object> map2 = dataLists.get(position);
            ImageOptions options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.ic_launcher)
                    .setFailureDrawableId(R.drawable.ic_launcher).setUseMemCache(true).build();
            x.image().bind(viewHolder.imageView, (String) map2.get("picurl"), options);
            viewHolder.title.setText((String) map2.get("title"));
            viewHolder.Price.setText((Integer) map2.get("num") + "件" + (String) map2.get("price"));
            viewHolder.state.setText((String) map2.get("state"));
            return convertView;
        }

        class ViewHolder {
            @ViewInject(R.id.iv_order_item_icon)
            private ImageView imageView;
            @ViewInject(R.id.tv_order_item_title)
            private TextView title;
            @ViewInject(R.id.tv_order_item_price)
            private TextView Price;
            @ViewInject(R.id.tv_order_item_state)
            private TextView state;
        }
    }
}
