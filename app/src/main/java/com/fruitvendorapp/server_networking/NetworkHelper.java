package com.fruitvendorapp.server_networking;

import android.content.Context;
import android.util.Log;

import com.fruitvendorapp.model.DeliveryFeeModel;
import com.fruitvendorapp.model.DeliveryType;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.SessionManager;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NetworkHelper {


    private static final String TAG = NetworkHelper.class.getSimpleName();
    public static final int REQ_CODE_LOGIN = 1;
    public static final int REQ_CODE_SIGN_UP = 2;
    public static final int REQ_CODE_VERIFY = 3;
    public static final int REQ_CODE_LOGOUT = 4;
    public static final int REQ_CODE_FORGOT_PASSWORD = 5;
    public static final int REQ_CODE_RESET_PASSWORD = 6;
    public static final int REQ_CODE_LOGIN_MOBILE = 7;
    public static final int REQ_CODE_RESEND_OTP = 8;
    public static final int REQ_CODE_GET_PLAN = 9;
    public static final int REQ_CODE_SUBCRIPTION_PLAN = 10;
    public static final int REQ_CODE_GET_CATEGROY_LIST = 11;
    public static final int REQ_CODE_GET_UNIT_LIST = 12;
    public static final int REQ_CODE_GET_PRODUCT_LIST = 13;
    public static final int REQ_CODE_GET_PRODUCT_DETAIL = 14;
    public static final int REQ_CODE_DELETE_PRODUCT = 15;
    public static final int REQ_CODE_HIDE_PRODUCT = 16;
    public static final int REQ_CODE_UPDATE_PRODUCT = 17;
    public static final int REQ_CODE_GET_SPECIAL_LIST = 18;
    public static final int REQ_CODE_GET_CATEGORY = 19;
    public static final int REQ_CODE_GET_FILTER_PRODUCT = 20;
    public static final int REQ_CODE_DELETE_MULTIPLE_PRODUCT = 21;
    public static final int REQ_CODE_HIDE_MULTIPLE_PRODUCT = 22;
    public static final int REQ_CODE_GET_ORDER = 23;
    public static final int REQ_CODE_GET_ORDER_DETAIL = 24;
    public static final int REQ_CODE_GET_SHOP_ONLINE_PRODUCT = 25;
    public static final int REQ_CODE_GET_ADDRESS = 26;
    public static final int REQ_CODE_GET_ADDRESS_BY_ID = 27;
    public static final int REQ_CODE_UPDATE_ADDRESS = 28;
    public static final int REQ_CODE_SEND_FCM = 29;
    public static final int REQ_CODE_GET_VENDOR_DETAIL = 30;
    public static final int REQ_CODE_GET_NOTIFICATION = 31;
    public static final int REQ_CODE_CHANGE_PASSWORD = 32;
    public static final int REQ_CODE_GET_TRAINING_VIDEO = 33;
    public static final int REQ_CODE_GET_ACCEPT_REJECT_STATUS = 34;
    public static final int REQ_CODE_READY_TO_DELIVER_STATUS = 35;
    public static final int REQ_CODE_CONFIRM_TO_DELIVER_STATUS = 36;
    public static final int REQ_CODE_READY_TO_PICKUP_STATUS = 37;
    public static final int REQ_CODE_CONFIRM_TO_PICKUP_STATUS = 38;
    public static final int REQ_CODE_GET_REJECT_STATUS = 39;
    public static final int REQ_CODE_GET_ORDER_COUNT = 40;
    public static final int REQ_CODE_GET_DELIVER_TYPE = 41;
    public static final int REQ_CODE_UPDATE_DELIVER_TYPE = 42;
    public static final int REQ_CODE_ADD_CATEGROY = 43;
    public static final int REQ_CODE_UPDATE_NOTIFICATION = 44;
    public static final int REQ_CODE_GET_NOTIFICATION_SETTING = 45;
    public static final int REQ_CODE_GET_CALL_SUPPORT = 46;
    public static final int REQ_CODE_GET_FREFER_FRIEND = 47;
    public static final int REQ_CODE_ADD_SAVE_MINI_ORDER = 48;
    public static final int REQ_CODE_ADD_SAVE_DIST_AND_FEE = 49;
    public static final int REQ_CODE_SAVE_ADD_PROMO_CODE = 50;
    public static final int REQ_CODE_GET_COUPAN = 51;
    public static final int REQ_CODE_DELETE_COUPAN = 52;
    public static final int REQ_CODE_GET_SLOT_DATE_TIME = 53;
    public static final int REQ_CODE_POST_SLOT_DATE_TIME = 54;
    public static final int REQ_CODE_POST_SLOT_DATE = 55;
    public static final int REQ_CODE_DELETE_NOTIFICATION = 56;
    public static final int REQ_CODE_GET_SCHEDULE_LIST = 57;
    public static final int REQ_CODE_TWILIO_OTP_SEND = 58;
    public static final int REQ_CODE_TWILIO_OTP_VERIFY = 59;
    public static final int REQ_CODE_GET_SAVE_DIST_AND_FEE = 60;
    public static final int REQ_CODE_GET_NOTIFICATION_COUNT = 61;
    public static final int REQ_CODE_GET_PRODUCTS = 62;
    private String userId = "";
    private String userName = "", fcmToken = "";
    private Context mContext;

    public NetworkHelper(Context context) {
        mContext = context;
        // userId = new SessionManager(context).getId();
        fcmToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "My token = " + FirebaseInstanceId.getInstance().getToken());
    }

    public JSONObject loginJson(String user_name, String password) {
        JSONObject object = new JSONObject();
        try {
            // object.putOpt(Constant.USER_NAME, user_name);
            object.putOpt(Constant.EMAIL, user_name);
            object.putOpt(Constant.PASSWORD, password);
            //  object.putOpt(Constant.DEVICE_ID, deviceId);
            //  object.putOpt(Constant.REGISTRATION_ID, fcmToken);
            //  object.putOpt(Constant.TYPE, "android");
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject loginWithMobileJson(String phone_no) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.PHONE_NUMBER, phone_no);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject signUpJson(String title, String email, String mobile, String abn, String website, String acn, String password, String confirm_pass, String address, String latitude, String longitude) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.TITLE, title);
            object.putOpt(Constant.EMAIL, email);
            object.putOpt(Constant.PHONE_NUMBER, mobile);
            object.putOpt(Constant.ABN, abn);
            object.putOpt(Constant.ACN, acn);
            object.putOpt(Constant.WEBSITE, website);
            object.putOpt(Constant.LOYAL, "12");
            object.putOpt(Constant.PASSWORD, password);
            object.putOpt(Constant.CONFIRM_PASSWORD, confirm_pass);
            //  object.putOpt(Constant.DEVICE_ID, deviceId);
            // object.putOpt(Constant.REGISTRATION_ID, fcmToken);
            //object.putOpt(Constant.TYPE, "android");
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(Constant.FULL_NAME, title);
            jsonObject.putOpt(Constant.ADDRESS, address);
            jsonObject.putOpt(Constant.LATITUDE, latitude);
            jsonObject.putOpt(Constant.LONGITUDE, longitude);
            object.putOpt(Constant.ADDRESS, jsonObject);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject twilioOtpJson(String mobileNumber, String countryCode) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.VIA, "sms");
            object.putOpt(Constant.PHONE_NUMBER, mobileNumber);
            object.putOpt(Constant.COUNTRY_CODE, countryCode);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject twilioOtpVerifyJson(String otp, String mobileNumber, String countryCode) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.PHONE_NUMBER, mobileNumber);
            object.putOpt(Constant.COUNTRY_CODE, countryCode);
            object.putOpt(Constant.VERIFICATION_CODE, otp);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject verifyUser() {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.TOKEN, new SessionManager(mContext).getToken());
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject otpJson(String otp, String mobile_no) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.OTP, otp);
            object.putOpt(Constant.TOKEN, new SessionManager(mContext).getToken());
            object.putOpt(Constant.PHONE_NUMBER, mobile_no);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject forgotPasswordJson(String emailid) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.EMAIL, emailid);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }


    public JSONObject resendOtpJson(String phone) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.PHONE_NUMBER, phone);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject resetPasswordJson(String email, String password, String otp) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.EMAIL, email);
            object.putOpt(Constant.PASSWORD, password);
            object.putOpt(Constant.OTP, otp);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }


    public JSONObject subscripationPlanJson(String stripe_id, String card_number, String exp_date, String exp_month, String cvc) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.STRIPE_PLAN_ID, stripe_id);
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOpt(Constant.NUMBER, card_number);
            jsonObject.putOpt(Constant.EXP_MONTH, exp_date);
            jsonObject.putOpt(Constant.EXP_YEAR, exp_month);
            jsonObject.putOpt(Constant.CVC, cvc);
            object.putOpt(Constant.CARD, jsonObject);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject updateProductSpecialJson(String title, String sortDes, String longDes, String price, String unitId, String stock, String category_id, String type, String quanity, String promotion, String promp_price) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.TITLE, title);
            object.putOpt(Constant.SHORT_DESCRIPATION, sortDes);
            object.putOpt(Constant.LONG_DESCRIPATION, longDes);
            object.putOpt(Constant.PRICE, price);
            object.putOpt(Constant.UNIT, unitId);
            object.putOpt(Constant.STOCK, stock);
            object.putOpt(Constant.CATEGORIES, category_id);
            object.putOpt(Constant.TYPE, type);
            object.putOpt(Constant.QUANTITY, quanity);
            object.putOpt(Constant.PROMOTION, promotion);
            object.putOpt(Constant.PROMOTION_PRICE, promp_price);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject updateProductSchedulelJson(String startDate, String endDate, String title, String sortDes, String longDes, String price, String unitId, String stock, String category_id, String quanity, String promotion, String promp_price) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.TITLE, title);
            object.putOpt(Constant.SHORT_DESCRIPATION, sortDes);
            object.putOpt(Constant.LONG_DESCRIPATION, longDes);
            object.putOpt(Constant.PRICE, price);
            object.putOpt(Constant.UNIT, unitId);
            object.putOpt(Constant.STOCK, stock);
            object.putOpt(Constant.CATEGORIES, category_id);
            object.putOpt(Constant.TYPE, "SCH");
            object.putOpt(Constant.START_DATE, startDate);
            object.putOpt(Constant.END_DATE, endDate);
            object.putOpt(Constant.QUANTITY, quanity);
            object.putOpt(Constant.PROMOTION, promotion);
            object.putOpt(Constant.PROMOTION_PRICE, promp_price);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject deleteIdsJson(List<Integer> product_ids) {
        Log.e("product_ids------->", String.valueOf(product_ids));
        JSONObject object = new JSONObject();
        try {
            JSONArray list = new JSONArray(product_ids);
            Log.e("Product_arr", String.valueOf(list));
            object.put(Constant.PRODUCT_ID_S, list);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject hideIdsJson(List<Integer> product_ids, String type) {
        Log.e("product_ids--------->", String.valueOf(product_ids));
        JSONObject object = new JSONObject();
        try {
            JSONArray list = new JSONArray(product_ids);
            Log.e("Product_arr", String.valueOf(list));
            object.put(Constant.PRODUCT_ID_S, list);
            object.put(Constant.TYPE, type);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }


    public JSONObject hideJson(String productId, String type) {
        JSONObject object = new JSONObject();
        try {
            object.put(Constant.Product_IDD, productId);
            object.put(Constant.TYPE, type);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject updateAddressJson(String full_name, String address, String mobile, String flatno, String landmark, String street, String address_type, String lat, String lng) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.FULL_NAME, full_name);
            object.putOpt(Constant.ADDRESS, address);
            object.putOpt(Constant.MOBILE, mobile);
            object.putOpt(Constant.FLAT_NUMBER, flatno);
            object.putOpt(Constant.LANDMARK, landmark);
            object.putOpt(Constant.STREET, street);
            object.putOpt(Constant.LATITUDE, lat);
            object.putOpt(Constant.LONGITUDE, lng);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject sendFcmJson(String deviceId) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.DEVICE_ID, deviceId);
            object.putOpt(Constant.REGISTRATION_ID, fcmToken);
            object.putOpt(Constant.TYPE, "android");
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject addCatgeoryJson(String cate_name, String vendor_id) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.SLUG, "slug");
            object.putOpt(Constant.NAME, cate_name);
            object.putOpt(Constant.VENDOR, vendor_id);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject changePasswordJson(String old_pass, String password, String conf_pass) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.OLD_PASSWORD, old_pass);
            object.putOpt(Constant.PASSWORD_1, password);
            object.putOpt(Constant.PASSWORD_2, conf_pass);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject selectAcceptJson(String status) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.STATUS_, status);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject getOrderRejectJson(String rejection_reason) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.STATUS_, "rejected");
            object.putOpt(Constant.REJECTION_REASON, rejection_reason);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject saveMiniOrderJson(String deliver_id, String order_value) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.DELIVERY_TYPES, deliver_id);
            object.putOpt(Constant.MINIMUM_ORDER_VALUE, order_value);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject updateDeliverTypeJson(ArrayList<DeliveryType> list) {
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray array = new JSONArray();
            for (DeliveryType deliveryType : list) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.putOpt(Constant.DELIVERY_TYPES, deliveryType.getId());
                jsonObject1.putOpt(Constant.ON, deliveryType.getIsActive());
                array.put(jsonObject1);
            }
            jsonObject.putOpt(Constant.DELIVERY_TYPE, array);
            Log.e(TAG, jsonObject.toString() + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject updateNotificationJson(String id, boolean is_sound, boolean is_vibration, boolean is_sms) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.ID, id);
            object.putOpt(Constant.IS_SOUND, is_sound);
            object.putOpt(Constant.IS_VIBRATION, is_vibration);
            object.putOpt(Constant.IS_SMS, is_sms);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject addDateJson(String date, String start_time, String end_time, String type) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.DATE, date);
            object.putOpt(Constant.START_TIME, start_time);
            object.putOpt(Constant.END_TIME, end_time);
            object.putOpt(Constant.TYPE, type);
            object.putOpt("delivery_type", type);

            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject dateJson(String date, String date_type) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.DATE, date);
            object.putOpt(Constant.DATE_TYPE, date_type);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject saveDistAndFeeJson(String delivery_id, ArrayList<DeliveryFeeModel> list) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(Constant.DELIVERY_TYPES, delivery_id);
            JSONArray array = new JSONArray();
            for (DeliveryFeeModel deliveryType : list) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.putOpt(Constant.DELIVERY_FEE, deliveryType.getDeliveryFee());
                jsonObject1.putOpt(Constant.DISTANCE, deliveryType.getDistance());
                array.put(jsonObject1);
            }
            jsonObject.putOpt(Constant.DISTANCE_DELIVERY_FEE, array);
            Log.e(TAG, jsonObject.toString() + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject addPromoJson(String promo_name, String promo_code, String start_date, String end_date, String amount, String no_of_use, boolean isPercentage) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.NAME, promo_name);
            object.putOpt(Constant.CODE, promo_code);
            object.putOpt(Constant.VALID_FORM, start_date);
            object.putOpt(Constant.VALID_TO, end_date);
            object.putOpt(Constant.VALUE, amount);
            object.putOpt(Constant.TIMES_USED, no_of_use);
            object.putOpt(Constant.IS_PERSENTANGE, isPercentage);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public JSONObject removeNotificationJson(String order_id) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt(Constant.ORDER_IDS, order_id);
            Log.e(TAG, object + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

}




