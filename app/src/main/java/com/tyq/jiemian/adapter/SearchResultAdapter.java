package com.tyq.jiemian.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tyq.jiemian.R;
import com.tyq.jiemian.bean.SearchItem;
import com.tyq.jiemian.databinding.ItemSearchResultBinding;
import com.tyq.jiemian.utils.Constant;

import java.util.List;

/**
 * Created by tyq on 2016/6/15.
 */
public class SearchResultAdapter extends RecyclerView.Adapter {

    private Context context;
    private LayoutInflater inflater;
    private SearchItem searchItem;

    public SearchResultAdapter(Context context,SearchItem searchItems) {
        inflater = LayoutInflater.from(context);
        this.searchItem = searchItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ResultViewHolder(inflater.inflate(R.layout.item_search_result,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ResultViewHolder){
            ResultViewHolder resultViewHolder = (ResultViewHolder) holder;
            resultViewHolder.updateUi(searchItem.getData().get(position));
        }
    }

    @Override
    public int getItemCount() {
        return null!=searchItem && searchItem.getData().size() == 0 ? 0 :searchItem.getData().size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder{
        private ItemSearchResultBinding dataBinding;

        public ResultViewHolder(View itemView) {
            super(itemView);
            dataBinding = DataBindingUtil.bind(itemView);
        }

        public void updateUi(SearchItem.DataEntity searchItem){
            ImageLoader.getInstance().displayImage(searchItem.getImg_url(),dataBinding.image);
            dataBinding.setTitle(searchItem.getTitle());
        }
    }
}
