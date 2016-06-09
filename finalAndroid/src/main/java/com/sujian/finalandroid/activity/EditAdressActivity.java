package com.sujian.finalandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.ui.ChangeAddressDialog;
import com.sujian.finalandroid.ui.TitleBuilder;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 修改收货地址页面
 */
@ContentView(R.layout.activity_edit_adress)
public class EditAdressActivity extends BaseActivity {
    //所在城市
    @ViewInject(R.id.tv_edit_address_city)
    private TextView tv_edit_address_city;
    //所在地址
    @ViewInject(R.id.tv_edit_address_detailed)
    private TextView tv_edit_address_detailed;
    //百度地图的标识码
    private static final int BAIDUMAP = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        initTitle();
    }


    //点击所在城市
    @Event(R.id.tv_edit_address_city)
    private void clickCity(final View v) {
        ChangeAddressDialog mChangeAddressDialog = new ChangeAddressDialog(EditAdressActivity.this);
        mChangeAddressDialog.setAddress("四川", "自贡");
        mChangeAddressDialog.show();
        mChangeAddressDialog.setAddresskListener(new ChangeAddressDialog.OnAddressCListener() {

            @Override
            public void onClick(String province, String city) {
                TextView tv = (TextView) v;
                tv.setText(province + "--" + city);
            }
        });
    }

    /**
     * 点击地址
     */
    @Event(R.id.tv_edit_address_detailed)
    private void clickDetail(View view) {
        Intent intent = new Intent(EditAdressActivity.this, LocationActivity.class);
        startActivityForResult(intent, BAIDUMAP);

    }


    /**
     * 百度地图返回的数据处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case BAIDUMAP:
                String address = data.getExtras().getString("address", "哈哈哈哈,迷路了");
                tv_edit_address_detailed.setText(address);
                break;
        }
    }

    /**
     * 初始化标题
     */
    private void initTitle() {

        new TitleBuilder(this).setLeftImageRes(R.drawable.head_top_title_left_icon).setMiddleTitleText("收货地址")
                .setRightImageRes(R.drawable.ic_launcher).setLeftTextOrImageListener(titleListener)
                .setRightTextOrImageListener(titleListener).initTitle(this);
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

                case R.id.title_right_imageview:
                    Toast.makeText(getApplicationContext(), "右边被点击", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
}
