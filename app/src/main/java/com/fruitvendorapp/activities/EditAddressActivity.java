package com.fruitvendorapp.activities;

import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.interfaces.CurrentLocationInterface;
import com.fruitvendorapp.model.AddressModel;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.LocationOnUtility;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.Urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class EditAddressActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener, CurrentLocationInterface {
    private static final String TAG = EditAddressActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ed_full_name)
    AppCompatEditText edFullName;
    @BindView(R.id.ed_street_add)
    AppCompatEditText edStreetAdd;
    @BindView(R.id.ed_city)
    AppCompatEditText edCity;
    @BindView(R.id.ed_state)
    AppCompatEditText edState;
    @BindView(R.id.ed_zip_code)
    AppCompatEditText edZipCode;
    @BindView(R.id.ed_phone)
    AppCompatEditText edPhoneNo;
    @BindView(R.id.ed_flat_no)
    AppCompatEditText edFlatNo;
    @BindView(R.id.cb_address)
    CheckBox cbAddress;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private AddressModel addressModel;
    private ProgressDialogUtil progressDialogUtil;
    private String full_nm = "", str_add = "", address_type = "", phone_no = "", state = "", city = "", postal_code = "", flat_no = "";
    private double latitude, longitude;
    private String address_value = "", address_Id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing EditAddressActivity");
        init();
    }

    private void init() {
        if (getIntent().hasExtra(Constant.ADDRESS_ID)) {
            address_Id = getIntent().getStringExtra(Constant.ADDRESS_ID);
        }
        tvToolbarTitle.setText(getString(R.string.update_address));
        new LocationOnUtility(this, this, this);
        progressDialogUtil = new ProgressDialogUtil(this);
        ivBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        getAddressApi();
    }

    // this method check login validation
    private void attemptAddressValidation() {
        edFullName.setError(null);
        edStreetAdd.setError(null);
        edCity.setError(null);
        edState.setError(null);
        edZipCode.setError(null);
        edPhoneNo.setError(null);
        edFlatNo.setError(null);
        full_nm = edFullName.getText().toString();
        str_add = edStreetAdd.getText().toString();
        state = edState.getText().toString();
        city = edCity.getText().toString();
        postal_code = edZipCode.getText().toString();
        phone_no = edPhoneNo.getText().toString();
        flat_no = edFlatNo.getText().toString();

        Log.e(TAG, "address_type>>>>>>>>>>>>>>>>>>>>>" + address_type);

        if (TextUtils.isEmpty(full_nm)) {
            edFullName.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(str_add)) {
            edStreetAdd.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(state)) {
            edState.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(city)) {
            edCity.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(postal_code)) {
            edZipCode.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(phone_no)) {
            edPhoneNo.setError(getString(R.string.error_field_required));
        } else {
            String address = str_add + " " + city + " " + state + " " + postal_code;
            if (!TextUtils.isEmpty(address_Id)) {
                updateAddressApi(address);
            }
        }
    }

    public void getAddressApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_ADDRESS_BY_ID, this, Urls.GET_ADDRESS_URL + address_Id + "/", this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }

    }

    //this method call createAddress api
    private void updateAddressApi(String address) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.putRequestWithToken(this, NetworkHelper.REQ_CODE_UPDATE_ADDRESS, Urls.GET_ADDRESS_URL + address_Id + "/", new NetworkHelper(this).updateAddressJson(full_nm, address, phone_no, flat_no, "", str_add, address_value, String.valueOf(latitude), String.valueOf(longitude)), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_submit:
                attemptAddressValidation();
                break;
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_ADDRESS_BY_ID:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                addressModel = gson.fromJson(String.valueOf(json), AddressModel.class);
                if (addressModel != null) {
                    edFullName.setText(addressModel.getFullName());
                    edStreetAdd.setText(addressModel.getStreat());
                    edFlatNo.setText(addressModel.getFlatNumber());
                    edPhoneNo.setText(addressModel.getMobile());

                } else {

                }
                break;
            case NetworkHelper.REQ_CODE_UPDATE_ADDRESS:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                BaseUtility.toastMsg(this, "Update Address Successfully");
                onBackPressed();
                break;
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }

    @Override
    public void getLocation(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }
}