package com.fruitvendorapp.server_networking;

import com.androidnetworking.error.ANError;

import org.json.JSONArray;


public interface JsonArrayResponseListener {
    /**
     * Method will be called when request made and returned response successfully.
     */
    void onSuccess(int requestCode, JSONArray json);

    /**
     * Method will be called when error occurred during  request.
     */
    void onError(ANError anError);
}
