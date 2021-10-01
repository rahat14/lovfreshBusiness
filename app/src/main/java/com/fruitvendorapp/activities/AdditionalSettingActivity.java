package com.fruitvendorapp.activities;

import androidx.appcompat.app.AppCompatActivity;
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
import com.fruitvendorapp.adapters.SlotSelectionAdapter;
import com.fruitvendorapp.interfaces.ItemMiniOrderInterface;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.model.DateAndTimeSlotModel;
import com.fruitvendorapp.model.DeliveryFeeModel;
import com.fruitvendorapp.model.DistanceFeeModel;
import com.fruitvendorapp.server_networking.JsonArrayResponseListener;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.DialogUtility;
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

public class AdditionalSettingActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener, ItemMiniOrderInterface, JsonArrayResponseListener {
    private static final String TAG = CustomerCollectionActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.ll_delivery_setting)
    LinearLayout llDeliverySettingView;
    @BindView(R.id.ll_pickup_setting)
    LinearLayout llPickupSettingView;
    @BindView(R.id.tv_deli_mini_order)
    TextView tvDeliMiniOrder;
    @BindView(R.id.tv_pic_mini_order)
    TextView tvPickMiniOrder;
    @BindView(R.id.tv_dist)
    TextView tvDeliveryDist;
    @BindView(R.id.tv_deli_slot)
    TextView tvDeliSlot;
    @BindView(R.id.tv_pic_slot)
    TextView tvPickupSlot;

    private boolean isDeliver = false;
    private String name = "", delivery_id = "", minimumOrderValue = "";
    private ProgressDialogUtil progressDialogUtil;
    ArrayList<DeliveryFeeModel> list;
    ArrayList<DistanceFeeModel> distanceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_setting);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        if (getIntent().hasExtra(Constant.NAME)) {
            name = getIntent().getStringExtra(Constant.NAME);
            delivery_id = getIntent().getStringExtra(Constant.DELIVERY_TYPES);
            minimumOrderValue = getIntent().getStringExtra(Constant.MINIMUM_ORDER_VALUE);
            if (name.equals("Home Delivery")) {
                tvToolbarTitle.setText("Delivery Settings");
                llDeliverySettingView.setVisibility(View.VISIBLE);
                llPickupSettingView.setVisibility(View.GONE);
            } else {
                tvToolbarTitle.setText("Pick up Settings");
                llDeliverySettingView.setVisibility(View.GONE);
                llPickupSettingView.setVisibility(View.VISIBLE);
            }
        }
        progressDialogUtil = new ProgressDialogUtil(this);
        tvDeliMiniOrder.setOnClickListener(this);
        tvPickMiniOrder.setOnClickListener(this);
        tvDeliveryDist.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvDeliSlot.setOnClickListener(this);
        tvPickupSlot.setOnClickListener(this);
        getDistAndFeeAPI();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_deli_mini_order:
                DialogUtility.dialogMinimumOrder(this, this, true, true, minimumOrderValue);
                break;
            case R.id.tv_pic_mini_order:
                DialogUtility.dialogMinimumOrder(this, this, false, true, minimumOrderValue);
                break;
            case R.id.tv_dist:
                DialogUtility.dialogDistanceDeliveryfee(this, this, false, distanceList);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_deli_slot:
                BaseUtility.sendActivityIntent(this, SlotSelectionActivity.class);
                break;
            case R.id.tv_pic_slot:
                BaseUtility.sendActivityIntent(this, PickupSlotSelectionActivity.class);
                break;
        }
    }


    @Override
    public void itemMiniOrder(String id, String order_val, boolean isDeliver, boolean isOrderValue) {
        if (isOrderValue) {
            if (isDeliver) {
                addMiniOrder(order_val);
            } else {
                addMiniOrder(order_val);
            }
        } else {
            list = new ArrayList<>();
            list.add(new DeliveryFeeModel(id, order_val));
            addDistAndFeeApi(list);
        }
        minimumOrderValue = order_val;
    }

    private void addMiniOrder(String order_val) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_ADD_SAVE_MINI_ORDER, this, Urls.SAVE_MINI_ORDER_VALUE, new NetworkHelper(this).saveMiniOrderJson(delivery_id, order_val), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    private void getDistAndFeeAPI() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_SAVE_DIST_AND_FEE, this,
                    Urls.DISTANCE_FEE_URL, this);
        } else {
            BaseUtility.toastMsg(this, getResources().getString(R.string.no_internet_connection));
        }
    }

    private void addDistAndFeeApi(ArrayList<DeliveryFeeModel> list) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_ADD_SAVE_DIST_AND_FEE, this, Urls.DISTANCE_FEE_URL,
                    new NetworkHelper(this).saveDistAndFeeJson(delivery_id, list), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_ADD_SAVE_MINI_ORDER:
            case NetworkHelper.REQ_CODE_ADD_SAVE_DIST_AND_FEE:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                BaseUtility.toastMsg(this, "Saved successfully");
                getDistAndFeeAPI();
                break;
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_SAVE_DIST_AND_FEE:
                try {
                    progressDialogUtil.dismissDialog();
                    Log.e(TAG, json.toString());
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Type distanceFee = new TypeToken<List<DistanceFeeModel>>() {
                    }.getType();
                    distanceList = gson.fromJson(json.toString(), distanceFee);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    BaseUtility.toastMsg(this, "Parsing Error.");
                }
                break;
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }
}