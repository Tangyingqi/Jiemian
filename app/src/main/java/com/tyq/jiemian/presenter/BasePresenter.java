package com.tyq.jiemian.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.tyq.jiemian.JMApplication;
import com.tyq.jiemian.bean.NewsItem;
import com.tyq.jiemian.utils.Constant;
import com.tyq.jiemian.utils.http.VolleyHttpClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tyq on 2016/5/14.
 */
public class BasePresenter {
    public Context context;
    public VolleyHttpClient httpClient;
    private static List<NewsItem> list;
    public static ParseDataInterface onParseListener;
    public static final int MSG_SUCCESS = 1;
    public static final int MSG_FAIL = 0;

    public BasePresenter(Context context) {
        this.context = context;
        this.httpClient = JMApplication.getInstance().getVolleyInstance(context);
    }

    public interface ParseDataInterface{
        void onParseSuccess(List<NewsItem> list);
        void onParseFailed();
    }


    public String handlerException(VolleyError error) {

        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            return "连接服务器失败";
        } else if (error instanceof AuthFailureError) {
            return "服务器验证失败";
        } else if (error instanceof ServerError) {
            return "服务器出错了";
        } else if (error instanceof NetworkError) {
            return "网络异常";
        } else if (error instanceof ParseError) {
            return "数据解析异常";
        } else {
            Log.d("error", error.toString());
            return "其他错误";
        }
    }


    private static DispatchStatusHandler mHandler = new DispatchStatusHandler();

    private static class DispatchStatusHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SUCCESS:
                    if (onParseListener != null) {
                        onParseListener.onParseSuccess(list);
                        break;
                    }
                case MSG_FAIL:
                    if (onParseListener != null) {
                        onParseListener.onParseFailed();
                        break;
                    }
            }
        }
    }

    public static void getFeed(final String html, ParseDataInterface onParseListener) {
        BasePresenter.onParseListener = onParseListener;
        new Thread() {
            @Override
            public void run() {
                Document doc = Jsoup.parse(html, Constant.TECHNOLOGICAL_URL);

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
