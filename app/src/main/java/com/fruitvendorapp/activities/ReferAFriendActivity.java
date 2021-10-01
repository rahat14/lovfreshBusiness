package com.fruitvendorapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.BuildConfig;
import com.fruitvendorapp.R;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.fruitvendorapp.utilities.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class ReferAFriendActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener {
    private static final String TAG = ReferAFriendActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_descripation)
    TextView tvDescipation;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.btn_send)
    Button btnSend;
    @BindView(R.id.tv_refer_code)
    TextView tvRefercode;
    private ProgressDialogUtil progressDialogUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_a_friend);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing ReferAFriendActivity");
        init();
    }

    private void init() {
        progressDialogUtil=new ProgressDialogUtil(this);
        tvToolbarTitle.setText(getString(R.string.refer_a_friend));
        ivBack.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        getReferFriendApi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_send:
                referAFriend();
                break;
        }
    }
    private void getReferFriendApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            progressDialogUtil.showDialog();
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_FREFER_FRIEND, this, Urls.REFER_FRIEND_URL, this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_FREFER_FRIEND:
                Log.e(TAG, json.toString());
                progressDialogUtil.dismissDialog();
                try {
                    JSONObject jsonObject = new JSONObject(json.toString());
                    String name = jsonObject.getString(Constant.NAME);
                    String descripation = jsonObject.getString(Constant.DESCRIPATION);
                    String refer_code = jsonObject.getString(Constant.REFER_CODE);
                    tvDescipation.setText(descripation);
                    tvRefercode.setText(refer_code);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                break;
        }
    }

    @Override
    public void onError(ANError anError) {
        progressDialogUtil.dismissDialog();
    }



    public void referAFriend() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "LovFreshVendor");
            String shareMessage = "\nCheck out the App at:\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n" + "Use Refer Code :" + tvRefercode.getText().toString();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose one"));
        } catch (Exception e) {
            e.toString();
        }
    }
}