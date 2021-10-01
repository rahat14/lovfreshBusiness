package com.fruitvendorapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.androidnetworking.error.ANError;
import com.fruitvendorapp.R;
import com.fruitvendorapp.fragments.HomeFragment;
import com.fruitvendorapp.fragments.MyProfileFragment;
import com.fruitvendorapp.fragments.ShopOnlineFragment;
import com.fruitvendorapp.fragments.SpecialFragment;
import com.fruitvendorapp.popups.OrderNotificationPopup;
import com.fruitvendorapp.server_networking.JsonArrayResponseListener;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.SessionManager;
import com.fruitvendorapp.utilities.Urls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener, JsonArrayResponseListener {
    private static final String TAG = DashboardActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 999;
    public static boolean isSearchSpecialVisible = false;
    public static boolean isSearchShopOnlineVisible = false;
    public static boolean isSearchHomeVisible = false;
    public static boolean isSpecialFilterVisible = false;
    public static boolean isShopOnlineFilterVisible = false;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.content_frame)
    FrameLayout contentFrame;
    @BindView(R.id.ll_home)
    LinearLayout llHomeView;
    @BindView(R.id.ll_offer)
    LinearLayout llOfferView;
    @BindView(R.id.ll_list)
    LinearLayout llProductView;
    @BindView(R.id.ll_carts)
    LinearLayout llMyCartView;
    @BindView(R.id.ll_profile)
    LinearLayout llMyProfile;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.iv_offer)
    ImageView ivOffer;
    @BindView(R.id.iv_carts)
    ImageView ivMyCart;
    @BindView(R.id.iv_profile)
    ImageView ivMyProfile;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_list)
    ImageView ivProduct;
    @BindView(R.id.rl_notification)
    RelativeLayout rlNotification;
    @BindView(R.id.iv_notification)
    ImageView ivNotification;
    @BindView(R.id.tv_notification_count)
    TextView tvNotification;
    @BindView(R.id.fab_upload_product)
    ImageButton ivUploadProduct;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.tv_specials)
    TextView tvSpecial;
    @BindView(R.id.tv_shop_online)
    TextView tvShopOnline;
    @BindView(R.id.tv_myaccount)
    TextView tvMyAccount;
    @BindView(R.id.iv_search_special)
    ImageView ivSearchSpecial;
    @BindView(R.id.iv_search_shopOn)
    ImageView ivSearchShopOnline;
    @BindView(R.id.iv_search_home)
    ImageView ivSearchHome;
    @BindView(R.id.iv_filter_special)
    ImageView ivFilterSpecial;
    @BindView(R.id.iv_filter_shop)
    ImageView ivFilterShopOnline;
    private String vendor_id = "", deviceid = "";
    private TelephonyManager mTelephonyManager;
    private SpecialFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing DashboardActivity");
        init();
    }

    private void init() {
        if (!TextUtils.isEmpty(vendor_id)) {
            vendor_id = new SessionManager(this).getVendorId();
            Log.e(TAG, "vendorId" + vendor_id);
        }

        if (getIntent().hasExtra(Constant.VENDOR_ID)) {
            vendor_id = getIntent().getStringExtra(Constant.VENDOR_ID);
            Log.e(TAG, "vendorId" + vendor_id);
            new SessionManager(this).setVendorId(vendor_id.toString());
        }

        if (getIntent().hasExtra(Constant.NOTIFICATION_ORDER_ID)) {
            String orderId = getIntent().getStringExtra(Constant.NOTIFICATION_ORDER_ID);
            Intent intent = new Intent(this, OrderNotificationPopup.class);
            intent.putExtra(Constant.ORDER_ID, orderId);
            startActivity(intent);
        }
        llHomeView.setOnClickListener(this);
        llOfferView.setOnClickListener(this);
        llMyCartView.setOnClickListener(this);
        llMyProfile.setOnClickListener(this);
        llProductView.setOnClickListener(this);
        ivFilterSpecial.setOnClickListener(this);
        ivFilterShopOnline.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivUploadProduct.setOnClickListener(this);
        rlNotification.setOnClickListener(this);
        ivSearchSpecial.setOnClickListener(this);
        ivSearchShopOnline.setOnClickListener(this);
        ivSearchHome.setOnClickListener(this);
        sentToHomeFragment();
        getNotificationApi();
        getDeviceId();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getNotificationApi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home:
                sentToHomeFragment();
                break;
            case R.id.ll_offer:
                sentToSpecialFragment();
                break;
            case R.id.ll_carts:
                sentToShopOnlineFragment();
                break;
            case R.id.ll_profile:
                sentToMyProfileFragment();
                break;
            case R.id.ll_list:
                sentToMyProductListFragment();
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.fab_upload_product:
                BaseUtility.sendActivityIntent(this, UploadProductActivity.class);
                break;
            case R.id.rl_notification:
                BaseUtility.sendActivityIntent(this, NotificationActivity.class);
                break;
            case R.id.iv_search_special:
                isSearchSpecialVisible = !isSearchSpecialVisible;
                sentToSpecialFragment();
                break;
            case R.id.iv_search_shopOn:
                isSearchShopOnlineVisible = !isSearchShopOnlineVisible;
                sentToShopOnlineFragment();
                break;
            case R.id.iv_search_home:
//                isSearchHomeVisible = !isSearchHomeVisible;
                vendor_id = new SessionManager(this).getVendorId();
                HomeFragment fragment = new HomeFragment();
                fragment.getVendorId(vendor_id);
                fragment.setUpSearchBar();
                if (isSearchHomeVisible) {
                    isSearchHomeVisible = false;
                    fragment.edAutoSearch.clearFocus();
                    BaseUtility.hideKeyboard(v, Objects.requireNonNull(getApplicationContext()));
                } else {
                    isSearchHomeVisible = true;
                    fragment.edAutoSearch.requestFocus();
                    BaseUtility.showSoftKeyboard(v, Objects.requireNonNull(getApplicationContext()));
                }
                break;
            case R.id.iv_filter_special:
                isSpecialFilterVisible = !isSpecialFilterVisible;
                sentToSpecialFragment();
                break;
            case R.id.iv_filter_shop:
                isShopOnlineFilterVisible = !isShopOnlineFilterVisible;
                sentToShopOnlineFragment();
                break;


        }
    }


    private void sentToShopOnlineFragment() {
        ivHome.setSelected(false);
        ivOffer.setSelected(false);
        ivMyCart.setSelected(true);
        ivMyProfile.setSelected(false);
        ivProduct.setSelected(false);
        tvToolbarTitle.setText(R.string.shop_online);
        tvHome.setSelected(false);
        tvShopOnline.setSelected(true);
        tvSpecial.setSelected(false);
        tvMyAccount.setSelected(false);
        ivSearchShopOnline.setVisibility(View.VISIBLE);
        ivFilterSpecial.setVisibility(View.GONE);
        ivFilterShopOnline.setVisibility(View.VISIBLE);
        ivSearchSpecial.setVisibility(View.GONE);
        ivSearchHome.setVisibility(View.GONE);
        ivUploadProduct.setVisibility(View.GONE);
        contentFrame.setVisibility(View.VISIBLE);
        ShopOnlineFragment fragment = new ShopOnlineFragment();
        fragment.getVendorId(vendor_id);
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        manager.replace(R.id.content_frame, fragment);
        manager.commit();

    }

    private void sentToSpecialFragment() {
        ivHome.setSelected(false);
        ivOffer.setSelected(true);
        ivMyCart.setSelected(false);
        ivMyProfile.setSelected(false);
        ivProduct.setSelected(false);
        tvToolbarTitle.setText(R.string.select_special);
        tvHome.setSelected(false);
        tvShopOnline.setSelected(false);
        tvSpecial.setSelected(true);
        tvMyAccount.setSelected(false);
        ivSearchShopOnline.setVisibility(View.GONE);
        ivSearchSpecial.setVisibility(View.VISIBLE);
        ivFilterSpecial.setVisibility(View.VISIBLE);
        ivFilterShopOnline.setVisibility(View.GONE);
        ivSearchHome.setVisibility(View.GONE);
        ivUploadProduct.setVisibility(View.GONE);
        contentFrame.setVisibility(View.VISIBLE);
        fragment = new SpecialFragment();
        fragment.getVendorId(vendor_id);
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        manager.replace(R.id.content_frame, fragment);
        manager.commit();
    }

    private void sentToHomeFragment() {
        ivHome.setSelected(true);
        ivOffer.setSelected(false);
        ivMyCart.setSelected(false);
        ivMyProfile.setSelected(false);
        ivProduct.setSelected(false);
        tvToolbarTitle.setText(R.string.home);
        tvHome.setSelected(true);
        tvShopOnline.setSelected(false);
        tvSpecial.setSelected(false);
        tvMyAccount.setSelected(false);
        ivSearchShopOnline.setVisibility(View.GONE);
        ivSearchSpecial.setVisibility(View.GONE);
        ivSearchHome.setVisibility(View.VISIBLE);
        ivFilterSpecial.setVisibility(View.GONE);
        ivFilterShopOnline.setVisibility(View.GONE);
        ivUploadProduct.setVisibility(View.VISIBLE);
        contentFrame.setVisibility(View.VISIBLE);
        HomeFragment fragment = new HomeFragment();
        fragment.getVendorId(vendor_id);
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        manager.replace(R.id.content_frame, fragment);
        manager.commit();
        /*Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (!(currentFragment instanceof HomeFragment)) {
            HomeFragment fragment = new HomeFragment();
            fragment.getVendorId(vendor_id);
            FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
            manager.replace(R.id.content_frame, fragment);
            manager.commit();
        }*/
    }


    private void sentToMyProfileFragment() {
        ivHome.setSelected(false);
        ivOffer.setSelected(false);
        ivMyCart.setSelected(false);
        ivMyProfile.setSelected(true);
        ivProduct.setSelected(false);
        tvToolbarTitle.setText(R.string.my_profile);
        tvHome.setSelected(false);
        tvShopOnline.setSelected(false);
        tvSpecial.setSelected(false);
        tvMyAccount.setSelected(true);
        ivSearchShopOnline.setVisibility(View.GONE);
        ivSearchSpecial.setVisibility(View.GONE);
        ivSearchHome.setVisibility(View.GONE);
        ivUploadProduct.setVisibility(View.GONE);
        ivFilterSpecial.setVisibility(View.GONE);
        ivFilterShopOnline.setVisibility(View.GONE);
        contentFrame.setVisibility(View.VISIBLE);
        MyProfileFragment fragment = new MyProfileFragment();
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        manager.replace(R.id.content_frame, fragment);
        manager.commit();
    }


    private void sentToMyProductListFragment() {
        ivHome.setSelected(false);
        ivOffer.setSelected(false);
        ivMyCart.setSelected(false);
        ivMyProfile.setSelected(false);
        ivProduct.setSelected(true);
        tvToolbarTitle.setText(R.string.product_list);
        contentFrame.setVisibility(View.VISIBLE);
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        manager.replace(R.id.content_frame, fragment);
        manager.commit();

    }

    private void getDeviceId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_REQUEST_READ_PHONE_STATE);
            } else {
                getDeviceImei();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDeviceImei();
        }
    }

    private void getDeviceImei() {
        try {
            mTelephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            deviceid = mTelephonyManager.getDeviceId();
            Log.e("msg", "DeviceImei " + deviceid);
            if (deviceid != null && !TextUtils.isEmpty(deviceid)) {
                getFcmApi();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getNotificationApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            Log.e(TAG, Urls.GET_NOTIFICATION_COUNT);
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_NOTIFICATION_COUNT, this, Urls.GET_NOTIFICATION_COUNT, this);

        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    private void getFcmApi() {
        if (ConnectionUtil.isInternetOn(this)) {
            RequestHelper.PostTokenRequest(NetworkHelper.REQ_CODE_SEND_FCM, this, Urls.FCM_URL, new NetworkHelper(this).sendFcmJson(deviceid), this);
        } else {
            BaseUtility.toastMsg(this, getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_SEND_FCM:
                Log.e(TAG, "fcm_api----------------->" + json.toString());
                break;

            case NetworkHelper.REQ_CODE_GET_NOTIFICATION_COUNT:
                Log.e(TAG, "REQ_CODE_GET_NOTIFICATION_COUNT --- >" + json.toString());
                try {
                    JSONObject responseObject = new JSONObject(json.toString());
                    if (responseObject.has("count")) {
                        int count = responseObject.optInt("count");
                        if (count == 0) {
                            tvNotification.setVisibility(View.GONE);
                        } else {
                            if (count > 99) {
                                tvNotification.setText("99+");
                            } else {
                                tvNotification.setText(String.valueOf(count));
                            }
                            tvNotification.setVisibility(View.VISIBLE);
                        }
                    } else {
                        tvNotification.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onSuccess(int requestCode, JSONArray json) {

    }

    @Override
    public void onError(ANError anError) {
    }
}