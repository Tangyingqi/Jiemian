package com.tyq.jiemian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tyq.jiemian.MyApplication;
import com.tyq.jiemian.R;
import com.tyq.jiemian.bean.NewsItem;

import java.util.List;

/**
 * Created by tyq on 2015/11/16.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<NewsItem> mData;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List list){
        mContext = context;
        mData = list;
        inflater = LayoutInflater.from(mContext);
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public void updateData(List<NewsItem> data){
        this.mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_title.setText(mData.get(position).getTitle());
        holder.tv_date.setText(mData.get(position).getDate());
        ImageLoader.getInstance().displayImage(mData.get(position).getImageurl(), holder.image, MyApplication.getInstance().getOptionsWithRoundedCorner());

        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView  tv_title;
        ImageView image;
        TextView tv_date;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            image = (ImageView) itemView.findViewById(R.id.image);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
}
