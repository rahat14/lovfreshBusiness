package com.fruitvendorapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.model.DeliveryType;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.Urls;
import com.fruitvendorapp.utilities.Validation;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class AddPromoCodeActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {
    private static final String TAG = AddPromoCodeActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.ed_amount_off)
    AppCompatEditText edAmountOff;
    @BindView(R.id.ed_promo_code)
    AppCompatEditText edPromoCode;
    @BindView(R.id.ed_offer_nm)
    AppCompatEditText edOfferName;
    @BindView(R.id.ed_limit)
    AppCompatEditText edLimitUse;
    @BindView(R.id.ed_start_date)
    TextView edStartDate;
    @BindView(R.id.ed_end_date)
    TextView edEndDate;
    @BindView(R.id.cb_percentage)
    CheckBox cbPercentage;

    private ProgressDialogUtil progressDialogUtil;
    private int checkDateTime = 0;
    private String startDate, endDate;
    private boolean isPercentage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promo_code);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing AddPromoCodeActivity");
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.add_promo_code));
        ivBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        edStartDate.setOnClickListener(this);
        edEndDate.setOnClickListener(this);
        cbPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbPercentage.isChecked()) {
                    isPercentage = true;
                } else {
                    isPercentage = false;
                }
            }
        });
    }

    private void validation() {
        edAmountOff.setError(null);
        edOfferName.setError(null);
        edPromoCode.setError(null);
        edLimitUse.setError(null);
        edStartDate.setError(null);
        edEndDate.setError(null);
        String amount_off = edAmountOff.getText().toString();
        String offer_name = edOfferName.getText().toString();
        String promo_code = edPromoCode.getText().toString();
        String limit_use = edLimitUse.getText().toString();
        String start_date = edStartDate.getText().toString();
        String end_date = edEndDate.getText().toString();

        if (TextUtils.isEmpty(amount_off)) {
            edAmountOff.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(offer_name)) {
            edOfferName.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(promo_code)) {
            edPromoCode.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(limit_use)) {
            edLimitUse.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(startDate)) {
            edStartDate.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(endDate)) {
            edEndDate.setError(getString(R.string.error_field_required));
        } else {
            if(cbPercentage.isChecked()){
                if (ConnectionUtil.isInternetOn(this)) {
                    progressDialogUtil.showDialog();
                    RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_SAVE_ADD_PROMO_CODE, this, Urls.PROMO_CODE_URL, new NetworkHelper(this).addPromoJson(offer_name, promo_code, startDate, endDate, amount_off, limit_use, true), this);
                } else {
                    BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
                }
            }
            else {
                if (ConnectionUtil.isInternetOn(this)) {
                    progressDialogUtil.showDialog();
                    RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_SAVE_ADD_PROMO_CODE, this, Urls.PROMO_CODE_URL, new NetworkHelper(this).addPromoJson(offer_name, promo_code, startDate, endDate, amount_off, limit_use, false), this);
                } else {
                    BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                validation();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.ed_start_date:
                checkDateTime = 1;
                openDatePicker(checkDateTime);
                break;
            case R.id.ed_end_date:
                checkDateTime = 2;
                openDatePicker(checkDateTime);
                break;
        }
    }

    public void openDatePicker(int checkDateTime) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        //launch datepicker modal
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Log.e("", "DATE SELECTED " + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        if (checkDateTime == 1) {
                            String startDate_ = BaseUtility.convertFormatDate(year, monthOfYear, dayOfMonth);
                            startDate = BaseUtility.convertStartAndEndFormat(startDate_);
                            edStartDate.setText(startDate_);
                            Log.e("startdate", startDate);
                        } else {
                            String endDate_ = BaseUtility.convertFormatDate(year, monthOfYear, dayOfMonth);
                            endDate = BaseUtility.convertStartAndEndFormat(endDate_);
                            edEndDate.setText(endDate_);
                            Log.e(" endDate", endDate);
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_SAVE_ADD_PROMO_CODE:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                onBackPressed();
                BaseUtility.toastMsg(this, "Saved successfully");
                break;
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }
}