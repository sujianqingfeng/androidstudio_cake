package com.sujian.finalandroid.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.sujian.finalandroid.activity.R;
import com.sujian.finalandroid.constant.Constants;
import com.sujian.finalandroid.entity.Commodity;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;


/**
 * 图片的适配器
 * Created by sujian on 2016/5/30.
 * Mail:121116111@qq.com
 */
public class CheeseCakeAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Commodity> data;
    private ImageOptions options;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public CheeseCakeAdapter(Context context, List<Commodity> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.cheesecake_recycler_item, parent, false);
        MyViewHolder vh = new MyViewHolder(inflate);
        options = new ImageOptions.Builder().setLoadingDrawableId(R.drawable.ic_launcher)
                .setFailureDrawableId(R.drawable.ic_launcher).setUseMemCache(true).build();
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myHolder = (MyViewHolder) holder;
        myHolder.itemView.setTag(position);
        Commodity commodity = data.get(position);
        myHolder.title.setText(commodity.getCommodity_name());
        myHolder.price.setText("$ " + commodity.getCommodity_price());
        myHolder.size.setText("" + commodity.getCommodity_size() + "寸");
        x.image().bind(myHolder.picture, Constants.SERVICEADDRESS + commodity.getDescription_pcture(), options);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @ViewInject(R.id.tv_cheese_title)
        TextView title;
        @ViewInject(R.id.iv_cheese_img)
        ImageView picture;

        @ViewInject(R.id.tv_cheese_size)
        TextView size;

        @ViewInject(R.id.tv_cheese_price)
        TextView price;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            x.view().inject(this, itemView);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
            }
        }
    }

    /**
     * 点击接口
     */
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

}
