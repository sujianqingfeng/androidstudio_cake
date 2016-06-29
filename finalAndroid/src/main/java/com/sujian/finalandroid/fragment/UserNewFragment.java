package com.sujian.finalandroid.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.adapter.DefauListViewAdapter;
import com.sujian.finalandroid.base.BaseFragment;
import com.sujian.finalandroid.base.BaseHolder;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.Message;
import com.sujian.finalandroid.entity.MessageCallBackEntity;
import com.sujian.finalandroid.net.MessageCallback;
import com.sujian.finalandroid.ui.LoadingPage;
import com.sujian.finalandroid.uitls.ToastUitls;
import com.zhy.http.okhttp.OkHttpUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * 用户消息fragment
 */
public class UserNewFragment extends BaseFragment {


    //listview 用户消息列表
    @ViewInject(R.id.lv_user_news)
    private ListView lv_user_news;
    //listview 的适配器
    private UserNewAdapter userAdapter;
    //放置数据的集合
    List<Message> dataLists;
    @Override
    protected View createSuccessView() {
        View view = View.inflate(x.app(), R.layout.fragment_user_new, null);
        return view;
    }

    @Override
    protected LoadingPage.LoadResult load() {

        return LoadingPage.LoadResult.success;
    }


    @Override
    public void initDatas(View view) {
        show();
        super.initDatas(view);
        initListView();
        getDateMessage();
    }

    /**
     * 初始化listview
     */
    private void initListView() {
        dataLists = new ArrayList<>();
        userAdapter = new UserNewAdapter(dataLists);
        lv_user_news.setAdapter(userAdapter);
    }


    /**
     * 从服务器得到数据
     */
    private void getDateMessage() {
        String url = Constants.SERVICEADDRESS + "message/message_messageselect.action";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new MessageCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtil.e("解析失败");
                    }

                    @Override
                    public void onResponse(MessageCallBackEntity response, int id) {
                        LogUtil.e("******************************************");
                        LogUtil.e(response.getMessage().toString());

                        if (response.isSuccess()) {
                            dataLists.addAll(response.getMessage());
                            userAdapter.notifyDataSetChanged();
                            show();
                        } else {
                            ToastUitls.show("获取数据失败！");
                        }
                    }
                });

    }


    private class UserNewAdapter extends DefauListViewAdapter<Message> {

        public UserNewAdapter(List<Message> data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder() {
            return new ViewHolder();
        }

        private class ViewHolder extends BaseHolder<Message> {

            @ViewInject(R.id.tv_new_item_title)
            private TextView tv_new_item_title;

            @ViewInject(R.id.tv_new_item_content)
            private TextView tv_new_item_content;

            @Override
            protected void refreshView() {
                tv_new_item_title.setText(data.getMessage_title());
                tv_new_item_content.setText(data.getMessage_content());
            }

            @Override
            public View initView() {
                View view = View.inflate(x.app(), R.layout.news_list_item_layout, null);
                x.view().inject(this, view);
                return view;
            }
        }
    }


}
