package com.fruitvendorapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.model.AddressModel;
import com.fruitvendorapp.model.UserModel;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.SessionManager;
import com.fruitvendorapp.utilities.Urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

import static com.fruitvendorapp.utilities.Constant.STATUS;
import static com.fruitvendorapp.utilities.Constant.TWILIO_API_KEY;

public class OTPVerifyActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener, View.OnTouchListener {
    private static final String TAG = OTPVerifyActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_resend)
    TextView tvResend;
    @BindView(R.id.ed_otp)
    AppCompatEditText edOtp;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    private ProgressDialogUtil progressDialogUtil;
    String phone_no = "", vendor_id = "", country_code = "", from_screen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verify);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing OTPVerifyActivity");
        init();
    }

    private void init() {
        if (getIntent().hasExtra(Constant.PHONE_NUMBER)) {
            country_code = getIntent().getStringExtra(Constant.COUNTRY_CODE);
            phone_no = getIntent().getStringExtra(Constant.PHONE_NUMBER);
            vendor_id = getIntent().getStringExtra(Constant.VENDOR_ID);
            from_screen = getIntent().getStringExtra(Constant.FROM_PLACE);
            Log.e(TAG, "mobile-------------->" + phone_no + "vendor_id" + vendor_id);
        }
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.otp_verification));
        ivBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tvResend.setOnClickListener(this);
        llLayout.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                attemptOTP();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_resend:
                resendOTPApi(phone_no, country_code);
                break;
        }
    }

    private void attemptOTP() {
        edOtp.setError(null);
        String otp = edOtp.getText().toString();
        if (TextUtils.isEmpty(otp)) {
            edOtp.setError(getString(R.string.error_field_required));
        } else {
            otpTwilioVerify(otp, phone_no, country_code);
            //otpVerify(otp, phone_no);
        }
    }

    private void resendOTPApi(String phone_no, String countryCode) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostTwilioRequest(NetworkHelper.REQ_CODE_TWILIO_OTP_SEND, this, TWILIO_API_KEY, Urls.SEND_TWILIO_OTP_URL,
                    new NetworkHelper(this).twilioOtpJson(phone_no, countryCode), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
        /*if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostRequest(NetworkHelper.REQ_CODE_RESEND_OTP, this, Urls.RESEND_OTP_URL, new NetworkHelper(this).resendOtpJson(phone_no), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }*/
    }

    private void otpTwilioVerify(String otp, String mobileNumber, String countryCode) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            String url = Urls.VERIFY_TWILIO_OTP_URL + "?country_code=" + countryCode + "&phone_number=" + mobileNumber + "&verification_code=" + otp;
            RequestHelper.GetTwilioRequest(NetworkHelper.REQ_CODE_TWILIO_OTP_VERIFY, this, TWILIO_API_KEY, url, this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    /**
     * API Call to verify the user.
     */
    private void verifyUser() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostRequest(NetworkHelper.REQ_CODE_VERIFY, this, Urls.VERIFY_URL,
                    new NetworkHelper(this).verifyUser(), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    private void otpVerify(String otp, String phone_no) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostRequest(NetworkHelper.REQ_CODE_VERIFY, this, Urls.VERIFY_URL, new NetworkHelper(this).otpJson(otp, phone_no), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_VERIFY:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                //String message = json.optString(Constant.MESSAGE);
                try {
                    String vendor_id = json.optString(Constant.VENDOR_ID);
                    String subscription = json.optString(Constant.SUBSCRIPATION);
                    JSONObject userMainObject = json.getJSONObject(Constant.USER);
                    JSONObject addressObject = json.getJSONObject(Constant.ADDRESS);
                    boolean is_verify = userMainObject.optBoolean(Constant.IS_VERIFY);
                    String token = userMainObject.optString(Constant.TOKEN);
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    UserModel userModel = gson.fromJson(String.valueOf(userMainObject), UserModel.class);
                    AddressModel addressModel = gson.fromJson(String.valueOf(addressObject), AddressModel.class);
                    SessionManager sessionManager = new SessionManager(OTPVerifyActivity.this);
                    new SessionManager(this).setToken(token);
                    new SessionManager(this).setVendorId(vendor_id.toString());
                    if (is_verify) {
                        sessionManager.saveUserDetail(userModel);
                        sessionManager.saveAddressDetail(addressModel);
                        if (json.has("subscribe")) {
                            if (json.getBoolean("subscribe")) {
                                if (subscription.equals("null")) {
                                    BaseUtility.toastMsg(this, "User Verify Successfully");
                                    Intent intent = new Intent(this, PickAPlanActivity.class);
                                    intent.putExtra(Constant.VENDOR_ID, vendor_id);
                                    startActivity(intent);
                                    // BaseUtility.sentToScreenIntentWithFlag(this, PickAPlanActivity.class);
                                } else {
                                    BaseUtility.toastMsg(this, "User Verify Successfully");
                                    Intent intent = new Intent(this, DashboardActivity.class);
                                    intent.putExtra(Constant.VENDOR_ID, vendor_id);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    //BaseUtility.sentToScreenIntentWithFlag(this, DashboardActivity.class);
                                }
                            } else {
                                BaseUtility.toastMsg(this, "User Verify Successfully");
                                Intent intent = new Intent(this, DashboardActivity.class);
                                intent.putExtra(Constant.VENDOR_ID, vendor_id);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                //BaseUtility.sentToScreenIntentWithFlag(this, DashboardActivity.class);
                            }
                            finish();
                        }
                    } else {
                        BaseUtility.toastMsg(this, "Vendor is not verified.");
                    }
                    break;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case NetworkHelper.REQ_CODE_RESEND_OTP:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                String message1 = json.optString(Constant.MESSAGE);
                String phone = json.optString(Constant.PHONE_NUMBER);
                if (message1.length() > 0) {
                    Toast.makeText(this, message1, Toast.LENGTH_SHORT).show();
                }
                break;

            case NetworkHelper.REQ_CODE_TWILIO_OTP_SEND:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                try {
                    if (Boolean.parseBoolean(json.optString(STATUS))) {
                        progressDialogUtil.dismissDialog();
                        Log.e(TAG, json.toString());
                        String msg = json.optString(Constant.MESSAGE);
                        if (msg.length() > 0) {
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    } else {
                        BaseUtility.toastMsg(this, json.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case NetworkHelper.REQ_CODE_TWILIO_OTP_VERIFY:
                Log.e(TAG, json.toString());
                try {
                    if (Boolean.parseBoolean(json.optString(Constant.STATUS))) {
                        if (from_screen.equalsIgnoreCase("edit_profile")) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra(Constant.FROM_PLACE, from_screen);
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        } else {
                            verifyUser();
                        }
                        /*if (from_screen.equalsIgnoreCase("edit_profile")) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra(Constant.FROM_PLACE, from_screen);
                            setResult(Activity.RESULT_OK, resultIntent);
                        } else {
                            if (new SessionManager(this).getSubscription().equals("null")) {
                                Intent intent = new Intent(this, PickAPlanActivity.class);
                                intent.putExtra(Constant.VENDOR_ID, new SessionManager(this).getVendorId());
                                startActivity(intent);
                                // BaseUtility.sentToScreenIntentWithFlag(this, PickAPlanActivity.class);
                            } else {
                                Intent intent = new Intent(this, DashboardActivity.class);
                                intent.putExtra(Constant.VENDOR_ID, new SessionManager(this).getVendorId());
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                //BaseUtility.sentToScreenIntentWithFlag(this, DashboardActivity.class);
                            }
                        }
                        finish();*/
                    } else {
                        BaseUtility.toastMsg(this, json.optString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
        if (anError.getErrorCode() != 0) {
            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
            String json = anError.getErrorBody();
            try {
                JSONObject object = new JSONObject(json);
                BaseUtility.toastMsg(OTPVerifyActivity.this, object.getString("message"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        BaseUtility.hideKeyboard(v, this);
        return false;
    }
}