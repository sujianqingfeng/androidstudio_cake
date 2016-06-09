package com.sujian.finalandroid.activity;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.fragment.HomeFragmentFactory;
import com.sujian.finalandroid.fragment.MenuFragment;
import com.sujian.finalandroid.ui.TitleBuilder;
import com.sujian.finalandroid.uitls.SetIconSizeUiutils;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
 * Sujian 121116111@QQ.COM
 * HomeActivity
 * TODO(主页)
 * 2016年4月13日 上午9:18:10
 */
@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity {

    //侧边菜单
    private SlidingMenu menu;

    //标题
    private TitleBuilder titleBuilder;
    //蛋糕铺标题 右图片点击弹出的窗口
    private PopupWindow popupWindow;
    // 切换的viewpager
    @ViewInject(R.id.vp_tab)
    private ViewPager vp_tab;

    // 放置单选按钮的容器
    @ViewInject(R.id.rg_group)
    private RadioGroup rg_group;
    // 首页
    @ViewInject(R.id.rb_home)
    private RadioButton rb_home;
    // 蛋糕铺
    @ViewInject(R.id.rb_cakeshop)
    private RadioButton rb_cakeshop;
    // 购物车
    @ViewInject(R.id.rb_shopcar)
    private RadioButton rb_shopcar;
    //tab 的点击事件监听
    private TabLinstener tabLinstener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        initSize();

        initTitle();

        initSlidingMenu();

        initFragments();

    }

    /**
     * 清除样式
     */
    private void clearStyle() {
        rb_home.setSelected(false);
        rb_cakeshop.setSelected(false);
        rb_shopcar.setSelected(false);
    }


    /**
     * 初始化标题
     */
    private void initTitle() {
        /**
         * 1.设置左边的图片按钮显示，以及事件 2.设置中间TextView显示的文字 3.设置右边的图片按钮显示，并设置事件
         */
        titleBuilder = new TitleBuilder(this).setLeftImageRes(R.drawable.home_title_left).setMiddleTitleText("首  页")
                .setRightImageRes(R.drawable.search).setLeftTextOrImageListener(titleListener)
                .setRightTextOrImageListener(titleListener).initTitle(this);
        //默认选择主页
        rb_home.setSelected(true);
        tabLinstener = new TabLinstener();
        // 判断是谁点击
        rg_group.setOnCheckedChangeListener(tabLinstener);
    }

    /**
     * tab 的点击事件监听
     */
    private class TabLinstener implements OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:// 主页
                    LogUtil.e("zhuye");
                    clearStyle();
                    vp_tab.setCurrentItem(0);
                    titleBuilder.setRightImageRes(R.drawable.search).setMiddleTitleText("主页");
                    rb_home.setSelected(true);
                    break;

                case R.id.rb_cakeshop:// 蛋糕铺
                    clearStyle();
                    vp_tab.setCurrentItem(1);
                    titleBuilder.setRightImageRes(R.drawable.radar).setMiddleTitleText("蛋糕铺");
                    rb_cakeshop.setSelected(true);
                    break;

                case R.id.rb_shopcar:// 购物车
                    clearStyle();
                    vp_tab.setCurrentItem(2);
                    titleBuilder.setRightIconVisiable(false).setMiddleTitleText("购物车");//不显示右边的图片
                    rb_shopcar.setSelected(true);
                    break;
            }
        }
    }


    /**
     * 标题栏的监听
     */
    private View.OnClickListener titleListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_left_imageview:
                    menu.toggle();
                    break;

                case R.id.title_right_imageview:
                    if (rb_home.isSelected()) {
                        startActivity(new Intent(HomeActivity.this, SearchActivity.class));
                    } else if (rb_cakeshop.isSelected()) {
                        initCakeTitShopleRightButton();
                        popupWindow.showAsDropDown(v);
                    }

                    break;
            }

        }
    };


    /**
     * 初始化蛋糕铺标题栏的右边按钮
     */
    private void initCakeTitShopleRightButton() {

        //popwindow 存在则消失 不存在就创建
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        }

        View view = getLayoutInflater().inflate(R.layout.popwindow_top_rigght, null, false);
        popupWindow = new PopupWindow(view, 320, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.AnimationFade); //设置动画
        //获取popwindow焦点
        //popupWindow.setFocusable(true);
        //设置popwindow如果点击外面区域，便关闭。
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
        popupWindow.setOutsideTouchable(true);

        //搜索的点击事件
        view.findViewById(R.id.ll_search).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchActivity.class));
            }
        });
        //扫一扫的点击事件
        view.findViewById(R.id.ll_scan).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(HomeActivity.this, CaptureActivity.class), 0);
            }
        });

    }


    /**
     * 设置底部tab图片的大小
     */
    private void initSize() {


        SetIconSizeUiutils setIconSizeUiutils = new SetIconSizeUiutils(mContext, R.drawable.home_button_bg_selector, 70, 70) {
            @Override
            public void setLocation(Drawable drawable) {
                rb_home.setCompoundDrawables(null, drawable, null, null);// 只放左边
            }
        };

        setIconSizeUiutils = new SetIconSizeUiutils(mContext, R.drawable.shop_button_bg_selector, 70, 70) {
            @Override
            public void setLocation(Drawable drawable) {
                rb_cakeshop.setCompoundDrawables(null, drawable, null, null);// 只放左边
            }
        };

        setIconSizeUiutils = new SetIconSizeUiutils(mContext, R.drawable.car_button_bg_selector, 70, 70) {
            @Override
            public void setLocation(Drawable drawable) {
                rb_shopcar.setCompoundDrawables(null, drawable, null, null);// 只放左边
            }
        };
    }

    /**
     * 返回的数据处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK://主要是二维码返回的数据
                String url = data.getExtras().getString("result");
                Intent intent = new Intent(this, WebActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
                break;

            case 11://购物界面返回
                rg_group.check(R.id.rb_shopcar);
                break;
        }
    }

    /**
     * 初始化fragment
     */
    private void initFragments() {

        vp_tab.setAdapter(new TabPagerAdapter(getSupportFragmentManager()));
        //滑动的时候更新数据
        vp_tab.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                HomeFragmentFactory.createFragment(position).show();
            }
        });
        vp_tab.setOffscreenPageLimit(3);
    }

    // viewPager 的适配器
    class TabPagerAdapter extends FragmentPagerAdapter {

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return HomeFragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {

            return 3;
        }

    }

    /**
     * 初始化侧边栏
     */
    private void initSlidingMenu() {
        menu = new SlidingMenu(this);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu_frame);
        // 侧边的fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, new MenuFragment()).commit();
    }

    /**
     * 物理按键的监听 主要是菜单键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU:
                menu.showMenu();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 返回键的监听 进行菜单操作
     */
    @Override
    public void onBackPressed() {
        if (menu.isMenuShowing()) {
            menu.showContent();
        } else {
            super.onBackPressed();
        }
    }

    public SlidingMenu getMenu() {
        return menu;
    }

}
