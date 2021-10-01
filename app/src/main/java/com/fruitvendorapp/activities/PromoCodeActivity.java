package com.fruitvendorapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.PromoCodeAdapter;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.model.PromoCodeModel;
import com.fruitvendorapp.server_networking.JsonArrayResponseListener;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.Urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class PromoCodeActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener, JsonArrayResponseListener, ItemSelectInterface {
    private static final String TAG = PromoCodeActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_promo_code)
    Button btnPromoCode;
    @BindView(R.id.rv_coupans)
    RecyclerView rvCoupans;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoRecordFound;

    @BindView(R.id.tv_no_data)
    TextView tvNoRecord;
    private ProgressDialogUtil progressDialogUtil;
    private PromoCodeAdapter promoCodeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_code);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing PromoCodeActivity");
        init();

    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.promo_code));
        ivBack.setOnClickListener(this);
        btnPromoCode.setOnClickListener(this);
        setAdapter();
        getCoupanApi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCoupanApi();
    }

    private void setAdapter() {
        promoCodeAdapter = new PromoCodeAdapter(this, this);
        rvCoupans.setLayoutManager(new LinearLayoutManager(this));
        rvCoupans.setAdapter(promoCodeAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_promo_code:
                BaseUtility.sendActivityIntent(this, AddPromoCodeActivity.class);
                break;
        }
    }

    //implement Address Api  for get All Address
    private void deleteProductApi(String coupan_id) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_DELETE_COUPAN, this, Urls.PROMO_CODE_REMOVE_URL + coupan_id + "/", this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }

    }

    private void getCoupanApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_COUPAN, this, Urls.PROMO_CODE_URL, this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_COUPAN:
                Log.e(TAG, json.toString());
                progressDialogUtil.dismissDialog();
                GsonBuilder gsonBuilder3 = new GsonBuilder();
                Gson gson3 = gsonBuilder3.create();
                Type coupanType = new TypeToken<List<PromoCodeModel>>() {
                }.getType();
                ArrayList<PromoCodeModel> promoCodeModelArrayList = gson3.fromJson(json.toString(), coupanType);
                if (promoCodeModelArrayList != null && !promoCodeModelArrayList.isEmpty()) {
                    promoCodeAdapter.setData(promoCodeModelArrayList);
                    promoCodeAdapter.notifyDataSetChanged();
                    llNoRecordFound.setVisibility(View.GONE);
                    rvCoupans.setVisibility(View.VISIBLE);
                } else {
                    tvNoRecord.setText("No Promo Code Found");
                    llNoRecordFound.setVisibility(View.VISIBLE);
                    rvCoupans.setVisibility(View.GONE);
                }
                break;
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_DELETE_COUPAN:
                Log.e(TAG, json.toString());
                progressDialogUtil.dismissDialog();
                getCoupanApi();
                BaseUtility.toastMsg(this, "Delete coupon successfully");
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }

    @Override
    public void itemSelect(String coupan_id, String title) {
        deleteProductApi(coupan_id);
    }
}