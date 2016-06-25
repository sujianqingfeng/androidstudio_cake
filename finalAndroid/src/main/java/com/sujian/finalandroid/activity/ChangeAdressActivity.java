package com.sujian.finalandroid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.sujian.finalandroid.ui.TitleBuilder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sujian  改变收获地址界面
 */
@ContentView(R.layout.activity_change_adress)
public class ChangeAdressActivity extends BaseActivity {

    @ViewInject(R.id.lv_change_adress)
    private ListView lv_change_adress;

    @ViewInject(R.id.bt_change_adress_add_adress)
    private Button bt_change_adress_add_adress;

    // 装载数据集合
    List<Map<String, Object>> dataLists;
    Map<String, Object> map;

    private ChangeAddressListViewAdapter changeAddressListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        initTitle();
        initChangeAdressListView();
    }

    /**
     * 初始化listview
     */
    private void initChangeAdressListView() {
        dataLists = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 10; i++) {
            map = new HashMap<String, Object>();
            map.put("name", "名字" + i);
            map.put("phone", "1519817391" + i);
            map.put("address", "地址地址地址地址地址地址地址地址" + i);
            dataLists.add(map);
        }
        lv_change_adress.setAdapter(new ChangeAddressListViewAdapter(dataLists));
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

        new TitleBuilder(this).setLeftImageRes(R.drawable.head_top_title_left_icon).setMiddleTitleText("收获地址")
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

    /**
     * listview的适配器
     */
    class ChangeAddressListViewAdapter extends DefauListViewAdapter<Map<String, Object>> {

        public ChangeAddressListViewAdapter(List<Map<String, Object>> data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder() {
            return new ViewHolder();
        }

        class ViewHolder extends BaseHolder<Map<String, Object>> {
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
                this.name.setText((String) data.get("name"));
                this.phone.setText((String) data.get("phone"));
                this.address.setText((String) data.get("address"));
                this.edit_icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //这个时候应该传一个地址id过去  先模拟数据 等服务器再写
                        Intent intent = new Intent(ChangeAdressActivity.this, EditAdressActivity.class);
                        intent.putExtra("flag", false);//修改的标识
                        startActivity(intent);
                    }
                });
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
