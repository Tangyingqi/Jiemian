package com.tyq.jiemian.adapter.VH;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.tool.util.L;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.tyq.jiemian.adapter.PopularListAdapter;
import com.tyq.jiemian.bean.PopularNews;
import com.tyq.jiemian.databinding.ItemPopularListBinding;
import com.tyq.jiemian.presenter.HomePresenter;
import com.tyq.jiemian.ui.callback.HomeInterface;

/**
 * Created by tyq on 2016/6/4.
 */
public class PopularViewHolder extends RecyclerView.ViewHolder implements HomeInterface {
    private ItemPopularListBinding dataBinding;
    private PopularListAdapter adapter;
    private HomePresenter presenter;

    public PopularViewHolder(Context context, View itemView) {
        super(itemView);
        dataBinding = DataBindingUtil.bind(itemView);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        dataBinding.recyclerView.setHasFixedSize(true);
        dataBinding.recyclerView.setLayoutManager(manager);
        presenter = new HomePresenter(context);
        presenter.requestPopularList(this);
        adapter = new PopularListAdapter(context);
        dataBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRequestPopularNewsSuccess(PopularNews news) {
        adapter.addAll(news);
    }

    @Override
    public void onRequestPopularNewsFailed(String errMsg) {

    }
}
