package com.sujian.finalandroid.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.uitls.UIUtils;

/**
 * 选择分享的窗口
 * Created by sujian on 2016/5/18.
 */
public class PublishSelectSharePopupWindow extends PopupWindow {

    //微信
    private Button btn_weChat;
    //朋友圈
    private Button btn_circleOfFriends;
    //新浪微博
    private Button btn_microlog;
    //qq空间
    private Button btn_qzone;
    private View view;

    public PublishSelectSharePopupWindow(View.OnClickListener itemListener) {
        LayoutInflater inflater = (LayoutInflater) UIUtils.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.select_share_layout, null);
        btn_weChat = (Button) view.findViewById(R.id.btn_weChat);
        btn_circleOfFriends = (Button) view.findViewById(R.id.btn_circleOfFriends);
        btn_microlog = (Button) view.findViewById(R.id.btn_microlog);
        btn_qzone = (Button) view.findViewById(R.id.btn_qzone);

        btn_weChat.setOnClickListener(itemListener);
        btn_circleOfFriends.setOnClickListener(itemListener);
        btn_microlog.setOnClickListener(itemListener);
        btn_qzone.setOnClickListener(itemListener);


        //设置PopupWindow的View
        this.setContentView(view);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置PopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //View添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.ll_share_root).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}
