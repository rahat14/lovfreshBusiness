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
import com.fruitvendorapp.adapters.MyAddressAdapter;
import com.fruitvendorapp.adapters.TrainingVideoAdapter;
import com.fruitvendorapp.model.AddressModel;
import com.fruitvendorapp.model.TrainingModel;
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

public class TrainingActivity extends AppCompatActivity implements View.OnClickListener,JsonArrayResponseListener {
    private static final String TAG = TrainingActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rv_training)
    RecyclerView rvTraining;
    private ProgressDialogUtil progressDialogUtil;
    private ArrayList<TrainingModel> trainingModelArrayList;
    private TrainingVideoAdapter trainingVideoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing TrainingActivity");
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.training));
        ivBack.setOnClickListener(this);
        setTrainingAdapter();
        getTrainingApi();
    }
    private void setTrainingAdapter() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rvTraining.setLayoutManager(manager);
        trainingVideoAdapter = new TrainingVideoAdapter(this);
        rvTraining.setAdapter(trainingVideoAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }


    //implement Address Api  for get All Address
    private void getTrainingApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.getRequestWithJSonArrayToken(NetworkHelper.REQ_CODE_GET_TRAINING_VIDEO, this, Urls.GET_TRAINING_URL, this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONArray json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_TRAINING_VIDEO:
                Log.e(TAG, json.toString());
                progressDialogUtil.dismissDialog();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Type videoType = new TypeToken<List<TrainingModel>>() {
                }.getType();
                trainingModelArrayList = gson.fromJson(json.toString(), videoType);
                if (trainingModelArrayList != null && !trainingModelArrayList.isEmpty()) {
                    trainingVideoAdapter.setData(trainingModelArrayList);
                    trainingVideoAdapter.notifyDataSetChanged();
                    rvTraining.setVisibility(View.VISIBLE);
                    //llNoData.setVisibility(View.GONE);

                } else {
                    rvTraining.setVisibility(View.GONE);
                   // llNoData.setVisibility(View.VISIBLE);
                }
                break;


        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }
}