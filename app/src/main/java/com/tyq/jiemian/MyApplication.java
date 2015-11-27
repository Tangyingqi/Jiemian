package com.tyq.jiemian;

import android.app.Application;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by tyq on 2015/11/17.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;
    private static RequestQueue requestQueue;

    public static MyApplication getInstance(){
        return myApplication;
    }
    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        requestQueue = Volley.newRequestQueue(this);
        initImageLoader();
    }
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .denyCacheImageMultipleSizesInMemory()
                .defaultDisplayImageOptions(getOptions())
                .threadPoolSize(3)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .imageDownloader(new BaseImageDownloader(getApplicationContext()))
                .memoryCache(new LruMemoryCache(5 * 1024 * 1024))
                .memoryCacheSize(5 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .build();
        ImageLoader.getInstance().init(config);
    }

    public DisplayImageOptions getOptions() {
        return new DisplayImageOptions.Builder()

                .showImageOnLoading(R.drawable.app_logo)
                .showImageForEmptyUri(R.drawable.app_logo)
                .showImageOnFail(R.drawable.app_logo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY) // default
                .bitmapConfig(Bitmap.Config.ARGB_4444) // default
                .displayer(new SimpleBitmapDisplayer())
                .build();
    }
    public DisplayImageOptions getOptions(int defaultImgResourceId) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImgResourceId)
                .showImageForEmptyUri(defaultImgResourceId)
                .showImageOnFail(defaultImgResourceId)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
    }
    public DisplayImageOptions getOptionsWithRoundedCorner() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.app_logo)
                .showImageForEmptyUri(R.drawable.app_logo)
                .showImageOnFail(R.drawable.app_logo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
                .bitmapConfig(Bitmap.Config.RGB_565) // default
                .displayer(new FadeInBitmapDisplayer(300))
                .displayer(new RoundedBitmapDisplayer(8))
                .build();
    }
}
