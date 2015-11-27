package com.tyq.jiemian.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tyq.jiemian.R;
import com.tyq.jiemian.bean.User;
import com.tyq.jiemian.biz.UserProxy;
import com.tyq.jiemian.views.ChooseDialog;
import com.tyq.jiemian.views.ConfirmDialog;
import com.tyq.jiemian.views.EditTextDialog;
import com.tyq.jiemian.views.LoadingDialog;

/**
 * Created by tyq on 2015/11/25.
 */
public class PersonalActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_UserName;
    private Button  btn_NickName;
    private Button btn_Sex;
    private Button btn_Signature;
    private Button btn_logout;
    private TextView tv_nickname;
    private TextView tv_sex;
    private LoadingDialog loadingDialog;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        initViews();

        user = UserProxy.getCurrentUser(this);
        if(user!=null){
            tv_UserName.setText(user.getUsername());
        }
        setListener();
    }

    private void setListener() {
        btn_NickName.setOnClickListener(this);
        btn_Sex.setOnClickListener(this);
        btn_Signature.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("个人信息");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadingDialog = new LoadingDialog();

        tv_UserName = (TextView) findViewById(R.id.username);
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        btn_NickName = (Button) findViewById(R.id.nickname_btn);
        btn_Sex = (Button) findViewById(R.id.sex_btn);
        btn_Signature = (Button) findViewById(R.id.signature_btn);
        btn_logout = (Button) findViewById(R.id.logout_btn);
    }

    @Override
    public void onClick(View v) {
        EditTextDialog editTextDialog = new EditTextDialog();
        switch (v.getId()){
            case R.id.nickname_btn:
                editTextDialog.setParams(tv_nickname.getText().toString(),true,20);
                editTextDialog.setMyOnClickListener(new EditTextDialog.MyOnClickListener() {
                    @Override
                    public void onClick(String str) {
                        if(str==null)return;
                        loadingDialog.show(getFragmentManager(), "loading");
                        UserProxy.upDataInfo(PersonalActivity.this,user, str, null, null, new UserProxy.UpdataInfo() {
                            @Override
                            public void onSuccess() {
                                loadingDialog.dismiss();
                                Toast.makeText(PersonalActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
                                tv_nickname.setText(user.getNickName());
                            }

                            @Override
                            public void onFailure(String msg) {
                                loadingDialog.dismiss();
                                Toast.makeText(PersonalActivity.this,"更新失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                editTextDialog.show(getFragmentManager(), "set_nickname_list_dialog");
                break;
            case R.id.sex_btn:
                final String[] str = {"男","女"};
                ChooseDialog chooseDialog = new ChooseDialog();
                chooseDialog.setParams(null, str, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadingDialog.show(getFragmentManager(),"loading");
                        UserProxy.upDataInfo(PersonalActivity.this, user, null, str[which], null, new UserProxy.UpdataInfo() {
                            @Override
                            public void onSuccess() {
                                loadingDialog.dismiss();
                                tv_sex.setText(user.getSex());
                                Toast.makeText(PersonalActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(String msg) {
                                loadingDialog.dismiss();
                                Toast.makeText(PersonalActivity.this,"更新失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
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
}
