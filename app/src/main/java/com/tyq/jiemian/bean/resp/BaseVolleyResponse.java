package com.tyq.jiemian.bean.resp;

/**
 * Created by tyq on 2016/6/4.
 */
public class BaseVolleyResponse<T> {
    private int code;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
