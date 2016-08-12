package com.tyq.jiemian.ui.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tyq.jiemian.R;
import com.tyq.jiemian.adapter.JMAdapter;
import com.tyq.jiemian.bean.NewsItem;
import com.tyq.jiemian.databinding.FragmentTechnologicalBinding;
import com.tyq.jiemian.presenter.TechnologicalPresenter;
import com.tyq.jiemian.ui.activity.JmDetailActivity;
import com.tyq.jiemian.ui.callback.OnItemClickListener;
import com.tyq.jiemian.ui.callback.TechnologicalInterface;
import com.tyq.jiemian.utils.ACache;
import com.tyq.jiemian.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tyq on 2015/11/22.
 */
public class TechnologicalFragment extends BaseFragment implements TechnologicalInterface, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {
    private static final String TAG = "Technological";
    private JMAdapter adapter;
    private ACache mACache;
    private TechnologicalPresenter presenter;
    private FragmentTechnologicalBinding dataBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mACache = ACache.get(getActivity());
    }

    public void onTabSelected(){
        requestData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_technological, container, false);
        dataBinding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.secondColor, R.color.purple, R.color.blue);
        dataBinding.swipeRefresh.setOnRefreshListener(this);
        adapter = new JMAdapter(getActivity(),this);
        dataBinding.recyclerView.setHasFixedSize(true);
        dataBinding.recyclerView.setAdapter(adapter);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new TechnologicalPresenter(getActivity());
        requestData();
    }


    @Override
    public void onTechnologicalDataSuccess(List<NewsItem> list) {
        if (dataBinding.swipeRefresh.isRefreshing()) {
            dataBinding.swipeRefresh.setRefreshing(false);
        }
        if (null != list) {
            adapter.addAll(list);
        }
    }

    @Override
    public void onTechnologicalDataFailed(String errMsg) {
        if (dataBinding.swipeRefresh.isRefreshing()) {
            dataBinding.swipeRefresh.setRefreshing(false);
        }
        showToast(errMsg);
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    public void requestData() {
        dataBinding.recyclerView.smoothScrollToPosition(0);
        presenter.requestData(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), JmDetailActivity.class);
        intent.putExtra(Constant.NEWS_ITEM, adapter.getItem(position));
        startActivity(intent);
    }
}
