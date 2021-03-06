package com.sujian.finalandroid.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.common.util.LogUtil;
import org.xutils.x;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.activity.ShoppingActivity;
import com.sujian.finalandroid.adapter.CheeseCakeAdapter;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.Commodity;
import com.sujian.finalandroid.entity.CommodityCallBackEntity;
import com.sujian.finalandroid.net.CommodityCallback;
import com.sujian.finalandroid.net.LoginCallback;
import com.sujian.finalandroid.ui.LoadingPage;
import com.sujian.finalandroid.uitls.MyUitls;
import com.sujian.finalandroid.uitls.ToastUitls;
import com.zhy.http.okhttp.OkHttpUtils;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Call;

/**
 * 天使蛋糕
 *
 * @author 12111
 */
public class AngelCakeFragment extends BaseFragment {


    @ViewInject(R.id.rv_cheese)
    private XRecyclerView rv_cheese;

    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private CheeseCakeAdapter adapter;

    // 判断是下拉，还是上拉的标记
    private boolean isLoadMore;


    // 装载数据集合
    List<Commodity> dataLists;//dataListswei 为list


    private CheeseCakeAdapter cheeseCakeAdapter;

    private int page;

    @Override
    protected View createSuccessView() {
        View view = View.inflate(getActivity(), R.layout.poundcake_fragment, null);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        LogUtil.e("天使蛋糕 ----datalist的个数为" + dataLists.size());
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
        initRecyclerView();
    }

    private void initRecyclerView() {

        dataLists = new ArrayList<>();
        rv_cheese.setHasFixedSize(true);
        rv_cheese.setLoadingMoreEnabled(true);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rv_cheese.setLayoutManager(staggeredGridLayoutManager);


        adapter = new CheeseCakeAdapter(getActivity(), dataLists);
        rv_cheese.setAdapter(adapter);


        rv_cheese.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                getDate();
            }

            @Override
            public void onLoadMore() {
                isLoadMore = true;
                getDate();
            }
        });

        //上拉与下拉的样式
        rv_cheese.setRefreshProgressStyle(ProgressStyle.BallBeat);
        rv_cheese.setLoadingMoreProgressStyle(ProgressStyle.Pacman);

        //条目点击事件
        adapter.setOnItemClickListener(new CheeseCakeAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
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
        LogUtil.e("页数" + page);
        OkHttpUtils
                .get()
                .addParams("commodity_type_id", String.valueOf(3))
                .addParams("page", String.valueOf(page))

                .url(url)
                .build()
                .execute(new CommodityCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(CommodityCallBackEntity response, int id) {
                        if (response.getList().size() > 0) {
                            LogUtil.e("天使蛋糕的json解析数据" + response.getList().toString());
                            if (response.isSuccess()) {
                                dataLists.addAll(response.getList());
                                adapter.notifyDataSetChanged();
                                show();
                            } else {
                                ToastUitls.show("获取数据失败！");
                            }
                        }
                    }

                });

    }


    private void getDate() {
        page++;
        String url = Constants.SERVICEADDRESS + "commodity/commodity_commodityselect.action";
        LogUtil.e("刷新的页数" + page);
        OkHttpUtils
                .get()
                .addParams("commodity_type_id", String.valueOf(3))
                .addParams("page", String.valueOf(page))
                .url(url)
                .build()
                .execute(new CommodityCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(CommodityCallBackEntity response, int id) {

                        if (isLoadMore) {//上拉
                            if (response.getList().size() == 0) {
                                ToastUitls.show("没有更多的蛋糕了！");
                                // 通知刷新已完成
                                rv_cheese.loadMoreComplete();
                            } else {
                                dataLists.addAll(response.getList());
                                rv_cheese.loadMoreComplete();
                            }
                        } else {//下拉
                            if (response.getList().size() == 0) {
                                ToastUitls.show("没有更多的蛋糕了！");
                                show();
                                // 通知刷新已完成
                                rv_cheese.refreshComplete();
                            } else {
                                dataLists.addAll(0, response.getList());
                                rv_cheese.refreshComplete();
                            }
                        }
                        show();
                        adapter.notifyDataSetChanged();

                    }
                });

    }


}
