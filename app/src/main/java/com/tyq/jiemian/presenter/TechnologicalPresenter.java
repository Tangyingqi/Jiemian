package com.tyq.jiemian.presenter;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tyq.jiemian.bean.NewsItem;
import com.tyq.jiemian.utils.Constant;
import com.tyq.jiemian.ui.callback.TechnologicalInterface;
import com.tyq.jiemian.utils.http.VolleyGetRequest;

import java.util.List;

/**
 * Created by tyq on 2016/5/14.
 */
public class TechnologicalPresenter extends BasePresenter {


    public TechnologicalPresenter(Context context) {
        super(context);
    }

    public void requestData(final TechnologicalInterface callback){
        String url = Constant.TECHNOLOGICAL_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getFeed(response, new ParseDataInterface() {
                    @Override
                    public void onParseSuccess(List<NewsItem> list) {
                        callback.onTechnologicalDataSuccess(list);
                    }

                    @Override
                    public void onParseFailed() {

                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onTechnologicalDataFailed(handlerException(error));
            }
        });
        httpClient.addToRequestQueue(stringRequest);

    }
}
