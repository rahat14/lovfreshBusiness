package com.fruitvendorapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.MyAddressAdapter;
import com.fruitvendorapp.model.AddressModel;
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

public class MyAddressActivity extends AppCompatActivity implements View.OnClickListener, JsonArrayResponseListener {
    private static final String TAG = MyAddressActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_address)
    RecyclerView rvMyAddress;
    @BindView(R.id.btn_add_address)
    Button btnAddress;
    @BindView(R.id.ll_no_data)
    LinearLayout llNoData;
    private MyAddressAdapter myAddressAdapter;
    private ProgressDialogUtil progressDialogUtil;
    private ArrayList<AddressModel> addressList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing MyAddressActivity");
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.my_address));
        ivBack.setOnClickListener(this);
        btnAddress.setOnClickListener(this);
        setMyAddressAdapter();
        getAddressListApi();
    }

    private void setMyAddressAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rvMyAddress.setLayoutManager(manager);
        myAddressAdapter = new MyAddressAdapter(this);
        rvMyAddress.setAdapter(myAddressAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressListApi();
    }

    //implement Address Api  for get All Address
    private void getAddressListApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_ADDRESS, this, Urls.GET_ADDRESS_URL, this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }

    }


    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_ADDRESS:
                Log.e(TAG, json.toString());
                progressDialogUtil.dismissDialog();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Type addressType = new TypeToken<List<AddressModel>>() {
                }.getType();
                addressList = gson.fromJson(json.toString(), addressType);
                if (addressList != null && !addressList.isEmpty()) {
                    myAddressAdapter.setData(addressList);
                    myAddressAdapter.notifyDataSetChanged();
                    rvMyAddress.setVisibility(View.VISIBLE);
                    llNoData.setVisibility(View.GONE);

                } else {
                    rvMyAddress.setVisibility(View.GONE);
                    llNoData.setVisibility(View.VISIBLE);
                }
                break;


        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }
}