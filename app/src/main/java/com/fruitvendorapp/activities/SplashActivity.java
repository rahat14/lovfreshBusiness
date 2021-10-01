package com.fruitvendorapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import com.fruitvendorapp.BuildConfig;
import com.fruitvendorapp.R;
import com.fruitvendorapp.fcm.NotificationUtils;
import com.fruitvendorapp.utilities.SessionManager;

import io.sentry.Sentry;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        Sentry.captureMessage("testing SplashActivity");

        TextView version = findViewById(R.id.slider_version);
        version.setText("v" + BuildConfig.VERSION_NAME + BuildConfig.BUILD_TYPE);

        int SPLASH_TIME_OUT = 3000;
        boolean isLogin = new SessionManager(this).isLogin();
        new Handler().postDelayed(() -> {
            if (isLogin && new SessionManager(this).getUserId().length() > 0) {
                Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(SplashActivity.this, WalkThroughActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }


            /*Intent resultIntent = null;

            resultIntent = new Intent(getApplicationContext(), DashboardActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
            taskStackBuilder.addNextIntentWithParentStack(resultIntent);
            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);


            NotificationUtils notificationUtils = new NotificationUtils(this);
            notificationUtils.showNotificationMessage("test", "ffffffff", "", resultIntent, pendingNotificationIntent);
            notificationUtils.playNotificationSound();*/

        }, SPLASH_TIME_OUT);
    }


}