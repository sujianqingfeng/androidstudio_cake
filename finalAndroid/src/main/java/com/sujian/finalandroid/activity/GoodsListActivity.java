package com.sujian.finalandroid.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.x;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.ui.TitleBuilder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 商品列表
 *
 * @author 12111
 */
@ContentView(R.layout.activity_goods_list)
public class GoodsListActivity extends BaseActivity {

    @ViewInject(R.id.ptrlv_goodsPullToRefreshListView)
    private PullToRefreshListView mPullRefreshListView;

    private ListView lv_goodslistview;

    //装载数据集合
    List<Map<String, Object>> dataLists;
    Map<String, Object> map;

    private GoodsListViewAdapter goodsListViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFormServer();
    }

    @Override
    protected void initData() {
        initTitle();
        initGoodsListView();
    }

    /**
     * 从服务器得到数据
     */
    private void getDataFormServer() {
        //从上一界面得到商品数据
        String goods = getIntent().getStringExtra("goods");
        Toast.makeText(mContext, goods, Toast.LENGTH_SHORT).show();
        //请求服务器数据
    }

    /**
     * 初始化listview
     */
    private void initGoodsListView() {
        //设置刷新的模式
        mPullRefreshListView.setMode(Mode.PULL_FROM_END);
        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(GoodsListActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                // Do work to refresh the list here.
                new GetDataTask().execute();
            }
        });
        // Add an end-of-list listener
        mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                Toast.makeText(GoodsListActivity.this, "End of List!", Toast.LENGTH_SHORT).show();
            }
        });

        lv_goodslistview = mPullRefreshListView.getRefreshableView();

        dataLists = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            map = new HashMap<String, Object>();
            map.put("picurl", "http://img0.imgtn.bdimg.com/it/u=2918690550,3711054886&fm=21&gp=0.jpg");
            map.put("title", "标题" + i);
            map.put("price", "价格" + i);
            map.put("size", "尺寸" + i);
            dataLists.add(map);
        }
        goodsListViewAdapter = new GoodsListViewAdapter();
        lv_goodslistview.setAdapter(goodsListViewAdapter);
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        /**
         * 1.设置左边的图片按钮显示，以及事件 2.设置中间TextView显示的文字 3.设置右边的图片按钮显示，并设置事件
         */
        new TitleBuilder(this).setLeftImageRes(R.drawable.head_top_title_left_icon).setMiddleTitleText("商品列表")
                .setRightImageRes(R.drawable.ic_launcher).setLeftTextOrImageListener(titleListener)
                .setRightTextOrImageListener(titleListener).initTitle(this);

    }

    /**
     * 标题栏的监听
     */
    private View.OnClickListener titleListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_left_imageview:
                    Toast.makeText(getApplicationContext(), "返回被点击", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.title_right_imageview:
                    Toast.makeText(getApplicationContext(), "右边被点击", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    /**
     * 商品列表的适配器
     *
     * @author 12111
     */
    class GoodsListViewAdapter extends BaseAdapter {

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
                convertView = View.inflate(mContext, R.layout.commodity_list_item, null);
                x.view().inject(viewHolder, convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Map<String, Object> map2 = dataLists.get(position);
            ImageOptions options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.ic_launcher).setFailureDrawableId(R.drawable.ic_launcher).setUseMemCache(true).build();
            x.image().bind(viewHolder.imageView, (String) map2.get("picurl"), options);
            viewHolder.title.setText((String) map2.get("title"));
            viewHolder.Price.setText((String) map2.get("price"));
            viewHolder.size.setText((String) map2.get("size"));
            return convertView;
        }

        class ViewHolder {
            @ViewInject(R.id.iv_pic)
            private ImageView imageView;
            @ViewInject(R.id.tv_title)
            private TextView title;
            @ViewInject(R.id.tv_price)
            private TextView Price;
            @ViewInject(R.id.tv_size)
            private TextView size;
        }
    }

    /**
     * 异步加载数据
     *
     * @author 12111
     */
    private class GetDataTask extends AsyncTask<Void, Void, List<Map<String, Object>>> {

        @SuppressWarnings("unchecked")
        @Override
        protected List<Map<String, Object>> doInBackground(Void... params) {
            Map map;
            List<Map<String, Object>> dataLists = new ArrayList<Map<String, Object>>();
            for (int j = 0; j < 10; j++) {
                map = new HashMap<String, Object>();
                map.put("picurl", "http://img5.imgtn.bdimg.com/it/u=582192550,850966327&fm=21&gp=0.jpg");
                map.put("title", "标题" + j);
                map.put("price", "价格1" + j);
                map.put("size", "尺寸" + j);
                dataLists.add(map);
            }
            return dataLists;
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> result) {
            for (int i = 0; i < result.size(); i++) {
                dataLists.add(result.get(i));
            }

            goodsListViewAdapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }
}
