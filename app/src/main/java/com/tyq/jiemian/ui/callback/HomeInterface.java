package com.tyq.jiemian.ui.callback;

import com.tyq.jiemian.bean.PopularNews;

/**
 * Created by tyq on 2016/6/4.
 */
public interface HomeInterface  {
    void onRequestPopularNewsSuccess(PopularNews news);

    void onRequestPopularNewsFailed(String errMsg);
}
