package com.tyq.jiemian.biz;

import android.os.Handler;
import android.os.Message;


import com.tyq.jiemian.bean.Constant;
import com.tyq.jiemian.bean.NewsItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tyq on 2015/11/16.
 */
public class NewsBiz {

    private static List<NewsItem> list;
    private static OnparseListener onparseListener;
    public static final int MSG_SUCCESS = 1;
    public static final int MSG_FAIL = 0;

    private static DispatchStatusHandler mHandler = new DispatchStatusHandler();

    private static class DispatchStatusHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SUCCESS:
                    if (onparseListener != null) {
                        onparseListener.onParseSuccess(list);
                        break;
                    }
                case MSG_FAIL:
                    if (onparseListener != null) {
                        onparseListener.onParseFailed();
                        break;
                    }
            }
        }
    }

    public static void getFeed(final String html, OnparseListener onparseListener) {
        NewsBiz.onparseListener = onparseListener;
        new Thread() {
            @Override
            public void run() {
                Document doc = Jsoup.parse(html, Constant.indexUrl);

                if (doc == null) {
                    mHandler.sendEmptyMessage(MSG_FAIL);
                    return;
                }
                list = new ArrayList<NewsItem>();
                Elements elements = doc.select(".news-view").select(".left");
                handlerFeedElements(list, elements);

                mHandler.sendEmptyMessage(MSG_SUCCESS);
            }
        }.start();
    }


    private static void handlerFeedElements(List<NewsItem> feedList, Elements elements) {
        if (feedList != null && elements.size() > 0) {
            for (int i = 0; i < elements.size(); i++) {
                NewsItem item = new NewsItem();
                Element element = elements.get(i);
                Elements elementTitle = element.select("div.news-header").select("a");
                Elements elementImageUrl = element.getElementsByTag("img");
                Elements elementDate = element.getElementsByClass("news-footer");
                elementDate.select("span.comment").remove();
                elementDate.select("a").remove();

//                Log.i("tyq"," "+elementImageUrl);
                if (elementTitle.size() > 0 && elementImageUrl.size() > 0 && elementDate.size() > 0) {
                    item.setTitle(elementTitle.text());
                    item.setUrl(elementTitle.attr("href"));
                    item.setImageurl(elementImageUrl.attr("src"));

                    item.setDate(elementDate.text());
                    feedList.add(item);
                }
            }

        }
    }
}
