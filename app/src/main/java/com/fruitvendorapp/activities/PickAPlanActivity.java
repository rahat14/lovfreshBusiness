package com.fruitvendorapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.PlanAdapter;
import com.fruitvendorapp.model.UpgradePlanModel;
import com.fruitvendorapp.server_networking.JsonArrayResponseListener;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
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

public class PickAPlanActivity extends AppCompatActivity implements View.OnClickListener, JsonArrayResponseListener {
    private static final String TAG = PickAPlanActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_plan)
    RecyclerView rvPlan;
    private ProgressDialogUtil progressDialogUtil;
    private PlanAdapter planAdapter;
    String vendor_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_a_plan);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing PickAPlanActivity");
        init();
    }

    private void init() {
        if (getIntent().hasExtra(Constant.VENDOR_ID)) {
            vendor_id = getIntent().getStringExtra(Constant.VENDOR_ID);
            Log.e(TAG,"vendorId"+vendor_id);
            // new SessionManager(this).setVendorId(vendor_id);
        }
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.pick_a_plan));
        ivBack.setOnClickListener(this);
        setPlanAdapter();
        getPlanApi();
    }

    private void setPlanAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        rvPlan.setLayoutManager(manager);
        planAdapter = new PlanAdapter(this, vendor_id);
        rvPlan.setAdapter(planAdapter);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            onBackPressed();
        }
    }

    private void getPlanApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            Log.e(TAG, Urls.PLAN_URL);
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_PLAN, this, Urls.PLAN_URL, this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        if (requestCode == NetworkHelper.REQ_CODE_GET_PLAN) {
            Log.e(TAG, json.toString());
            progressDialogUtil.dismissDialog();
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Type planType = new TypeToken<List<UpgradePlanModel>>() {
            }.getType();
            ArrayList<UpgradePlanModel> upgradePlanModelArrayList = gson.fromJson(json.toString(), planType);
            if (upgradePlanModelArrayList != null && !upgradePlanModelArrayList.isEmpty()) {
                planAdapter.setData(upgradePlanModelArrayList);
                planAdapter.notifyDataSetChanged();
            } else {

            }
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }


}