package com.sujian.finalandroid.activity;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import org.xutils.x;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.sujian.finalandroid.uitls.DepthPageTransformer;
import com.sujian.finalandroid.uitls.MyActivityManager;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @author Sujian 121116111@QQ.COM
 * @ClassName: GuideAcrivity
 * @Description: TODO(引导页面)
 * @date 2016年4月11日 下午11:07:27
 */
@ContentView(R.layout.activity_guide)
public class GuideAcrivity extends Activity {

    @ViewInject(R.id.vp_guide)
    private ViewPager vp_guide;

    //viewpager指示器
    @ViewInject(R.id.indicator)
    private CirclePageIndicator indicator;

    //开始体验按钮
    @ViewInject(R.id.bt_strat)
    private Button bt_strat;

    // 图片id数组
    private static final int[] imagesIds = new int[]{R.drawable.guide_image1, R.drawable.guide_image2,
            R.drawable.guide_image3};
    // 图片集合
    private List<ImageView> imagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        MyActivityManager.getMyActivityManager().addActivity(this);
        initViews();
        vp_guide.setAdapter(new GuideAdapter());
        vp_guide.setPageTransformer(true, new DepthPageTransformer());
        indicator.setViewPager(vp_guide);

        indicator.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == imagesIds.length - 1) {
                    bt_strat.setVisibility(View.VISIBLE);
                } else {
                    bt_strat.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * @param
     * @return void
     * @throws
     * @Title: goLogin
     * @Description: TODO(跳转登陆)
     */
    @Event(value = R.id.bt_strat)
    private void goHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * @param @return void @throws
     * @Title: initViews
     * @Description: TODO(初始化一些视图数据)
     */
    private void initViews() {
        imagesList = new ArrayList<ImageView>();
        for (int i = 0; i < imagesIds.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imagesIds[i]);
            imagesList.add(imageView);
        }
    }

    /**
     * @author Sujian 121116111@QQ.COM
     * @ClassName: GuideAdapter
     * @Description: TODO(viewpager的适配器)
     * @date 2016年4月11日 下午11:33:38
     */
    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imagesIds.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imagesList.get(position));
            return imagesList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyActivityManager.getMyActivityManager().finishActivity(this);
    }

}
