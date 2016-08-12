package com.tyq.jiemian.adapter.VH;

import android.databinding.DataBindingUtil;
import android.databinding.tool.DataBinder;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tyq.jiemian.databinding.ItemWelcomeBinding;

/**
 * Created by tyq on 2016/6/1.
 */
public class WelcomeViewHolder extends RecyclerView.ViewHolder {

    private ItemWelcomeBinding dataBinding;
    public WelcomeViewHolder(View itemView) {
        super(itemView);
        dataBinding = DataBindingUtil.bind(itemView);
    }
}
