package com.tyq.jiemian.ui.callback;


import com.tyq.jiemian.bean.NewsEntity;
import com.tyq.jiemian.bean.StoryDetailsEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by diff on 2016/2/16.
 */
public interface ZhihuApi {

    @GET("api/4/news/latest")
    Observable<NewsEntity> getLastestNews();

    @GET("api/4/news/before/{id}")
    Observable<NewsEntity> getBeforeNews(@Path("id") String id);

    @GET("api/4/news/{id}")
    Observable<StoryDetailsEntity> getNewsDetails(@Path("id") int id);
}
