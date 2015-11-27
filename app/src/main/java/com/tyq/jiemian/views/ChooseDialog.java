package com.tyq.jiemian.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by tyq on 2015/11/26.
 */
public class ChooseDialog extends DialogFragment {
    private String title;
    private String[] item;
    private DialogInterface.OnClickListener chooseOnClickListener;

    public void setParams(String title,String[] item,DialogInterface.OnClickListener chooseOnClickListener){
        this.title = title;
        this.item = item;
        this.chooseOnClickListener = chooseOnClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(title!=null)builder.setTitle(title);
        if(item!=null)builder.setItems(item,chooseOnClickListener);
        builder.setCancelable(true);
        return builder.create();
    }
}
