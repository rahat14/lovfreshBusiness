package com.fruitvendorapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.fruitvendorapp.R;
import com.fruitvendorapp.activities.HelpandSupportActivity;
import com.fruitvendorapp.activities.NotificationActivity;
import com.fruitvendorapp.activities.OrdersActivity;
import com.fruitvendorapp.activities.ProfileSettingActivity;
import com.fruitvendorapp.activities.ReferAFriendActivity;
import com.fruitvendorapp.activities.SettingActivity;
import com.fruitvendorapp.model.VendorModel;
import com.fruitvendorapp.server_networking.NetworkHelper;
import com.fruitvendorapp.server_networking.RequestHelper;
import com.fruitvendorapp.server_networking.ResponseListener;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.ConnectionUtil;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.SessionManager;
import com.fruitvendorapp.utilities.Urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileFragment extends Fragment implements View.OnClickListener, ResponseListener {
    private static final String TAG = MyProfileFragment.class.getSimpleName();
    private Context mContext;
    /*  @BindView(R.id.tv_address)
      TextView tvAddress;*/
    @BindView(R.id.tv_refer_friend)
    TextView tvReferFriend;
    @BindView(R.id.tv_notification)
    TextView tvNotification;
    @BindView(R.id.tv_my_profile)
    TextView tvMyProfile;
    @BindView(R.id.tv_orders)
    TextView tvOrders;
    @BindView(R.id.tv_help_and_support)
    TextView tvHelpSupport;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.tv_vendor_shop)
    TextView tvShopTitle;
    @BindView(R.id.tv_url)
    TextView tvUrl;
    @BindView(R.id.tv_arrow_order_comp)
    TextView tvArrowComplete;
    @BindView(R.id.tv_arrow_order_pend)
    TextView tvArrowPending;
    @BindView(R.id.cmv_profile)
    CircleImageView cmvShopImage;
    @BindView(R.id.ll_order_view)
    LinearLayout llOrderView;
    @BindView(R.id.ll_setting_view)
    LinearLayout llSettingView;
    @BindView(R.id.ll_help_view)
    LinearLayout llHelpView;
    @BindView(R.id.ll_refer_view)
    LinearLayout llReferView;
    @BindView(R.id.rl_pend_view)
    RelativeLayout rlPendingView;
    @BindView(R.id.rl_compl_view)
    RelativeLayout rlCompletedView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        tvNotification.setOnClickListener(this);
        tvMyProfile.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        llOrderView.setOnClickListener(this);
        llHelpView.setOnClickListener(this);
        llSettingView.setOnClickListener(this);
        llReferView.setOnClickListener(this);
        tvUrl.setOnClickListener(this);
        rlPendingView.setOnClickListener(this);
        rlCompletedView.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        getVendorApi();
        getOrderCountApi();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_refer_view:
                BaseUtility.sendActivityIntent(mContext, ReferAFriendActivity.class);
                break;
            case R.id.tv_notification:
                BaseUtility.sendActivityIntent(mContext, NotificationActivity.class);
                break;
            case R.id.tv_my_profile:
                BaseUtility.sendActivityIntent(mContext, ProfileSettingActivity.class);
                break;
            case R.id.ll_order_view:
                BaseUtility.sendActivityIntent(mContext, OrdersActivity.class);
                break;
            case R.id.tv_logout:
                new SessionManager(getActivity()).logout();
                break;
            case R.id.ll_help_view:
                BaseUtility.sendActivityIntent(mContext, HelpandSupportActivity.class);
                break;
            case R.id.ll_setting_view:
                BaseUtility.sendActivityIntent(mContext, SettingActivity.class);
                break;
            case R.id.rl_compl_view:
                intent = new Intent(mContext, OrdersActivity.class);
                intent.putExtra(Constant.INDEX, "2");
                startActivity(intent);
                break;
            case R.id.rl_pend_view:
                intent = new Intent(mContext, OrdersActivity.class);
                intent.putExtra(Constant.INDEX, "1");
                startActivity(intent);
                break;
            case R.id.tv_url:
                String url = "https://www.lovfresh.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                //   BaseUtility.sendActivityIntent(mContext, ShowWebUrlActivity.class);
                break;
        }
    }

    public void getVendorApi() {
        if (ConnectionUtil.isInternetOn(mContext)) {
            Log.e(TAG, Urls.SIGNUP_URL + new SessionManager(mContext).getVendorId());
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_VENDOR_DETAIL, getActivity(), Urls.SIGNUP_URL + new SessionManager(mContext).getVendorId(), this);
        } else {
            BaseUtility.toastMsg(mContext, getString(R.string.no_internet_connection));
        }
    }

    public void getOrderCountApi() {
        if (ConnectionUtil.isInternetOn(mContext)) {
            Log.e(TAG, Urls.ORDER_COUNT_URL);
            RequestHelper.getRequestWithToken(NetworkHelper.REQ_CODE_GET_ORDER_COUNT, getActivity(), Urls.ORDER_COUNT_URL, this);
        } else {
            BaseUtility.toastMsg(mContext, getString(R.string.no_internet_connection));
        }
    }


    @Override
    public void onSuccess(int requestCode, JSONObject json) {
        switch (requestCode) {
            case NetworkHelper.REQ_CODE_GET_VENDOR_DETAIL:
                Log.e(TAG, json.toString());
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                VendorModel vendorModel = gson.fromJson(String.valueOf(json), VendorModel.class);
                if (vendorModel != null) {
                    tvShopTitle.setText(vendorModel.getTitle());
                    if (!TextUtils.isEmpty(vendorModel.getImageUrl())) {
                        Glide.with(mContext).load(Urls.IMAGE_URL + vendorModel.getImageUrl()).placeholder(R.drawable.ic_fruit_img).error(R.drawable.ic_fruit_img).into(cmvShopImage);
                    } else {
                    }
                } else {

                }
                break;
            case NetworkHelper.REQ_CODE_GET_ORDER_COUNT:
                Log.e(TAG, json.toString());
                String comp_order_count = json.optString(Constant.NUMBER_COMPLETE_ORDER);
                String pend_order_count = json.optString(Constant.NUMBER_PENDING_ORDER);
                if (!TextUtils.isEmpty(comp_order_count)) {
                    tvArrowComplete.setText(comp_order_count);
                }
                if (!TextUtils.isEmpty(pend_order_count)) {
                    tvArrowPending.setText(pend_order_count);
                }
                break;
        }
    }

    @Override
    public void onError(ANError anError) {
        //progressDialogUtil.dismissDialog();
    }


}