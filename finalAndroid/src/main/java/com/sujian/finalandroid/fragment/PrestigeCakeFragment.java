package com.sujian.finalandroid.fragment;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.activity.ShoppingActivity;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.Commodity;
import com.sujian.finalandroid.entity.CommodityCallBackEntity;
import com.sujian.finalandroid.net.CommodityCallback;
import com.sujian.finalandroid.ui.LoadingPage;
import com.sujian.finalandroid.uitls.ToastUitls;
import com.zhy.http.okhttp.OkHttpUtils;

import android.content.Intent;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 威风蛋糕
 *
 * @author 12111
 */
public class PrestigeCakeFragment extends BaseFragment {


    private boolean isFrist = true;
    // 刷新控件
    @ViewInject(R.id.ptrlv_angelCake)
    private PullToRefreshListView mPullRefreshListView;
    // 判断是下拉，还是上拉的标记
    private boolean isPullDownRefresh = true;

    private ListView lv_goodslistview;


    // 装载数据集合
    List<Commodity> dataLists;//dataListswei 为list


    private GoodsListViewAdapter goodsListViewAdapter;

    private int page;


    @Override
    protected View createSuccessView() {
        View view = View.inflate(getActivity(), R.layout.prestigecake_fragment, null);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        LogUtil.e("-datalist的个数为" + dataLists.size());
        if (dataLists.size() > 0) {
            return LoadingPage.LoadResult.success;
        } else if (dataLists.size() == 0) {
            return LoadingPage.LoadResult.success;
        } else {
            return LoadingPage.LoadResult.loading;
        }
    }


    @Override
    public void initDatas(View view) {
        super.initDatas(view);
        getDateFromService();
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


        dataLists = new ArrayList<>();
        //得到listview视图
        lv_goodslistview = mPullRefreshListView.getRefreshableView();

        goodsListViewAdapter = new GoodsListViewAdapter();
        lv_goodslistview.setAdapter(goodsListViewAdapter);

        //listview 条目点击事件
        lv_goodslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long commodity_id = dataLists.get(position).getCommodity_id();
                Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                intent.putExtra("id", "" + commodity_id);
                startActivityForResult(intent, Constants.GOSHOPCAR);
            }
        });
    }


    private void getDateFromService() {
        page = 1;
        String url = Constants.SERVICEADDRESS + "commodity/commodity_commodityselect.action";
        LogUtil.e(page + "");
        OkHttpUtils
                .get()
                .addParams("commodity_type_id", String.valueOf(4))
                .addParams("page", String.valueOf(1))
                .url(url)
                .build()
                .execute(new CommodityCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(CommodityCallBackEntity response, int id) {
                        LogUtil.e(response.getList().toString());
                        LogUtil.e(String.valueOf(id));
                        if (response.isSuccess()) {
                            dataLists.addAll(response.getList());
                            goodsListViewAdapter.notifyDataSetChanged();
                            show();
                        } else {
                            ToastUitls.show("获取数据失败！");
                        }

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

        page = page + 1;
        //获取数据
        getDate();
    }

    private void getDate() {
        String url = Constants.SERVICEADDRESS + "commodity/commodity_commodityselect.action";
        LogUtil.e(page + "");
        OkHttpUtils
                .get()
                .addParams("commodity_type_id", String.valueOf(4))
                .addParams("page", String.valueOf(page))
                .url(url)
                .build()
                .execute(new CommodityCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(CommodityCallBackEntity response, int id) {

                        if (response.getList().size() == 0) {
                            ToastUitls.show("没有更多的蛋糕了！");
                            show();
                            // 通知刷新已完成
                            mPullRefreshListView.onRefreshComplete();
                        } else {
                            if (isPullDownRefresh) {
                                dataLists.addAll(0, response.getList());
                            } else {//上拉
                                dataLists.addAll(response.getList());
                            }
                            show();
                            goodsListViewAdapter.notifyDataSetChanged();
                            // 通知刷新已完成
                            mPullRefreshListView.onRefreshComplete();
                        }

                    }
                });

    }


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
                convertView = View.inflate(getActivity(), R.layout.commodity_list_item, null);
                x.view().inject(viewHolder, convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Commodity map2 = dataLists.get(position);
            ImageOptions options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.ic_launcher)
                    .setFailureDrawableId(R.drawable.ic_launcher).setUseMemCache(true).build();
            x.image().bind(viewHolder.imageView, Constants.SERVICEADDRESS + map2.getDescription_pcture(), options);
            viewHolder.title.setText(map2.getCommodity_name());
            viewHolder.Price.setText("$ " + String.valueOf(map2.getCommodity_price()));
            viewHolder.size.setText(String.valueOf(map2.getCommodity_size()) + "寸");
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


}

