package com.tyq.jiemian.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tyq.greendao.HaveRead;
import com.tyq.jiemian.JMApplication;
import com.tyq.jiemian.R;
import com.tyq.jiemian.bean.StoriesEntity;
import com.tyq.jiemian.databinding.ZhihuItemNewsBinding;
import com.tyq.jiemian.ui.callback.OnItemClickListener;
import com.tyq.jiemian.utils.DBUtils;

import java.util.List;

/**
 * Created by tyq on 2016/5/15.
 */
public class ZhihuAdapter extends RecyclerView.Adapter<ZhihuAdapter.ZhihuVH> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<StoriesEntity> newsList;
    private OnItemClickListener onItemClickListener;


    public ZhihuAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void addAll(List<StoriesEntity> newsList,OnItemClickListener onItemClickListener) {
        this.newsList = newsList;
        notifyDataSetChanged();
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ZhihuVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZhihuVH(mInflater.inflate(R.layout.zhihu_item_news, parent, false),onItemClickListener);
    }

    @Override
    public void onBindViewHolder(ZhihuVH holder, int position) {
        holder.updateUi(newsList,position);
    }

    @Override
    public int getItemCount() {
        return newsList == null ? 0 : newsList.size();
    }

    public class ZhihuVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ZhihuItemNewsBinding dataBinding;
        private OnItemClickListener mOnItemClickListener;
        private int position;

        public ZhihuVH(View itemView,OnItemClickListener mOnItemClickListener) {
            super(itemView);
            dataBinding = DataBindingUtil.bind(itemView);
            dataBinding.itemView.setOnClickListener(this);
            this.mOnItemClickListener = mOnItemClickListener;
        }

        public void updateUi(List<StoriesEntity> newsList,int position) {
            this.position = position;
            dataBinding.setTitle(newsList.get(position).getTitle());
            if (newsList.get(position).getImages().size() > 0)
            ImageLoader.getInstance().displayImage(newsList.get(position).getImages().get(0), dataBinding.image, JMApplication.getInstance().getOptionsWithRoundedCorner());
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemClick(v,position);
        }
    }
}
