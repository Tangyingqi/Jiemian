package com.tyq.jiemian.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by tyq on 2015/11/15.
 */
public class NewsItem implements Serializable {

    private String title;//标题
    private String imageurl;//图片
    private String date;//日期
    private String url;//新闻地址
    private boolean isReaded;
    private boolean isCollected;

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded(boolean readed) {
        isReaded = readed;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public JSONObject toJSONObj() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("url", url);
            jsonObject.put("imageurl", imageurl);
            jsonObject.put("date", date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static NewsItem parse(JSONObject jsonObject) {
        if(jsonObject == null) {
            return null;
        }
        NewsItem newsItem = new NewsItem();
        newsItem.setTitle(jsonObject.optString("title"));
        newsItem.setUrl(jsonObject.optString("url"));
        newsItem.setImageurl(jsonObject.optString("imageurl"));
        newsItem.setDate(jsonObject.optString("date"));
        return newsItem;
    }
}
