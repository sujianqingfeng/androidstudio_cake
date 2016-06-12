package com.sujian.finalandroid.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.common.util.LogUtil;
import org.xutils.x;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.Commodity;
import com.sujian.finalandroid.entity.CommodityDetailCallBackEntity;
import com.sujian.finalandroid.net.CommodityDetailCallBack;
import com.sujian.finalandroid.ui.BadgeFloatingActionButton;
import com.sujian.finalandroid.ui.TitleBuilder;
import com.viewpagerindicator.CirclePageIndicator;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * 购买页面 by sujian
 */
@ContentView(R.layout.shopping_activity)
public class ShoppingActivity extends BaseActivity {

    //图片url地址
    private List<String> urlList;
    //商品的id
    private String id;
    //徽章按钮
    @ViewInject(R.id.bfab_shoping_shopcar)
    private BadgeFloatingActionButton bfab_shoping_shopcar;
    //商品的主图片
    @ViewInject(R.id.iv_shoping_commodity_icon)
    private ImageView iv_shoping_commodity_icon;
    //商品详情放置的容器
    @ViewInject(R.id.ll_shoping_detail_pics)
    private LinearLayout ll_shoping_detail_pics;
    //商品的名字
    @ViewInject(R.id.tv_shoping_commodity_name)
    private TextView tv_shoping_commodity_name;
    //商品的价格
    @ViewInject(R.id.tv_shoping_commodity_price)
    private TextView tv_shoping_commodity_price;
    //商品的类型
    @ViewInject(R.id.tv_shoping_commodity_price)
    private TextView tv_shoping_commodity_kind;
    //商品的尺寸
    @ViewInject(R.id.tv_shoping_commodity_size)
    private TextView tv_shoping_commodity_size;
    //商品的描述
    @ViewInject(R.id.tv_order_detail_desi)
    private TextView tv_order_detail_desi;

    private Commodity commodity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromServces();
    }

    /**
     * 从服务器得到数据
     */
    private void getDataFromServces() {
        id = getIntent().getStringExtra("id");
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        String url = Constants.SERVICEADDRESS + "commodity/commodity_returnCommodity.cake";
        OkHttpUtils.get()
                .url(url)
                .addParams("commodity_id", "10")
                .build()
                .execute(new CommodityDetailCallBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtil.e("huang li  jie shi sha  bi");
                    }

                    @Override
                    public void onResponse(CommodityDetailCallBackEntity response, int id) {
                        LogUtil.e(response.toString());
                        if (response.getSuccess()) {
                            commodity = response.getCommodity();
                            initPic();
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        initTitle();
        bfab_shoping_shopcar.showTextBadge("2");

        initShopCarButtonClickEvent();
    }

    /**
     * 购物车按钮点击事件
     */
    private void initShopCarButtonClickEvent() {
        bfab_shoping_shopcar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(11, getIntent());
                finish();
            }
        });
    }

    /**
     * 加载图片
     */
    private void initPic() {
        ImageOptions options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.ic_launcher)
                .setFailureDrawableId(R.drawable.ic_launcher).setUseMemCache(true).build();
        x.image().bind(iv_shoping_commodity_icon, commodity.getDescription_pcture(), options);
        ImageView img = new ImageView(this);
        x.image().bind(img, commodity.getDescription_pcture(), options);
        ll_shoping_detail_pics.addView(img);
        tv_shoping_commodity_name.setText(commodity.getCommodity_name());
        tv_shoping_commodity_price.setText(commodity.getCommodity_price() + "");
        tv_shoping_commodity_kind.setText(commodity.getCommodity_type_id() + "");
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        new TitleBuilder(this).setLeftImageRes(R.drawable.head_top_title_left_icon).setMiddleTitleText("购  买")
                .setRightImageRes(R.drawable.ic_launcher).setLeftTextOrImageListener(titleListener)
                .setRightTextOrImageListener(titleListener).initTitle(this);
    }

    /**
     * 标题的监听器
     */
    private View.OnClickListener titleListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_left_imageview:
                    Toast.makeText(getApplicationContext(), "返回被点击", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.title_right_imageview:
                    Toast.makeText(getApplicationContext(), "右边被点击", Toast.LENGTH_LONG).show();
                    break;
            }

        }
    };


}
