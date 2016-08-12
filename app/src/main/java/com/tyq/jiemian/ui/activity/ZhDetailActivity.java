package com.tyq.jiemian.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.tyq.jiemian.R;
import com.tyq.jiemian.bean.NewsItem;
import com.tyq.jiemian.databinding.ActivityDetailBinding;
import com.tyq.jiemian.presenter.ZhihuPresenter;
import com.tyq.jiemian.ui.callback.ZhihuApi;
import com.tyq.jiemian.utils.Constant;
import com.tyq.jiemian.utils.HtmlUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tyq on 2016/5/16.
 */
public class ZhDetailActivity extends AppCompatActivity {

    private int item_id;
    private String item_title;
    private ZhihuPresenter presenter;
    private ActivityDetailBinding dataBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_detail);
        presenter = new ZhihuPresenter(this);
        setUpToolbar();
        initView();
        loadData();
    }

    private void initView() {

        Bundle bundle = getIntent().getExtras();
        item_id = bundle.getInt(Constant.ITEM_ID);
        item_title = bundle.getString(Constant.NEWS_TITLE);

        if (null!=getSupportActionBar() && null!=item_title)
        getSupportActionBar().setTitle(item_title);

        dataBinding.webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress == 100){
                    dataBinding.progressBar.setVisibility(View.INVISIBLE);
                }else{
                    if(dataBinding.progressBar.getVisibility()==View.INVISIBLE){
                        dataBinding.progressBar.setVisibility(View.VISIBLE);
                    }
                    dataBinding.progressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    public void setUpToolbar(){
        setSupportActionBar(dataBinding.toolbar);
    }

    private void loadData() {
        ZhihuApi service = presenter.createZhihuService();
        service.getNewsDetails(item_id).observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .map(storyDetailsEntity -> HtmlUtils.structHtml(storyDetailsEntity.getBody(), storyDetailsEntity.getCss()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> webShowData(s), throwable -> handlerFailure());

    }
    public void webShowData(String url){
        dataBinding.webView.loadData(url,"text/html; charset=UTF-8", null);
    }

    private void handlerFailure() {
    }
}
