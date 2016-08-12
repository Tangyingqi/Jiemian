package com.tyq.jiemian.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyq.greendao.HaveRead;
import com.tyq.jiemian.R;
import com.tyq.jiemian.adapter.ZhihuAdapter;
import com.tyq.jiemian.bean.NewsEntity;
import com.tyq.jiemian.databinding.FragmentZhihuBinding;
import com.tyq.jiemian.presenter.ZhihuPresenter;
import com.tyq.jiemian.ui.activity.ZhDetailActivity;
import com.tyq.jiemian.ui.callback.OnItemClickListener;
import com.tyq.jiemian.ui.callback.ZhihuApi;
import com.tyq.jiemian.utils.Constant;
import com.tyq.jiemian.utils.DBUtils;
import com.tyq.jiemian.utils.ViewUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tyq on 2016/5/15.
 */
public class ZhiHuFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

    private FragmentZhihuBinding dataBinding;
    private ZhihuPresenter presenter;
    private String curDate;
    private ZhihuAdapter adapter;
    private NewsEntity entity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onTabSelected(){
        loadLatestData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_zhihu,container,false);
        adapter = new ZhihuAdapter(getContext());
        dataBinding.swipeRefresh.setOnRefreshListener(this);
        dataBinding.recyclerView.setHasFixedSize(true);
        dataBinding.recyclerView.setAdapter(adapter);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new ZhihuPresenter(getActivity());
        loadLatestData();
    }

    public void loadLatestData() {
        dataBinding.recyclerView.smoothScrollToPosition(0);
        ZhihuApi service = presenter.createZhihuService() ;
        service.getLastestNews()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(newsEntity1 -> handlerSuccess(newsEntity1), throwable -> handlerFailure());
    }

    private void handlerSuccess(NewsEntity entity) {
        this.entity = entity;
        curDate = entity.getDate();
        adapter.addAll(entity.getStories(),this);
        if (dataBinding.swipeRefresh.isRefreshing())
            dataBinding.swipeRefresh.setRefreshing(false);
    }

    private void handlerFailure() {
        if (dataBinding.swipeRefresh.isRefreshing())
            dataBinding.swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        loadLatestData();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), ZhDetailActivity.class);
        intent.putExtra(Constant.ITEM_ID,entity.getStories().get(position).getId());
        intent.putExtra(Constant.NEWS_TITLE,entity.getStories().get(position).getTitle());
        getActivity().startActivity(intent);

    }
}
