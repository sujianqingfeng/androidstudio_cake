package com.sujian.finalandroid.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

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
import com.sujian.finalandroid.entity.UpdateUserCallBackEntity;
import com.sujian.finalandroid.entity.User;
import com.sujian.finalandroid.net.UpdateUserCallBack;
import com.sujian.finalandroid.ui.CircleImageView;
import com.sujian.finalandroid.ui.LoadingPage;
import com.sujian.finalandroid.uitls.MyUitls;
import com.sujian.finalandroid.uitls.SharedPreferencesUitls;
import com.sujian.finalandroid.uitls.ToastUitls;
import com.zhy.http.okhttp.OkHttpUtils;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import okhttp3.Call;


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
                        if (MyUitls.isUserExistence()) {
                            getActivity().startActivity(new Intent(getActivity(), MyOrderActivity.class));
                            ((HomeActivity) getActivity()).getMenu().toggle();
                            break;
                        } else {
                            ToastUitls.show("请先登录");
                            break;
                        }


                    case 1:
                        if (MyUitls.isUserExistence()) {
                            getActivity().startActivity(new Intent(getActivity(), MyNewsActivity.class));
                            ((HomeActivity) getActivity()).getMenu().toggle();
                            break;
                        } else {
                            ToastUitls.show("请先登录");
                            break;
                        }

                    case 2:
                        if (MyUitls.isUserExistence()) {
                        getActivity().startActivity(new Intent(getActivity(), MySettingsActivity.class));
                        ((HomeActivity) getActivity()).getMenu().toggle();
                        break;
                        } else {
                            ToastUitls.show("请先登录");
                            break;
                        }

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
            LogUtil.e("这里为什么会执行！");
            getDateFromService();
            bt_login.setVisibility(View.GONE);
            bt_reg.setVisibility(View.GONE);
            user_account.setVisibility(View.VISIBLE);
            user_account.setText("账户  " + SharedPreferencesUitls.getStringValue(Constants.USER_ACOUNT, "无"));
        } else {
            civ_pic.setImageDrawable(getResources().getDrawable(R.drawable.app_icon));
            user_account.setVisibility(View.GONE);
            bt_login.setVisibility(View.VISIBLE);
            bt_reg.setVisibility(View.VISIBLE);
        }
    }


    private void getDateFromService() {
        String url = Constants.SERVICEADDRESS + "user/user_select.cake";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("user_id", String.valueOf(MyUitls.getUid()))
                .build()
                .execute(new UpdateUserCallBack() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(UpdateUserCallBackEntity response, int id) {

                        if (response != null) {
                            User u = response.getUser();
                            if (u != null) {
                                //加载头像
                                ImageOptions options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.ic_launcher)
                                        .setFailureDrawableId(R.drawable.ic_launcher).setUseMemCache(true).build();

                                x.image().loadDrawable(Constants.SERVICEADDRESS + u.getUser_head(), options, new Callback.CommonCallback<Drawable>() {
                                    @Override
                                    public void onSuccess(Drawable drawable) {
                                        civ_pic.setImageDrawable(drawable);
                                    }

                                    @Override
                                    public void onError(Throwable throwable, boolean b) {

                                    }

                                    @Override
                                    public void onCancelled(CancelledException e) {

                                    }

                                    @Override
                                    public void onFinished() {

                                    }
                                });
                            }

                        }
                    }
                });
    }
}
