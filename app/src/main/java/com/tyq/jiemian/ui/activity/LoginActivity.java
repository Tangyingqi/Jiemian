package com.tyq.jiemian.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.tyq.jiemian.R;
import com.tyq.jiemian.databinding.ActivityLoginBinding;
import com.tyq.jiemian.utils.Constant;
import com.tyq.jiemian.utils.UserProxy;
import com.tyq.jiemian.ui.widget.LoadingDialog;

import cn.bmob.v3.Bmob;

/**
 * Created by tyq on 2015/11/22.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, UserProxy.LoginAndRegisterInterface {

    private LoadingDialog dialog;
    private String username, password;
    private ActivityLoginBinding dataBinding;


    private enum LoginOrRegister {
        LOGIN, REGISTER
    }

    private LoginOrRegister loginOrRegister = LoginOrRegister.LOGIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        dataBinding.loginBtn.setOnClickListener(this);
        dataBinding.registerBtn.setOnClickListener(this);
        dialog = new LoadingDialog();
        dialog.setParams("请稍等...");
        Bmob.initialize(this, Constant.Bmob_App_ID);
    }

    private boolean isUserComplete() {
        username = dataBinding.loginUsernameEt.getText().toString();
        password = dataBinding.loginPasswordEt.getText().toString();
        if (username == null) {
            dataBinding.loginUsernameEt.setShakeAnimation();
            Toast.makeText(this, "请填写用户名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password == null) {
            dataBinding.loginPasswordEt.setShakeAnimation();
            Toast.makeText(this, "请填写密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (loginOrRegister == LoginOrRegister.LOGIN) {
                    if (!isUserComplete()) return;
                    dialog.show(getFragmentManager(), "loading");
                    UserProxy.login(getApplicationContext(), username, password, this);
                } else if (loginOrRegister == LoginOrRegister.REGISTER) {
                    if (!isUserComplete()) return;
                    dialog.show(getFragmentManager(), "loading...");
                    UserProxy.register(getApplicationContext(), username, password, this);
                }

                break;
            case R.id.register_btn:
                if (loginOrRegister == LoginOrRegister.LOGIN) {
                    loginOrRegister = LoginOrRegister.REGISTER;
                    dataBinding.registerBtn.setText("登录");
                } else if (loginOrRegister == LoginOrRegister.REGISTER) {
                    loginOrRegister = LoginOrRegister.LOGIN;
                    dataBinding.registerBtn.setText("注册帐号");
                }
                updateLayout(loginOrRegister);
                break;

        }

    }

    private void updateLayout(LoginOrRegister loginOrRegister) {
        if (loginOrRegister == LoginOrRegister.LOGIN) {
            dataBinding.loginBtn.setText("登录");
        } else if (loginOrRegister == LoginOrRegister.REGISTER) {
            dataBinding.loginBtn.setText("注册");
        }
    }

    @Override
    public void onSuccess() {
        dialog.dismiss();
        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        LoginActivity.this.finish();
    }

    @Override
    public void onFailure(String msg) {
        dialog.dismiss();
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
