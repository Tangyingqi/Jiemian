package com.tyq.jiemian.presenter;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tyq.jiemian.bean.NewsItem;
import com.tyq.jiemian.utils.Constant;
import com.tyq.jiemian.ui.callback.EntertainmentInterface;
import com.tyq.jiemian.utils.http.VolleyGetRequest;

import java.util.List;

/**
 * Created by tyq on 2016/5/14.
 */
public class EntertainmentPresenter extends BasePresenter{
    public EntertainmentPresenter(Context context) {
        super(context);
    }
    public void requestData(final EntertainmentInterface callback){
        String url = Constant.ENTERTAINMENT_URL;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getFeed(response, new ParseDataInterface() {
                    @Override
                    public void onParseSuccess(List<NewsItem> list) {
                        callback.onEntertainmentDataSuccess(list);
                    }

                    @Override
                    public void onParseFailed() {
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onEntertainmentDataFailed(handlerException(error));
            }
        });
        httpClient.addToRequestQueue(stringRequest);

    }
}
