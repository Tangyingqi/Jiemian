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
import com.tyq.jiemian.bean.NewsItem;
import com.tyq.jiemian.databinding.JmItemNewsBinding;
import com.tyq.jiemian.ui.callback.OnItemClickListener;
import com.tyq.jiemian.utils.DBUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyq on 2015/11/16.
 */
public class JMAdapter extends RecyclerView.Adapter<JMAdapter.NewsVH> {

    private Context mContext;
    private List<NewsItem> mData;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public JMAdapter(Context context, OnItemClickListener onItemClickListener) {
        mData = new ArrayList<>();
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.onItemClickListener = onItemClickListener;
    }

    public void addAll(List<NewsItem> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public NewsItem getItem(int position) {
        if (null != mData) {
            return mData.get(position);
        }
        return null;
    }

    @Override
    public NewsVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsVH(mContext,inflater.inflate(R.layout.jm_item_news, parent, false), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(final NewsVH holder, final int position) {
        holder.updateUi(mData, position);
    }


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class NewsVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private JmItemNewsBinding dataBinding;
        private OnItemClickListener mOnItemClickListener;
        private int position;
        private NewsItem newsItem;
        private Context context;

        public NewsVH(Context context,View itemView, OnItemClickListener mOnItemClickListener) {
            super(itemView);
            dataBinding = DataBindingUtil.bind(itemView);
            dataBinding.itemView.setOnClickListener(this);
            this.mOnItemClickListener = mOnItemClickListener;
            this.context = context;
        }

        public void updateUi(List<NewsItem> newsItems, int position) {
            this.newsItem = newsItems.get(position);
            this.position = position;
            dataBinding.setTitle(newsItems.get(position).getTitle());
            dataBinding.setDate(newsItems.get(position).getDate());
            ImageLoader.getInstance().displayImage(newsItems.get(position).getImageurl(), dataBinding.image, JMApplication.getInstance().getOptionsWithRoundedCorner());
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemClick(v, position);
            if (!newsItem.isReaded()) {
                newsItem.setReaded(true);
                dataBinding.tvTitle.setTextColor(ContextCompat.getColor(context,R.color.grey));
                HaveRead haveReadNews = new HaveRead(null,newsItem.getTitle(),newsItem.getImageurl(),newsItem.getUrl());
                DBUtils.getInstance(context).addNewsToHaveRead(haveReadNews);
            }
        }
    }
}
