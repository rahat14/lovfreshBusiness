package com.fruitvendorapp.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.SlotSelectionAdapter;
import com.fruitvendorapp.adapters.TimeSlotSelectionAdapter;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.interfaces.ItemSelectTimeInterface;
import com.fruitvendorapp.interfaces.ItemSelectTimeListInterface;
import com.fruitvendorapp.model.DateAndTimeSlotModel;
import com.fruitvendorapp.model.TimeSlotModel;
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

public class PickupSlotSelectionActivity extends AppCompatActivity implements View.OnClickListener, ItemSelectInterface, ResponseListener, JsonArrayResponseListener, ItemSelectTimeListInterface, ItemSelectTimeInterface {
    private static final String TAG = PickupSlotSelectionActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_slot_selection)
    RecyclerView rvSlotSelection;
    private String startTime = "", endTime = "";
    private SlotSelectionAdapter slotSelectionAdapter;
    private ProgressDialogUtil progressDialogUtil;
    private ArrayList<DateAndTimeSlotModel> slotModelArrayList;
    private String switch_type = "", selectedDate = "";
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_selection);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing SlotSelectionActivity");
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText("Slot Selection");
        ivBack.setOnClickListener(this);
        setSlotAdapter();
        getSlotSelectionApi();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            onBackPressed();
        }
    }

    private void setSlotAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvSlotSelection.setLayoutManager(linearLayoutManager);
        slotSelectionAdapter = new SlotSelectionAdapter(this, this, this);
        rvSlotSelection.setAdapter(slotSelectionAdapter);
    }

    private void getSlotSelectionApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_SLOT_DATE_TIME, this, Urls.GET_SLOT_SETTING_URL+ "?delivery_type=5", this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    private void postSlotSelectionApi(String date, String start_time, String end_Time, String type) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_POST_SLOT_DATE_TIME, this, Urls.GET_SLOT_SETTING_URL, new NetworkHelper(this).addDateJson(date, start_time, end_Time, type), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    private void postDateSlotSelectionApi(String date, String date_type) {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_POST_SLOT_DATE, this, Urls.GET_SLOT_SETTING_URL, new NetworkHelper(this).dateJson(date, date_type), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_SLOT_DATE_TIME:
                Log.e(TAG, json.toString());
                progressDialogUtil.dismissDialog();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Type slotType = new TypeToken<List<DateAndTimeSlotModel>>() {
                }.getType();
                slotModelArrayList = gson.fromJson(json.toString(), slotType);
                if (slotModelArrayList != null && !slotModelArrayList.isEmpty()) {
                    slotSelectionAdapter.setData(slotModelArrayList);
                    slotSelectionAdapter.notifyDataSetChanged();
                    rvSlotSelection.setVisibility(View.VISIBLE);
                    //llNoData.setVisibility(View.GONE);

                } else {
                    rvSlotSelection.setVisibility(View.GONE);
                    // llNoData.setVisibility(View.VISIBLE);
                }
                break;


        }
    }

    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_POST_SLOT_DATE_TIME:
            case NetworkHelper.REQ_CODE_POST_SLOT_DATE:
                Log.e(TAG, json.toString());
                progressDialogUtil.dismissDialog();
                getSlotSelectionApi();
                break;

        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }


    @Override
    public void itemTime(boolean isSelect, ArrayList<TimeSlotModel> list, String date) {
        selectedDate = date;
        if (isSelect) {
            timeSlotDialog("on", list, date);
        } else {
            timeSlotDialog("off", list, date);
        }

    }

    public void timeSlotDialog(String type, ArrayList<TimeSlotModel> timeSlotModels, String date) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogview = inflater.inflate(R.layout.dialog_time_slot, null);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
        dialogbuilder.setView(dialogview);
        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView ivClose = dialogview.findViewById(R.id.iv_close);
        RecyclerView rvTime = dialogview.findViewById(R.id.rv_time_slot);
        TextView tvDate = dialogview.findViewById(R.id.tv_date_select);
        Button btnSave = dialogview.findViewById(R.id.btn_save);
        tvDate.setText("Time slot for " + date);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvTime.setLayoutManager(linearLayoutManager);
        TimeSlotSelectionAdapter timeSlotSelectionAdapter = new TimeSlotSelectionAdapter(this, this, alertDialog);
        timeSlotSelectionAdapter.setData(timeSlotModels);
        rvTime.setAdapter(timeSlotSelectionAdapter);
        ivClose.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        btnSave.setOnClickListener(v -> {
            String start_time = startTime;
            String end_time = endTime;
            if (TextUtils.isEmpty(start_time)) {
                BaseUtility.toastMsg(getApplicationContext(), getString(R.string.select_time));
            } else {
                postSlotSelectionApi(date, start_time, end_time, type);
                alertDialog.dismiss();
            }

        });
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    @Override
    public void itemSelect(String date, String type) {
        postDateSlotSelectionApi(date, type);
    }

    @Override
    public void itemTime(String time, String start_time, String end_time, String type, AlertDialog alertDialog) {
        alertDialog.dismiss();
        Log.e(TAG, "" + start_time + "  " + end_time);
        startTime = start_time;
        endTime = end_time;
        if (TextUtils.isEmpty(start_time)) {
            BaseUtility.toastMsg(getApplicationContext(), getString(R.string.select_time));
        } else {
            postSlotSelectionApi(selectedDate, start_time, end_time, type);
        }
    }
}