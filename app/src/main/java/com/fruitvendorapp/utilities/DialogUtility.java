package com.fruitvendorapp.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.DistanceFeeAdapter;
import com.fruitvendorapp.adapters.TimeSlotSelectionAdapter;
import com.fruitvendorapp.interfaces.ItemDeleteInterface;
import com.fruitvendorapp.interfaces.ItemMiniOrderInterface;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.model.DeliveryFeeModel;
import com.fruitvendorapp.model.DistanceFeeModel;

import java.util.ArrayList;

public class DialogUtility {

    public static void deleteProductAlert(Context context, ItemDeleteInterface itemDeleteInterface) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogCustom);
        builder.setMessage("Are you sure you want to delete this Product?");
        builder.setCancelable(true);
        builder.setPositiveButton(context.getText(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        itemDeleteInterface.itemDelete();

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

    public static void updateConfirBackAlert(Activity activity) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View dialogview = inflater.inflate(R.layout.dialog_alert_update_confimation, null);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(activity);
        dialogbuilder.setView(dialogview);
        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView btnCancel = dialogview.findViewById(R.id.btn_cancel);
        TextView btnOk = dialogview.findViewById(R.id.btn_ok);
        btnCancel.setOnClickListener(v -> {
            activity.onBackPressed();
        });

        btnOk.setOnClickListener(v -> {
            alertDialog.dismiss();
        });


        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public static void alertLimitDialog(Context context, ItemSelectInterface itemSelectInterface) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogview = inflater.inflate(R.layout.dialog_alert_limit, null);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
        dialogbuilder.setView(dialogview);
        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnCancel = dialogview.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }


    public static void dialogAddCategory(Context context) {
        // custom dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogview = inflater.inflate(R.layout.dialog_add_category_view, null);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
        dialogbuilder.setView(dialogview);
        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnSubmit = dialogview.findViewById(R.id.btn_submit);
        Button btnCancel = dialogview.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(v -> alertDialog.dismiss());
        btnSubmit.setOnClickListener(v -> {

        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public static void dialogMinimumOrder(Context context, ItemMiniOrderInterface itemMiniOrderInterface, boolean isDeliver, boolean isOrderValue, String minimumOrderValue) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogview = inflater.inflate(R.layout.dialog_minimum_order, null);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
        dialogbuilder.setView(dialogview);
        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnSubmit = dialogview.findViewById(R.id.btn_submit);
        ImageView ivClose = dialogview.findViewById(R.id.iv_close);
        AppCompatEditText edMiniValue = dialogview.findViewById(R.id.ed_minimum_value);
        if (minimumOrderValue != null) {
            edMiniValue.setText(minimumOrderValue);
        }
        ivClose.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        btnSubmit.setOnClickListener(v -> {
            String order_mini_val = edMiniValue.getText().toString();
            if (!TextUtils.isEmpty(order_mini_val)) {
                itemMiniOrderInterface.itemMiniOrder("", order_mini_val, isDeliver, isOrderValue);
                alertDialog.dismiss();
            } else {
                BaseUtility.toastMsg(context, "Please add Order minimum value");
            }

        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public static void dialogDistanceDeliveryfee(Context context, ItemMiniOrderInterface itemMiniOrderInterface, boolean isDistance, ArrayList<DistanceFeeModel> distanceList) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogview = inflater.inflate(R.layout.dialog_distance_delivery_fee, null);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
        dialogbuilder.setView(dialogview);
        final AlertDialog alertDialog = dialogbuilder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btnSave = dialogview.findViewById(R.id.btn_save);
        RelativeLayout rlDistanceView = dialogview.findViewById(R.id.rl_dist_view);
        ImageView ivClose = dialogview.findViewById(R.id.iv_close);
        RecyclerView distance_rv = dialogview.findViewById(R.id.distance_rv);
        TextView tv_add = dialogview.findViewById(R.id.tv_add);
        AppCompatEditText eddistanse = dialogview.findViewById(R.id.ed_dist);
        AppCompatEditText edFee = dialogview.findViewById(R.id.ed_fee);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        distance_rv.setLayoutManager(linearLayoutManager);
        DistanceFeeAdapter distanceFeeAdapter = new DistanceFeeAdapter(context);
        distanceFeeAdapter.setData(distanceList);
        distance_rv.setAdapter(distanceFeeAdapter);

        tv_add.setOnClickListener(v -> {
            if (rlDistanceView.getVisibility() == View.GONE) {
                rlDistanceView.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.VISIBLE);
            } else {
                rlDistanceView.setVisibility(View.GONE);
                btnSave.setVisibility(View.GONE);
            }
        });
        ivClose.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        btnSave.setOnClickListener(v -> {
            String dist_delivery = eddistanse.getText().toString();
            String fee = edFee.getText().toString();
            if (TextUtils.isEmpty(dist_delivery)) {
                BaseUtility.toastMsg(context, "Please add distance");
            }
            if (TextUtils.isEmpty(fee)) {
                BaseUtility.toastMsg(context, "Please add fee");
            } else {
                itemMiniOrderInterface.itemMiniOrder(fee, dist_delivery, false, false);
                alertDialog.dismiss();
            }

        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public static void dialogTermAlert(Context context, CheckBox checkBox) {
        // custom dialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogview = inflater.inflate(R.layout.dialog_term_condition, null);
        AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(context);
        dialogbuilder.setView(dialogview);
        final AlertDialog alertDialog = dialogbuilder.create();
        ImageView iv_close = dialogview.findViewById(R.id.iv_close);
        WebView wv_terms_and_conditions = (WebView) dialogview.findViewById(R.id.wv_terms_and_conditions);
        Button btnAgree = dialogview.findViewById(R.id.btn_agree);
        Button btnDisAgree = dialogview.findViewById(R.id.btn_disagree);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        wv_terms_and_conditions.getSettings().setJavaScriptEnabled(true);
        wv_terms_and_conditions.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        wv_terms_and_conditions.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv_terms_and_conditions.loadUrl("file:///android_asset/terms_of_use.html");
        wv_terms_and_conditions.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                try {
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                try {
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }


            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
       /* btnDisAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setChecked(false);
                alertDialog.dismiss();
            }
        });*/
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }


}
