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
import com.sujian.finalandroid.ui.LoadingPage;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户消息fragment
 */
public class UserNewFragment extends BaseFragment {


    //listview 用户消息列表
    @ViewInject(R.id.lv_user_news)
    private ListView lv_user_news;

    //放置数据的集合
    List<Map<String, Object>> listDatas;

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
    }

    /**
     * 初始化listview
     */
    private void initListView() {

        listDatas = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (int i = 0; i < 5; i++) {
            map = new HashMap<String, Object>();
            map.put("title", "标题标题标题标题标题" + i);
            map.put("content", "内容内容内容内容内容内容" + i);
            listDatas.add(map);
        }
        lv_user_news.setAdapter(new UserNewAdapter(listDatas));

    }


    private class UserNewAdapter extends DefauListViewAdapter<Map<String, Object>> {
        public UserNewAdapter(List<Map<String, Object>> data) {
            super(data);
        }

        @Override
        public BaseHolder getHolder() {
            return new ViewHolder();
        }

        private class ViewHolder extends BaseHolder<Map<String, Object>> {

            @ViewInject(R.id.tv_new_item_title)
            private TextView tv_new_item_title;

            @ViewInject(R.id.tv_new_item_content)
            private TextView tv_new_item_content;

            @Override
            protected void refreshView() {
                tv_new_item_title.setText((String) data.get("title"));
                tv_new_item_content.setText((String) data.get("content"));
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
