package com.sujian.finalandroid.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.ui.TitleBuilder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

/**
 * 反馈页面
 *
 * @author 12111
 */
@ContentView(R.layout.activity_feedback)
public class FeedbackActivity extends BaseActivity {

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
                    submitEvent();
                    break;
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
