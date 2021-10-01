package com.fruitvendorapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.SessionManager;
import com.fruitvendorapp.utilities.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class NotificationSettingActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {
    private static final String TAG = NotificationSettingActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.sc_sound)
    SwitchCompat scSound;
    @BindView(R.id.sc_vibrate)
    SwitchCompat scVibrate;
    @BindView(R.id.sc_sms)
    SwitchCompat scSms;
    private ProgressDialogUtil progressDialogUtil;
    private boolean is_sound = false;
    private boolean is_vibrate = false;
    private boolean is_sms = false;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing NotificationSettingActivity");
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.notification_setting));
        ivBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        scSound.setOnClickListener(this);
        scVibrate.setOnClickListener(this);
        scSms.setOnClickListener(this);
        getNotificationApi();
    }

    private void getNotificationApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            Log.e(TAG, new SessionManager(this).getToken());
            progressDialogUtil.showDialog();
            Log.e(TAG, Urls.SIGNUP_URL + new SessionManager(this).getVendorId());
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_NOTIFICATION_SETTING, this, Urls.SIGNUP_URL + new SessionManager(this).getVendorId(), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    private void postNotificationApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_UPDATE_NOTIFICATION, this, Urls.SAVE_NOTIFICATION_URL, new NetworkHelper(this).updateNotificationJson(id, is_sound, is_vibrate, is_sms), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_UPDATE_NOTIFICATION:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                getNotificationApi();
                BaseUtility.toastMsg(this, "Saved successfully");
                break;
            case NetworkHelper.REQ_CODE_GET_NOTIFICATION_SETTING:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                try {
                    JSONArray notifiArray = json.getJSONArray("vendor_notification");
                    JSONObject jsonObject = notifiArray.getJSONObject(0);
                    id = jsonObject.optString("id");
                    is_sound = jsonObject.optBoolean("is_sound");
                    is_vibrate = jsonObject.optBoolean("is_vibration");
                    is_sms = jsonObject.optBoolean("is_sms");
                    if (is_sound) {
                        scSound.setChecked(true);
                    } else {
                        scSound.setChecked(false);
                    }
                     if (is_vibrate) {
                        scVibrate.setChecked(true);
                    } else {
                         scVibrate.setChecked(false);
                    }
                     if (is_sms) {
                        scSms.setChecked(true);
                    } else {
                         scSms.setChecked(false);
                    }
                    new SessionManager(NotificationSettingActivity.this).setSound(is_sound);
                    new SessionManager(NotificationSettingActivity.this).setVibrate(is_vibrate);
                     Log.e(TAG, is_sound + "" + is_vibrate + "" + is_sms);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                postNotificationApi();
                break;
            case R.id.sc_sound:
                if (scSound.isChecked()) {
                    is_sound = true;
                } else {
                    is_sound = false;
                }
                Log.e(TAG, String.valueOf(is_sound));
                break;
            case R.id.sc_sms:
                if (scSms.isChecked()) {
                    is_sms = true;
                } else {
                    is_sms = false;
                }
                Log.e(TAG, String.valueOf(is_sms));
                break;
            case R.id.sc_vibrate:
                if (scVibrate.isChecked()) {
                    is_vibrate = true;
                } else {
                    is_vibrate = false;
                }
                Log.e(TAG, String.valueOf(is_vibrate));
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

}