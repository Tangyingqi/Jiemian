package com.tyq.jiemian.utils.http;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.tyq.jiemian.utils.Constant;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tyq on 2016/5/14.
 */
public class VolleyGetRequest<T> extends VolleyHttpRequest<T>{


    public VolleyGetRequest(Context context, String url, Map<String, Object> mParams, Type classType,
                            Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(context, Method.GET, urlFormat(url, mParams), classType, listener, errorListener);
    }
    public VolleyGetRequest(Context context,String url, Type classType, Response.Listener listener, Response.ErrorListener errorListener) {
        super(context, Method.GET, urlFormat(url, null), classType, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> mHeaders = new HashMap<>();
        mHeaders.put("apikey","891eeffe6f1b4652128542bb8c9971a2");
        return mHeaders;
    }

    public static String urlFormat(String _url, Map<String, Object> mGetParams) {
        if (mGetParams == null) {
            mGetParams = new HashMap<>();
        }
        StringBuilder builder = new StringBuilder(_url);
        if (_url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }
        for (Map.Entry<String, Object> entry : mGetParams.entrySet()) {

            builder.append(entry.getKey());
            builder.append("=");
            try {
                String value = URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8");
                builder.append(value);
            } catch (UnsupportedEncodingException e) {
                builder.append(String.valueOf(entry.getValue()));
                e.printStackTrace();
            }
            builder.append("&");
        }
        if (builder.lastIndexOf("&") != -1) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }
}
