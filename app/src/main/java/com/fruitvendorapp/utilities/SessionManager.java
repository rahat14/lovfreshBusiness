package com.fruitvendorapp.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.fruitvendorapp.R;
import com.fruitvendorapp.activities.LoginActivity;
import com.fruitvendorapp.model.AddressModel;
import com.fruitvendorapp.model.UserModel;
import com.fruitvendorapp.model.VendorModel;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;


public class SessionManager {
    private Context context;
    private static final String APP_PREF_NAME = "the Lovefresh Vendor";
    private static final String APP_PREF = "Vendor";
    private SharedPreferences appPref;
    private SharedPreferences.Editor appPrefEditor;


    public SessionManager(Context context) {
        this.context = context;
        appPref = context.getSharedPreferences(APP_PREF_NAME, Context.MODE_PRIVATE);
        appPrefEditor = appPref.edit();
        appPrefEditor.apply();
    }


    public void saveVendorDetail(VendorModel vendorModel) {
        appPrefEditor.putString(Constant.VENDOR_ID, vendorModel.getId());
        appPrefEditor.putString(Constant.TITLE, vendorModel.getTitle());
        appPrefEditor.putString(Constant.EMAIL, vendorModel.getEmail());
        appPrefEditor.putString(Constant.MOBILE, vendorModel.getMobile());
        appPrefEditor.putString(Constant.LOYAL, vendorModel.getLoyal());
        appPrefEditor.putString(Constant.WEBSITE, vendorModel.getWebsite());
        appPrefEditor.putString(Constant.ABN, vendorModel.getAbn());
        appPrefEditor.putString(Constant.IMAGES, vendorModel.getImages().get(0).getImage());
        appPrefEditor.commit();
    }

    public void saveUserDetail(UserModel userModel) {
        appPrefEditor.putString(Constant.USER_ID, userModel.getUser_id());
        appPrefEditor.putString(Constant.USER_NAME, userModel.getUsername());
        appPrefEditor.putString(Constant.NATIONAL_NUMBER, userModel.getNational_number());
        appPrefEditor.putString(Constant.EMAIL, userModel.getEmail());
        appPrefEditor.putString(Constant.FIRST_NAME, userModel.getFirstName());
        appPrefEditor.putString(Constant.LAST_NAME, userModel.getLastName());
        appPrefEditor.putString(Constant.PHONE_NUMBER, userModel.getPhoneNumber());
        appPrefEditor.putBoolean(Constant.IS_LOGIN, true);
        appPrefEditor.commit();
    }

    public void saveAddressDetail(AddressModel addressModel) {
        appPrefEditor.putInt(Constant.ADD_ID, Integer.parseInt(addressModel.getId()));
        appPrefEditor.putString(Constant.LATITUDE, addressModel.getLatitude());
        appPrefEditor.putString(Constant.LONGITUDE, addressModel.getLongitude());
        appPrefEditor.putString(Constant.ADDRESS, addressModel.getAddress());
        appPrefEditor.putString(Constant.FULL_NAME, addressModel.getFullName());
        appPrefEditor.commit();
    }

    public JSONObject getAddress() {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.FULL_NAME, appPref.getString(Constant.FULL_NAME, ""));
            object.putOpt(Constant.ADDRESS, appPref.getString(Constant.ADDRESS, ""));
            object.putOpt(Constant.ADD_ID, appPref.getInt(Constant.ADD_ID, 0));
            object.putOpt(Constant.LATITUDE, appPref.getString(Constant.LATITUDE, ""));
            object.putOpt(Constant.LONGITUDE, appPref.getString(Constant.LONGITUDE, ""));
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public String getUserId() {
        return appPref.getString(Constant.USER_ID, "");
    }

    public boolean isLogin() {
        return appPref.getBoolean(Constant.IS_LOGIN, false);
    }

    public void setCustomerNu(String mobileno) {
        appPrefEditor.putString(Constant.NATIONAL_NUMBER, mobileno);
        appPrefEditor.commit();
    }

    public String getCustomerNu() {
        return appPref.getString(Constant.NATIONAL_NUMBER, "");
    }

    public void setToken(String token) {
        appPrefEditor.putString(Constant.TOKEN, token);
        appPrefEditor.commit();
    }

    public String getToken() {
        return appPref.getString(Constant.TOKEN, "");
    }


    public void setShopTitle(String title) {
        appPrefEditor.putString(Constant.TITLE, title);
        appPrefEditor.commit();
    }

    public String getShopTitle() {
        return appPref.getString(Constant.TITLE, "");
    }

    public void setVendorId(String vendor_id) {
        appPrefEditor.putString(Constant.VENDOR_ID, vendor_id);
        appPrefEditor.commit();
    }

    public String getVendorId() {
        return appPref.getString(Constant.VENDOR_ID, "");
    }

    public void setIsVerify(String verify) {
        appPrefEditor.putString(Constant.IS_VERIFY, verify);
        appPrefEditor.commit();
    }

    public String getIsVerify() {
        return appPref.getString(Constant.IS_VERIFY, "");
    }


    public void setSubscription(String subscription) {
        appPrefEditor.putString(Constant.SUBSCRIPATION, subscription);
        appPrefEditor.commit();
    }

    public String getSubscription() {
        return appPref.getString(Constant.SUBSCRIPATION, "");
    }

    public void setEmail(String email) {
        appPrefEditor.putString(Constant.EMAIL, email);
        appPrefEditor.commit();
    }

    public String getEmail() {
        return appPref.getString(Constant.EMAIL, "");
    }

    public String getNational_number() {
        return appPref.getString(Constant.NATIONAL_NUMBER, "");
    }

    public String getFirstName() {
        return appPref.getString(Constant.FIRST_NAME, "");
    }

    public void setPhone(String phone) {
        appPrefEditor.putString(Constant.PHONE_NUMBER, phone);
        appPrefEditor.commit();
    }

    public String getPhone() {
        return appPref.getString(Constant.PHONE_NUMBER, "");
    }

    public String getImageUrl() {
        return appPref.getString(Constant.IMAGE, "");
    }

    public void setSound(boolean isSound) {
        appPrefEditor.putBoolean(Constant.IS_SOUND, isSound);
        appPrefEditor.commit();
    }

    public boolean getSound() {
        return appPref.getBoolean(Constant.IS_SOUND, false);
    }

    public void setVibrate(boolean isVibrate) {
        appPrefEditor.putBoolean(Constant.IS_VIBRATION, isVibrate);
        appPrefEditor.commit();
    }

    public boolean getVibrate() {
        return appPref.getBoolean(Constant.IS_VIBRATION, false);
    }

    public void logout() {
        logoutAlert();
    }

    private void logoutAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        builder.setMessage(R.string.logout_alert_msg);
        builder.setCancelable(true);
        builder.setPositiveButton(
                context.getText(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.e("token = ", getToken());
                        //logoutApi();
                        appPrefEditor.clear();
                        appPrefEditor.commit();
                        sentToLoginScreen();
                    }
                });

        builder.setNegativeButton(
                context.getText(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    private void sentToLoginScreen() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constant.IS_LOGIN, Constant.IS_LOGIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
