package com.sujian.finalandroid.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.common.util.LogUtil;
import org.xutils.x;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;

import com.sujian.finalandroid.activity.HomeActivity;
import com.sujian.finalandroid.activity.MyOrderActivity;
import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.activity.SearchActivity;
import com.sujian.finalandroid.activity.ShoppingActivity;
import com.sujian.finalandroid.activity.WebActivity;
import com.sujian.finalandroid.adapter.DefauListViewAdapter;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.base.BaseHolder;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.Commodity;
import com.sujian.finalandroid.entity.CommodityCallBackEntity;
import com.sujian.finalandroid.entity.CommodityKind;
import com.sujian.finalandroid.entity.CommodityKindCalBackEntity;
import com.sujian.finalandroid.entity.HomeObject;
import com.sujian.finalandroid.net.CommodityKindCalBack;
import com.sujian.finalandroid.ui.LoadingPage;
import com.sujian.finalandroid.ui.TitleBuilder;
import com.sujian.finalandroid.uitls.ToastUitls;
import com.viewpagerindicator.CirclePageIndicator;
import com.zhy.http.okhttp.OkHttpUtils;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
    private ViewPager vp_home;
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
    // 放置分类数据的集合
    private HomeAdapter homeAdapter;

    private boolean isFrist = true;

    @Override
    public void initDatas(View view) {
        show();
        initViewPager();

        initListView();
        initGridView();

    }

    @Override
    public void onResume() {
        super.onResume();
        getDate();
    }

    /**
     * 初始化gridview
     */
    private void initGridView() {
//       gridDatas = new ArrayList<>();
//        Map<String, Object> map;
//        for (int i = 0; i < 6; i++) {
//            map = new HashMap<>();
//            map.put("title", "蛋糕" + i);
//            gridDatas.add(map);
//        }

//        SimpleAdapter sim = new SimpleAdapter(x.app(), gridDatas, R.layout.home_gridview_item, new String[]{"title"}, new int[]{R.id.tv_home_gridview_item_title});
//        gv_home.setAdapter(sim);


    }

    /**
     * 初始化listview
     */
    private void initListView() {
        dataLists = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 4; i++) {
            map = new HashMap<String, Object>();
            map.put("classification", "热门分类" + i);
            map.put("imgtop", "http://img0.imgtn.bdimg.com/it/u=2918690550,3711054886&fm=21&gp=0.jpg");
            map.put("imgleft", "http://img0.imgtn.bdimg.com/it/u=2918690550,3711054886&fm=21&gp=0.jpg");
            map.put("imgcenter", "http://img0.imgtn.bdimg.com/it/u=2918690550,3711054886&fm=21&gp=0.jpg");
            map.put("imgright", "http://img0.imgtn.bdimg.com/it/u=2918690550,3711054886&fm=21&gp=0.jpg");
            map.put("tvlefttitle", "左标题" + i);
            map.put("tvleftsize", "左尺寸" + i);
            map.put("tvleftprice", "左价格" + i);
            map.put("tvrighttitle", "右标题" + i);
            map.put("tvrightsize", "右尺寸" + i);
            http:
//127.0.0.1:8080/CakeWeb/nainao.html
            map.put("tvrightprice", "右价格" + i);
            map.put("tvcentertitle", "中标题" + i);
            map.put("tvcentersize", "中尺寸" + i);
            map.put("tvcenterprice", "中价格" + i);
            map.put("topurl", Constants.SERVICEADDRESS + "nainao.html");
            dataLists.add(map);

        }
        lv_home.setAdapter(new HomeListViewAdapter());

        //移动到第一个  移动到最顶部
        myScrollView.smoothScrollTo(0, 0);
        lv_home.setFocusable(false);
    }


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
                        final String url = Constants.SERVICEADDRESS + "commodity/commodity_returnHomeData.action";
                        LogUtil.e(response.getHomelist().toString());
                        LogUtil.e("成功成功成功成功成功成功成功成功成功成功成功成功成功成功成功成功成功0成功成功");
                        OkHttpUtils
                                .get()
                                .url(url)
                                .build()
                                .execute(new CommodityKindCalBack() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {

                                    }

                                    @Override
                                    public void onResponse(CommodityKindCalBackEntity response, int id) {
                                        show();
                                        if (response.isSuccess()) {
                                            homeObject = response.getHomelist();
                                            if (homeObject != null) {
                                                List<Commodity> headlist = homeObject.getHeadList();
                                                urlList.clear();
                                                LogUtil.e("-----------------" + headlist.size());
                                                for (Commodity c : headlist) {
                                                    urlList.add(Constants.SERVICEADDRESS + c.getDescription_pcture());

                                                }
                                                homeAdapter.notifyDataSetChanged();
                                                vp_home.setAdapter(homeAdapter);
                                                vp_home_indicator.setViewPager(vp_home);
                                                //gridlist
                                                List<Commodity> gridlist = homeObject.getGridList();
                                                gv_home.setAdapter(new GridViewAdapter(gridlist));

                                            }


//                                            LogUtil.e(response.getKindlist().toString());
//                                            commoditykind=response.getKindlist();
//                                            if (commoditykind!=null){
//
//                                                List<Commodity> kindlistcom=commoditykind.getCommodityList();
//
//
//
//                                            }


//                                            List<CommodityKind> KindList= response.getHomelist().getKindList();
//                                            if (KindList!=null){
//                                                ListViewAdapter adapter=new ListViewAdapter(KindList);
//                                                lv_home.setAdapter(adapter);
//                                            }else {
//                                                LogUtil.e("数据为空");
//                                            }

                                            // List<Commodity> headList= homeObject.getHeadList();


                                            show();
                                        } else {
                                            ToastUitls.show("获取数据失败！");
                                        }

                                    }
                                });
                    }
                });
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        urlList = new ArrayList<String>();
        homeAdapter = new HomeAdapter();

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
     * listView 的适配器
     *
     * @author 12111
     */
    class HomeListViewAdapter extends BaseAdapter {

        @Override
        public int getItemViewType(int position) {
            return position > 0 ? 0 : 1;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

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
                convertView = View.inflate(getActivity(), R.layout.home_list_item, null);
                x.view().inject(viewHolder, convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final Map<String, Object> map2 = dataLists.get(position);

            ImageOptions options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.ic_launcher)
                    .setFailureDrawableId(R.drawable.ic_launcher).setUseMemCache(true).build();
            viewHolder.classification.setText((String) map2.get("classification"));
            x.image().bind(viewHolder.img_top, (String) map2.get("imgtop"), options);
            x.image().bind(viewHolder.img_left, (String) map2.get("imgleft"), options);
            x.image().bind(viewHolder.img_center, (String) map2.get("imgcenter"), options);
            x.image().bind(viewHolder.img_right, (String) map2.get("imgright"), options);
            viewHolder.tv_left_title.setText((String) map2.get("tvcentertitle"));
            viewHolder.tv_left_size.setText((String) map2.get("tvcentersize"));
            viewHolder.tv_left_price.setText((String) map2.get("tvcentersize"));
            viewHolder.tv_center_title.setText((String) map2.get("tvcentertitle"));
            viewHolder.tv_center_size.setText((String) map2.get("tvcentersize"));
            viewHolder.tv_center_price.setText((String) map2.get("tvcenterprice"));
            viewHolder.tv_right_title.setText((String) map2.get("tvrighttitle"));
            viewHolder.tv_right_size.setText((String) map2.get("tvrightsize"));
            viewHolder.tv_right_price.setText((String) map2.get("tvrightprice"));

            //处理上面top图片的点击事件
            viewHolder.img_top.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
//					Toast.makeText(getActivity(), (String)map2.get("topurl"), 0).show();
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("url", (String) map2.get("topurl"));
                    startActivityForResult(intent, 11);
                }
            });

            //处理左边的点击事件
            viewHolder.img_left.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "左边商品被点击", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                    intent.putExtra("id", "1");
                    startActivityForResult(intent, 11);
                }
            });

            //处理中间的点击事件
            viewHolder.img_center.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "中间商品被点击", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                    intent.putExtra("id", "1");
                    startActivityForResult(intent, 11);
                }
            });

            //处理右边的点击事件
            viewHolder.img_right.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "右边商品被点击", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                    intent.putExtra("id", "1");
                    startActivityForResult(intent, 11);
                }
            });


            return convertView;

        }

        class ViewHolder {
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
        }
    }


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

                Commodity leftCom = data.getCommodityList().get(0);
                Commodity centerCom = data.getCommodityList().get(1);
                Commodity rightCom = data.getCommodityList().get(2);
                x.image().bind(img_left, Constants.SERVICEADDRESS + leftCom.getDescription_pcture(), options);
                x.image().bind(img_center, Constants.SERVICEADDRESS + centerCom.getDescription_pcture(), options);
                x.image().bind(img_right, Constants.SERVICEADDRESS + rightCom.getDescription_pcture(), options);

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
                    Toast.makeText(getActivity(), "第" + position + "页被点击", Toast.LENGTH_SHORT).show();
                    switch (position) {
                        case 0:
                            Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                            intent.putExtra("id", "1");
                            startActivity(intent);
                            break;
                        case 1:
                            Intent intent1 = new Intent(getActivity(), ShoppingActivity.class);
                            intent1.putExtra("id", "1");
                            startActivity(intent1);
                            break;
                        case 2:
                            Intent intent2 = new Intent(getActivity(), ShoppingActivity.class);
                            intent2.putExtra("id", "1");
                            startActivity(intent2);
                            break;
                        case 3:
                            Intent intent3 = new Intent(getActivity(), ShoppingActivity.class);
                            intent3.putExtra("id", "1");
                            startActivity(intent3);
                            break;

                        default:
                            break;
                    }

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
