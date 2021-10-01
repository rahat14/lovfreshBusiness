package com.fruitvendorapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ResetPassActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {
    private static final String TAG = ResetPassActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ed_otp)
    EditText edOTP;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_conf_password)
    EditText edConfPassword;
    @BindView(R.id.tv_resend)
    TextView tvResend;
    private String email = "";
    private ProgressDialogUtil progressDialogUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing ResetPassActivity");
        init();
    }

    private void init() {
        tvToolbarTitle.setText(R.string.reset_password);
        progressDialogUtil = new ProgressDialogUtil(this);
        if (getIntent().hasExtra(Constant.EMAIL)) {
            email = getIntent().getStringExtra(Constant.EMAIL);
        }
        ivBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tvResend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                attempResetPass();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_resend:
                forgotPassApi(email);
                break;
        }

    }

    // set  validation for submit reset password
    public void attempResetPass() {
        String otp = edOTP.getText().toString();
        String pass = edPassword.getText().toString();
        String confPassword = edConfPassword.getText().toString();
        if (TextUtils.isEmpty(otp)) {
            edOTP.setError(this.getString(R.string.otp_error_msg));
        }
        // Check user entered password.
        else if (TextUtils.isEmpty(pass)) {
            edPassword.setError(this.getString(R.string.error_field_required));
        } else if (!Validation.isValidPassword(pass)) {
            edPassword.setError(getString(R.string.error_invalid_pass));
        } else if (TextUtils.isEmpty(confPassword)) {
            edConfPassword.setError(this.getString(R.string.error_field_required));
        } else if (!confPassword.equals(pass)) {
            edConfPassword.setError(this.getString(R.string.error_password_mismatch));

        } else {
            resetPasswordApi(email, pass, otp);

        }
    }

    // this method call Reset Password api
    private void resetPasswordApi(String email, String password, String otp) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostRequest(NetworkHelper.REQ_CODE_RESET_PASSWORD, this, Urls.RESET_PASSWORD_URL, new NetworkHelper(this).resetPasswordJson(email, password, otp), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
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
            case NetworkHelper.REQ_CODE_RESET_PASSWORD:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                Toast.makeText(this, "Reset Password Successfully", Toast.LENGTH_SHORT).show();
                BaseUtility.sendActivityIntent(ResetPassActivity.this, LoginActivity.class);
                finishAffinity();
                break;
            case NetworkHelper.REQ_CODE_FORGOT_PASSWORD:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                String message = json.optString(Constant.MESSAGE);
                if (message.length() > 0) {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }


}