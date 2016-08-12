package com.tyq.jiemian.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tyq.greendao.Collect;
import com.tyq.jiemian.R;
import com.tyq.jiemian.databinding.ActivityDetailBinding;
import com.tyq.jiemian.utils.Constant;
import com.tyq.jiemian.bean.NewsItem;
import com.tyq.jiemian.utils.DBUtils;

import java.util.Date;


/**
 * Created by tyq on 2015/11/18.
 */
public class JmDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private NewsItem newsItem;
    private String newsUrl;
    private ActivityDetailBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        initView();
        initListener();
    }

    private void initListener() {
        dataBinding.collect.setOnClickListener(this);
    }

    private void initView() {
        setSupportActionBar(dataBinding.toolbar);
        if (getIntent().hasExtra(Constant.NEWS_URL)) {
            newsUrl = getIntent().getStringExtra(Constant.NEWS_URL);
        }
        Bundle bundle = getIntent().getExtras();
        if (null != bundle)
            newsItem = (NewsItem) bundle.getSerializable(Constant.NEWS_ITEM);
        dataBinding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    dataBinding.progressBar.setVisibility(View.GONE);
                } else {
                    dataBinding.progressBar.setVisibility(View.VISIBLE);
                    dataBinding.progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        if (null != newsItem)
            dataBinding.webView.loadUrl(newsItem.getUrl());

        if (null != newsUrl) {
            dataBinding.webView.loadUrl(newsUrl);
        }

    }

    @Override
    public void onClick(View v) {

        if (null != newsItem) {
            if (newsItem.isCollected()){
                Toast.makeText(this,"取消收藏成功",Toast.LENGTH_SHORT).show();
                Collect collect = new Collect(null, newsItem.getTitle(), newsItem.getImageurl(), newsItem.getUrl(), new Date());
                DBUtils.getInstance(this).delectNews(collect);
                newsItem.setCollected(false);
                dataBinding.collect.setImageResource(R.mipmap.icon_collect);
            }else {
                Collect collect = new Collect(null, newsItem.getTitle(), newsItem.getImageurl(), newsItem.getUrl(), new Date());
                DBUtils.getInstance(this).addNewsToCollect(collect);
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                newsItem.setCollected(true);
                dataBinding.collect.setImageResource(R.mipmap.icon_fav);
            }
        }
    }
}
