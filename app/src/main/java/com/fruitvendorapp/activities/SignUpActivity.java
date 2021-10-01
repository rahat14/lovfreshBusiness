package com.fruitvendorapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.interfaces.CurrentLocationInterface;
import com.fruitvendorapp.model.UserModel;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.DialogUtility;
import com.fruitvendorapp.utilities.LocationOnUtility;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.SessionManager;
import com.fruitvendorapp.utilities.Urls;
import com.fruitvendorapp.utilities.Validation;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hbb20.CountryCodePicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

import static com.fruitvendorapp.utilities.Constant.STATUS;
import static com.fruitvendorapp.utilities.Constant.TWILIO_API_KEY;
import static com.fruitvendorapp.utilities.Validation.isValidPasswordz;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener, View.OnTouchListener, CurrentLocationInterface {
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private TelephonyManager mTelephonyManager;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.ed_name)
    AppCompatEditText edName;
    @BindView(R.id.ed_email)
    AppCompatEditText edEmail;
    @BindView(R.id.ed_phone)
    AppCompatEditText edPhone;
    @BindView(R.id.ed_address)
    AppCompatEditText edAddress;
    @BindView(R.id.ed_loyal_no)
    AppCompatEditText edLoyalNo;
    @BindView(R.id.ed_password)
    AppCompatEditText edPasswrd;
    @BindView(R.id.ed_conf_pass)
    AppCompatEditText edConfPassword;
    @BindView(R.id.ed_website)
    AppCompatEditText edWebSite;
    @BindView(R.id.ed_abn)
    AppCompatEditText edAbn;
    @BindView(R.id.ed_acn)
    AppCompatEditText edACN;
    @BindView(R.id.ccp)
    CountryCodePicker ccp;
    @BindView(R.id.btn_signup)
    Button btnSignUp;
    @BindView(R.id.tv_already_account)
    TextView tvLogin;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.cb_term_condi)
    CheckBox cbTerms;
    @BindView(R.id.tv_term_cond)
    TextView tvTermCond;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.show_pass_btn)
    ImageView ivShowPass;
    @BindView(R.id.show_pass_conf)
    ImageView ivShowPassConf;
    private ProgressDialogUtil progressDialogUtil;
    String phone = "";
    private String countryCode = "", deviceid = "";
    private double latitude, longitude;
    private boolean isPlaceSelect;
    // defining our own password pattern
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing SignUpActivity");
        init();
    }

    private void init() {
        new LocationOnUtility(this, this, this);
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.sing_up));
        btnSignUp.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        llLayout.setOnTouchListener(this);
        tvTermCond.setOnClickListener(this);
        edAddress.setOnClickListener(this);
        ivShowPass.setOnClickListener(this);
        ivShowPassConf.setOnClickListener(this);
        edAbn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (!TextUtils.isEmpty(s)) {
                    edACN.setText("");
                }

            }
        });
        edACN.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (!TextUtils.isEmpty(s)) {
                    edAbn.setText("");
                }


            }
        });


        // Initialize Places.
        Places.initialize(getApplicationContext(), getString(R.string.places_key));

// Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                attemptSignUp();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_term_cond:
                DialogUtility.dialogTermAlert(SignUpActivity.this, cbTerms);
                break;
            case R.id.tv_already_account:
                BaseUtility.sentToScreenIntentWithFlag(this, LoginActivity.class);
                break;
            case R.id.show_pass_btn:
                if (edPasswrd.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    ivShowPass.setImageResource(R.drawable.ic_baseline_visibility_24);
                    edPasswrd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    ivShowPass.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    edPasswrd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.show_pass_conf:
                if (edConfPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                    ivShowPassConf.setImageResource(R.drawable.ic_baseline_visibility_24);
                    edConfPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    ivShowPassConf.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    edConfPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.ed_address:
                // Specify the fields to return.
                List<Place.Field> placeFields = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.ID,
                        Place.Field.LAT_LNG,
                        Place.Field.NAME);
// Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, placeFields)
                        .build(this);
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + place.getAddress() + place.getLatLng());
                isPlaceSelect = true;
                latitude = Objects.requireNonNull(place.getLatLng()).latitude;
                longitude = Objects.requireNonNull(place.getLatLng()).longitude;
                edAddress.setText(place.getAddress());
                edAddress.setError(null);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                assert data != null;
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //add Validation for Signup field
    private void attemptSignUp() {
        edName.setError(null);
        edEmail.setError(null);
        edPhone.setError(null);
        edAddress.setError(null);
        edLoyalNo.setError(null);
        edAbn.setError(null);
        edACN.setError(null);
        edWebSite.setError(null);
        edPasswrd.setError(null);
        edConfPassword.setError(null);
        boolean cancel = false;
        View focusView = null;
        String name = edName.getText().toString();
        String email = edEmail.getText().toString();
        phone = edPhone.getText().toString();
        String address = edAddress.getText().toString();
        String loyal_no = edLoyalNo.getText().toString();
        String abn = edAbn.getText().toString();
        String acn = edACN.getText().toString();
        String website = edWebSite.getText().toString();
        countryCode = ccp.getSelectedCountryCode();
        String password = edPasswrd.getText().toString();
        String cofpassword = edConfPassword.getText().toString();

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = ccp.getSelectedCountryCode();
                Log.e(TAG, countryCode);
            }
        });
        if (TextUtils.isEmpty(name)) {
            edName.setError(getString(R.string.error_field_required));
            focusView = edName;
            cancel = true;

        }

        if (TextUtils.isEmpty(address)) {
            edAddress.setError(getString(R.string.error_field_required));
            focusView = edAddress;
            cancel = true;

        }
        if (TextUtils.isEmpty(phone)) {
            edPhone.setError(getString(R.string.error_field_required));
            focusView = edPhone;
            cancel = true;

        } else if (!Validation.isValidPhone(phone)) {
            edPhone.setError(getString(R.string.invalid_mobile));
            focusView = edPhone;
            cancel = true;
        }


        if (TextUtils.isEmpty(email)) {
            edEmail.setError(getString(R.string.error_field_required));
            focusView = edEmail;
            cancel = true;
        } else if (!Validation.isValidEmail(email)) {
            edEmail.setError(getString(R.string.error_invalid_email));
            focusView = edEmail;
            cancel = true;
        }


        if (!isValidPasswordz(edPasswrd.getText().toString())) {
            edPasswrd.setError(getString(R.string.error_invalid_pass));
            focusView = edPasswrd;
            cancel = true;
            System.out.println("Not Valid");
        }


        //Check Confirm Password and Match both Password field validation
        if (TextUtils.isEmpty(cofpassword)) {
            edConfPassword.setError(getString(R.string.error_field_required));
            focusView = edConfPassword;
            cancel = true;
        } else if (!cofpassword.equals(password)) {
            edConfPassword.setError(getString(R.string.error_password_mismatch));
            focusView = edConfPassword;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if (TextUtils.isEmpty(abn) && TextUtils.isEmpty(acn)) {
                BaseUtility.toastMsg(this, getString(R.string.abn_acn_check));
            } else {
                if (cbTerms.isChecked()) {
                    //Call Signup api
                    if (ConnectionUtil.isInternetOn(this)) {
                        progressDialogUtil.showDialog();
                        RequestHelper.PostRequest(NetworkHelper.REQ_CODE_SIGN_UP, this, Urls.SIGNUP_URL, new NetworkHelper(this).signUpJson(name, email, "+" + countryCode + phone, abn, website, acn, password, cofpassword, address, String.valueOf(latitude), String.valueOf(longitude)), this);
                    } else {
                        BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
                    }
                } else {
                    BaseUtility.toastMsg(this, getString(R.string.check_terms_condition));
                }
            }
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        if (requestCode == NetworkHelper.REQ_CODE_SIGN_UP) {
            progressDialogUtil.dismissDialog();
            Log.e(TAG, json.toString());
            try {
                String vendor_id = json.optString(Constant.VENDOR_ID);
                JSONObject jsonObject = json.getJSONObject(Constant.USER);
                String token = jsonObject.optString(Constant.TOKEN);
                callOTPSendAPI(phone, "+" + countryCode);
                //boolean is_verify = jsonObject.optBoolean(Constant.IS_VERIFY);
                String subscription = json.optString(Constant.SUBSCRIPATION);
                //phone = jsonObject.optString(Constant.PHONE_NUMBER);
                String shopTitle = jsonObject.optString(Constant.TITLE);
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                UserModel userModel = gson.fromJson(String.valueOf(jsonObject), UserModel.class);

                new SessionManager(this).setShopTitle(shopTitle);
                new SessionManager(this).setToken(token);
                new SessionManager(this).setPhone(jsonObject.optString(Constant.PHONE_NUMBER));
                new SessionManager(this).setVendorId(vendor_id.toString());
                new SessionManager(this).setSubscription(subscription);

                /*SessionManager sessionManager = new SessionManager(SignUpActivity.this);
                sessionManager.saveUserDetail(userModel);*/

                BaseUtility.toastMsg(this, "SignUp SuccessFully");
                getDeviceId();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == NetworkHelper.REQ_CODE_TWILIO_OTP_SEND) {
            progressDialogUtil.dismissDialog();
            Log.e(TAG, json.toString());
            try {
                if (Boolean.parseBoolean(json.optString(STATUS))) {
                    BaseUtility.toastMsg(this, "OTP Send Successfully");
                    Intent intent = new Intent(this, OTPVerifyActivity.class);
                    intent.putExtra(Constant.COUNTRY_CODE, countryCode);
                    intent.putExtra(Constant.PHONE_NUMBER, phone);
                    intent.putExtra(Constant.FROM_PLACE, "sign_up");
                    intent.putExtra(Constant.VENDOR_ID, new SessionManager(this).getVendorId());
                    startActivity(intent);
                    finish();
                } else {
                    BaseUtility.toastMsg(this, json.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (requestCode == NetworkHelper.REQ_CODE_SEND_FCM) {
            Log.e(TAG, "fcm_api----------------->" + json.toString());
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
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
        String email = "", mobile = "";
        if (anError.getErrorCode() != 0) {
            Log.e(TAG, "onError errorCode : " + anError.getErrorCode());
            if (anError.getErrorCode() == 400) {
                String json = anError.getErrorBody();
                try {
                    JSONObject object = new JSONObject(json);
                    if (object.has(Constant.EMAIL)) {
                        JSONArray jsonArray = object.getJSONArray(Constant.EMAIL);
                        email = jsonArray.getString(0);
                        if (email.length() > 0) {
                            Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
                        }

                    }
                    if (object.has(Constant.PHONE_NUMBER)) {
                        JSONArray jsonArray1 = object.getJSONArray(Constant.PHONE_NUMBER);
                        mobile = jsonArray1.getString(0);
                        if (mobile.length() > 0) {
                            Toast.makeText(this, mobile, Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(mobile)) {
                        Toast.makeText(this, email + " " + mobile, Toast.LENGTH_SHORT).show();
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


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        BaseUtility.hideKeyboard(v, this);
        return false;
    }


    @Override
    public void getLocation(Location location) {
        if (!isPlaceSelect) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        Log.e(TAG, "CurrentLatLong------------>" + latitude + ".........." + longitude);
    }
}