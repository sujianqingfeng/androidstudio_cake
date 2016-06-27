package com.sujian.finalandroid.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sujian.finalandroid.base.BaseActivity;
import com.sujian.finalandroid.ui.TitleBuilder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 帮助中心
 */
@ContentView(R.layout.activity_help_center)
public class HelpCenterActivity extends BaseActivity {

    //可展开的listview
    @ViewInject(R.id.elv_help)
    private ExpandableListView elv_help;

    private List<String> parent;
    private List<String> child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        initTitle();
        initExpandableListView();
    }

    /**
     * 初始化可展开的listview
     */
    private void initExpandableListView() {

        //先加载数据
        parent = new ArrayList<String>();
        child = new ArrayList<String>();

        for (int i = 0; i < 10; i++) {
            parent.add("问题问题问题问题" + i);
            child.add("回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答回答" + i);
        }

        elv_help.setAdapter(new HelpCenterAdapter());
        elv_help.setGroupIndicator(null);
    }


    /**
     * 初始化标题
     */
    private void initTitle() {
        new TitleBuilder(this)
                .initTitle(this)
                .setLeftImageRes(R.drawable.head_top_title_left_icon)
                .setMiddleTitleText("常见问题")
                .setLeftTextOrImageListener(titleListener);

    }

    /**
     * 标题栏的监听
     */
    private View.OnClickListener titleListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_left_imageview:
                    finish();
                    break;


            }

        }
    };

    /**
     * 帮助中心的适配器
     */
    class HelpCenterAdapter extends BaseExpandableListAdapter {


        @Override
        public int getGroupCount() {
            return parent.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return parent.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return child.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup p) {
            ParentViewHolder patentViewHolder;
            if (convertView == null) {
                patentViewHolder = new ParentViewHolder();
                convertView = View.inflate(mContext, R.layout.help_center_parent_layout, null);
                x.view().inject(patentViewHolder, convertView);
                convertView.setTag(patentViewHolder);
            } else {
                patentViewHolder = (ParentViewHolder) convertView.getTag();
            }

            patentViewHolder.tv.setText(parent.get(groupPosition));

            if (isExpanded) {
                patentViewHolder.iv.setBackgroundResource(R.drawable.icon_arrow_up);
            } else {
                patentViewHolder.iv.setBackgroundResource(R.drawable.icon_arrow_down);
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder childViewHolder;
            if (convertView == null) {
                childViewHolder = new ChildViewHolder();
                convertView = View.inflate(mContext, R.layout.help_center_child_layout, null);
                x.view().inject(childViewHolder, convertView);
                convertView.setTag(childViewHolder);
            } else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }

            childViewHolder.tv.setText(child.get(groupPosition));


            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

    class ParentViewHolder {
        @ViewInject(R.id.tv_help_center_parent_problem)
        private TextView tv;
        @ViewInject(R.id.iv_help_center_parent_right_icon)
        private ImageView iv;
    }

    class ChildViewHolder {
        @ViewInject(R.id.tv_help_center_child_answer)
        private TextView tv;
    }
}
