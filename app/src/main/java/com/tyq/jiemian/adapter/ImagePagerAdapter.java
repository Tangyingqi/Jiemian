package com.tyq.jiemian.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.salvage.RecyclingPagerAdapter;
import com.tyq.jiemian.MyApplication;
import com.tyq.jiemian.activity.DetailActivity;
import com.tyq.jiemian.bean.Constant;
import com.tyq.jiemian.bean.NewsItem;

import java.util.List;

/**
 * desc: 纯图片PagerAdapter
 * author: kinneyYan
 * date: 2015/7/1
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<NewsItem> list;

    public ImagePagerAdapter(Context context, List<NewsItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup container) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            convertView = holder.imageView = imageView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final NewsItem item = list.get(position);
        if (null != item) {
            holder.imageView.post(new Runnable() {
                @Override
                public void run() {
                    ImageLoader.getInstance().displayImage(item.getImageurl(), holder.imageView, MyApplication.getInstance().getOptions(), new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUrl, View view, Bitmap loadedImage) {

                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {

                        }
                    });
                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(Constant.NEWS_ITEM, item);
                    context.startActivity(intent);
                }
            });
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
    }

}
