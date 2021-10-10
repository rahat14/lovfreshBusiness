package com.fruitvendorapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.ed_card_number)
    AppCompatEditText edCardNumber;
    @BindView(R.id.ed_expery_date)
    AppCompatEditText edExperiyDate;
    @BindView(R.id.ed_cvc)
    AppCompatEditText edCVC;
    @BindView(R.id.ll_view_paypal)
    LinearLayout llViewPaypal;
    @BindView(R.id.ll_credit_view)
    LinearLayout llCreditView;
    private String stripe_id = "";
    private ProgressDialogUtil progressDialogUtil;
    private String card_no = "", card_exp_month = "", card_year = "", exp_month = "", card_cvc = "";
    private static final int TOTAL_SYMBOLS = 19; // size of pattern 0000-0000-0000-0000
    private static final int TOTAL_DIGITS = 16; // max numbers of digits in pattern: 0000 x 4
    private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
    private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
    private static final char DIVIDER = '-';
    private static final int REQUEST_CODE = 101;
    private boolean isDelete;
    private String vendor_id = "", deviceCollectionData = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing PaymentActivity");
        init();
    }

    private void init() {
        Log.e(TAG, new SessionManager(this).getToken());
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText("Payment");
        if (getIntent().hasExtra(Constant.STRIPE_ID)) {
            stripe_id = getIntent().getStringExtra(Constant.STRIPE_ID);
            //  vendor_id =getIntent().getStringExtra(Constant.VENDOR_ID);
            Log.e(TAG, "stripe_id----------->" + stripe_id);
        }
        ivBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        llCreditView.setOnClickListener(this);
        llViewPaypal.setOnClickListener(this);
        cardCardEditFormat();
    }

    private void cardCardEditFormat() {
        edCardNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }


            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });

        edExperiyDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i1 == 0)
                    isDelete = false;
                else
                    isDelete = true;

            }

            @Override
            public void afterTextChanged(Editable s) {
                String source = s.toString();
                int length = source.length();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(source);
                Log.e("esp_date", source);
                try {
                    String[] items1 = source.split("/");
                    String d1 = items1[0];
                    String d2 = items1[1];
                    Log.e("month", d1 + " " + d2);
                    exp_month = d1;
                    card_year = d2;
                } catch (Exception e) {
                    Log.e("errorMags", e.getMessage());
                }


                if (length > 0 && length == 3) {
                    if (isDelete)
                        stringBuilder.deleteCharAt(length - 1);
                    else
                        stringBuilder.insert(length - 1, "/");
                    edExperiyDate.setText(stringBuilder.toString());
                    edExperiyDate.setSelection(edExperiyDate.getText().length());
                    Log.e("test" + s.toString(), "afterTextChanged: append " + length);
                } else {

                }

            }
        });

    }


    private void validationPlaceOrder() {
        edCardNumber.setError(null);
        edExperiyDate.setError(null);
        edCVC.setError(null);
        card_no = edCardNumber.getText().toString();
        card_exp_month = edExperiyDate.getText().toString();
        card_cvc = edCVC.getText().toString();
        if (TextUtils.isEmpty(card_no)) {
            edCardNumber.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(card_exp_month) || !isValidDate(card_exp_month)) {
            edExperiyDate.setError(getString(R.string.error_field_required));
        } else if (TextUtils.isEmpty(card_cvc) || card_cvc.length() < 3) {
            Toast.makeText(this, "Enter valid security number", Toast.LENGTH_SHORT).show();
        } else {
            subscriptionPlanApi();
        }

    }

    private void subscriptionPlanApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_SUBCRIPTION_PLAN, this, Urls.SUBSCRIPATION_URL,
                    new NetworkHelper(this).subscripationPlanJson(stripe_id, card_no, exp_month, card_year, card_cvc), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        if (requestCode == NetworkHelper.REQ_CODE_SUBCRIPTION_PLAN) {
            progressDialogUtil.dismissDialog();
            Log.e(TAG, json.toString());
            String message = json.optString(Constant.MESSAGE);
            BaseUtility.toastMsg(this, message);
            Intent intent = new Intent(this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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
                    String message = object.optString(Constant.MESSAGE);
                    BaseUtility.toastMsg(this, message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (anError.getErrorCode() == 401) {
                String json = anError.getErrorBody();
                try {
                    JSONObject object = new JSONObject(json);
                    String message = object.optString(Constant.MESSAGE);
                    // String message = object.optString(Constant.);
                    BaseUtility.toastMsg(this, message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isValidDate(String cardValidity) {
        if (!TextUtils.isEmpty(cardValidity) && cardValidity.length() == 5) {
            String month = cardValidity.substring(0, 2);
            String year = cardValidity.substring(3, 5);
            int monthpart = -1, yearpart = -1;
            try {
                monthpart = Integer.parseInt(month) - 1;
                yearpart = Integer.parseInt(year);
                Calendar current = Calendar.getInstance();
                current.set(Calendar.DATE, 1);
                current.set(Calendar.HOUR, 12);
                current.set(Calendar.MINUTE, 0);
                current.set(Calendar.SECOND, 0);
                current.set(Calendar.MILLISECOND, 0);
                Calendar validity = Calendar.getInstance();
                validity.set(Calendar.DATE, 1);
                validity.set(Calendar.HOUR, 12);
                validity.set(Calendar.MINUTE, 0);
                validity.set(Calendar.SECOND, 0);
                validity.set(Calendar.MILLISECOND, 0);
                if (monthpart > -1 && monthpart < 12 && yearpart > -1) {
                    validity.set(Calendar.MONTH, monthpart);
                    validity.set(Calendar.YEAR, yearpart + 2000);
                } else
                    return false;
                Log.d("Util", "isValidDate: " + current.compareTo(validity));
                if (current.compareTo(validity) <= 0)
                    return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_submit:
                validationPlaceOrder();
                break;
            case R.id.ll_view_paypal:
                llCreditView.setSelected(false);
                llViewPaypal.setSelected(true);
                break;
            case R.id.ll_credit_view:
                llCreditView.setSelected(true);
                llViewPaypal.setSelected(false);
                break;
        }
    }

    public void onBraintreeSubmit(String token) {
//        DropInRequest dropInRequest = new DropInRequest().clientToken(token);
//        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
//                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                // use the result to update your UI and send the payment method nonce to your server
//                Log.e(TAG, "DropInResult" + result.getPaymentMethodNonce().getNonce() + "");
//                //api//////////
//                postToken(result.getPaymentMethodNonce().getNonce());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Toast.makeText(PaymentActivity.this, "Transaction cancelled", Toast.LENGTH_LONG).show();
            } else {
                // handle errors here, an exception may be available in
            //    Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            //    Log.e(TAG, "error" + error.getMessage());
            }
        }
    }


    private void getToken() {
        progressDialogUtil.showDialog();
        AndroidNetworking.get(Urls.CLIENT_TOKEN)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialogUtil.dismissDialog();
                        String token = response.optString("client_token");
                        Log.e("Client Token=", response + "");
                        try {
                            onBraintreeSubmit(token);
                        } catch (Exception e) {

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialogUtil.dismissDialog();
                    }
                });
    }

    private void postToken(String nonce) {
        progressDialogUtil.showDialog();
        AndroidNetworking.post(Urls.NONCE_FROM_THE_CLIENT_URL)
                .addBodyParameter(Constant.NONCE, nonce)
                .addBodyParameter(Constant.AMOUNT, "")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialogUtil.dismissDialog();
                        Log.e("Response nonus : ", response + "");
                        // String status = response.optString(Constant.RESPONSE_CODE);
                        //  String message = response.optString(Constant.MSG);
                       /* if (status.equals("true")) {
                            addMoneyInWallet(response.optString(Constant.TRANSACTIONID));
                        } else {
                            BaseUtility.toastMsg(PaymentActivity.this, message);

                        }*/
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialogUtil.dismissDialog();
                        System.out.println("anError : " + anError.getMessage());
                    }
                });
    }
}