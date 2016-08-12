package com.tyq.jiemian.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tyq.jiemian.JMApplication;
import com.tyq.jiemian.R;
import com.tyq.jiemian.bean.PopularNews;
import com.tyq.jiemian.databinding.ItemPopularNewsBinding;
import com.tyq.jiemian.ui.activity.JmDetailActivity;
import com.tyq.jiemian.utils.Constant;
import com.tyq.jiemian.utils.http.VolleyHttpClient;

/**
 * Created by tyq on 2016/6/4.
 */
public class PopularListAdapter extends RecyclerView.Adapter {

    private PopularNews popularNews;
    private LayoutInflater inflater;
    private Context context;
    public PopularListAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addAll(PopularNews popularNews){
        this.popularNews = popularNews;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PopularViewHolder(context,inflater.inflate(R.layout.item_popular_news,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PopularViewHolder){
            ((PopularViewHolder) holder).updateUi(popularNews.getArticle().get(position));
        }
    }

    @Override
    public int getItemCount() {
        return popularNews == null ? 0 : popularNews.getArticle().size() > 10 ? 10 : popularNews.getArticle().size();
    }

    static class PopularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemPopularNewsBinding dataBinding;
        private PopularNews.ArticleEntity article;
        private Context context;

        public PopularViewHolder(Context context,View itemView) {
            super(itemView);
            this.context = context;
            dataBinding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }
        public void updateUi(PopularNews.ArticleEntity article){
            this.article = article;
            dataBinding.setArticle(article);
            ImageLoader.getInstance().displayImage(article.getImg(),dataBinding.image);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, JmDetailActivity.class);
            intent.putExtra(Constant.NEWS_URL,article.getUrl());
            context.startActivity(intent);
        }
    }
}
