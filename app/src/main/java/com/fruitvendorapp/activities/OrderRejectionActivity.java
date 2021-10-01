package com.fruitvendorapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.model.OrderModel;
import com.fruitvendorapp.popups.OrderNotificationPopup;
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

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

import static com.fruitvendorapp.popups.OrderNotificationPopup.ordernotificationActivity;

public class OrderRejectionActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {
    private static final String TAG = OrderRejectionActivity.class.getSimpleName();
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.sp_reason)
    Spinner spReason;
    @BindView(R.id.ed_reason)
    AppCompatEditText edReason;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private ProgressDialogUtil progressDialogUtil;
    private String reason_type = "";
    private String other_reason = "";
    private String order_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_rejection);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing OrderRejectionActivity");
        init();

    }

    private void init() {
        if (getIntent().hasExtra(Constant.ORDER_ID)) {
            order_id = getIntent().getStringExtra(Constant.ORDER_ID);
        }
        tvToolbarTitle.setText("Order Rejection Reason");
        progressDialogUtil = new ProgressDialogUtil(this);
        btnSubmit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        getTypeSpinner();
    }

    private void attemptReson() {
        //  edReason.setError(null);
        other_reason = edReason.getText().toString();

        if (TextUtils.isEmpty(reason_type) && TextUtils.isEmpty(other_reason)) {
            BaseUtility.toastMsg(this, getString(R.string.reject_reason_required));
        } else {
            selectStatusApi();
        }
    }

    private void getTypeSpinner() {
        spReason.setAdapter(new ArrayAdapter<String>(OrderRejectionActivity.this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.array_reason_type)));
        spReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reason_type = parent.getSelectedItem().toString();
                Log.e("Type_special", reason_type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void selectStatusApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            String rejectReason = "";
            if (other_reason.isEmpty()){
                rejectReason = reason_type;
            }else {
                rejectReason = other_reason;
            }

            RequestHelper.patchRequest(NetworkHelper.REQ_CODE_GET_REJECT_STATUS, this, Urls.ORDER_URL + order_id + "/", new NetworkHelper(this).getOrderRejectJson(rejectReason), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    private void deleteOrderNotificationApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            // progressDialogUtil.showDialog();
            Log.e(TAG, Urls.NOTIFICATION_REMOVE_URL);
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_DELETE_NOTIFICATION, this, Urls.NOTIFICATION_REMOVE_URL,
                    new NetworkHelper(this).removeNotificationJson(order_id), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_REJECT_STATUS:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                deleteOrderNotificationApi();
                edReason.setText("");
                BaseUtility.sendActivityIntent(this, NotificationActivity.class);
                ordernotificationActivity.finish();
                finish();
                break;
            case NetworkHelper.REQ_CODE_DELETE_NOTIFICATION:
                // progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                //BaseUtility.sendActivityIntent(this, NotificationActivity.class);
                //    finish();
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
            case R.id.btn_submit:
                attemptReson();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }

    }
}