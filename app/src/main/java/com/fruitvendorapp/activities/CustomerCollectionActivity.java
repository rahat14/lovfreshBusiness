package com.fruitvendorapp.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.DeliverTypeAdapter;
import com.fruitvendorapp.model.DeliveryType;
import com.fruitvendorapp.server_networking.JsonArrayResponseListener;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.SessionManager;
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


public class CustomerCollectionActivity extends AppCompatActivity implements View.OnClickListener, JsonArrayResponseListener, ResponseListener {
    private static final String TAG = CustomerCollectionActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_deliver)
    RecyclerView rvDeliver;
    @BindView(R.id.btn_save)
    Button btnSave;

    private ProgressDialogUtil progressDialogUtil;
    private DeliverTypeAdapter deliverTypeAdapter;
    private ArrayList<DeliveryType> deliveryTypesList;
    private String deliver_id = "";
    private ArrayList<DeliveryType> settingModellist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_collection);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing CustomerCollectionActivity");
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.customer_colletion));
        ivBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        setDeliverTypeAdapter();
        getCustomerCollectionApi();
    }

    private void setDeliverTypeAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rvDeliver.setLayoutManager(manager);
        deliverTypeAdapter = new DeliverTypeAdapter(this);
        rvDeliver.setAdapter(deliverTypeAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCustomerCollectionApi();
    }

    public void getCustomerCollectionApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            Log.e(TAG, new SessionManager(this).getToken());
            progressDialogUtil.showDialog();
            Log.e(TAG, Urls.DELIVER_TYPE_URL);
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_DELIVER_TYPE, this, Urls.DELIVER_TYPE_URL, this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    public void postCollectionApi(ArrayList<DeliveryType> deliveryTypes) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_UPDATE_DELIVER_TYPE, this, Urls.DELIVER_TYPE_UPDATE_URL, new NetworkHelper(this).updateDeliverTypeJson(deliveryTypes), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        if (requestCode == NetworkHelper.REQ_CODE_GET_DELIVER_TYPE) {
            progressDialogUtil.dismissDialog();
            Log.e(TAG, json.toString());
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            if (json.length() > 0) {
                Type deliverType = new TypeToken<List<DeliveryType>>() {
                }.getType();
                deliveryTypesList = gson.fromJson(json.toString(), deliverType);
                if (deliveryTypesList != null && !deliveryTypesList.isEmpty()) {
                    deliverTypeAdapter.setData(deliveryTypesList);
                    deliverTypeAdapter.notifyDataSetChanged();
                    //llNoData.setVisibility(View.GONE);
                } else {
                    // llNoData.setVisibility(View.VISIBLE);
                }
            } else {
                System.out.println("Array is not initialized or empty");
            }
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        if (requestCode == NetworkHelper.REQ_CODE_UPDATE_DELIVER_TYPE) {
            progressDialogUtil.dismissDialog();
            Log.e(TAG, json.toString());
            getCustomerCollectionApi();
            BaseUtility.toastMsg(this, "Saved successfully");
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                getSelectedPrivacySetting();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    public boolean getSelectedPrivacySetting() {
        boolean onoffflag;
        String selecteDeliverId = "";
        boolean isSelected = false;
        if (deliveryTypesList != null && !deliveryTypesList.isEmpty()) {
            settingModellist = new ArrayList<>();
            for (DeliveryType model : deliveryTypesList) {
                if (model.isSelected()) {
                    selecteDeliverId = model.getId();
                    onoffflag = model.getIsActive();
                    Log.e(TAG, "string selectedSettingId= " + selecteDeliverId + "" + onoffflag);
                }
                isSelected = true;
                settingModellist.add(new DeliveryType(model.getId(), model.getIsActive()));
            }
            if (isSelected) {
                //   if(!TextUtils.isEmpty(selecteDeliverId))
                // {
                postCollectionApi(settingModellist);
                // }
               /* else {
                    BaseUtility.toastMsg(this,"Please select atleast one delivery type");
                }*/

            } else {
                isSelected = false;
            }
        }
        return isSelected;
    }


    private void getDeliverTypeId() {
        deliver_id = "";
        String selecteddeliverId = "";
        String SelectedName = "";
        if (deliveryTypesList != null && !deliveryTypesList.isEmpty()) {
            for (DeliveryType model : deliveryTypesList) {
                if (model.isSelected()) {
                    selecteddeliverId = String.format("%s,%s", selecteddeliverId, model.getId());
                    SelectedName = String.format("%s,%s", SelectedName, model.getName());
                    deliver_id = selecteddeliverId.replaceFirst(",", "");
                    Log.e(TAG, "deliver_id = " + deliver_id);
                }
            }
        }
    }
}