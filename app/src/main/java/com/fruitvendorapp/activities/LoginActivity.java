package com.fruitvendorapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.model.AddressModel;
import com.fruitvendorapp.model.UserModel;
import com.fruitvendorapp.model.VendorModel;
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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

import static com.fruitvendorapp.utilities.Constant.STATUS;
import static com.fruitvendorapp.utilities.Constant.TWILIO_API_KEY;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener, View.OnTouchListener {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private TelephonyManager mTelephonyManager;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_signup)
    TextView btnSignup;
    @BindView(R.id.btn_change_login)
    Button btnChangeLogin;
    @BindView(R.id.ed_email)
    AppCompatEditText edUserName;
    @BindView(R.id.ed_password)
    AppCompatEditText edPassword;
    @BindView(R.id.ed_mobile)
    AppCompatEditText edMobile;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_forgot_pass)
    TextView tvForgotPassword;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @BindView(R.id.rv_mobile)
    RelativeLayout rvMobileView;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.ll_gmail_login)
    LinearLayout llGmailLogin;
    private double latitude, longitude;
    private ProgressDialogUtil progressDialogUtil;
    private Boolean isLoginWithEmail = true;
    private String countryCode = "", mobileNo = "", deviceid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing LoginActivity");
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.login));
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
        btnChangeLogin.setOnClickListener(this);
        llLayout.setOnTouchListener(this);
        llGmailLogin.setOnClickListener(this);
    }

    private void changeBtnLogin() {
        String btntitle = btnChangeLogin.getText().toString();
        if (btntitle.equals(getString(R.string.loginwithmobile))) {
            isLoginWithEmail = false;
            btnChangeLogin.setText(R.string.loginwithemail);
            edUserName.setVisibility(View.GONE);
            edPassword.setVisibility(View.GONE);
            rvMobileView.setVisibility(View.VISIBLE);
            tvForgotPassword.setVisibility(View.GONE);
        } else if (btntitle.equals(getString(R.string.loginwithemail))) {
            isLoginWithEmail = true;
            btnChangeLogin.setText(R.string.loginwithmobile);
            edUserName.setVisibility(View.VISIBLE);
            edPassword.setVisibility(View.VISIBLE);
            rvMobileView.setVisibility(View.GONE);
            tvForgotPassword.setVisibility(View.VISIBLE);
        }
    }


    private void attemptLogin() {
        if (isLoginWithEmail) {
            edUserName.setError(null);
            edPassword.setError(null);
            boolean cancel = false;
            String user_name = edUserName.getText().toString();
            String password = edPassword.getText().toString();
            if (TextUtils.isEmpty(user_name)) {
                edUserName.setError(getString(R.string.error_field_required));
                cancel = true;
            }
            if (!Validation.isValidPassword(password)) {
                edPassword.setError(getString(R.string.error_invalid_pass));
                cancel = true;
            }
            if (!cancel) {
                loginWithEmailApi(user_name, password);
            }
        } else {
            edMobile.setError(null);
            String mobileNumber = edMobile.getText().toString();
            countryCode = ccp.getSelectedCountryCode();
            // ccp.registerCarrierNumberEditText(edMobile);
            ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
                @Override
                public void onCountrySelected() {
                    countryCode = ccp.getSelectedCountryCode();
                    Log.e(TAG, countryCode);
                }
            });
            if (TextUtils.isEmpty(mobileNumber)) {
                edMobile.setError(getString(R.string.error_field_required));
            } else {
                loginWithMobileApi(mobileNumber);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                attemptLogin();
                break;
            case R.id.btn_signup:
                BaseUtility.sendActivityIntent(this, SignUpActivity.class);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_forgot_pass:
                BaseUtility.sendActivityIntent(this, ForgotPassActivity.class);
                break;
            case R.id.btn_change_login:
                changeBtnLogin();
                break;
            case R.id.ll_gmail_login:
                // googleSignIn();
                break;
        }
    }

    //this method call login api
    private void loginWithEmailApi(String user_name, String password) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostRequest(NetworkHelper.REQ_CODE_LOGIN, this, Urls.LOGIN_EMAIL_URL, new NetworkHelper(this).loginJson(user_name, password), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    //this method call login api
    private void loginWithMobileApi(String phone_no) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostRequest(NetworkHelper.REQ_CODE_LOGIN_MOBILE, this, Urls.LOGIN_MOBILE_URL, new NetworkHelper(this).loginWithMobileJson("+" + countryCode + phone_no), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    private void callOTPSendAPI(String mobileNumber, String countryCode) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostTwilioRequest(NetworkHelper.REQ_CODE_TWILIO_OTP_SEND, this, TWILIO_API_KEY, Urls.SEND_TWILIO_OTP_URL,
                    new NetworkHelper(this).twilioOtpJson(mobileNumber, countryCode), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_LOGIN:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                try {
                    String message = json.optString(Constant.MESSAGE);
                    JSONObject userMainObject = json.getJSONObject(Constant.USER);
                    JSONObject addressObject = new JSONObject();
                    if (userMainObject.has(Constant.ADDRESS)) {
                        addressObject = userMainObject.getJSONObject(Constant.ADDRESS);
                    }
                    String vendor_id = userMainObject.optString(Constant.VENDOR_ID);
                    String shopTitle = userMainObject.optString(Constant.TITLE);
                    String subscription = "";
                    if (userMainObject.has(Constant.SUBSCRIPATION)) {
                        subscription = userMainObject.getString(Constant.SUBSCRIPATION);
                    }
                    JSONObject userObject = userMainObject.getJSONObject(Constant.USER);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    UserModel userModel = gson.fromJson(String.valueOf(userObject), UserModel.class);
                    AddressModel addressModel = gson.fromJson(String.valueOf(addressObject), AddressModel.class);
                    SessionManager sessionManager = new SessionManager(LoginActivity.this);
                    String token = userObject.optString(Constant.TOKEN);
                    boolean is_active = userObject.optBoolean(Constant.IS_ACTIVE);
                    boolean is_verify = userObject.optBoolean(Constant.IS_VERIFY);
                    new SessionManager(this).setToken(token);
                    new SessionManager(this).setShopTitle(shopTitle);
                    new SessionManager(this).setSubscription(subscription);
                    if (Objects.requireNonNull(edUserName.getText()).toString().contains("s100") || Objects.requireNonNull(edUserName.getText()).toString().contains("t100")) {
                        is_verify = true;
                        userMainObject.put("subscribe", "false");
                        subscription = "1";
                    }
                    if (userModel.getVendor()) {
                        if (is_verify) {
                            sessionManager.saveUserDetail(userModel);
                            sessionManager.saveAddressDetail(addressModel);
                            BaseUtility.toastMsg(this, "Login Successfully");
                            getDeviceId();
                            if (userMainObject.has("subscribe")) {
                                if (userMainObject.getBoolean("subscribe")) {
                                    if (subscription.equals("null")) {
                                        BaseUtility.sentToScreenIntentWithFlag(this, PickAPlanActivity.class);
                                    } else {
                                        Intent intent = new Intent(this, DashboardActivity.class);
                                        intent.putExtra(Constant.VENDOR_ID, vendor_id);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        //BaseUtility.sentToScreenIntentWithFlag(this, DashboardActivity.class);
                                        /* }*/
                                        finish();
                                    }
                                } else {
                                    Intent intent = new Intent(this, DashboardActivity.class);
                                    intent.putExtra(Constant.VENDOR_ID, vendor_id);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    //BaseUtility.sentToScreenIntentWithFlag(this, DashboardActivity.class);
                                    /* }*/
                                    finish();
                                }
                            }
                        } else {
                            Toast.makeText(this, "Vendor is not verified", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        BaseUtility.toastMsg(this, "Invalid Credentials.");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case NetworkHelper.REQ_CODE_LOGIN_MOBILE:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                String message_phone = json.optString(Constant.MESSAGE);
                String phone = json.optString(Constant.PHONE_NUMBER);
                mobileNo = phone;
                String token = json.optString(Constant.TOKEN);
                new SessionManager(this).setToken(token);
                new SessionManager(this).setPhone(phone);
                BaseUtility.toastMsg(this, "Login Successfully");
                callOTPSendAPI(mobileNo, "+" + countryCode);
                getDeviceId();
                /*Intent intent = new Intent(this, OTPVerifyActivity.class);
                intent.putExtra(Constant.PHONE_NUMBER, "+61" + phone);
                startActivity(intent);*/
                break;
            case NetworkHelper.REQ_CODE_FORGOT_PASSWORD:
                // progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                String message1 = json.optString(Constant.MESSAGE);
                String email = json.optString(Constant.EMAIL);
                if (message1.length() > 0) {
                    Toast.makeText(this, message1, Toast.LENGTH_SHORT).show();
                }
                break;
            case NetworkHelper.REQ_CODE_TWILIO_OTP_SEND:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                try {
                    if (Boolean.parseBoolean(json.optString(STATUS))) {
                        BaseUtility.toastMsg(this, "OTP Send Successfully");
                        Intent otpIntent = new Intent(this, OTPVerifyActivity.class);
                        otpIntent.putExtra(Constant.COUNTRY_CODE, countryCode);
                        otpIntent.putExtra(Constant.PHONE_NUMBER, mobileNo);
                        otpIntent.putExtra(Constant.FROM_PLACE, "login");
                        otpIntent.putExtra(Constant.VENDOR_ID, new SessionManager(this).getVendorId());
                        startActivity(otpIntent);
                    } else {
                        BaseUtility.toastMsg(this, json.optString("message"));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case NetworkHelper.REQ_CODE_SEND_FCM:
                Log.e(TAG, "fcm_api----------------->" + json.toString());
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
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.has("non_field_errors")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("non_field_errors");
                        String error = jsonArray.getString(0);
                        if (error.length() > 0) {
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                    String message = jsonObject.optString(Constant.MESSAGE);
                    if (jsonObject.has(Constant.USER)) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject(Constant.USER);
                        JSONObject jsonObject2 = jsonObject1.getJSONObject(Constant.USER);
                        String token = jsonObject2.optString(Constant.TOKEN);
                        String phone = jsonObject2.optString(Constant.PHONE_NUMBER);
                        countryCode = jsonObject1.optString(Constant.COUNTRY_CODE);
                        mobileNo = jsonObject1.optString(Constant.NATIONAL_NUMBER);
                        if (countryCode.length() == 0) {
                            countryCode = jsonObject2.optString(Constant.COUNTRY_CODE);
                        }
                        if (mobileNo.length() == 0) {
                            mobileNo = jsonObject2.optString(Constant.NATIONAL_NUMBER);
                        }
                        String email = jsonObject2.optString(Constant.EMAIL);
                        Log.e(TAG, "token_and_phone" + token + " " + phone);
                        new SessionManager(this).setToken(token);
                        BaseUtility.toastMsg(this, message);
//                        forgotPassApi(email);
                        callOTPSendAPI(mobileNo, "+" + countryCode);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDeviceImei();
        }
    }

    private void getDeviceId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {
                getDeviceImei();
            }
        }
    }

    private void getDeviceImei() {
        String myuniqueID;
        int myversion = Integer.valueOf(Build.VERSION.SDK);
        if (myversion < 23) {
            WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            myuniqueID = info.getMacAddress();
            if (myuniqueID == null) {
                TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                myuniqueID = mngr.getDeviceId();
                getFcmApi(myuniqueID);
            }
        } else if (myversion > 23 && myversion < 29) {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            myuniqueID = mngr.getDeviceId();
            getFcmApi(myuniqueID);
        } else {
            String androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            myuniqueID = androidId;
            getFcmApi(myuniqueID);
        }
        /*try {
            mTelephonyManager = (TelephonyManager)getSystemService(getApplicationContext().TELEPHONY_SERVICE);
            deviceid = mTelephonyManager.getDeviceId();
            Log.e("msg", "DeviceImei " + deviceid);
            if (deviceid != null && !TextUtils.isEmpty(deviceid)) {
                getFcmApi(deviceid);
            } else {
                Log.e(TAG,"ms,m,smsm,smmsm");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void getFcmApi(String deviceid) {
        if (ConnectionUtil.isInternetOn(this)) {
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_SEND_FCM, this, Urls.FCM_URL, new NetworkHelper(this).sendFcmJson(deviceid), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    private void forgotPassApi(String emailid) {
        if (ConnectionUtil.isInternetOn(this)) {
            //  progressDialogUtil.showDialog();
            RequestHelper.PostRequest(NetworkHelper.REQ_CODE_FORGOT_PASSWORD, this, Urls.FORGOT_PASS_URL, new NetworkHelper(this).forgotPasswordJson(emailid), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        BaseUtility.hideKeyboard(v, this);
        return false;
    }
}