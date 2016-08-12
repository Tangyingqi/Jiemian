package com.tyq.jiemian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpanWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tyq.jiemian.R;
import com.tyq.jiemian.adapter.VH.IsReadViewHolder;
import com.tyq.jiemian.adapter.VH.PopularViewHolder;
import com.tyq.jiemian.adapter.VH.WelcomeViewHolder;

/**
 * Created by tyq on 2016/6/1.
 */
public class MainAdapter extends RecyclerView.Adapter {
    private Context context;
    private LayoutInflater inflater;
    private final static int WELCOME_VIEW = 0;
    private final static int ISREADED_VIEW = 1;
    private final static int POPULAR_VIEW = 2;
    private final static int HISTORY_VIEW = 3;

    public MainAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case WELCOME_VIEW:
                return new WelcomeViewHolder(inflater.inflate(R.layout.item_welcome,parent,false));
            case ISREADED_VIEW:
                return new IsReadViewHolder(context,inflater.inflate(R.layout.item_is_read,parent,false));
            case POPULAR_VIEW:
                return new PopularViewHolder(context,inflater.inflate(R.layout.item_popular_list,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return WELCOME_VIEW;
            case 1:
                return ISREADED_VIEW;
            case 2:
                return POPULAR_VIEW;
        }
        return super.getItemViewType(position);
    }
}
