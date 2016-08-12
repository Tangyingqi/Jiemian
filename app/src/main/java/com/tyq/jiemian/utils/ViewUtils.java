package com.tyq.jiemian.utils;

import android.view.View;

/**
 * Created by tyq on 2016/5/16.
 */
public class ViewUtils {
    public static void setViewVisibility(View view, boolean visibility) {
        if (view == null) {
            return;
        }
        if (visibility) {
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        } else {
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            }
        }
    }
}
