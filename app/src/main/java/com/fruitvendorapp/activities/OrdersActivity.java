package com.fruitvendorapp.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.OrderListAdapter;
import com.fruitvendorapp.model.OrderModel;
import com.fruitvendorapp.model.StoreModel;
import com.fruitvendorapp.server_networking.JsonArrayResponseListener;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.Urls;
import com.google.android.material.tabs.TabLayout;
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

public class OrdersActivity extends AppCompatActivity implements View.OnClickListener, JsonArrayResponseListener {
    private static final String TAG = OrdersActivity.class.getSimpleName();
    @BindView(R.id.tab_order_cate)
    TabLayout tabOrderCate;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.rv_order)
    RecyclerView rvMyOrder;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoFound;
    @BindView(R.id.tv_no_data)
    TextView tvNoRecord;
    @BindView(R.id.auto_search)
    EditText edSearchOrder;
    @BindView(R.id.iv_search_order)
    ImageView ivSearchOrder;
    public boolean isSearchOrderVisible = false;
    private ProgressDialogUtil progressDialogUtil;
    private ArrayList<OrderModel> orderModelArrayList;
    private OrderListAdapter orderListAdapter;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing OrdersActivity");
        ivSearchOrder.setVisibility(View.VISIBLE);
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        if (getIntent().hasExtra(Constant.INDEX)) {
            String indexs = getIntent().getStringExtra(Constant.INDEX);
            try {
                index = Integer.parseInt(indexs);
                Log.e(TAG, String.valueOf(index));
            } catch (NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
        }
        ivBack.setOnClickListener(this);
        ivSearchOrder.setOnClickListener(this);
        tvToolbarTitle.setText("Orders");
        setTabOrderCategory(index);
        setOrderProduct();

        try {
            edSearchOrder.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String s = editable.toString();
                    s = s.toLowerCase();
                    filter(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //getVendorByProductApi("");
    }

    private void setTabOrderCategory(int index) {
        ArrayList<StoreModel> list = new ArrayList<>();
        list.add(new StoreModel("All"));
        list.add(new StoreModel("PENDING"));
        list.add(new StoreModel("COMPLETED"));
        list.add(new StoreModel("CANCELLED"));

        for (StoreModel storeModel : list) {
            TabLayout.Tab tab = tabOrderCate.newTab();
            tab.setCustomView(R.layout.tab);
            tab.setText(storeModel.getTitle1());
            tabOrderCate.addTab(tab);
            tabOrderCate.selectTab(tabOrderCate.getTabAt(index));
        }

        if (index == 1) {
            getVendorByPendingProductApi();
        } else if (index == 2) {
            getVendorByProductApi("completed");
        } else {
            getVendorByProductApi("");
        }

        tabOrderCate.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e(TAG, String.valueOf(tab.getPosition()));
                if (tab.getText().equals("PENDING")) {
                    getVendorByPendingProductApi();
                } else if (tab.getText().equals("COMPLETED")) {
                    getVendorByProductApi("completed");
                } else if (tab.getText().equals("CANCELLED")) {
                    getVendorByProductApi("rejected");
                } else {
                    getVendorByProductApi("");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getVendorByPendingProductApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            //  if (!TextUtils.isEmpty("pending")) {
            Log.e(TAG, Urls.ORDER_URL + "?pending_status=" + "pending");
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_ORDER, this, Urls.ORDER_URL + "?pending_status=" + "pending", this);
//            } else {
//                Log.e(TAG, Urls.CREATE_ORDER_URL + "?pending_status=" + "pending");
//                RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_ORDER, this, Urls.CREATE_ORDER_URL, this);
//            }
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    // this method search news by title
    private void filter(String text) {
        if (orderModelArrayList != null) {
            ArrayList<OrderModel> temp = new ArrayList<>();
            for (OrderModel d : orderModelArrayList) {
                if (!TextUtils.isEmpty(d.getUser().getFirstName()) || !TextUtils.isEmpty(d.getOrderNumber()) || !TextUtils.isEmpty(d.getUser().getNational_number()) || !TextUtils.isEmpty(d.getUser().getEmail())) {
                    if (d.getUser().getFirstName().toLowerCase().contains(text) || d.getOrderNumber().toLowerCase().contains(text) || d.getUser().getNational_number().toLowerCase().contains(text) || d.getUser().getEmail().toLowerCase().contains(text)) {
                        temp.add(d);
                    }
                }
            }
            if (orderModelArrayList != null && !orderModelArrayList.isEmpty()) {
                orderListAdapter.updateList(temp);
            }
        }
    }


    private void setOrderProduct() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        orderListAdapter = new OrderListAdapter(this);
        rvMyOrder.setAdapter(orderListAdapter);
        rvMyOrder.setLayoutManager(manager);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_search_order:
                if (isSearchOrderVisible) {
                    isSearchOrderVisible = false;
                    edSearchOrder.setVisibility(View.GONE);
                } else {
                    isSearchOrderVisible = true;
                    edSearchOrder.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void getVendorByProductApi(String status) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            if (!TextUtils.isEmpty(status)) {
                Log.e(TAG, Urls.ORDER_URL + "?status=" + status);
                RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_ORDER, this, Urls.ORDER_URL + "?status=" + status, this);

            } else {
                RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_ORDER, this, Urls.ORDER_URL, this);
            }
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }


    }

    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_ORDER:
                progressDialogUtil.dismissDialog();
                Log.e(TAG, json.toString());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Type orderType = new TypeToken<List<OrderModel>>() {
                }.getType();
                orderModelArrayList = gson.fromJson(json.toString(), orderType);
                if (orderModelArrayList != null && !orderModelArrayList.isEmpty()) {
                    orderListAdapter.setData(orderModelArrayList);
                    orderListAdapter.notifyDataSetChanged();
                    llNoFound.setVisibility(View.GONE);
                    rvMyOrder.setVisibility(View.VISIBLE);
                } else {
                    llNoFound.setVisibility(View.VISIBLE);
                    tvNoRecord.setText("No Order Found");
                    rvMyOrder.setVisibility(View.GONE);
                }
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }
}