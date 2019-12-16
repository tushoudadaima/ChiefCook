package com.example.administrator.shixun;

import android.content.Context;
import android.support.v7.app.AlertDialog;

public class LoadingDialog {
    private Context mContext;
    private AlertDialog mAlertDialog;

    public LoadingDialog(Context aContext) {
        mContext = aContext;
        mAlertDialog = new AlertDialog.Builder(mContext, R.style.NoDimAlertDialog)
                .setView(R.layout.dialog_item)
                .create();
        // Set AlertDialog background to transparent
        mAlertDialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
    }

    public void show() {
        if (!mAlertDialog.isShowing()) {
            mAlertDialog.show();
        }
    }

    public void dismiss() {
        if (mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }
}
