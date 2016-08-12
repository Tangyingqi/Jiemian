package com.tyq.jiemian.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.tyq.jiemian.R;
import com.tyq.jiemian.adapter.SearchResultAdapter;
import com.tyq.jiemian.bean.SearchItem;
import com.tyq.jiemian.databinding.ActivitySearchResultBinding;
import com.tyq.jiemian.utils.Constant;

/**
 * Created by tyq on 2016/6/15.
 */
public class SearchResultActivity extends AppCompatActivity {
    private ActivitySearchResultBinding dataBinding;
    private SearchResultAdapter adapter;
    private SearchItem searchItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_search_result);
        if (getIntent().hasExtra(Constant.SEARCH_ITEM)){
            searchItem = (SearchItem) getIntent().getSerializableExtra(Constant.SEARCH_ITEM);
        }
        adapter = new SearchResultAdapter(this,searchItem);
        dataBinding.recyclerView.setAdapter(adapter);
    }

}
