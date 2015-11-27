package com.tyq.jiemian.fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tyq.jiemian.R;
import com.tyq.jiemian.utils.ACache;
import com.tyq.jiemian.utils.DataCleanManager;
import com.tyq.jiemian.utils.SDCardUtils;
import com.tyq.jiemian.views.ConfirmDialog;
import com.tyq.jiemian.views.LoadingDialog;

import java.text.DecimalFormat;

/**
 * Created by tyq on 2015/11/26.
 */
public class Fragment_setting extends Fragment {
    private Button clear_btn;
    private TextView clear_tv;
    private DialogHandler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.setting_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        CacheSize();
    }
    private void CacheSize(){
        //内置存储cache
        double internalCacheSize = SDCardUtils.getDirSize(getActivity().getCacheDir());
        //外置存储cache
        double externalCacheSize = 0;
        if (SDCardUtils.isSDCardEnable())
            externalCacheSize = SDCardUtils.getDirSize(getActivity().getExternalCacheDir());
        //WebView的cache
        String totalCacheSize = new DecimalFormat("#.00").format(internalCacheSize + externalCacheSize);
        if (totalCacheSize.startsWith(".")) totalCacheSize = "0" + totalCacheSize;
        //若总大小小于0.1MB，直接显示0.00MB
        if (Float.parseFloat(totalCacheSize) < 0.10f) totalCacheSize = "0.00";
        String str = totalCacheSize+"MB";

        clear_tv.setText(str);
    }

    private void initViews(View view) {
        clear_btn = (Button) view.findViewById(R.id.clear_btn);
        clear_tv = (TextView) view.findViewById(R.id.clear_tv);
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "确认清除吗？";
                ConfirmDialog confirmDialog = new ConfirmDialog();
                confirmDialog.setParams(title, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoadingDialog loadingDialog = new LoadingDialog();
                        loadingDialog.setParams("请稍后...");
                        loadingDialog.show(getActivity().getFragmentManager(), "loading");
                        clearCache();
                        handler = new DialogHandler(loadingDialog);
                        handler.sendEmptyMessageDelayed(0, 1000);
                    }
                }, null);
                confirmDialog.show(getActivity().getFragmentManager(), "confirm");
            }
        });
    }

    private void clearCache() {
        ACache.get(getActivity()).clear();
        DataCleanManager.cleanInternalCache(getActivity());
        DataCleanManager.cleanExternalCache(getActivity());
    }

    private class DialogHandler extends Handler{
        LoadingDialog dialog;
        DialogHandler(LoadingDialog dialog){
            this.dialog = dialog;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(dialog!=null && dialog.getDialog().isShowing()){
                dialog.dismiss();
                Toast.makeText(getActivity(),"清除成功",Toast.LENGTH_SHORT).show();
                String str = "0.00MB";
                clear_tv.setText(str);
            }
        }

    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        //当fragment显示时
        if (!hidden) {
            CacheSize();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
        }
    }

}
