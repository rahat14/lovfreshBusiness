package com.fruitvendorapp.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fruitvendorapp.BuildConfig;
import com.fruitvendorapp.R;
import com.fruitvendorapp.utilities.BaseUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = SettingActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_other_s)
    TextView tvOtherS;
    @BindView(R.id.tv_build_type)
    TextView tvBuildType;
    @BindView(R.id.tv_term_condition)
    TextView tvTermCondi;
    @BindView(R.id.tv_notification_s)
    TextView tvNotificationSetting;
    @BindView(R.id.tv_promo_code)
    TextView tvPromoCode;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing SettingActivity");
        init();
    }

    private void init() {
        tvToolbarTitle.setText(getString(R.string.setting));
        ivBack.setOnClickListener(this);
        tvNotificationSetting.setOnClickListener(this);
        tvOtherS.setOnClickListener(this);
        tvTermCondi.setOnClickListener(this);
        tvPromoCode.setOnClickListener(this);
        getBuildNumberAndVersion();
    }


    private void getBuildNumberAndVersion() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionCode = BuildConfig.VERSION_CODE;
            tvBuildType.setText("Build version: " + versionCode + "." + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_other_s:
                BaseUtility.sendActivityIntent(this, CustomerCollectionActivity.class);
                break;
            case R.id.tv_notification_s:
                BaseUtility.sendActivityIntent(this, NotificationSettingActivity.class);
                break;
            case R.id.tv_term_condition:
                BaseUtility.sendActivityIntent(this, TermAndConditionActivity.class);
                break;
            case R.id.tv_promo_code:
                BaseUtility.sendActivityIntent(this, PromoCodeActivity.class);
                break;
        }
    }
}