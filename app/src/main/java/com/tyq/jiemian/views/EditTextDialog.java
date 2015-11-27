package com.tyq.jiemian.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tyq.jiemian.R;

/**
 * Created by tyq on 2015/11/25.
 */
public class EditTextDialog extends DialogFragment {
    private String[] item = {"确定","取消"};
    private EditText editText;
    private String editStr;
    private boolean singleline;
    private int maxEms;


    private MyOnClickListener myOnClickListener;

    public interface MyOnClickListener{
        void onClick(String str);
    }
    public void setMyOnClickListener(MyOnClickListener myOnClickListener){
        this.myOnClickListener = myOnClickListener;
    }
    public void setParams(String editStr,boolean singleline,int maxEms){
        this.editStr = editStr;
        this.singleline = singleline;
        this.maxEms = maxEms;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view= getActivity().getLayoutInflater().inflate(R.layout.view_edit_dialog,null);
        editText = (EditText) view.findViewById(R.id.edit_dialog_view_et);
        editText.setText(editStr);
        editText.setSingleLine(singleline);
        editText.setMaxEms(maxEms);
        builder.setView(view).setPositiveButton(item[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myOnClickListener.onClick(editText.getText().toString());
            }
        }).setNegativeButton(item[1],null).setCancelable(true);
        return builder.create();
    }
}
