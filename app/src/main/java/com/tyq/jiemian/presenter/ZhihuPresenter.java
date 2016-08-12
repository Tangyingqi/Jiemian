package com.tyq.jiemian.presenter;

import android.content.Context;

import com.tyq.jiemian.ui.callback.ZhihuApi;
import com.tyq.jiemian.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tyq on 2016/5/15.
 */
public class ZhihuPresenter extends BasePresenter {



    public ZhihuPresenter(Context context) {
        super(context);
    }

    private  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.BASE_ZHIHU_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    public ZhihuApi createZhihuService() {
        return retrofit.create(ZhihuApi.class);
    }

}
