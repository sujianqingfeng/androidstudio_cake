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
import com.sujian.finalandroid.entity.AddressInfoEntity;
import com.sujian.finalandroid.entity.BooleabEntity;
import com.sujian.finalandroid.entity.ShopCarOrderInfo;
import com.sujian.finalandroid.entity.ShopCarOrderInfoCallBackEntity;
import com.sujian.finalandroid.entity.ShopCart;
import com.sujian.finalandroid.net.AddressInfoCallback;
import com.sujian.finalandroid.net.BooleanCallback;
import com.sujian.finalandroid.net.ShopCarOrderInfoCallBack;
import com.sujian.finalandroid.ui.LoadingPage;
import com.sujian.finalandroid.ui.PublishSelectTimePopupWindow;
import com.sujian.finalandroid.uitls.MyUitls;
import com.sujian.finalandroid.uitls.ToastUitls;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.content.Intent;
import android.preference.PreferenceManager;
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
    //购买按钮
    @ViewInject(R.id.sp_purchase)
    private Button sp_purchase;
    //备注
    @ViewInject(R.id.et_need)
    private EditText textView;
    //展现商品的列表
    @ViewInject(R.id.lv_shopcar)
    private ListView lv_shopcar;
    // 装载数据集合
    List<ShopCarOrderInfo> dataLists;
    //地址
    @ViewInject(R.id.rl_update)
    private RelativeLayout rl_update;
    //送货时间
    @ViewInject(R.id.rl_time)
    private RelativeLayout rl_time;
    //全选的checkbox
    @ViewInject(R.id.scb_shopcar_choose)
    private SmoothCheckBox scb_shopcar_choose;
    //listview的适配器
    private ShopCarAdapter shopCarAdapter;
    //全部金额
    @ViewInject(R.id.tv_total)
    private TextView tv_total;
    //装载选中的信息
    private List<ShopCarOrderInfo> checkOrder;
    //收件人
    @ViewInject(R.id.tv_person)
    private TextView tv_person;
    //电话
    @ViewInject(R.id.tv_phone)
    private TextView tv_phone;
    //地址
    @ViewInject(R.id.tv_address)
    private TextView tv_address;
    //返回码
    private final int ADDRESS_CODE = 520;
    //送货时间
    @ViewInject(R.id.tv_time)
    private TextView tv_time;


    @Override
    public void initDatas(View view) {
        initCheckBox();
        getAddress();
    }


    @Override
    public void show() {
        super.show();
        initListView();
        if (shopCarAdapter != null)
            shopCarAdapter.notifyDataSetChanged();
    }


    /**
     * 得到地址
     */
    private void getAddress() {
        String url = Constants.SERVICEADDRESS + "address/address_getCheckAddress.cake";
        OkHttpUtils.get()
                .url(url)
                .addParams("user_id", MyUitls.getUid() + "")
                .build()
                .execute(new AddressInfoCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(AddressInfoEntity response, int id) {
                        if (response.isSuccess()) {
                            tv_address.setText("地址：" + response.getAddress().getAddress_content());
                            tv_phone.setText("联系电话：" + response.getAddress().getAddress_phone() + "");
                            tv_person.setText("联系人：" + response.getAddress().getAddress_name());
                        }
                    }
                });
    }

    /**
     * 初始化checkbox
     */
    private void initCheckBox() {


        scb_shopcar_choose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scb_shopcar_choose.isChecked()) {
                    scb_shopcar_choose.setChecked(false, true);
                    for (ShopCarOrderInfo sc : dataLists) {
                        if (sc.getChecked() == 0) {
                            sc.setChecked(1);

                            changeCheckState(sc.getShopping_cart_id() + "", sc.getChecked() + "");
                        }
                    }
                    shopCarAdapter.notifyDataSetChanged();
                } else {
                    scb_shopcar_choose.setChecked(true, true);
                    for (ShopCarOrderInfo s : dataLists) {
                        if (s.getChecked() == 1) {
                            s.setChecked(0);
                            changeCheckState(s.getShopping_cart_id() + "", s.getChecked() + "");
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
                            setMOney();
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


    /**
     * 设置金额
     */
    private void setMOney() {
        float money = 0;
        for (int i = 0; i < dataLists.size(); i++) {
            money = money + (dataLists.get(i).getCommodity_price() * dataLists.get(i).getCommodity_quantity());
        }
        tv_total.setText("共" + money + "元");
    }


    @Override
    protected View createSuccessView() {
        View view = View.inflate(getActivity(), R.layout.shopcar_fragment, null);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {
        if (MyUitls.isUserExistence()) {
            return LoadingPage.LoadResult.success;
        } else {
            return LoadingPage.LoadResult.empty;
        }

    }

    /**
     * 点击修改
     */
    @Event(R.id.rl_update)
    private void clickUpdate(View view) {
        startActivityForResult(new Intent(getActivity(), ChangeAdressActivity.class), ADDRESS_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADDRESS_CODE) {
            getAddress();
        }
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
     * 购买按钮的监听器
     *
     * @param view
     */
    @Event(value = R.id.sp_purchase)
    private void clickpurchaseButton(View view) {
        if (MyUitls.isUserExistence()) {
            if (checkOrder == null) {
                checkOrder = new ArrayList<>();
            }
            checkOrder.clear();
            LogUtil.e("订单的大小size--" + dataLists.size() + "选中的角标的大小--" + checkOrder.size());
            //逻辑为先遍历所有的订单  并且是选中的状态才能添加
            for (int i = 0; i < dataLists.size(); i++) {
                if (MyUitls.getBoolean(dataLists.get(i).getChecked())) {
                    addOrder(dataLists.get(i).getShopping_cart_id(), 1);
                    checkOrder.add(dataLists.get(i));
                }
            }

        } else {
            ToastUitls.show("请先登录账号！");
        }
    }


    /**
     * 添加订单
     * @param shop_id
     * @param states
     */
    private void addOrder(long shop_id, int states) {
        String url = Constants.SERVICEADDRESS + "order/order_addOrder.cake";
        OkHttpUtils.get()
                .url(url)
                .addParams("shopcar_id", shop_id + "")
                .addParams("order_status", states + "")
                .build()
                .execute(new BooleanCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUitls.show("网络异常，请稍后再试");
                    }

                    @Override
                    public void onResponse(BooleabEntity response, int id) {
                        //成功则清除数据
                        if (response.isSuccess()) {
                            //如果不单独拿一个集合临时装载 直接移除会造成数组越界异常（切记）
                            dataLists.removeAll(checkOrder);
                            shopCarAdapter.notifyDataSetChanged();
                        } else {
                            ToastUitls.show("添加订单失败");
                        }
                    }
                });
    }


    /**
     * 改变选中状态
     *
     * @param id
     * @param check
     */
    private void changeCheckState(String id, String check) {

        String url = Constants.SERVICEADDRESS + "shopcart/shopcart_updateShopCarCheck.cake";
        OkHttpUtils.get()
                .url(url)
                .addParams("shopping_cart_id", id)
                .addParams("checked", check)
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


    /**
     * 送货时间的监听器
     */
    private class SelectTimeLinstener implements PublishSelectTimePopupWindow.SelectTimeListener {
        @Override
        public void onclik(View view, int year, int month, int day, int hour) {
            //Toast.makeText(x.app(), year + "年" + month + "月" + day + "日" + hour + "小时", Toast.LENGTH_LONG).show();
            tv_time.setText(year + "-" + month + "-" + day + "-" + hour);
        }
    }

    /**
     *  订单的适配器
     */
    private class ShopCarAdapter extends DefauListViewAdapter<ShopCarOrderInfo> {

        public ShopCarAdapter(List<ShopCarOrderInfo> data) {
            super(data);
        }


        @Override
        public BaseHolder getHolder() {
            return new ViewHolder();
        }


        class ViewHolder extends BaseHolder<ShopCarOrderInfo> {
            //图片加载参数
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
                        changeCheckState(data.getShopping_cart_id() + "", data.getChecked() + "");

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
