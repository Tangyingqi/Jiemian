package com.tyq.jiemian.utils;

import android.content.Context;
import android.util.Log;

import com.tyq.jiemian.bean.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by tyq on 2015/11/25.
 */
public class UserProxy {


    public interface LoginAndRegisterInterface{
        void onSuccess();
        void onFailure(String msg);
    }
    public interface UpdateInfoInterface{
        void onSuccess();
        void onFailure(String msg);
    }

    public UpdateInfoInterface updateInfoInterface;

    public static void register(Context context,String username,String password, final LoginAndRegisterInterface loginAndRegisterInterface){
        User user  = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                if(loginAndRegisterInterface!=null)
                    loginAndRegisterInterface.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                loginAndRegisterInterface.onFailure(s);
            }
        });
    }

    public static void login(Context context,String username,String password,final LoginAndRegisterInterface loginAndRegisterInterface){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                if (loginAndRegisterInterface != null)
                    loginAndRegisterInterface.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                loginAndRegisterInterface.onFailure(s);
            }
        });
    }

    public static User getCurrentUser(Context context){
        User user = BmobUser.getCurrentUser(context,User.class);
        if(user!=null){
            return user;
        }
        return null;
    }
    public static Boolean isLogin(Context context) {
        User user = BmobUser.getCurrentUser(context, User.class);
        return user != null;
    }

    public void upDataInfo(Context context,User user,String nickname,
                                  String sex,String signature, final UpdateInfoInterface updateInfoInterface){
        if(user!=null){
            this.updateInfoInterface = updateInfoInterface;
            if(nickname!=null)user.setNickName(nickname);
            if (sex!=null)user.setSex(sex);
            if(signature!=null)user.setSignature(signature);
            user.update(context, new UpdateListener() {
                @Override
                public void onSuccess() {
                    if(updateInfoInterface!=null){
                        updateInfoInterface.onSuccess();
                    }
                }

                @Override
                public void onFailure(int i, String s) {
                    if(updateInfoInterface!=null){
                        updateInfoInterface.onFailure(s);
                    }
                }
            });
        }
    }
    public static void logout(Context context) {
        BmobUser.logOut(context);
    }
}
