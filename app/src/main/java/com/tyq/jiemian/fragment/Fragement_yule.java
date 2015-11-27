package com.tyq.jiemian.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tyq.jiemian.MyApplication;
import com.tyq.jiemian.R;
import com.tyq.jiemian.activity.DetailActivity;
import com.tyq.jiemian.adapter.MyRecyclerAdapter;
import com.tyq.jiemian.bean.Constant;
import com.tyq.jiemian.bean.NewsItem;
import com.tyq.jiemian.biz.NewsBiz;
import com.tyq.jiemian.biz.OnparseListener;
import com.tyq.jiemian.utils.ACache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyq on 2015/11/22.
 */
public class Fragement_yule extends Fragment {

    private static final String TAG = "Fragement_yule";
    private SwipeRefreshLayout mSrl;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private Button reloadBtn;
    private Handler mHandler = new Handler();
    private ACache mACache;
    final List<NewsItem> item_list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mACache = ACache.get(getActivity());
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setListener();
        loadCache();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setViewVisible(true, true, false);
                loadData();
            }
        }, 1000);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.yule_fragment, container, false);
    }



    private void loadCache(){
        if(covertToList()){
            if(adapter!=null){
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void setListener() {
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void initView(View view) {
        //下拉刷新
        mSrl = (SwipeRefreshLayout) view.findViewById(R.id.srl_content);
        mSrl.setColorSchemeResources(R.color.colorPrimary, R.color.secondColor, R.color.purple, R.color.blue);
        //主要内容
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_content);
        adapter = new MyRecyclerAdapter(getActivity(), item_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(Constant.NEWS_ITEM, item_list.get(position));
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);

        reloadBtn = (Button) view.findViewById(R.id.refresh_btn);
        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }


    public void loadData() {
        StringRequest stringrequest = new StringRequest(Constant.yuleUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Log.i("tyq",response);
                NewsBiz.getFeed(response, new OnparseListener() {
                    @Override
                    public void onParseSuccess(List<NewsItem> list) {
                        if (list != null) {
                            item_list.clear();
                            item_list.addAll(list);
                            if (adapter != null) {
                                adapter.updateData(item_list);
                            }
                            setViewVisible(false, true, false);
                            mACache.put(TAG, convertToJson(item_list));
                        } else {

                        }

                    }

                    @Override
                    public void onParseFailed() {
                        setViewVisible(false, false, true);
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(item_list==null){
                    setViewVisible(false, false, true);
                }else{
                    setViewVisible(false,true,false);
                }
            }
        });
        stringrequest.setTag(TAG);
        MyApplication.getRequestQueue().add(stringrequest);

    }

    public void setViewVisible(boolean mSrl, boolean mRecyclerView, boolean reloadBtn) {
        if (mSrl) {
            if (this.mSrl != null) {
                this.mSrl.setRefreshing(true);
            }
        }else{
            if(this.mSrl!=null){
                this.mSrl.setRefreshing(false);
            }
        }
        if (mRecyclerView) {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.setVisibility(View.VISIBLE);
            }
        }else {
            if (this.mRecyclerView != null) {
                this.mRecyclerView.setVisibility(View.GONE);
            }
        }
        if (reloadBtn) {
            if (this.reloadBtn != null) {
                this.reloadBtn.setVisibility(View.VISIBLE);
            }
        }else {
            if (this.reloadBtn != null) {
                this.reloadBtn.setVisibility(View.GONE);
            }
        }

    }

    public boolean covertToList(){
        JSONObject jsonObj = mACache.getAsJSONObject(TAG);
        if(jsonObj!=null){
            try {
                JSONArray feedlistAr = jsonObj.getJSONArray("feed_list");
                item_list.clear();
                for (int i=0;i<feedlistAr.length();i++){
                    JSONObject feedlist = feedlistAr.getJSONObject(i);
                    NewsItem item = NewsItem.parse(feedlist);
                    item_list.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }


    private JSONObject convertToJson(List<NewsItem> item_list) {
        JSONObject outJsonObj = new JSONObject();
        JSONArray itemJsonAr = new JSONArray();
        for(NewsItem feedlistItem:item_list){
            JSONObject jsonObj = feedlistItem.toJSONObj();
            itemJsonAr.put(jsonObj);
        }
        try {
            outJsonObj.put("feed_list",itemJsonAr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return outJsonObj;

    }


}
