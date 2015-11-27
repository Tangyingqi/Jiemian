package com.tyq.jiemian.biz;

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

    public interface RegsiterListener{
        void onSuccess();
        void onFailure(String msg);
    }
    public interface LoginListener{
        void onSuccess();
        void onFailure(String msg);
    }
    public interface UpdataInfo{
        void onSuccess();
        void onFailure(String msg);
    }

    public static void register(Context context,String username,String password, final RegsiterListener registerListener){
        User user  = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                if(registerListener!=null)
                registerListener.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                registerListener.onFailure(s);
            }
        });
    }

    public static void login(Context context,String username,String password,final LoginListener loginListener){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                if (loginListener != null)
                    loginListener.onSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                loginListener.onFailure(s);
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

    public static void upDataInfo(Context context,User user,String nickname,String sex,String signature, final UpdataInfo updataInfo){
        if(user!=null){
            if(nickname!=null)user.setNickName(nickname);
            Log.i("tyq",user.getNickName());
            if (sex!=null)user.setSex(sex);
            if(signature!=null)user.setSignature(signature);
            user.update(context, new UpdateListener() {
                @Override
                public void onSuccess() {
                    if(updataInfo!=null){
                        updataInfo.onSuccess();
                    }
                }

                @Override
                public void onFailure(int i, String s) {
                    if(updataInfo!=null){
                        updataInfo.onFailure(s);
                    }
                }
            });
        }
    }
    public static void logout(Context context) {
        BmobUser.logOut(context);
    }
}
