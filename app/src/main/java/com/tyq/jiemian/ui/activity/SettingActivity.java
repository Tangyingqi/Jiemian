package com.tyq.jiemian.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tyq.jiemian.R;
import com.tyq.jiemian.databinding.ActivitySettingBinding;
import com.tyq.jiemian.ui.widget.ConfirmDialog;
import com.tyq.jiemian.ui.widget.LoadingDialog;
import com.tyq.jiemian.utils.ACache;
import com.tyq.jiemian.utils.DataCleanManager;
import com.tyq.jiemian.utils.SDCardUtils;

import java.text.DecimalFormat;

/**
 * Created by tyq on 2015/11/26.
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private DialogHandler handler;
    private ActivitySettingBinding dataBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_setting);
        initListener();
        CacheSize();
    }

    private void CacheSize(){
        //内置存储cache
        double internalCacheSize = SDCardUtils.getDirSize(getCacheDir());
        //外置存储cache
        double externalCacheSize = 0;
        if (SDCardUtils.isSDCardEnable())
            externalCacheSize = SDCardUtils.getDirSize(getExternalCacheDir());
        //WebView的cache
        String totalCacheSize = new DecimalFormat("#.00").format(internalCacheSize + externalCacheSize);
        if (totalCacheSize.startsWith(".")) totalCacheSize = "0" + totalCacheSize;
        //若总大小小于0.1MB，直接显示0.00MB
        if (Float.parseFloat(totalCacheSize) < 0.10f) totalCacheSize = "0.00";
        String str = totalCacheSize+"MB";

        dataBinding.clearTv.setText(str);
    }

    private void initListener() {
        dataBinding.clearCache.setOnClickListener(this);

    }

    private void clearCache() {
        ACache.get(this).clear();
        DataCleanManager.cleanInternalCache(this);
        DataCleanManager.cleanExternalCache(this);
    }

    @Override
    public void onClick(View v) {
        String title = "确认清除吗？";
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setParams(title, (dialog, which1) -> {
            LoadingDialog loadingDialog = new LoadingDialog();
            loadingDialog.setParams("请稍后...");
            loadingDialog.show(getFragmentManager(), "loading");
            clearCache();
            handler = new DialogHandler(loadingDialog);
            handler.sendEmptyMessageDelayed(0, 1000);
        }, null);
        confirmDialog.show(getFragmentManager(), "confirm");
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
                Toast.makeText(SettingActivity.this,"清除成功",Toast.LENGTH_SHORT).show();
                String str = "0.00MB";
                dataBinding.clearTv.setText(str);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
