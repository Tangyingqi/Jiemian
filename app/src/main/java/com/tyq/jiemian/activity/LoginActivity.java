package com.tyq.jiemian.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tyq.jiemian.R;
import com.tyq.jiemian.bean.Constant;
import com.tyq.jiemian.biz.UserProxy;
import com.tyq.jiemian.views.DeletableEditText;
import com.tyq.jiemian.views.LoadingDialog;

import cn.bmob.v3.Bmob;

/**
 * Created by tyq on 2015/11/22.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private DeletableEditText username_et,password_et;
    private Button login_btn,register_btn;
    private LoadingDialog dialog;
    private String username,password;

    private enum LoginOrRegister{
        LOGIN,REGISTER
    }
    private LoginOrRegister loginOrRegister = LoginOrRegister.LOGIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, Constant.Bmob_App_ID);

        initView();
    }

    private void initView() {

        username_et = (DeletableEditText) findViewById(R.id.login_username_et);
        password_et = (DeletableEditText) findViewById(R.id.login_password_et);
        login_btn = (Button) findViewById(R.id.login_btn);
        register_btn = (Button) findViewById(R.id.register_btn);
        dialog = new LoadingDialog();
        dialog.setParams("请稍等...");

        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
    }
    private boolean isUserComplete(){
        username = username_et.getText().toString();
        password = password_et.getText().toString();
        if(username==null){
            username_et.setShakeAnimation();
            Toast.makeText(this,"请填写用户名",Toast.LENGTH_SHORT);
            return false;
        }
        if(password==null){
            password_et.setShakeAnimation();
            Toast.makeText(this,"请填写密码",Toast.LENGTH_SHORT);
            return  false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn:
                if(loginOrRegister == LoginOrRegister.LOGIN){
                    if(!isUserComplete())return;
                    dialog.show(getFragmentManager(),"loading");
                    UserProxy.login(getApplicationContext(), username, password, new UserProxy.LoginListener() {
                        @Override
                        public void onSuccess() {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                           // Constant.isLogin = true;
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void onFailure(String msg) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else if(loginOrRegister == LoginOrRegister.REGISTER){
                    if(!isUserComplete())return;
                    dialog.show(getFragmentManager(),"loading...");
                    UserProxy.register(getApplicationContext(), username, password, new UserProxy.RegsiterListener() {
                        @Override
                        public void onSuccess() {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void onFailure(String msg) {
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                break;
            case R.id.register_btn:
                if(loginOrRegister == LoginOrRegister.LOGIN) {
                    loginOrRegister = LoginOrRegister.REGISTER;
                    register_btn.setText("登录");
                }
                else if(loginOrRegister == LoginOrRegister.REGISTER) {
                    loginOrRegister = LoginOrRegister.LOGIN;
                    register_btn.setText("注册帐号");
                }
                updateLayout(loginOrRegister);
                break;

        }

    }
    private void updateLayout(LoginOrRegister loginOrRegister) {
        if(loginOrRegister == LoginOrRegister.LOGIN) {
            login_btn.setText("登录");
        }
        else if(loginOrRegister == LoginOrRegister.REGISTER) {
            login_btn.setText("注册");
        }
    }
}
