package com.fruitvendorapp.utilities;
import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtil {
    private ProgressDialog progressDialog;

    public ProgressDialogUtil(Context context) {
        progressDialog = new ProgressDialog(context);
    }

    public void showDialog() {
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public void dismissDialog(){
        progressDialog.dismiss();
    }
}
