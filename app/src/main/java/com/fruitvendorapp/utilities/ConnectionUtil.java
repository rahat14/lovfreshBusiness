package com.fruitvendorapp.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;


public class ConnectionUtil {
    @SuppressLint("MissingPermission")
    public static boolean isInternetOn(Context context) {
        // get Connectivity Manager object to check connection

        try {
            ConnectivityManager connectionInfo = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            if (connectionInfo != null) {
                // Check for network connections
                if (connectionInfo.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                        connectionInfo.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connectionInfo.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connectionInfo.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
                    return true;

                } else if (connectionInfo.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connectionInfo.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
                    return false;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }
}

