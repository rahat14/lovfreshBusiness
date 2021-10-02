package com.fruitvendorapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.NotificationAdapter;
import com.fruitvendorapp.model.OrderModel;
import com.fruitvendorapp.model.OrderResp;
import com.fruitvendorapp.model.StoreModel;
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

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {
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
    LinearLayoutManager manager;
    private Boolean isFirstTime = true;
    int currentPage = 1;
    int totalPage = 10000;
    int itemCount = 0;
    boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    @BindView(R.id.progress_circular)
    ProgressBar pbar;

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
        initScrollListener();
        getNotificationApi(1);

    }

    private void setNotificationAdapter() {
        manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rvStore.setLayoutManager(manager);
        notificationAdapter = new NotificationAdapter(this, new ArrayList<>());
        rvStore.setAdapter(notificationAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotificationApi(currentPage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    private void getNotificationApi(int page) {
        if (ConnectionUtil.isInternetOn(this)) {
            if (isFirstTime) {
                if(currentPage==1) progressDialogUtil.showDialog();
            }
            //  Log.e(TAG, Urls.NOTIFICATION_URL + "?status=");
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_NOTIFICATION, this, Urls.BASE_URL + "notification-list?page_size=8&page=" + page, this);
            isFirstTime = false;
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    private void loadMore() {
        if (currentPage != totalPage) {
             pbar.setVisibility(View.VISIBLE);
            getNotificationApi(currentPage++);
            isScrolling = false;

        } else {
            isScrolling = false;
            Toast.makeText(getApplicationContext(), "You Are At The Last Page", Toast.LENGTH_SHORT).show();
        }


    }


    private void initScrollListener() {


        rvStore.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { // scroll down
                    currentItems = manager.getChildCount();
                    totalItems = manager.getItemCount();
                    scrollOutItems = manager.findFirstVisibleItemPosition();

                    if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                        isScrolling = false;
                        loadMore();
                    }
                }


            }
        });


    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_NOTIFICATION:
                if (currentPage == 1) progressDialogUtil.dismissDialog();
                pbar.setVisibility(View.GONE);
                Log.e(TAG, json.toString());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                OrderResp resp = gson.fromJson(json.toString(), OrderResp.class);
                ArrayList<OrderModel> orderModelArrayList = (ArrayList<OrderModel>) resp.getData().getResults();
                if (orderModelArrayList != null && !orderModelArrayList.isEmpty()) {
                    currentPage = resp.getMeta().getCurrent_page();
                    totalPage = resp.getMeta().getTotal();
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
        if (currentPage == 1) progressDialogUtil.dismissDialog();
        Toast.makeText(getApplicationContext(), "ERROR : " + anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
    }
}