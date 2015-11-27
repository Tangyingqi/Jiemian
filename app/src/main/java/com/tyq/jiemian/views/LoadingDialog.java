package com.tyq.jiemian.views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * Created by tyq on 2015/11/24.
 */
public class LoadingDialog extends DialogFragment {

    private String title;
    private String message;

    public void setParams(String message) {
        setParams(null, message);
    }

    public void setParams(String title, String message) {
        this.title = title;
        this.message = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);

        if(title!=null)progressDialog.setTitle(title);
        if (message!=null)progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);


        return progressDialog;
    }
}
