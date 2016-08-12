package com.tyq.jiemian.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tyq.greendao.HaveRead;
import com.tyq.jiemian.R;
import com.tyq.jiemian.databinding.ItemHaveReadBinding;
import com.tyq.jiemian.ui.activity.JmDetailActivity;
import com.tyq.jiemian.utils.Constant;

import java.util.List;

/**
 * Created by tyq on 2016/6/4.
 */
public class IsReadAdapter extends RecyclerView.Adapter{

    private List<HaveRead> haveReads;
    private Context context;
    private LayoutInflater inflater;
    public IsReadAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addAll(List<HaveRead> data){
        this.haveReads = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(context,inflater.inflate(R.layout.item_have_read,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).updateUi(haveReads.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return haveReads.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemHaveReadBinding dataBinding;
        private Context context;
        private HaveRead haveRead;
        public ViewHolder(Context context,View itemView) {
            super(itemView);
            this.context = context;
            dataBinding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        public void updateUi(HaveRead haveRead){
            this.haveRead = haveRead;
            ImageLoader.getInstance().displayImage(haveRead.getImageUrl(),dataBinding.image);
            dataBinding.setNews(haveRead);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, JmDetailActivity.class);
            intent.putExtra(Constant.NEWS_URL,haveRead.getUrl());
            context.startActivity(intent);
        }
    }
}
