package com.fruitvendorapp.popups;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.activities.DashboardActivity;
import com.fruitvendorapp.activities.OrderRejectionActivity;
import com.fruitvendorapp.adapters.OrderProductAdapter;
import com.fruitvendorapp.model.OrderModel;
import com.fruitvendorapp.model.OrderProductModel;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderNotificationPopup extends AppCompatActivity implements ResponseListener, View.OnClickListener {
    private static final String TAG = OrderNotificationPopup.class.getSimpleName();
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_order_total)
    TextView tvOrderTotal;

    @BindView(R.id.tv_cust_name)
    TextView tvCustName;
    @BindView(R.id.tv_phone_no)
    TextView tvOrderPhone;
    @BindView(R.id.tv_email_id)
    TextView tvOrderEmail;

    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_total_amount)
    TextView tvTotal;

    @BindView(R.id.rv_order)
    RecyclerView rvOrderProduct;
    @BindView(R.id.btn_accept)
    TextView tvAccept;
    @BindView(R.id.btn_reject)
    TextView tvReject;
    @BindView(R.id.tv_order_type)
    TextView tvOrderType;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_pickup_slot)
    TextView tvOrderSlot;
    @BindView(R.id.tv_show_accept)
    TextView tvShowAccepted;
    @BindView(R.id.ll_btn_view)
    LinearLayout llBtnView;

    @BindView(R.id.btn_cust_call)
    Button btnCustCall;

    @BindView(R.id.tv_ship_date)
    TextView tvDeliveryDate;
    @BindView(R.id.tv_pickup_time)
    TextView tvDeliveryTime;

    @BindView(R.id.tv_distance_fee_amount)
    TextView tvDistanceFeeAmount;

    @BindView(R.id.iv_close)
    ImageView ivClose;

    private String orderId = "";
    private ProgressDialogUtil progressDialogUtil;
    private OrderModel orderModel;
    private ArrayList<OrderProductModel> productModels;
    private OrderProductAdapter orderListAdapter;
    private String status = "";
    private String mobileno = "";
    String app_state = "", vendor_id = "", flag = "";
    public static OrderNotificationPopup ordernotificationActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_notification_popup);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.setFinishOnTouchOutside(false);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        if (getIntent().hasExtra(Constant.ORDER_ID)) {
            orderId = getIntent().getStringExtra(Constant.ORDER_ID);
            app_state = getIntent().getStringExtra(Constant.APP_STATE);
            if (getIntent().getStringExtra(Constant.FLAG) != null)
                flag = getIntent().getStringExtra(Constant.FLAG);
        }
        if (flag != null && flag.equals("1")) {
            getHandlerMethod();
        }
        ordernotificationActivity = this;
        progressDialogUtil = new ProgressDialogUtil(this);
        tvAccept.setOnClickListener(this);
        tvReject.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        btnCustCall.setOnClickListener(this);
        setOrderProduct();
        getOrderDetailApi();

    }

    private void getHandlerMethod() {
        // Hide after some seconds
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //isDismiss =true;
                // if (isDismiss) {
                deleteOrderNotificationApi();
                // }
            }
        };

        handler.postDelayed(runnable, 4000);
    }

    private void setOrderProduct() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        orderListAdapter = new OrderProductAdapter(this);
        rvOrderProduct.setAdapter(orderListAdapter);
        rvOrderProduct.setLayoutManager(manager);
    }

    private void getOrderDetailApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            Log.e(TAG, Urls.ORDER_URL + orderId + "/");
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_ORDER_DETAIL, this, Urls.ORDER_URL + orderId + "/", this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    private void deleteOrderNotificationApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            // progressDialogUtil.showDialog();
            Log.e(TAG, Urls.NOTIFICATION_REMOVE_URL);
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_DELETE_NOTIFICATION, this, Urls.NOTIFICATION_REMOVE_URL, new NetworkHelper(this).removeNotificationJson(orderId), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_accept:
                status = "accepted";
                selectStatusApi();
                break;
            case R.id.btn_reject:
                Intent intent = new Intent(this, OrderRejectionActivity.class);
                intent.putExtra(Constant.ORDER_ID, orderId);
                startActivity(intent);
                //  finish();
                //  BaseUtility.sendActivityIntent(this, OrderRejectionActivity.class);
                break;
            case R.id.iv_close:
                finish();
                break;

            case R.id.btn_cust_call:
                onCallBtnClick();
                break;
        }
    }

    private void selectStatusApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.patchRequest(NetworkHelper.REQ_CODE_GET_ACCEPT_REJECT_STATUS, OrderNotificationPopup.this, Urls.ORDER_URL + orderId + "/", new NetworkHelper(this).selectAcceptJson(status), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_ORDER_DETAIL:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                orderModel = gson.fromJson(String.valueOf(json), OrderModel.class);
                if (orderModel != null) {
                    try {
                        JSONObject jsonObject = json.getJSONObject(Constant.ADDRESS);
                        String address = jsonObject.optString(Constant.ADDRESS);
                        tvAddress.setText(address);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    tvOrderId.setText(orderModel.getOrderNumber());
                    mobileno = orderModel.getUser().getPhoneNumber();
                    new SessionManager(this).setCustomerNu(mobileno);
                    tvOrderPhone.setText("Phone : " + orderModel.getUser().getPhoneNumber());
                    tvOrderEmail.setText("Email Id: " + orderModel.getUser().getEmail());

                    /*String order_date = orderModel.getCreatedAt();
                    String splitDate = order_date.substring(0, 10);
                    tvOrderDate.setText(splitDate);*/
                    tvOrderDate.setText(BaseUtility.toDefaultFormattedDateStr(orderModel.getCreatedAt()));

                    vendor_id = orderModel.getVendor().getId();
                    Log.e(TAG, "vendor_id---------->" + vendor_id);
                    tvOrderTotal.setText("Order Total:" + "$" + orderModel.getTotal());
                    tvTotal.setText("$" + orderModel.getTotal());
                    tvOrderType.setText(orderModel.getOrderType());
                    tvOrderSlot.setText(BaseUtility.convertDateFormat(orderModel.getDeliverDate()) + "-" + BaseUtility.parseDateToddMMyyyy(orderModel.getStartTime())+ "-" + BaseUtility.parseDateToddMMyyyy(orderModel.getEndTime()));
                    tvOrderStatus.setText(orderModel.getGetStatus());

                    tvDeliveryDate.setText("Shipping Date : " + BaseUtility.toDefaultFormattedDateStr(orderModel.getDeliverDate()));
                    tvDeliveryTime.setText("Pickup Time : " + orderModel.getStartTime());
                    if (orderModel.getOrderType().contains("delivery")) {
                        tvDeliveryTime.setText(getString(R.string.delivery_time) + " : " + orderModel.getStartTime());
                    }
                    if (orderModel.getGetStatus().equals("Order Accepted")) {
                        llBtnView.setVisibility(View.GONE);
                        tvShowAccepted.setVisibility(View.VISIBLE);
                    } else {
                        llBtnView.setVisibility(View.VISIBLE);
                        tvShowAccepted.setVisibility(View.GONE);
                    }
                    tvCustName.setText("Customer Name: "+ orderModel.getUser().getFirstName() + " " + BaseUtility.toNullableStr(orderModel.getUser().getLastName()));
//                    tvCustName.setText("Customer Name: " + orderModel.getUser().getFirstName());
                    productModels = orderModel.getOrderProducts();
                    if (productModels != null && !productModels.isEmpty()) {
                        orderListAdapter.setData(productModels);
                        orderListAdapter.notifyDataSetChanged();

                    } else {

                    }

                    try {
                        String delivery_charges = String.format(Locale.ENGLISH, "%.2f", Float.parseFloat(orderModel.getDeliveryCost()));
                        tvDistanceFeeAmount.setText("$" + delivery_charges);
                    }catch (Exception e){
                        tvDistanceFeeAmount.setText("$0");
                    }

                }
                break;
            case NetworkHelper.REQ_CODE_GET_ACCEPT_REJECT_STATUS:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                // if (flag.equals("forground")) {
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.putExtra(Constant.VENDOR_ID, vendor_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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

    private void onCallBtnClick() {
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall();
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                phoneCall();
            } else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permissionGranted = false;
        switch (requestCode) {
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            phoneCall();
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    private void phoneCall() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + mobileno));
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }

    }


}