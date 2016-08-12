package com.tyq.jiemian.ui.callback;

import com.tyq.jiemian.bean.SearchItem;

/**
 * Created by tyq on 2016/6/15.
 */
public interface SearchInterface {
    void onSearchResultSuccess(SearchItem searchItem);
    void onSearchResultFailed(String errMsg);
}
