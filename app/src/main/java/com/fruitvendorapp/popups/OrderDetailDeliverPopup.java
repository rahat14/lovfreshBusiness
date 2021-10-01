package com.fruitvendorapp.popups;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agik.AGIKSwipeButton.Controller.OnSwipeCompleteListener;
import com.agik.AGIKSwipeButton.View.Swipe_Button_View;
import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.OrderProductAdapter;
import com.fruitvendorapp.model.OrderModel;
import com.fruitvendorapp.model.OrderProductModel;
import com.fruitvendorapp.model.TransactionModel;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.FileDownloader;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.Urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailDeliverPopup extends AppCompatActivity implements ResponseListener, View.OnClickListener {
    private static final String TAG = OrderDetailPickupPopup.class.getSimpleName();
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_cust_name)
    TextView tvCustName;
    @BindView(R.id.tv_order_phone)
    TextView tvOrderPhone;
    @BindView(R.id.tv_order_email)
    TextView tvOrderEmail;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_order_date)
    TextView tvOrderDate;
    @BindView(R.id.tv_order_total)
    TextView tvOrderTotal;
    @BindView(R.id.tv_total_amount)
    TextView tvTotal;
    @BindView(R.id.rv_order)
    RecyclerView rvOrderProduct;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_download_invoice)
    TextView tvDowloadInvoice;
    @BindView(R.id.btn_change_status)
    Button btnChangeStatus;
    @BindView(R.id.tv_shop_nm)
    TextView tvShopNm;
    @BindView(R.id.tv_shop_address)
    TextView tvShopAddress;
    @BindView(R.id.tv_shop_phone)
    TextView tvShopphone;
    @BindView(R.id.tv_payemnt_type)
    TextView tvPaymentType;
    @BindView(R.id.sbtn_ready)
    Swipe_Button_View swipeReady;
    @BindView(R.id.sbtn_confirm)
    Swipe_Button_View swipeConfirm;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    @BindView(R.id.tv_reject_reason)
    TextView tvRejectReason;
    @BindView(R.id.tv_reject_reason_header)
    TextView tvRejectReasonHeader;



    private String orderId = "", flag = "";
    private ProgressDialogUtil progressDialogUtil;
    private OrderModel orderModel;
    private ArrayList<OrderProductModel> productModels;
    private OrderProductAdapter orderListAdapter;
    private String mobileno = "", status = "";
    private static final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String invoicePdf = "", order_no = "";
    private Intent pdfIntent;

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_deliver_popup);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.setFinishOnTouchOutside(false);
        ActivityCompat.requestPermissions(OrderDetailDeliverPopup.this, PERMISSIONS, 112);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        if (getIntent().hasExtra(Constant.ORDER_ID)) {
            orderId = getIntent().getStringExtra(Constant.ORDER_ID);
            if (getIntent().getStringExtra(Constant.FLAG) != null)
                flag = getIntent().getStringExtra(Constant.FLAG);
        }
        if (flag != null && flag.equals("1")) {
            getHandlerMethod();
        }
        progressDialogUtil = new ProgressDialogUtil(this);
        ivClose.setOnClickListener(this);
        tvContact.setOnClickListener(this);
        tvDowloadInvoice.setOnClickListener(this);
        btnChangeStatus.setOnClickListener(this);
        setOrderProduct();
        getOrderDetailApi();

       /* swipeButtonView.setOnSwipeCompleteListener_forward_reverse(new OnSwipeCompleteListener() {
            @Override
            public void onSwipe_Reverse(Swipe_Button_View swipeView) {
                //   swipeButtonView.setText("Ready to Deliver");
                swipeButtonView.setThumbBackgroundColor(getResources().getColor(R.color.colorAccent));
                swipeButtonView.setSwipeBackgroundColor(getResources().getColor(R.color.colorAccent));
                confirmToDeliverStatusApi();
            }

            @Override
            public void onSwipe_Forward(Swipe_Button_View swipeView) {
                // swipeButtonView.setText("Confirm to pickup");
                swipeButtonView.setThumbBackgroundColor(getResources().getColor(R.color.colorAccent));
                swipeButtonView.setSwipeBackgroundColor(getResources().getColor(R.color.colorAccent));
                readyToDeliverStatusApi();
            }

        });*/


        swipeReady.setOnSwipeCompleteListener_forward_reverse(new OnSwipeCompleteListener() {
            @Override
            public void onSwipe_Reverse(Swipe_Button_View swipeView) {
           /*   swipeReady.setThumbBackgroundColor(getResources().getColor(R.color.colorAccent));
                swipeReady.setSwipeBackgroundColor(getResources().getColor(R.color.colorAccent));
                confirmToPickupStatusApi();*/
            }

            @Override
            public void onSwipe_Forward(Swipe_Button_View swipeView) {
                swipeReady.setThumbBackgroundColor(getResources().getColor(R.color.colorAccent));
                swipeReady.setSwipeBackgroundColor(getResources().getColor(R.color.colorAccent));
                readyToDeliverStatusApi();
            }

        });

        swipeConfirm.setOnSwipeCompleteListener_forward_reverse(new OnSwipeCompleteListener() {
            @Override
            public void onSwipe_Reverse(Swipe_Button_View swipeView) {
              /*  swipeReady.setThumbBackgroundColor(getResources().getColor(R.color.colorAccent));
                swipeReady.setSwipeBackgroundColor(getResources().getColor(R.color.colorAccent));
                confirmToPickupStatusApi();*/
            }

            @Override
            public void onSwipe_Forward(Swipe_Button_View swipeView) {
                swipeReady.setThumbBackgroundColor(getResources().getColor(R.color.colorAccent));
                swipeReady.setSwipeBackgroundColor(getResources().getColor(R.color.colorAccent));
                confirmToDeliverStatusApi();
            }

        });
    }

    private void setOrderProduct() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        orderListAdapter = new OrderProductAdapter(this);
        rvOrderProduct.setAdapter(orderListAdapter);
        rvOrderProduct.setLayoutManager(manager);
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

    private void getOrderDetailApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_ORDER_DETAIL, this, Urls.ORDER_URL + orderId + "/", this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    private void readyToDeliverStatusApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.patchRequest(NetworkHelper.REQ_CODE_READY_TO_DELIVER_STATUS, OrderDetailDeliverPopup.this, Urls.ORDER_URL + orderId + "/", new NetworkHelper(this).selectAcceptJson("packed"), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    private void confirmToDeliverStatusApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.patchRequest(NetworkHelper.REQ_CODE_CONFIRM_TO_DELIVER_STATUS, OrderDetailDeliverPopup.this, Urls.ORDER_URL + orderId + "/", new NetworkHelper(this).selectAcceptJson("completed"), this);
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
                    tvCustName.setText(orderModel.getUser().getFirstName());
                    tvOrderPhone.setText(orderModel.getUser().getPhoneNumber());
                    tvOrderEmail.setText(orderModel.getUser().getEmail());
                    String order_date = orderModel.getCreatedAt();
                    String splitDate = order_date.substring(0, 10);
                    tvOrderDate.setText(splitDate);
                    if (orderModel.getVendor().getAddress() != null) {
                        tvShopAddress.setText(orderModel.getVendor().getAddress().getAddress());
                    }
                    tvShopNm.setText(orderModel.getVendor().getTitle());
                    tvShopphone.setText("+" + orderModel.getVendor().getUser().getCountry_code() + orderModel.getVendor().getUser().getNational_number());
                    // tvOrderDate.setText(BaseUtility.convertDateFormat(orderModel.getDeliverDate()));
                    //  tvOrderTotal.setText("$" + orderModel.getTotal());
                    tvTotal.setText("$" + orderModel.getTotal());
                    tvOrderTotal.setText("Date: " + BaseUtility.convertDateFormat(orderModel.getDeliverDate()) + "      " + " Time :" + BaseUtility.parseDateToddMMyyyy(orderModel.getStartTime()) + "-" + BaseUtility.parseDateToddMMyyyy(orderModel.getEndTime()));
                    tvPaymentType.setText(orderModel.getPaymentType());
                    productModels = orderModel.getOrderProducts();
                    switch (orderModel.getStatus()) {
                        case "accepted":
                            swipeReady.setText("Ready to Deliver");
                            swipeReady.setVisibility(View.VISIBLE);
                            swipeConfirm.setVisibility(View.GONE);
                            break;
                        case "packed":
                            swipeConfirm.setText("Confirm Delivery");
                            swipeConfirm.setVisibility(View.VISIBLE);
                            swipeReady.setVisibility(View.GONE);
                            break;
                        case "completed":
                        case "created":
                            swipeConfirm.setVisibility(View.GONE);
                            swipeReady.setVisibility(View.GONE);
                            break;
                        case "rejected":
                            tvDowloadInvoice.setVisibility(View.GONE);
                            swipeConfirm.setVisibility(View.GONE);
                            swipeReady.setVisibility(View.GONE);

                            tvRejectReasonHeader.setVisibility(View.VISIBLE);
                            tvRejectReason.setVisibility(View.VISIBLE);
                            tvRejectReason.setText(orderModel.getRejectionReason());
                            break;
                    }
                    if (productModels != null && !productModels.isEmpty()) {
                        orderListAdapter.setData(productModels);
                        orderListAdapter.notifyDataSetChanged();
                    } else {

                    }

                    ArrayList<TransactionModel> transactionModelArrayList = orderModel.getTransaction();
                    if (transactionModelArrayList != null && !transactionModelArrayList.isEmpty()) {
                        invoicePdf = transactionModelArrayList.get(0).getInvoice();
                    } else {
                    }
                }
                break;
            case NetworkHelper.REQ_CODE_READY_TO_DELIVER_STATUS:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                // getOrderDetailApi();
                swipeConfirm.setText("Confirm Delivery");
                swipeReady.setVisibility(View.GONE);
                swipeConfirm.setVisibility(View.VISIBLE);
                break;
            case NetworkHelper.REQ_CODE_CONFIRM_TO_DELIVER_STATUS:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                //  getOrderDetailApi();
                swipeConfirm.setVisibility(View.GONE);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_contact:
                onCallBtnClick();
                break;
            case R.id.tv_download_invoice:
                if (!TextUtils.isEmpty(invoicePdf)) {
                    download(invoicePdf, order_no);
                    Fileview(invoicePdf, order_no);
                }
                break;
            case R.id.btn_change_status:
                selectStatusBtn();
                break;
        }
    }

    private void selectStatusBtn() {
        if (btnChangeStatus.getText().toString().equals("Ready to Deliver")) {
            readyToDeliverStatusApi();
        } else {
            confirmToDeliverStatusApi();
        }
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
            Log.e(TAG, "You don't assign permission.");
        }
    }

    private void phoneCall() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + mobileno));
            startActivity(callIntent);
        } else {
            Log.e(TAG, "You don't assign permission.");
        }
    }


    public void Fileview(String invoicePdf, String order_no) {
        Log.v(TAG, "view() Method invoked ");
        if (!hasPermissions(OrderDetailDeliverPopup.this, PERMISSIONS)) {
            Log.v(TAG, "download() Method DON'T HAVE PERMISSIONS ");
            Toast t = Toast.makeText(getApplicationContext(), "You don't have read access !", Toast.LENGTH_LONG);
            t.show();
        } else {
            File d = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);  // -> filename = maven.pdf
            File pdfFile = new File(d, "OD" + order_no + "Lovfresh.pdf");
            Log.v(TAG, "view() Method pdfFile " + pdfFile.getAbsolutePath());
            Uri path = FileProvider.getUriForFile(this, "com.fruitvendorapp.fileprovider", pdfFile);
            Log.v(TAG, "view() Method path " + path);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                startActivity(pdfIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(OrderDetailDeliverPopup.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
            }
        }
        Log.v(TAG, "view() Method completed ");

    }

    public void download(String invoicePdf, String order_no) {
        Log.v(TAG, "download() Method invoked ");
        if (!hasPermissions(OrderDetailDeliverPopup.this, PERMISSIONS)) {
            Log.v(TAG, "download() Method DON'T HAVE PERMISSIONS ");
            Toast t = Toast.makeText(getApplicationContext(), "You don't have write access !", Toast.LENGTH_LONG);
            t.show();
        } else {
            Log.v(TAG, "download() Method HAVE PERMISSIONS ");
            new DownloadFile().execute(invoicePdf, "OD" + order_no + "Lovfresh.pdf");
        }
        Log.v(TAG, "download() Method completed ");
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialogUtil = new ProgressDialogUtil(OrderDetailDeliverPopup.this);
            progressDialogUtil.showDialog();

        }

        @Override
        protected Void doInBackground(String... strings) {
            Log.v(TAG, "doInBackground() Method invoked ");
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            File pdfFile = new File(folder, fileName);
            Log.v(TAG, "doInBackground() pdfFile invoked " + pdfFile.getAbsolutePath());
            Log.v(TAG, "doInBackground() pdfFile invoked " + pdfFile.getAbsoluteFile());

            try {
                pdfFile.createNewFile();
                Log.v(TAG, "doInBackground() file created" + pdfFile);

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground() error" + e.getMessage());
                Log.e(TAG, "doInBackground() error" + e.getStackTrace());
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            Log.v(TAG, "doInBackground() file download completed");

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(OrderDetailDeliverPopup.this, "Download Complete", Toast.LENGTH_SHORT).show();
            progressDialogUtil.dismissDialog();
        }
    }


}