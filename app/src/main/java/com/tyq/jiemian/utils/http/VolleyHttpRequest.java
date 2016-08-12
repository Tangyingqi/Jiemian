package com.tyq.jiemian.utils.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.tyq.jiemian.BuildConfig;

import java.lang.reflect.Type;

/**
 * Created by tyq on 2016/5/14.
 */
public class VolleyHttpRequest<T> extends Request<T> {


    public final Response.Listener<T> listener;
    public Context context;
    public final Gson gson = new Gson();
    public final Type classType;

    public VolleyHttpRequest(Context context, int method, String url, Type classType,
                             Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.context = context;
        this.classType = classType;
        this.listener = listener;
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String json;
        try{
            json = new String(response.data, "UTF-8");

            if (BuildConfig.DEBUG) {
                Log.d("HTTP", getUrl());
                Log.d("HTTP", json);
            }
            T resultObject = gson.fromJson(json,classType);

            return  Response.success(resultObject,HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }

    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }
}
