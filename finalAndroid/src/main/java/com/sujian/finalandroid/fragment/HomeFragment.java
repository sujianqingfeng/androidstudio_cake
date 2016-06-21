package com.sujian.finalandroid.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.common.util.LogUtil;
import org.xutils.x;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;

import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.activity.ShoppingActivity;
import com.sujian.finalandroid.activity.WebActivity;
import com.sujian.finalandroid.adapter.DefauListViewAdapter;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.base.BaseHolder;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.Commodity;
import com.sujian.finalandroid.entity.CommodityKind;
import com.sujian.finalandroid.entity.CommodityKindCalBackEntity;
import com.sujian.finalandroid.entity.HomeObject;
import com.sujian.finalandroid.net.CommodityKindCalBack;
import com.sujian.finalandroid.ui.LoadingPage;
import com.sujian.finalandroid.ui.LoopPagerAdapterWrapper;
import com.sujian.finalandroid.ui.LoopViewPager;
import com.sujian.finalandroid.uitls.ToastUitls;
import com.viewpagerindicator.CirclePageIndicator;
import com.zhy.http.okhttp.OkHttpUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.Call;

/**
 * 主页 fragment
 *
 * @author 12111
 */
public class HomeFragment extends BaseFragment {

    // 轮播容器
    @ViewInject(R.id.vp_home)
    private LoopViewPager vp_home;
    // 轮播指示器
    @ViewInject(R.id.vp_home_indicator)
    private CirclePageIndicator vp_home_indicator;

    HomeObject homeObject;
    CommodityKind commoditykind;


    // listview
    @ViewInject(R.id.lv_home)
    private ListView lv_home;

    @ViewInject(R.id.myScrooll)
    private ScrollView myScrollView;

    //gridview
    @ViewInject(R.id.gv_home)
    private GridView gv_home;

    // 装载数据集合
    List<Map<String, Object>> dataLists;
    Map<String, Object> map;

    // 图片url集合
    private List<String> urlList;

    //grid
    private List<Map<String, Object>> gridDatas;
    // 图片集合
    private List<String> imageList;

    // viewpager
    private HomeAdapter homeAdapter;
    private GridViewAdapter gridViewAdapter;
    private List<Commodity> data;
    //adapter 的包装类
    private LoopPagerAdapterWrapper loopPagerAdapterWrapper;
    //viewpager 的数据集合
    List<Commodity> headlist;

    @Override
    public void initDatas(View view) {
        show();
        initViewPager();
        initListView();
        getDate();
    }



    /**
     * 初始化viewpager
     */

    private void initViewPager() {
        urlList = new ArrayList<String>();
        homeAdapter = new HomeAdapter();
        loopPagerAdapterWrapper = new LoopPagerAdapterWrapper(homeAdapter);
        vp_home.setAdapter(loopPagerAdapterWrapper);
        vp_home_indicator.setViewPager(vp_home);
    }

    /**
     * 初始化listview
     */
    private void initListView() {

        //移动到第一个  移动到最顶部
        myScrollView.smoothScrollTo(0, 0);
        lv_home.setFocusable(false);

    }

    /**
     * 从服务器得到数据
     */
    private void getDate() {

        final String url = Constants.SERVICEADDRESS + "commodity/commodity_returnHomeData.action";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new CommodityKindCalBack() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onResponse(CommodityKindCalBackEntity response, int id) {
                        LogUtil.e("解析成功 返回的数据为————————" + response.getHomelist().toString());
                        show();
                        if (response.isSuccess()) {
                            homeObject = response.getHomelist();
                            if (homeObject != null) {
                                //viewpager
                                headlist = homeObject.getHeadList();

                                LogUtil.e("viewpager的数据大小" + headlist.size());
                                for (Commodity c : headlist) {
                                    urlList.add(Constants.SERVICEADDRESS + c.getDescription_pcture());
                                }
                                LogUtil.e("开始刷新----viewpager");
                                loopPagerAdapterWrapper.notifyDataSetChanged();

                                //gridlist
                                List<Commodity> gridlist = homeObject.getGridList();
                                gv_home.setAdapter(new GridViewAdapter(gridlist));


                                //listview
                                LogUtil.e("listview解析的数据-----" + homeObject.getKindList().toString());
                                List<CommodityKind> kindList = homeObject.getKindList();
                                lv_home.setAdapter(new ListViewAdapter(kindList));
                            }

                        } else {
                            ToastUitls.show("获取数据失败！");
                        }
                    }
                });
    }


    @Override
    protected View createSuccessView() {
        View view = View.inflate(getActivity(), R.layout.home_fragment, null);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }


    /**
     * listview的适配器
     */
    private class ListViewAdapter extends DefauListViewAdapter<CommodityKind> {

        public ListViewAdapter(List<CommodityKind> data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder() {
            return new KindHolder();
        }

        class KindHolder extends BaseHolder<CommodityKind> {
            private ImageOptions options;

            public KindHolder() {
                options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.ic_launcher)
                        .setFailureDrawableId(R.drawable.ic_launcher).setUseMemCache(true).build();
            }

            @ViewInject(R.id.tv_classification)
            private TextView classification;
            @ViewInject(R.id.img_top)
            private ImageView img_top;
            @ViewInject(R.id.img_left)
            private ImageView img_left;
            @ViewInject(R.id.tv_left_title)
            private TextView tv_left_title;
            @ViewInject(R.id.tv_left_size)
            private TextView tv_left_size;
            @ViewInject(R.id.tv_left_price)
            private TextView tv_left_price;
            @ViewInject(R.id.img_center)
            private ImageView img_center;
            @ViewInject(R.id.tv_center_title)
            private TextView tv_center_title;
            @ViewInject(R.id.tv_center_size)
            private TextView tv_center_size;
            @ViewInject(R.id.tv_center_price)
            private TextView tv_center_price;
            @ViewInject(R.id.img_right)
            private ImageView img_right;
            @ViewInject(R.id.tv_right_title)
            private TextView tv_right_title;
            @ViewInject(R.id.tv_right_size)
            private TextView tv_right_size;
            @ViewInject(R.id.tv_right_price)
            private TextView tv_right_price;



            @Override
            protected void refreshView() {

                x.image().bind(img_top, Constants.SERVICEADDRESS + data.getToppicurl(), options);
                img_top.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), WebActivity.class);
                        i.putExtra("url", Constants.SERVICEADDRESS + data.getActivityUrl());
                        startActivity(i);
                    }
                });

                Commodity leftCom = data.getCommodityList().get(0);
                Commodity centerCom = data.getCommodityList().get(1);
                Commodity rightCom = data.getCommodityList().get(2);
                x.image().bind(img_left, Constants.SERVICEADDRESS + leftCom.getDescription_pcture(), options);
                x.image().bind(img_center, Constants.SERVICEADDRESS + centerCom.getDescription_pcture(), options);
                x.image().bind(img_right, Constants.SERVICEADDRESS + rightCom.getDescription_pcture(), options);

                img_left.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                        intent.putExtra("id", "" + data.getCommodityList().get(0).getCommodity_id());
                        startActivityForResult(intent, Constants.GOSHOPCAR);
                    }
                });

                img_center.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                        intent.putExtra("id", "" + data.getCommodityList().get(1).getCommodity_id());
                        startActivityForResult(intent, Constants.GOSHOPCAR);
                    }
                });

                img_right.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                        intent.putExtra("id", "" + data.getCommodityList().get(2).getCommodity_id());
                        startActivityForResult(intent, Constants.GOSHOPCAR);
                    }
                });

                tv_left_title.setText(leftCom.getCommodity_name());
                tv_left_size.setText(leftCom.getCommodity_size() + "");
                tv_left_price.setText(leftCom.getCommodity_price() + "");
                tv_center_title.setText(centerCom.getCommodity_name());
                tv_center_size.setText(centerCom.getCommodity_size() + "");
                tv_center_price.setText(centerCom.getCommodity_price() + "");
                tv_right_title.setText(rightCom.getCommodity_name());
                tv_right_size.setText(rightCom.getCommodity_size() + "");
                tv_right_price.setText(rightCom.getCommodity_price() + "");


            }

            @Override
            public View initView() {
                View inflate = View.inflate(getActivity(), R.layout.home_list_item, null);
                x.view().inject(this, inflate);
                return inflate;
            }
        }


    }


    /**
     * 轮播的适配器
     *
     * @author 12111
     */
    class HomeAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return urlList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageOptions options = new ImageOptions.Builder()
                    // 设置加载过程中的图片
                    .setLoadingDrawableId(R.drawable.ic_launcher)
                    // 设置加载失败后的图片
                    .setFailureDrawableId(R.drawable.ic_launcher)
                    // 设置使用缓存
                    .setUseMemCache(true).build();

            ImageView imageView = new ImageView(getActivity());
            if (getCount() != 0) {
                x.image().bind(imageView, urlList.get(position), options);
            }


            imageView.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                    intent.putExtra("id", headlist.get(position).getCommodity_id() + "");
                    startActivityForResult(intent, Constants.GOSHOPCAR);
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }





    /**
     * gridview适配器
     */
    class GridViewAdapter extends DefauListViewAdapter<Commodity> {
        public GridViewAdapter(List<Commodity> data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder() {
            return new GridViewHolder();
        }

        class GridViewHolder extends BaseHolder<Commodity> {

            @ViewInject(R.id.iv_home_gridview_item_pic)
            private ImageView pic;
            @ViewInject(R.id.tv_home_gridview_item_title)
            private TextView title;


            @Override
            protected void refreshView() {
                ImageOptions options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.ic_launcher)
                        .setFailureDrawableId(R.drawable.ic_launcher).setUseMemCache(true).build();
                title.setText(data.getCommodity_name());
                x.image().bind(pic, Constants.SERVICEADDRESS + data.getDescription_pcture(), options);

                pic.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                        intent.putExtra("id", "" + data.getCommodity_id());
                        startActivityForResult(intent, Constants.GOSHOPCAR);
                    }
                });

            }


            @Override
            public View initView() {
                View inflate = View.inflate(getActivity(), R.layout.home_gridview_item, null);
                x.view().inject(this, inflate);
                return inflate;
            }
        }
    }
}
