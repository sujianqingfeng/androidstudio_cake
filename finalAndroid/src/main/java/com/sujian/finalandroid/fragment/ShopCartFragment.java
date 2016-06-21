package com.sujian.finalandroid.fragment;

import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import com.google.gson.Gson;
import com.sujian.finalandroid.activity.ChangeAdressActivity;
import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.activity.ShoppingActivity;
import com.sujian.finalandroid.adapter.DefauListViewAdapter;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.base.BaseHolder;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.ShopCarOrderInfo;
import com.sujian.finalandroid.entity.ShopCarOrderInfoCallBackEntity;
import com.sujian.finalandroid.entity.ShopCart;
import com.sujian.finalandroid.net.ShopCarOrderInfoCallBack;
import com.sujian.finalandroid.ui.LoadingPage;
import com.sujian.finalandroid.ui.PublishSelectTimePopupWindow;
import com.sujian.finalandroid.uitls.MyUitls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.refactor.library.SmoothCheckBox;
import okhttp3.Call;

/**
 * 购物车界面
 */
public class ShopCartFragment extends BaseFragment {

    @ViewInject(R.id.sp_purchase)
    private Button sp_purchase;

    @ViewInject(R.id.et_need)
    private EditText textView;
    //展现商品的列表
    @ViewInject(R.id.lv_shopcar)
    private ListView lv_shopcar;
    // 装载数据集合
    List<ShopCarOrderInfo> dataLists;

    @ViewInject(R.id.rl_update)
    private RelativeLayout rl_update;

    @ViewInject(R.id.rl_time)
    private RelativeLayout rl_time;
    //全选的checkbox
    @ViewInject(R.id.scb_shopcar_choose)
    private SmoothCheckBox scb_shopcar_choose;

    private ShopCarAdapter shopCarAdapter;

    @Override
    public void initDatas(View view) {

        initCheckBox();
    }

    @Override
    public void show() {
        super.show();
        initListView();
        if (shopCarAdapter != null)
            shopCarAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化checkbox
     */
    private void initCheckBox() {

        scb_shopcar_choose.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    for (ShopCarOrderInfo s : dataLists) {
                        if (s.getChecked() == 1) {
                            s.setChecked(0);
                        }
                    }
                    shopCarAdapter.notifyDataSetChanged();
                } else {
                    for (ShopCarOrderInfo sc : dataLists) {
                        if (sc.getChecked() == 0) {
                            sc.setChecked(1);
                        }
                    }
                    shopCarAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 初始化listview
     */
    private void initListView() {
        String url = Constants.SERVICEADDRESS + "shopcart/shopcart_shopCar.cake";
        OkHttpUtils.get()
                .url(url)
                .addParams("user_id", MyUitls.getUid() + "")
                .build()
                .execute(new ShopCarOrderInfoCallBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtil.e("错误解析------购物车");
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(ShopCarOrderInfoCallBackEntity response, int id) {
                        LogUtil.e("购物车返回的数据--------" + response.toString());
                        //加载数据
                        if (response.isSuccess()) {
                            dataLists = new ArrayList<>();
                            dataLists.addAll(response.getShopCarOrderInfo());
                            lv_shopcar.setFocusable(false);
                            shopCarAdapter = new ShopCarAdapter(dataLists);
                            lv_shopcar.setAdapter(shopCarAdapter);
                        }

                    }
                });


        lv_shopcar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtil.e("点击的位置----" + position);
                Intent intent = new Intent(getActivity(), ShoppingActivity.class);
                intent.putExtra("id", dataLists.get(position).getCommodity_id() + "");
                startActivityForResult(intent, Constants.GOSHOPCAR);
            }
        });


    }


    @Override
    protected View createSuccessView() {
        View view = View.inflate(getActivity(), R.layout.shopcar_fragment, null);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    /**
     * 点击修改
     */
    @Event(R.id.rl_update)
    private void clickUpdate(View view) {
        startActivity(new Intent(getActivity(), ChangeAdressActivity.class));
    }

    /**
     * 点击收货时间
     */
    @Event(R.id.rl_time)
    private void clickTime(View view) {
        PublishSelectTimePopupWindow publishSelectTimePopupWindow = new PublishSelectTimePopupWindow();
        publishSelectTimePopupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        publishSelectTimePopupWindow.setOnSelectTimeListener(new SelectTimeLinstener());
    }


    /**
     * 购买的监听器
     *
     * @param view
     */
    @Event(value = R.id.sp_purchase)
    private void clickpurchaseButton(View view) {
        Toast.makeText(getActivity(), "购买被点击", Toast.LENGTH_SHORT).show();

    }

    /**
     * 送货时间的监听器
     */
    private class SelectTimeLinstener implements PublishSelectTimePopupWindow.SelectTimeListener {
        @Override
        public void onclik(View view, int year, int month, int day, int hour) {
            Toast.makeText(x.app(), year + "年" + month + "月" + day + "日" + hour + "小时", Toast.LENGTH_LONG).show();
        }
    }

    //订单的适配器
    private class ShopCarAdapter extends DefauListViewAdapter<ShopCarOrderInfo> {


        public ShopCarAdapter(List<ShopCarOrderInfo> data) {
            super(data);
        }


        @Override
        public BaseHolder getHolder() {
            return new ViewHolder();
        }


        class ViewHolder extends BaseHolder<ShopCarOrderInfo> {
            private ImageOptions options;

            public ViewHolder() {
                options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.ic_launcher)
                        .setFailureDrawableId(R.drawable.ic_launcher).setUseMemCache(true).build();
            }

            @ViewInject(R.id.iv_shopcar_item_commodity_icon)
            private ImageView iv_shopcar_item_commodity_icon;

            @ViewInject(R.id.tv_shopcar_item_comodity_title)
            private TextView tv_shopcar_item_comodity_title;

            @ViewInject(R.id.tv_shopcar_item_comodity_price)
            private TextView tv_shopcar_item_comodity_price;

            @ViewInject(R.id.tv_shopcar_item_comodity_num)
            private TextView tv_shopcar_item_comodity_num;

            @ViewInject(R.id.scb_shopcar_item_choose)
            private SmoothCheckBox scb_shopcar_item_choose;

            @ViewInject(R.id.ibt_shopcar_item_comdity_reduce)
            private ImageButton ibt_shopcar_item_comdity_reduce;

            @ViewInject(R.id.ibt_shopcar_item_comdity_add)
            private ImageButton ibt_shopcar_item_comdity_add;

            @Override
            protected void refreshView() {
                tv_shopcar_item_comodity_title.setText(data.getCommodity_name() + "");
                tv_shopcar_item_comodity_price.setText(data.getCommodity_price() + "元");
                tv_shopcar_item_comodity_num.setText(data.getCommodity_quantity() + "");
                x.image().bind(iv_shopcar_item_commodity_icon, Constants.SERVICEADDRESS + data.getDescription_pcture(), options);

                LogUtil.e("查看状态--" + MyUitls.getBoolean(data.getChecked()) + "数字为--" + data.getChecked() + "商品id--" + data.getCommodity_id());
                scb_shopcar_item_choose.setChecked(MyUitls.getBoolean(data.getChecked()));


                scb_shopcar_item_choose.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data.setChecked(MyUitls.getInt(!scb_shopcar_item_choose.isChecked()));
                        LogUtil.e("改变之后的check" + MyUitls.getInt(!scb_shopcar_item_choose.isChecked()));
                        scb_shopcar_item_choose.setChecked(!scb_shopcar_item_choose.isChecked(), true);
                        //提交数据
                        String url = Constants.SERVICEADDRESS + "shopcart/shopcart_updateShopCar.cake";
                        OkHttpUtils.get()
                                .url(url)
                                .addParams("shopping_cart_id", data.getShopping_cart_id() + "")
                                .addParams("checked", data.getChecked() + "")
                                .addParams("user_id", data.getUser_id() + "")
                                .addParams("commodity_id", data.getCommodity_id() + "")
                                .addParams("commodity_quantity", data.getCommodity_quantity() + "")
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {

                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        LogUtil.e(response);
                                    }
                                });

                    }
                });


                ibt_shopcar_item_comdity_reduce.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtil.e("减被点击了");
                    }
                });

                ibt_shopcar_item_comdity_add.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtil.e("加被点击了");
                    }
                });
            }

            @Override
            public View initView() {
                View view = View.inflate(x.app(), R.layout.shop_car_list_item, null);
                x.view().inject(this, view);
                return view;
            }
        }
    }


}
