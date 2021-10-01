package com.fruitvendorapp.fcm;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.activities.DashboardActivity;
import com.fruitvendorapp.model.OrderModel;
import com.fruitvendorapp.popups.OrderNotificationPopup;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.SessionManager;
import com.fruitvendorapp.utilities.Urls;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService implements ResponseListener {
    private static final String TAG = "Notification";
    private NotificationUtils notificationUtils;
    private int notificationIndex = 0;


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN", s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("remote_mshe", "From: " + remoteMessage.getFrom());
        if (remoteMessage == null)
            return;
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            //   Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getTitle());
            try {
                Map<String, String> params = remoteMessage.getData();
                String customData = (String) params.get("data");
                if (customData.isEmpty()) {
                    NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                    notificationUtils.playNotificationSound();

                } else {
                    JSONObject object = new JSONObject(customData);
                    handleNotification(object);
                }
                //
                Log.e(TAG, "Data object background: " + customData);

//                if (object != null) {
//                  //
//                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {

                Map<String, String> params = remoteMessage.getData();
                String customData = (String) params.get("data");
                //JSONObject object = new JSONObject(params);
                Log.e(TAG, "Data object forground: " + customData);
                JSONObject object = new JSONObject(customData);
                handleDataMessage(object);

            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        } else {
            Intent resultIntent = null;
            String order_id = "1";
            resultIntent = new Intent(getApplicationContext(), DashboardActivity.class);
            if (order_id.length() > 0) {
                resultIntent.putExtra(Constant.NOTIFICATION_ORDER_ID, order_id);
            }
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
            taskStackBuilder.addNextIntentWithParentStack(resultIntent);

            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(getApplicationContext(), notificationIndex, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
            showNotificationMessage(getApplicationContext(), remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), "", resultIntent, pendingNotificationIntent);

        }

    }

    private void handleNotification(JSONObject json) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Constant.PUSH_NOTIFICATION);
          /*  try {
                pushNotification =new Intent(getApplicationContext(), OrderNotificationPopup.class);
                pushNotification.putExtra("order_id", json.getString("order_id"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            // play notification sound
            if (new SessionManager(this).getSound()) {
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            }
        } else {

            //  new SessionManager(getApplicationContext()).isNotification(true);
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString().replaceAll("data:.\\s", ""));
        try {

            String title = "";
            String message = "";
            String order_id = "";
            try {
                title = json.getString("title");
                message = json.getString("body");
                order_id = json.getString("order_id");
                String status = json.getString("status");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e(TAG, "title: " + title);
            Log.e(TAG, "body: " + message);
            Log.e(TAG, "order_id: " + order_id);

            Intent resultIntent = null;
            //   if(title.equals("New Order Created")){
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                if (json.optString("click_action").equals("OrderNotificationPopup")) {
                    getOrderDetailApi(json.getString("order_id"));
                   /* if(status.equals("created")){
                        resultIntent =new Intent(getApplicationContext(), OrderNotificationPopup.class);
                        resultIntent.putExtra(Constant.FLAG, "forground");
                        resultIntent.putExtra(Constant.ORDER_ID,order_id);
                    }*/
                  /*  resultIntent =new Intent(getApplicationContext(), OrderNotificationPopup.class);
                    resultIntent.putExtra(Constant.FLAG, "forground");
                    resultIntent.putExtra(Constant.ORDER_ID,order_id);*/
                }
            }
         /*   }
            else {
                resultIntent = new Intent(getApplicationContext(), DashboardActivity.class);
            }*/
            resultIntent = new Intent(getApplicationContext(), DashboardActivity.class);
            if (order_id.length() > 0) {
                resultIntent.putExtra(Constant.NOTIFICATION_ORDER_ID, order_id);
            }
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(getApplicationContext());
            taskStackBuilder.addNextIntentWithParentStack(resultIntent);

            PendingIntent pendingNotificationIntent = PendingIntent.getActivity(getApplicationContext(), notificationIndex, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
            showNotificationMessage(getApplicationContext(), title, message, "", resultIntent, pendingNotificationIntent);

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent, PendingIntent pendingNotificationIntent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.playNotificationSound();
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, pendingNotificationIntent);
    }

    private void getOrderDetailApi(String orderId) {
        if (ConnectionUtil.isInternetOn(this)) {
            Log.e(TAG, Urls.ORDER_URL + orderId + "/");
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_ORDER_DETAIL, this, Urls.ORDER_URL + orderId + "/", this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_ORDER_DETAIL:
                Log.e(TAG, json.toString());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                OrderModel orderModel = gson.fromJson(String.valueOf(json), OrderModel.class);
                if (orderModel != null) {
                    if (orderModel.getGetStatus().equals("Waiting to Accepted")) {
                        Intent resultIntent = new Intent(getApplicationContext(), OrderNotificationPopup.class);
                        resultIntent.putExtra(Constant.APP_STATE, "forground");
                        resultIntent.putExtra(Constant.ORDER_ID, orderModel.getId());
                        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getApplicationContext().startActivity(resultIntent);
                    } else {
                        Intent resultIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //resultIntent.putExtra(Constant.ORDER_ID,orderModel.getId());
                        getApplicationContext().startActivity(resultIntent);
                    }


                }
                break;
        }
    }

    @Override
    public void onError(ANError anError) {

    }
}