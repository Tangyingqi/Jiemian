package com.tyq.jiemian.ui.callback;

import com.tyq.jiemian.bean.TREntity;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by tyq on 2016/6/5.
 */
public interface TRApi {
    @FormUrlEncoded
    @POST("api")
    Call<TREntity> getTRResponse(@Field("key") String key, @Field("info") String info, @Field("userid") String userid);
}
