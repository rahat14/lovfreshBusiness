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
import com.fruitvendorapp.utilities.SessionManager;
import com.fruitvendorapp.utilities.Urls;
import com.fruitvendorapp.utilities.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {
    private static final String TAG = ChangePasswordActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ed_old_pass)
    EditText edOldPass;
    @BindView(R.id.ed_password)
    EditText edPassword;
    @BindView(R.id.ed_conf_password)
    EditText edConfPassword;
    private ProgressDialogUtil progressDialogUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing ChangePasswordActivity");
        init();
    }

    private void init() {
        tvToolbarTitle.setText(R.string.change_password);
        progressDialogUtil = new ProgressDialogUtil(this);
        ivBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                attempPass();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;

        }
    }

    public void attempPass() {
        String old_pass = edOldPass.getText().toString();
        String pass = edPassword.getText().toString();
        String confPassword = edConfPassword.getText().toString();
        boolean cancel = false;
        if (TextUtils.isEmpty(old_pass)) {
            edOldPass.setError(this.getString(R.string.error_field_required));
            cancel = true;
        }
        // Check user entered password.
        else if (TextUtils.isEmpty(pass)) {
            edPassword.setError(this.getString(R.string.error_field_required));
            cancel = true;
        } else if (!Validation.isValidPasswordz(pass)) {
            edPassword.setError(getString(R.string.error_invalid_pass));
            cancel = true;
        }

        if (TextUtils.isEmpty(confPassword)) {
            edConfPassword.setError(this.getString(R.string.error_field_required));
            cancel = true;
        } else if (!confPassword.equals(pass)) {
            edConfPassword.setError(this.getString(R.string.error_password_mismatch));
            cancel = true;
        }
        if (!cancel) {
            changePasswordApi(old_pass, pass, confPassword);

        }
    }

    // this method call Reset Password api
    private void changePasswordApi(String old_pass, String password, String confPassword) {
        Log.e(TAG, new SessionManager(this).getToken());
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_CHANGE_PASSWORD, this, Urls.CHANGE_PASSWORD_URL, new NetworkHelper(this).changePasswordJson(old_pass, password, confPassword), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_CHANGE_PASSWORD:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                String message = json.optString(Constant.MESSAGE);
                edOldPass.setText("");
                edPassword.setText("");
                edConfPassword.setText("");
                onBackPressed();
                if (message.length() > 0) {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
        if (anError.getErrorCode() != 0) {
            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
            if (anError.getErrorCode() == 400) {
                String json = anError.getErrorBody();
                try {
                    JSONObject object = new JSONObject(json);
                    String error = object.optString(Constant.ERROR);
                    if (error.length() > 0) {
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}