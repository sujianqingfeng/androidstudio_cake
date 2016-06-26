package com.sujian.finalandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.Feedback;
import com.sujian.finalandroid.entity.FeedbackCallBackEntity;
import com.sujian.finalandroid.net.FeedbackCallBack;
import com.sujian.finalandroid.ui.TitleBuilder;
import com.sujian.finalandroid.uitls.MyUitls;
import com.sujian.finalandroid.uitls.ToastUitls;
import com.zhy.http.okhttp.OkHttpUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import okhttp3.Call;

/**
 * 反馈页面
 *
 * @author 12111
 */
@ContentView(R.layout.activity_feedback)
public class FeedbackActivity extends BaseActivity {

    private Feedback feedback = new Feedback();
    @ViewInject(R.id.ed_feedback)
    private EditText ed_feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        initTitle();
    }


    /**
     * 初始化标题
     */
    private void initTitle() {

        new TitleBuilder(this).setLeftImageRes(R.drawable.head_top_title_left_icon).setMiddleTitleText("意见反馈")
                .setRightText("提交").setLeftTextOrImageListener(titleListener)
                .setRightTextOrImageListener(titleListener).initTitle(this);

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

                case R.id.title_right_textview:
                    try {
                        String content = URLEncoder.encode(ed_feedback.getText().toString(), "UTF-8");

                        String url = Constants.SERVICEADDRESS + "feedback/feedback_addFeedback.action";
                        OkHttpUtils
                                .get()
                                .url(url)
                                .addParams("feedback_content", content)
                                .build()
                                .execute(new FeedbackCallBack() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        LogUtil.e("蒲琳傻逼    失败了，失败了");
                                    }

                                    @Override
                                    public void onResponse(FeedbackCallBackEntity response, int id) {

                                        LogUtil.e("成功成功成功成功成功成功成功成功成功成功成功成功成功成功" + response.toString());
                                        LogUtil.e(ed_feedback.getText().toString());


                                        if (response.isSuccess()) {
                                            ToastUitls.show("反馈成功");

                                        } else {
                                            Toast.makeText(x.app(), "反馈失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                        // submitEvent();
                        break;

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


            }

        }
    };

    /**
     * 文字提交事件处理
     */
    private void submitEvent() {
        String content = ed_feedback.getText().toString().trim();
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

}
