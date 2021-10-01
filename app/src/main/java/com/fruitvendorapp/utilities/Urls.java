package com.fruitvendorapp.utilities;

public class Urls {

    public static final String BASE_URL = "http://13.55.122.237/api/";
    public static final String IMAGE_URL = "http://13.55.122.237";
    //    public static final String BASE_URL = "http://148.66.131.15/api/";
//    public static final String IMAGE_URL = "http://148.66.131.15";
    public static final String TWILIO_API = "https://api.authy.com/";

    //  public static final String BASE_URL = "https://0ad2a17f8fb3.ngrok.io/api/";
    //  public static final String IMAGE_URL = "https://0ad2a17f8fb3.ngrok.io";

    public static final String LOGIN_EMAIL_URL = BASE_URL + "authentication/login-app";
    public static final String LOGIN_MOBILE_URL = BASE_URL + "authentication/login-phone-number";
    public static final String SIGNUP_URL = BASE_URL + "vendors/";
    public static final String GET_VENDOR = BASE_URL + "v2/vendors/";
    public static final String GET_PRODUCTS = BASE_URL + "product_categories/";
    public static final String VERIFY_URL = BASE_URL + "authentication/verify";
    public static final String FORGOT_PASS_URL = BASE_URL + "authentication/forgot-password";
    public static final String RESET_PASSWORD_URL = BASE_URL + "authentication/reset-password";
    public static final String RESEND_OTP_URL = BASE_URL + "authentication/resend-otp";
    public static final String PLAN_URL = BASE_URL + "plans/";
    public static final String SUBSCRIPATION_URL = BASE_URL + "vendor/subscription/";
    public static final String CATEGORY_LIST_URL = BASE_URL + "vendor_categories/";
    public static final String UNIT_LIST_URL = BASE_URL + "product_units/";
    public static final String PRODUCT_UPLOAD_URL = BASE_URL + "product_create/";
    public static final String PRODUCT_LIST_URL = BASE_URL + "products/?vendor=";
    public static final String PRODUCT_DETAIL_URL = BASE_URL + "products/";
    public static final String PRODUCT_HIDE_URL = BASE_URL + "product/hide/";
    public static final String VENDOR_BY_CATEGORY_URL = BASE_URL + "vendor/search-list/?id=";
    public static final String PRODUCT_FILTER_URL = BASE_URL + "products/?";
    public static final String PRODUCT_MULITPLE_DELETE_URL = BASE_URL + "product/multiple_delete/";
    public static final String PRODUCT_MULITPLE_HIDE_URL = BASE_URL + "product/multiple_hide_unhide/";
    public static final String PRODUCT_SINGLE_HIDE_URL = BASE_URL + "product/hide_unhide/";
    public static final String ORDER_URL = BASE_URL + "orders/";
    public static final String GET_ADDRESS_URL = BASE_URL + "address/";
    public static final String FCM_URL = BASE_URL + "fcmdevices/";
    public static final String VENDOR_SHOP_PROFILE_URL = BASE_URL + "vendor/profile/";
    public static final String CHANGE_PASSWORD_URL = BASE_URL + "authentication/update-password";
    public static final String CLIENT_TOKEN = "paypal-checkout/";
    public static final String NONCE_FROM_THE_CLIENT_URL = "";
    public static final String GET_TRAINING_URL = BASE_URL + "training-video/";
    public static final String ORDER_COUNT_URL = BASE_URL + "vendor/order-count/";
    public static final String DELIVER_TYPE_URL = BASE_URL + "vendor/delivery-type/";
    public static final String DELIVER_TYPE_UPDATE_URL = BASE_URL + "vendor/vendor-delivery-type/";
    public static final String GET_NOTIFICATION_COUNT = BASE_URL + "orders/notification-count";
    public static final String NOTIFICATION_URL = BASE_URL + "orders/notification-list";
    public static final String SHOP_ONLINE_URL = BASE_URL + "vendor/product-user-filter/?vendor=";
    public static final String SAVE_NOTIFICATION_URL = BASE_URL + "vendor/notification-settings-update/";
    public static final String CALL_SUPPORT_URL = BASE_URL + "lovfresh/support";
    public static final String REFER_FRIEND_URL = BASE_URL + "lovfresh/referfriend";
    public static final String SAVE_MINI_ORDER_VALUE = BASE_URL + "vendor/vendor-minimum-order-value/";
    public static final String DISTANCE_FEE_URL = BASE_URL + "vendor/vendor-distance-fee/";
    public static final String PROMO_CODE_URL = BASE_URL + "coupons/";
    public static final String PROMO_CODE_REMOVE_URL = BASE_URL + "coupons/remove/";
    public static final String IMAGE_UPDATE_URL = BASE_URL + "product/image-update/";
    public static final String NOTIFICATION_REMOVE_URL = BASE_URL + "orders/notification-remove";
    public static final String GET_SLOT_SETTING_URL = BASE_URL + "vendor/slot-setting/";
    public static final String SEND_TWILIO_OTP_URL = TWILIO_API + "protected/json/phones/verification/start";
    public static final String VERIFY_TWILIO_OTP_URL = TWILIO_API + "protected/json/phones/verification/check";
}
