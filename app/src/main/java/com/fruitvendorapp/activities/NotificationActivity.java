package com.fruitvendorapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.NotificationAdapter;
import com.fruitvendorapp.model.OrderModel;
import com.fruitvendorapp.model.StoreModel;
import com.fruitvendorapp.server_networking.JsonArrayResponseListener;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.Urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener, JsonArrayResponseListener {
    private static final String TAG = NotificationActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.rv_store)
    RecyclerView rvStore;
    @BindView(R.id.auto_vendor_search)
    EditText edVendorSearch;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoFound;
    @BindView(R.id.tv_no_data)
    TextView tvNoRecord;
    private ProgressDialogUtil progressDialogUtil;
    NotificationAdapter notificationAdapter;

    private Boolean isFirstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing NotificationActivity");
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.notification));
        ivBack.setOnClickListener(this);
        setNotificationAdapter();
        getNotificationApi();
    }

    private void setNotificationAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rvStore.setLayoutManager(manager);
        notificationAdapter= new NotificationAdapter(this);
        rvStore.setAdapter(notificationAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotificationApi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    private void getNotificationApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            if (isFirstTime) {
                progressDialogUtil.showDialog();
            }
            Log.e(TAG, Urls.NOTIFICATION_URL + "?status=");
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_NOTIFICATION, this, Urls.NOTIFICATION_URL, this);
            isFirstTime = false;
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_NOTIFICATION:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Type orderType = new TypeToken<List<OrderModel>>() {
                }.getType();
                ArrayList<OrderModel> orderModelArrayList = gson.fromJson(json.toString(), orderType);
                if (orderModelArrayList != null && !orderModelArrayList.isEmpty()) {
                    notificationAdapter.setData(orderModelArrayList);
                    notificationAdapter.notifyDataSetChanged();
                    llNoFound.setVisibility(View.GONE);
                    rvStore.setVisibility(View.VISIBLE);
                } else {
                    llNoFound.setVisibility(View.VISIBLE);
                    tvNoRecord.setText("No Notification Found");
                    rvStore.setVisibility(View.GONE);
                }
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }
}