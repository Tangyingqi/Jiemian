package com.tyq.jiemian.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.tyq.jiemian.R;
import com.tyq.jiemian.bean.SearchItem;
import com.tyq.jiemian.databinding.ActivitySearchBinding;
import com.tyq.jiemian.presenter.HomePresenter;
import com.tyq.jiemian.ui.callback.SearchInterface;
import com.tyq.jiemian.utils.Constant;

/**
 * Created by tyq on 2016/6/15.
 */
public class SearchActivity extends AppCompatActivity implements SearchInterface {

    private ActivitySearchBinding dataBinding;
    private HomePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        presenter = new HomePresenter(this);
        dataBinding.searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    requestData();
                }
                return false;
            }
        });

    }

    public void requestData() {
        String keyWord = dataBinding.searchView.getText().toString();
        presenter.requestSearchResult(keyWord, this);
    }

    @Override
    public void onSearchResultSuccess(SearchItem searchItem) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        intent.putExtra(Constant.SEARCH_ITEM, searchItem);
        startActivity(intent);
    }

    @Override
    public void onSearchResultFailed(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }
}
