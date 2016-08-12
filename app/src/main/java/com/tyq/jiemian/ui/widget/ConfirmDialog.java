package com.tyq.jiemian.ui.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by tyq on 2015/11/26.
 */
public class ConfirmDialog extends DialogFragment {

    private String title;
    private String[] items = {"确认","取消"};
    private DialogInterface.OnClickListener okOnClickListener;
    private DialogInterface.OnClickListener cancelOnClickListener;

    public void setParams(String title,DialogInterface.OnClickListener okOnClickListener,DialogInterface.OnClickListener cancelOnClickListener){
        this.title = title;
        this.okOnClickListener = okOnClickListener;
        this.cancelOnClickListener = cancelOnClickListener;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (title!=null)builder.setTitle(title);
        if(items!=null){
            builder.setPositiveButton(items[0],okOnClickListener);
            builder.setNegativeButton(items[1],cancelOnClickListener);
        }
        builder.setCancelable(true);
        return builder.create();
    }
}
