package com.tyq.jiemian.utils.http;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by tyq on 2016/5/14.
 */
public class VolleyHttpClient {
    private final RequestQueue mRequestQueue;

    public VolleyHttpClient(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public <T> void addToRequestQueue(Request<T> req){
        req.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        mRequestQueue.add(req);
    }
}
