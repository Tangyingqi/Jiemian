package com.tyq.jiemian.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.tyq.jiemian.R;
import com.tyq.jiemian.adapter.TRClientAdapter;
import com.tyq.jiemian.bean.ChatBean;
import com.tyq.jiemian.bean.TREntity;
import com.tyq.jiemian.databinding.ActivityTrclientBinding;
import com.tyq.jiemian.ui.callback.TRApi;
import com.tyq.jiemian.ui.callback.TRService;
import com.tyq.jiemian.utils.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tyq on 2016/6/5.
 */
public class TRClientActivity extends AppCompatActivity {

    private TRClientAdapter adapter;
    private TRApi service;
    private ActivityTrclientBinding dataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_trclient);
        adapter = new TRClientAdapter(this);
        dataBinding.trcList.setAdapter(adapter);
        service = TRService.createTRService();
        adapter.addData(new ChatBean(TRClientAdapter.TYPE_ROBOT, Constant.TRC_ROBOT_REC));
        initListener();
    }

    private void initListener() {
        dataBinding.trcBtnSend.setOnClickListener(v -> {
            String str = dataBinding.trcEdit.getText().toString();
            if (TextUtils.isEmpty(str)) {
                return;
            }
            addData(new ChatBean(TRClientAdapter.TYPE_USER, str));
            dataBinding.trcEdit.setText("");
            gainChat(str);
        });
    }

    private void gainChat(String str) {
        Call<TREntity> call = service.getTRResponse(Constant.TRC_KEY, str, Constant.TRC_USER_ID);
        call.enqueue(new Callback<TREntity>() {
            @Override
            public void onResponse(Call<TREntity> call, Response<TREntity> response) {
                TREntity entity = response.body();
                if (entity != null) {
                    String str;
                    if (entity.getCode() == 40004) {
                        str = Constant.TRC_ROBOT_REST;
                    } else {
                        str = entity.getText();
                    }
                    addData(new ChatBean(TRClientAdapter.TYPE_ROBOT, str));
                }
            }

            @Override
            public void onFailure(Call<TREntity> call, Throwable t) {
                addData(new ChatBean(TRClientAdapter.TYPE_ROBOT, Constant.TRC_ROBOT_FAILED));
            }
        });
    }

    private void addData(ChatBean chatBean) {
        adapter.addData(chatBean);
        dataBinding.trcList.setSelection(adapter.getCount());
    }
}
