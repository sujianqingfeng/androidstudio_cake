package com.sujian.finalandroid.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.ui.TitleBuilder;
import com.sujian.finalandroid.uitls.MyUitls;
import com.sujian.finalandroid.uitls.ToastUitls;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * @author Sujian  121116111@QQ.COM
 * @ClassName: MySettingsActivity
 * @Description: TODO(我的设置)
 * @date 2016年4月17日 下午6:04:17
 */
@ContentView(R.layout.activity_mysettings)
public class MySettingsActivity extends BaseActivity {
    //设置列表
    @ViewInject(R.id.setting_listview)
    private ListView settingListView;

    @ViewInject(R.id.bt_setting_sing_out)
    private Button bt_setting_sing_out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化分享
        ShareSDK.initSDK(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }

    @Override
    protected void initData() {
        initTitle();
        initListView();
        initSingOut();
    }

    /**
     * 退出按钮显示判断
     */
    private void initSingOut() {
        bt_setting_sing_out.setVisibility(MyUitls.isUserExistence() ? View.VISIBLE : View.GONE);
    }


    /**
     * 初始化listview
     */
    private void initListView() {
        int[] img = {R.drawable.receiptaddress, R.drawable.common_problem, R.drawable.share_app, R.drawable.feedback, R.drawable.current_version, R.drawable.about_us};
        String imgString[] = {"收货地址", "常见问题", "分享app", "意见反馈", "当前版本", "关于我们"};
        String rightString[] = {"", "", "", "", MyUitls.getVersionName(mContext), ""};

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < img.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", img[i]);
            map.put("imgString", imgString[i]);
            map.put("rightString", rightString[i]);
            list.add(map);
        }

        SimpleAdapter simAdapter = new SimpleAdapter(this, list, R.layout.setting_item, new String[]{"img", "imgString", "rightString"}, new int[]{R.id.menu_item_img, R.id.menu_item_text, R.id.menu_right_text});
        settingListView.setAdapter(simAdapter);
        //菜单列表点击
        settingListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MySettingsActivity.this, ChangeAdressActivity.class));
                        break;


                    case 1:
                        startActivity(new Intent(MySettingsActivity.this, HelpCenterActivity.class));
                        break;

                    case 2:
                        startShare();
                        break;
                    case 3:
                        startActivity(new Intent(MySettingsActivity.this, FeedbackActivity.class));
                        break;
                    case 4:

                        break;

                    case 5:
                        startActivity(new Intent(MySettingsActivity.this, AboutUsActivity.class));
                        break;

                }
            }
        });//点击事件结束
    }

    /**
     * 开始分享
     */
    private void startShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题：微信、QQ（新浪微博不需要标题）
        oks.setTitle("我是分享标题");  //最多30个字符

        // text是分享文本：所有平台都需要这个字段
        oks.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");  //最多40个字符

        // imagePath是图片的本地路径：除Linked-In以外的平台都支持此参数
        //oks.setImagePath(Environment.getExternalStorageDirectory() + "/meinv.jpg");//确保SDcard下面存在此张图片

        //网络图片的url：所有平台
        oks.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul

        // url：仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情

        // Url：仅在QQ空间使用
        oks.setTitleUrl("http://www.baidu.com");  //网友点进链接后，可以看到分享的详情

        // 启动分享GUI
        oks.show(this);
    }


    /**
     * 初始化标题
     */
    private void initTitle() {
        /**
         * 1.设置左边的图片按钮显示，以及事件 2.设置中间TextView显示的文字 3.设置右边的图片按钮显示，并设置事件
         */
        new TitleBuilder(this).setLeftImageRes(R.drawable.head_top_title_left_icon).setMiddleTitleText("我的设置")
                .setLeftTextOrImageListener(titleListener)
                .initTitle(this);

    }

    /**
     * 标题栏的监听
     */
    private View.OnClickListener titleListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_left_imageview:
                    finish();
                    break;

                case R.id.title_right_imageview:
                    break;
            }
        }
    };

    /**
     * 点击退出账号
     *
     * @param v
     */
    @Event(R.id.bt_setting_sing_out)
    private void clickSingOut(View v) {
        MyUitls.setUid(0);
        ToastUitls.show("退出成功");
        //未完成  还要清除sp里面的数据


        bt_setting_sing_out.setVisibility(View.GONE);
    }


}
