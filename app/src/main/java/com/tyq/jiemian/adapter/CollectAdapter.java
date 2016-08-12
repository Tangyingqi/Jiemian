package com.tyq.jiemian.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tyq.greendao.Collect;
import com.tyq.jiemian.R;
import com.tyq.jiemian.databinding.ItemCollectBinding;
import com.tyq.jiemian.ui.activity.JmDetailActivity;
import com.tyq.jiemian.utils.Constant;

import java.util.List;

/**
 * Created by tyq on 2016/6/5.
 */
public class CollectAdapter extends RecyclerView.Adapter {

    private List<Collect> collectList;
    private LayoutInflater inflater;
    private Context context;
    public CollectAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CollectVH(context,inflater.inflate(R.layout.item_collect,parent,false));
    }

    public void addAll(List<Collect> collectList){
        this.collectList = collectList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CollectVH){
            ((CollectVH) holder).updateUi(collectList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return collectList.size();
    }

    static class CollectVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemCollectBinding dataBinding;
        private Collect collect;
        private Context context;
        public CollectVH(Context context,View itemView) {
            super(itemView);
            this.context = context;
            dataBinding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        public void updateUi(Collect collect){
            this.collect = collect;
            ImageLoader.getInstance().displayImage(collect.getImageUrl(),dataBinding.image);
            dataBinding.setTitle(collect.getTitle());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, JmDetailActivity.class);
            intent.putExtra(Constant.NEWS_URL,collect.getUrl());
            context.startActivity(intent);
        }
    }
}
