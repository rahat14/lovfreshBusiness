package com.fruitvendorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.Urls;
import com.fruitvendorapp.utilities.Validation;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener, View.OnTouchListener {
    private static final String TAG = ForgotPassActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    private ProgressDialogUtil progressDialogUtil;
    private String emailId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing ForgotPassActivity");
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.forgot_pass));
        btnSubmit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        llLayout.setOnTouchListener(this);
    }

    //attempt forgot pass validation
    private void attemptForgotPass() {
        edEmail.setError(null);
        emailId = edEmail.getText().toString();
        if (TextUtils.isEmpty(emailId)) {
            edEmail.setError(getString(R.string.error_field_required));
        } else if (!Validation.isValidEmail(emailId)) {
            edEmail.setError(getString(R.string.error_invalid_email));
        } else {
            forgotPassApi(emailId);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                attemptForgotPass();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    // this method call Forgot Password api
    private void forgotPassApi(String emailid) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostRequest(NetworkHelper.REQ_CODE_FORGOT_PASSWORD, this, Urls.FORGOT_PASS_URL, new NetworkHelper(this).forgotPasswordJson(emailid), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_FORGOT_PASSWORD:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                String message = json.optString(Constant.MESSAGE);
                String email = json.optString(Constant.EMAIL);
                if (message.length() > 0) {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(this, ResetPassActivity.class);
                i.putExtra(Constant.EMAIL, email);
                startActivity(i);
                break;
        }

    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        BaseUtility.hideKeyboard(v, this);
        return false;
    }

}