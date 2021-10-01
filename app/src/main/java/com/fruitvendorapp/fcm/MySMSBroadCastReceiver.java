package com.fruitvendorapp.fcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.fruitvendorapp.R;
import com.fruitvendorapp.popups.OrderNotificationPopup;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.Constant;

import org.json.JSONException;
import org.json.JSONObject;

public class MySMSBroadCastReceiver extends BroadcastReceiver {
    // Get the object of SmsManager
    public void onReceive(Context context, Intent intent) {
        // Get Bundle object contained in the SMS intent passed in
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            try {
                if (bundle.getString("data") != null) {
                    JSONObject jsonObject = new JSONObject(bundle.getString("data"));
                    Log.e("broadcast_recevier", jsonObject.toString());
                    String ctpn = jsonObject.getString("click_action");
                    String order_id = jsonObject.getString("order_id");
                    if (ctpn != null && ctpn.equals("OrderNotificationPopup")) {
                        Intent intent1 = new Intent(context, OrderNotificationPopup.class);
                        intent1.putExtra(Constant.ORDER_ID, order_id);
                        intent1.putExtra(Constant.APP_STATE, "background");
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent1);
                    } else {

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }
}
