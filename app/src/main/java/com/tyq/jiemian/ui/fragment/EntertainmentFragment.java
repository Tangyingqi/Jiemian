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
import com.tyq.jiemian.databinding.FragmentEntertainmentBinding;
import com.tyq.jiemian.presenter.EntertainmentPresenter;
import com.tyq.jiemian.ui.activity.JmDetailActivity;
import com.tyq.jiemian.ui.callback.EntertainmentInterface;
import com.tyq.jiemian.ui.callback.OnItemClickListener;
import com.tyq.jiemian.utils.ACache;
import com.tyq.jiemian.utils.Constant;
import com.tyq.jiemian.utils.DBUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyq on 2015/11/22.
 */
public class EntertainmentFragment extends BaseFragment implements EntertainmentInterface, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

    private static final String TAG = "Entertainment";
    private JMAdapter adapter;
    private ACache mACache;
    private EntertainmentPresenter presenter;
    private FragmentEntertainmentBinding dataBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onTabSelected(){
        requestData();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_entertainment,container,false);
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
        presenter = new EntertainmentPresenter(getActivity());
        requestData();
    }


    public void requestData(){
        //dataBinding.swipeRefresh.setRefreshing(true);
        dataBinding.recyclerView.smoothScrollToPosition(0);
        presenter.requestData(this);
    }




    @Override
    public void onEntertainmentDataSuccess(List<NewsItem> list) {
        if (dataBinding.swipeRefresh.isRefreshing()){
            dataBinding.swipeRefresh.setRefreshing(false);
        }
        if (null != list) {
            adapter.addAll(list);
        }
    }

    @Override
    public void onEntertainmentDataFailed(String errMsg) {
        if (dataBinding.swipeRefresh.isRefreshing()){
            dataBinding.swipeRefresh.setRefreshing(false);
        }
        showToast(errMsg);
    }

    @Override
    public void onRefresh() {
        requestData();
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), JmDetailActivity.class);
        intent.putExtra(Constant.NEWS_ITEM, adapter.getItem(position));
        startActivity(intent);
    }

}
