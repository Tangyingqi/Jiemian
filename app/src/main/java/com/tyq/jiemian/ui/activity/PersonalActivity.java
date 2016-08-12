package com.tyq.jiemian.ui.activity;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tyq.jiemian.R;
import com.tyq.jiemian.bean.User;
import com.tyq.jiemian.databinding.ActivityPersonalBinding;
import com.tyq.jiemian.utils.UserProxy;
import com.tyq.jiemian.ui.widget.ChooseDialog;
import com.tyq.jiemian.ui.widget.ConfirmDialog;
import com.tyq.jiemian.ui.widget.EditTextDialog;
import com.tyq.jiemian.ui.widget.LoadingDialog;

/**
 * Created by tyq on 2015/11/25.
 */
public class PersonalActivity extends AppCompatActivity implements View.OnClickListener,UserProxy.UpdateInfoInterface, EditTextDialog.MyOnClickListener, DialogInterface.OnClickListener {
    private LoadingDialog loadingDialog;
    private ActivityPersonalBinding dataBinding;
    private UserProxy userProxy;
    private String[] str= {"男","女"};
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this,R.layout.activity_personal);
        loadingDialog = new LoadingDialog();
        user = UserProxy.getCurrentUser(this);
        if(null != user){
            dataBinding.setUser(user);
        }
        setListener();
    }

    private void setListener() {
        dataBinding.nicknameBtn.setOnClickListener(this);
        dataBinding.sexBtn.setOnClickListener(this);
        dataBinding.signatureBtn.setOnClickListener(this);
        dataBinding.logoutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditTextDialog editTextDialog = new EditTextDialog();
        userProxy = new UserProxy();
        switch (v.getId()){
            case R.id.nickname_btn:
                editTextDialog.setParams(dataBinding.tvNickname.getText().toString(),true,20);
                editTextDialog.setMyOnClickListener(this);
                editTextDialog.show(getFragmentManager(), "set_nickname_list_dialog");
                break;
            case R.id.sex_btn:
                ChooseDialog chooseDialog = new ChooseDialog();
                chooseDialog.setParams(null, str,this);
                chooseDialog.show(getFragmentManager(),"choose");
                break;
            case R.id.signature_btn:
                break;
            case R.id.logout_btn:
                ConfirmDialog confirmDialog = new ConfirmDialog();
                confirmDialog.setParams("确认退出登录吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserProxy.logout(getApplicationContext());
                        PersonalActivity.this.finish();
                    }
                },null);
                confirmDialog.show(getFragmentManager(),"confirm");
                break;
        }
    }

    @Override
    public void onSuccess() {
        loadingDialog.dismiss();
        dataBinding.setUser(user);
        Toast.makeText(PersonalActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String msg) {
        loadingDialog.dismiss();
        Toast.makeText(PersonalActivity.this,"更新失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(String str) {
        if(str==null)return;
        loadingDialog.show(getFragmentManager(), "loading");
        userProxy.upDataInfo(PersonalActivity.this, user, str, null, null, this);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        loadingDialog.show(getFragmentManager(),"loading");
        userProxy.upDataInfo(PersonalActivity.this, user, null, str[which], null,this);
    }
}
