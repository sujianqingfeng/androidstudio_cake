package com.sujian.finalandroid.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.uitls.UIUtils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.adapter.BaseWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 选择分享的窗口
 * Created by sujian on 2016/5/18.
 */
public class PublishSelectTimePopupWindow extends PopupWindow implements View.OnClickListener {

    private TextView tv_cancel;
    private TextView tv_sure;
    private WheelView wv_data;
    private WheelView wv_time;

    private List<String> list;

    private RelativeLayout rl_top;

    private View view;

    Calendar calendar = Calendar.getInstance();

    int hours;

    private SelectTimeListener selectTimeListener;

    public PublishSelectTimePopupWindow() {
        LayoutInflater inflater = (LayoutInflater) UIUtils.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.select_time_layout, null);

        tv_sure = (TextView) view.findViewById(R.id.tv_sure);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        wv_data = (WheelView) view.findViewById(R.id.wv_data);
        wv_time = (WheelView) view.findViewById(R.id.wv_time);
        rl_top = (RelativeLayout) view.findViewById(R.id.rl_top);

        tv_cancel.setOnClickListener(this);
        tv_sure.setOnClickListener(this);

        wv_data.setWheelAdapter(new ArrayWheelAdapter(x.app()));
        wv_data.setSkin(WheelView.Skin.Holo);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 14;
        style.holoBorderColor = Color.parseColor("#FAD611");
        wv_data.setStyle(style);
        wv_data.setWheelData(createDateDatas());

        wv_time.setWheelAdapter(new ArrayWheelAdapter(x.app()));
        wv_time.setSkin(WheelView.Skin.Holo);
        wv_time.setStyle(style);
        wv_time.setWheelSize(5);
        wv_time.setWheelData(createTimeDatas().get(createDateDatas().get(wv_data.getSelection())));
        wv_data.join(wv_time);
        wv_data.joinDatas(createTimeDatas());


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

                int height = view.findViewById(R.id.ll_time_root).getTop();
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

    private List<String> createDateDatas() {
        String[] strings = {"今天", "明天", "后天"};
        return Arrays.asList(strings);
    }


    private HashMap<String, List<String>> createTimeDatas() {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        String[] strings = {"今天", "明天", "后天"};


        hours = calendar.get(Calendar.HOUR_OF_DAY);
        LogUtil.e(hours + "");
        String[] t1 = creatTime(hours);
        String[] t2 = creatTime(0);
        String[] t3 = t2;
        String[][] ss = {t1, t2, t3};

        for (int i = 0; i < strings.length; i++) {
            map.put(strings[i], Arrays.asList(ss[i]));
        }
        return map;
    }

    private String[] creatTime(int hour) {
        String[] s = new String[24 - hour];
        for (int i = hour; i < 24; i++) {
            s[i - hour] = i + ":00-" + (i + 1) + ":00";
        }
        return s;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int h = 0;
                Date date = null;
                try {
                    date = new SimpleDateFormat("yy-MM-dd").parse(year + "-" + month + "-" + day);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                LogUtil.e(wv_data.getCurrentPosition() + "");

                switch (wv_data.getCurrentPosition()) {

                    case 0:
                        LogUtil.e("今天");

                        h = hours + wv_time.getCurrentPosition();
                        break;
                    case 1:
                        LogUtil.e("明天");
                        calendar.setTime(date);
                        int d = calendar.get(Calendar.DATE);
                        //加一天
                        calendar.set(Calendar.DATE, d + 1);
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH) + 1;
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                        h = wv_time.getCurrentPosition();
                        break;
                    case 2:
                        LogUtil.e("后天");
                        calendar.setTime(date);
                        int da = calendar.get(Calendar.DATE);
                        //加一天
                        calendar.set(Calendar.DATE, da + 2);
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH) + 1;
                        day = calendar.get(Calendar.DAY_OF_MONTH);
                        h = wv_time.getCurrentPosition();
                        break;
                }

                selectTimeListener.onclik(v, year, month, day, h);
                dismiss();
                break;

            case R.id.tv_cancel:
                dismiss();
                break;

        }
    }


    /**
     * data的适配器
     */
    private class DataWheelAdapter extends BaseWheelAdapter {
        @Override
        protected View bindView(int position, View convertView, ViewGroup parent) {
            Viewholder viewholder;
            if (convertView == null) {
                viewholder = new Viewholder();
                convertView = View.inflate(x.app(), R.layout.select_time_item, null);
                x.view().inject(viewholder, convertView);
                convertView.setTag(viewholder);
            } else {
                viewholder = (Viewholder) convertView.getTag();
            }
            viewholder.tv.setText(list.get(position));

            return convertView;
        }

        class Viewholder {
            @ViewInject(R.id.item_name)
            private TextView tv;
        }
    }

    public void setOnSelectTimeListener(SelectTimeListener selectTimeListener) {
        this.selectTimeListener = selectTimeListener;
    }

    public interface SelectTimeListener {
        void onclik(View view, int year, int month, int day, int hour);
    }
}
