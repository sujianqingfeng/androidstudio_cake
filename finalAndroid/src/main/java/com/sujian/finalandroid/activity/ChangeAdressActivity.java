package com.sujian.finalandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sujian.finalandroid.adapter.DefauListViewAdapter;
import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.base.BaseHolder;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.Address;
import com.sujian.finalandroid.entity.AddressCallBackEntity;
import com.sujian.finalandroid.net.AddressCallback;
import com.sujian.finalandroid.ui.TitleBuilder;
import com.sujian.finalandroid.uitls.MyUitls;
import com.zhy.http.okhttp.OkHttpUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 改变收获地址界面
 * @author 12111
 */
@ContentView(R.layout.activity_change_adress)
public class ChangeAdressActivity extends BaseActivity {
    //listview
    @ViewInject(R.id.lv_change_adress)
    private ListView lv_change_adress;
    //新增按钮
    @ViewInject(R.id.bt_change_adress_add_adress)
    private Button bt_change_adress_add_adress;
    // 装载数据集合
    List<Address> dataLists;
    //适配器
    private ChangeAddressListViewAdapter changeAddressListViewAdapter;


    @Override
    protected void initData() {
        super.initData();
        initTitle();
        initChangeAdressListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataLists.clear();
        getDataFromService();
    }

    /**
     * 从服务器得到数据
     */
    private void getDataFromService() {
        String url = Constants.SERVICEADDRESS + "address/address_returnAddressByUserId.cake";
        OkHttpUtils.get()
                .url(url)
                .addParams("user_id", MyUitls.getUid() + "")
                .build()
                .execute(new AddressCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(AddressCallBackEntity response, int id) {
                        if (response.isSuccess()) {
                            dataLists.addAll(response.getAddresslist());
                            changeAddressListViewAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    /**
     * 初始化listview
     */
    private void initChangeAdressListView() {
        dataLists = new ArrayList<Address>();
        changeAddressListViewAdapter = new ChangeAddressListViewAdapter(dataLists);
        lv_change_adress.setAdapter(changeAddressListViewAdapter);
    }

    /**
     * 点击增加按钮
     *
     * @param view
     */
    @Event(R.id.bt_change_adress_add_adress)
    private void clickAddButton(View view) {
        Intent intent = new Intent(ChangeAdressActivity.this, EditAdressActivity.class);
        intent.putExtra("flag", true); //true 代表添加的标识
        startActivity(intent);
    }


    /**
     * 初始化标题
     */
    private void initTitle() {

        new TitleBuilder(this).
                setLeftImageRes(R.drawable.head_top_title_left_icon)
                .setMiddleTitleText("收获地址")
                .setLeftTextOrImageListener(titleListener)
                .initTitle(this);
    }


    /**
     * 标题栏的监听
     */
    private View.OnClickListener titleListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_left_imageview:
                    finish();
                    break;
            }

        }
    };

    /**
     * listview的适配器
     */
    class ChangeAddressListViewAdapter extends DefauListViewAdapter<Address> {

        public ChangeAddressListViewAdapter(List<Address> data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder() {
            return new ViewHolder();
        }

        class ViewHolder extends BaseHolder<Address> {
            @ViewInject(R.id.tv_change_address_item_name)
            private TextView name;
            @ViewInject(R.id.tv_change_address_item_phone)
            private TextView phone;
            @ViewInject(R.id.tv_change_address_item_address)
            private TextView address;
            @ViewInject(R.id.iv_change_address_edit_icon)
            private ImageView edit_icon;

            @Override
            public void refreshView() {
                this.name.setText(data.getAddress_name());
                this.phone.setText(data.getAddress_phone() + "");
                this.address.setText(data.getAddress_content());
                this.edit_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //这个时候应该传一个地址id过去  先模拟数据 等服务器再写
                        Intent intent = new Intent(ChangeAdressActivity.this, EditAdressActivity.class);
                        intent.putExtra("flag", false);//修改的标识
                        intent.putExtra("address_id", data.getAddress_id());//传递id过去
                        startActivity(intent);
                    }
                });

                if (data.getIscheck() == 1) {
                    name.setTextColor(Color.parseColor("#FAD611"));
                    phone.setTextColor(Color.parseColor("#FAD611"));
                    address.setTextColor(Color.parseColor("#FAD611"));
                }
            }

            @Override
            public View initView() {
                View view = View.inflate(mContext, R.layout.change_address_list_item, null);
                x.view().inject(this, view);
                return view;
            }
        }
    }
}
