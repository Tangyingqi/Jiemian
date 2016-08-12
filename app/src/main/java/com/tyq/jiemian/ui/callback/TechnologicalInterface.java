package com.tyq.jiemian.ui.callback;

import com.tyq.jiemian.bean.NewsItem;

import java.util.List;

/**
 * Created by tyq on 2016/5/14.
 */
public interface TechnologicalInterface {

    void onTechnologicalDataSuccess(List<NewsItem> list);

    void onTechnologicalDataFailed(String errMsg);
}
