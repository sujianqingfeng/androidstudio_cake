package com.sujian.finalandroid.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.sujian.finalandroid.activity.HomeActivity;
import com.sujian.finalandroid.activity.LoginActivity;
import com.sujian.finalandroid.activity.MyNewsActivity;
import com.sujian.finalandroid.activity.MyOrderActivity;
import com.sujian.finalandroid.activity.MySettingsActivity;
import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.activity.RegisterActivity;
import com.sujian.finalandroid.activity.UpdataActivity;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.ui.CircleImageView;
import com.sujian.finalandroid.ui.LoadingPage;
import com.sujian.finalandroid.uitls.MyUitls;
import com.sujian.finalandroid.uitls.SharedPreferencesUitls;
import com.sujian.finalandroid.uitls.ToastUitls;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class MenuFragment extends BaseFragment {


    //头像图片
    @ViewInject(R.id.civ_pic)
    private CircleImageView civ_pic;
    //list菜单列表
    @ViewInject(R.id.menu_listview)
    private ListView menu_listview;
    //登陆按钮
    @ViewInject(R.id.bt_login)
    private Button bt_login;
    //注册按钮
    @ViewInject(R.id.bt_reg)
    private Button bt_reg;
    //账户
    @ViewInject(R.id.user_account)
    private TextView user_account;

    private SimpleAdapter simAdapter;
    private List<Map<String, Object>> list;


    @Override
    protected View createSuccessView() {
        return View.inflate(getActivity(), R.layout.menu_layout, null);
    }

    @Override
    protected LoadingPage.LoadResult load() {
        return LoadingPage.LoadResult.success;
    }

    @Override
    public void initDatas(View view) {
        show();
        initList();
    }

    //初始化listview
    private void initList() {
        int[] img = {R.drawable.my_order, R.drawable.my_message, R.drawable.my_setting};
        String imgString[] = {"我的订单", "我的消息", "我的设置"};

        list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < img.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", img[i]);
            map.put("imgString", imgString[i]);
            list.add(map);
        }

        simAdapter = new SimpleAdapter(getActivity(), list, R.layout.menu_item, new String[]{"img", "imgString"}, new int[]{R.id.menu_item_img, R.id.menu_item_text});
        menu_listview.setAdapter(simAdapter);

        //菜单列表点击
        menu_listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        getActivity().startActivity(new Intent(getActivity(), MyOrderActivity.class));
                        ((HomeActivity) getActivity()).getMenu().toggle();
                        break;

                    case 1:
                        getActivity().startActivity(new Intent(getActivity(), MyNewsActivity.class));
                        ((HomeActivity) getActivity()).getMenu().toggle();
                        break;

                    case 2:
                        getActivity().startActivity(new Intent(getActivity(), MySettingsActivity.class));
                        ((HomeActivity) getActivity()).getMenu().toggle();
                        break;


                }
            }
        });//点击事件结束


    }

    //头像点击
    @Event(R.id.civ_pic)
    private void clickPic(View view) {

        if (MyUitls.isUserExistence()) {
            startActivity(new Intent(getActivity(), UpdataActivity.class));
            ((HomeActivity) getActivity()).getMenu().toggle();
        } else {
            ToastUitls.show("请先登录！");
        }

    }

    //点击登陆
    @Event(R.id.bt_login)
    private void clickLogin(View view) {
        goLogin();
        ((HomeActivity) getActivity()).getMenu().toggle();
    }

    //头像点击
    @Event(R.id.bt_reg)
    private void clickRegister(View view) {
        startActivity(new Intent(getActivity(), RegisterActivity.class));
        ((HomeActivity) getActivity()).getMenu().toggle();
    }

    /**
     * TODO(跳转登陆)
     */
    private void goLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        ((HomeActivity) getActivity()).getMenu().toggle();
    }

    /**
     * 生命周期
     */
    @Override
    public void onResume() {
        super.onResume();
        if (MyUitls.isUserExistence()) {
            bt_login.setVisibility(View.GONE);
            bt_reg.setVisibility(View.GONE);
            user_account.setVisibility(View.VISIBLE);
            user_account.setText("账户" + SharedPreferencesUitls.getStringValue(Constants.USER_ACOUNT, "无"));
        } else {
            user_account.setVisibility(View.GONE);
            bt_login.setVisibility(View.VISIBLE);
            bt_reg.setVisibility(View.VISIBLE);
        }
    }
}
