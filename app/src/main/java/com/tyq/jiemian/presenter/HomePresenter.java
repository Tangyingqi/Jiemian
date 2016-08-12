package com.tyq.jiemian.presenter;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.tyq.jiemian.bean.PopularNews;
import com.tyq.jiemian.bean.SearchItem;
import com.tyq.jiemian.bean.resp.BaseSearchResponse;
import com.tyq.jiemian.bean.resp.BaseVolleyResponse;
import com.tyq.jiemian.ui.callback.HomeInterface;
import com.tyq.jiemian.ui.callback.SearchInterface;
import com.tyq.jiemian.utils.Constant;
import com.tyq.jiemian.utils.http.VolleyGetRequest;
import com.tyq.jiemian.utils.http.VolleyHttpClient;
import com.tyq.jiemian.utils.http.VolleyHttpRequest;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tyq on 2016/6/4.
 */
public class HomePresenter extends BasePresenter {
    private Context context;

    public HomePresenter(Context context) {
        super(context);
        this.context = context;
    }

    public void requestPopularList(final HomeInterface homeInterface) {
        Type type = new TypeToken<BaseVolleyResponse<PopularNews>>() {
        }.getType();
        String url = Constant.POPULAR_NEWS_URL;
        Map<String, Object> mParams = new HashMap<>();
        mParams.put("id", "recomm");
        mParams.put("page", "1");
        VolleyGetRequest<BaseVolleyResponse<PopularNews>> request = new VolleyGetRequest<BaseVolleyResponse<PopularNews>>(context, url, mParams, type, new Response.Listener<BaseVolleyResponse<PopularNews>>() {
            @Override
            public void onResponse(BaseVolleyResponse<PopularNews> response) {
                homeInterface.onRequestPopularNewsSuccess(response.getData());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                homeInterface.onRequestPopularNewsFailed(handlerException(error));
            }
        });
        httpClient.addToRequestQueue(request);

    }

    public void requestSearchResult(String keyWord, final SearchInterface searchInterface){
        Type type = new TypeToken<BaseSearchResponse<SearchItem>>(){}.getType();
        String url = Constant.SEARCH_URL;
        Map<String,Object> mParams = new HashMap<>();
        mParams.put("keyword",keyWord);
        mParams.put("page",1);
        mParams.put("count",20);
        VolleyHttpRequest<BaseSearchResponse<SearchItem>> request = new VolleyGetRequest<BaseSearchResponse<SearchItem>>(context, url, mParams, type, new Response.Listener<BaseSearchResponse<SearchItem>>() {
            @Override
            public void onResponse(BaseSearchResponse<SearchItem> response) {
                searchInterface.onSearchResultSuccess(response.getRetData());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                searchInterface.onSearchResultFailed(handlerException(error));
            }
        });
        httpClient.addToRequestQueue(request);
    }
}
