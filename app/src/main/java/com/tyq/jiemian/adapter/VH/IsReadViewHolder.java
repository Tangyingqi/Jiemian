package com.tyq.jiemian.adapter.VH;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.tyq.greendao.HaveRead;
import com.tyq.jiemian.adapter.IsReadAdapter;
import com.tyq.jiemian.databinding.ItemIsReadBinding;
import com.tyq.jiemian.utils.DBUtils;

import java.util.List;

/**
 * Created by tyq on 2016/6/2.
 */
public class IsReadViewHolder extends RecyclerView.ViewHolder {

    private ItemIsReadBinding dataBinding;
    private IsReadAdapter adapter;
    public IsReadViewHolder(Context context,View itemView) {
        super(itemView);
        dataBinding = DataBindingUtil.bind(itemView);
        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,true);
        dataBinding.recyclerView.setHasFixedSize(true);
        dataBinding.recyclerView.setLayoutManager(manager);
        adapter = new IsReadAdapter(context);
        List<HaveRead> haveReadList = DBUtils.getInstance(context).getHaveReadList();
        adapter.addAll(haveReadList);
        dataBinding.recyclerView.setAdapter(adapter);
    }
}
