package com.sujian.finalandroid.ui;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.sujian.finalandroid.activity.R;
import com.sujian.widget.adapters.AbstractWheelTextAdapter;
import com.sujian.widget.views.OnWheelChangedListener;
import com.sujian.widget.views.OnWheelScrollListener;
import com.sujian.widget.views.WheelView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 职业选择dialog
 *
 * @author 12111
 */

public class ProfessionDialog extends Dialog implements android.view.View.OnClickListener {

    private Context context;
    private WheelView wvProfession;
    private ArrayList<String> arrProfessions = new ArrayList<String>();
    private int maxsize = 24;
    private int minsize = 14;
    private View lyProfession;
    private View lyProfessionressChild;
    private TextView btnSure;
    private TextView btnCancel;
    private String strProfession = "学生";
    private ProfessionTextAdapter professionTextAdapter;
    private OnProfessionListener onProfessionListener;

    public ProfessionDialog(Context context) {
        super(context, R.style.ShareDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_myinfo_profession);
        wvProfession = (WheelView) findViewById(R.id.wv_profession);

        lyProfession = findViewById(R.id.ly_myinfo_profession);
        lyProfessionressChild = findViewById(R.id.ly_myinfo_profession_child);
        btnSure = (TextView) findViewById(R.id.btn_myinfo_sure);
        btnCancel = (TextView) findViewById(R.id.btn_myinfo_cancel);
        initData();

        lyProfession.setOnClickListener(this);
        lyProfessionressChild.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        professionTextAdapter = new ProfessionTextAdapter(context, arrProfessions, getProfessionItem(strProfession), maxsize, minsize);

        wvProfession.setVisibleItems(3);
        wvProfession.setViewAdapter(professionTextAdapter);
        wvProfession.setCurrentItem(getProfessionItem(strProfession));

        wvProfession.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

                String currentText = (String) professionTextAdapter.getItemText(wheel.getCurrentItem());
                strProfession = currentText;
                setTextviewSize(currentText, professionTextAdapter);

            }

        });


        wvProfession.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) professionTextAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, professionTextAdapter);
            }
        });


    }

    /**
     * 设置监听
     *
     * @param onProfessionListenerth
     */
    public void setOnProfessionListener(OnProfessionListener onProfessionListenerth) {
        this.onProfessionListener = onProfessionListenerth;
    }

    /**
     * 按钮的监听事件
     */
    @Override
    public void onClick(View v) {
        if (v == btnSure) {
            if (onProfessionListener != null) {
                try {
                    onProfessionListener.onClick(strProfession);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        } else if (v == btnCancel) {

        } else if (v == lyProfessionressChild) {
            return;
        } else {
            dismiss();
        }
        dismiss();
    }

    /**
     * 设置默认的职业
     *
     * @param profession
     */
    public void setProfession(String profession) {
        if (profession != null && profession.length() > 0) {
            this.strProfession = profession;
        }
    }


    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    private void setTextviewSize(String curriteItemText, ProfessionTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(24);
            } else {
                textvew.setTextSize(14);
            }
        }

    }

    /**
     * 根据名称返回索引 感觉这个方法有问题
     *
     * @param strProfession2
     * @return
     */
    private int getProfessionItem(String strProfession2) {
        int size = arrProfessions.size();
        int professionIndex = 0;
        boolean noprofession = true;
        for (int i = 0; i < size; i++) {
            if (strProfession2.equals(arrProfessions.get(i))) {
                noprofession = false;
                return professionIndex;
            } else {
                professionIndex++;
            }
        }
        if (noprofession) {
            strProfession = "其他";
            return 7;
        }
        return professionIndex;
    }

    private void initData() {
        arrProfessions.add("学生");
        arrProfessions.add("老师");
        arrProfessions.add("it");
        arrProfessions.add("a");
        arrProfessions.add("b");
        arrProfessions.add("c");
        arrProfessions.add("d");
        arrProfessions.add("其他");
    }


    private class ProfessionTextAdapter extends AbstractWheelTextAdapter {
        ArrayList<String> list;

        protected ProfessionTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize,
                                        int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    /**
     * 回掉接口
     *
     * @author 12111
     */
    public interface OnProfessionListener {
        public void onClick(String profession) throws UnsupportedEncodingException;
    }

}
