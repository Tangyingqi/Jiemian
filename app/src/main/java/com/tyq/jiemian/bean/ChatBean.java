package com.tyq.jiemian.bean;

/**
 * Created by tyq on 2016/6/5.
 */
public class ChatBean {
    private int type;
    private String info;

    public ChatBean(int type, String info) {
        this.type = type;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
