package com.sujian.finalandroid.activity;

import org.xutils.DbManager;
import org.xutils.common.util.LogUtil;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.table.DbModel;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import com.fyales.tagcloud.library.TagBaseAdapter;
import com.fyales.tagcloud.library.TagCloudLayout;
import com.sujian.finalandroid.adapter.HisttoryAdapter;
import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.HistorySearchEntity;
import com.sujian.finalandroid.ui.EditTextTitleBuilder;
import com.sujian.finalandroid.uitls.DataBaseUitls;
import com.sujian.finalandroid.uitls.SharedPreferencesUitls;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 搜索界面
 *
 * @author 12111
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends BaseActivity {

    //热门标签
    @ViewInject(R.id.tcl_hotsearch)
    private TagCloudLayout tcl_hotsearch;
    //热门标签数据集
    List<String> mList;
    //热门标签的适配器
    private TagBaseAdapter mAdapter;
    //历史搜索标签
    @ViewInject(R.id.tcl_historysearch)
    private TagCloudLayout tcl_historysearch;
    //历史数据集
    List<String> mHistoryList;
    //历史标签的适配器
    private HisttoryAdapter mHistoryAdapter;
    private DbManager db = DataBaseUitls.getDB(Constants.DataBaseName, 1);
    //清除历史数据按钮
    @ViewInject(R.id.bt_clearhistory)
    private Button bt_clearhistory;
    //存放历史数据的集合
    List<String> historyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initData() {
        initEditTextTitle();
        initHotSearch();
        initHistorySearch();
    }

    /**
     * 初始化标题
     */
    private void initEditTextTitle() {
        new EditTextTitleBuilder(this).initTitle(this)
                .setLeftImageRes(R.drawable.head_top_title_left_icon)
                .setRightImageRes(R.drawable.main_head_search_btn)
                .setMiddleTitleHintTextAndHintColor("请输入关键字")
                .setLeftTextOrImageListener(editTextTitleListener)
                .setRightTextOrImageListener(editTextTitleListener);
    }


    /**
     * 历史搜索 标签初始化
     */
    private void initHistorySearch() {
        mHistoryList = new ArrayList<>();
        //得到历史数据 从本地数据库

        try {
            List<HistorySearchEntity> historys = db.selector(HistorySearchEntity.class).orderBy("id", true).limit(8).findAll();
            historyList = new ArrayList<>();
            if (historys != null && historys.size() > 0) {
                bt_clearhistory.setVisibility(View.VISIBLE);
                for (HistorySearchEntity history : historys) {
                    LogUtil.e("内容--" + history.getContent() + "|id--" + history.getId());
                    historyList.add(history.getContent());
                }
            } else {
                bt_clearhistory.setVisibility(View.GONE);
            }

        } catch (DbException e) {
            e.printStackTrace();
        }

        if (historyList.size() > 0) {
            mHistoryList.addAll(historyList);
            mHistoryAdapter = new HisttoryAdapter(this, mHistoryList);
            tcl_historysearch.setAdapter(mHistoryAdapter);
            tcl_historysearch.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
                @Override
                public void itemClick(int position) {
                    Toast.makeText(mContext, mHistoryList.get(position), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    /**
     * 热门搜索 标签初始化
     */
    private void initHotSearch() {

        mList = new ArrayList<>();
        mList.add("奶酪蛋糕");
        mList.add("天使");
        mList.add("春暖花开");
        mList.add("红色樱桃");
        mList.add("更上一层楼");
        mList.add("车厘子巧克力蛋糕");
        mList.add("幸福时光");

        mAdapter = new TagBaseAdapter(this, mList);

        tcl_hotsearch.setAdapter(mAdapter);
        tcl_hotsearch.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                Toast.makeText(mContext, mList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //点击清除数据按钮
    @Event(R.id.bt_clearhistory)
    private void clickClearHistory(View v) {
        //先清空适配器里面的数据
        historyList.clear();
        LogUtil.e("历史数据集---" + historyList.toString());
        //通知适配器  数据发生变化  动态刷新数据
        mHistoryAdapter.notifyDataSetChanged();
        //再清除数据库里面的数据
        try {
            db.executeUpdateDelete(new SqlInfo("delete from historysearch where 1=1"));
            LogUtil.e("删除历史数据！");
        } catch (DbException e) {
            e.printStackTrace();
        }

        v.setVisibility(View.GONE);
    }


    /**
     * 标题栏的点击事件
     */
    private EditTextTitleBuilder.EditTextTitleListener editTextTitleListener = new EditTextTitleBuilder.EditTextTitleListener() {
        @Override
        public void onClick(View v, String serachString) {
            switch (v.getId()) {
                case R.id.title_left_imageview:
                    finish();
                    break;
                case R.id.title_right_imageview:
                    if (TextUtils.isEmpty(serachString)) {
                        Toast.makeText(mContext, "请输入搜索商品名称", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //保存数据到本地数据库
                    HistorySearchEntity history = new HistorySearchEntity();
                    history.setContent(serachString);

                    try {
                        db.save(history);
                        LogUtil.e("保存历史搜索到数据库成功！");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(SearchActivity.this, GoodsListActivity.class);
                    intent.putExtra("goods", serachString);
                    startActivity(intent);
                    break;
            }
        }
    };


}
