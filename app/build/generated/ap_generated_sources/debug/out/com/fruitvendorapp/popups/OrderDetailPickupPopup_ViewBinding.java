// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.popups;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.agik.AGIKSwipeButton.View.Swipe_Button_View;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderDetailPickupPopup_ViewBinding implements Unbinder {
  private OrderDetailPickupPopup target;

  @UiThread
  public OrderDetailPickupPopup_ViewBinding(OrderDetailPickupPopup target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OrderDetailPickupPopup_ViewBinding(OrderDetailPickupPopup target, View source) {
    this.target = target;

    target.tvOrderId = Utils.findRequiredViewAsType(source, R.id.tv_order_id, "field 'tvOrderId'", TextView.class);
    target.tvOrderPhone = Utils.findRequiredViewAsType(source, R.id.tv_order_phone, "field 'tvOrderPhone'", TextView.class);
    target.tvOrderEmail = Utils.findRequiredViewAsType(source, R.id.tv_order_email, "field 'tvOrderEmail'", TextView.class);
    target.tvOrder = Utils.findRequiredViewAsType(source, R.id.tv_order, "field 'tvOrder'", TextView.class);
    target.tvOrderDate = Utils.findRequiredViewAsType(source, R.id.tv_order_date, "field 'tvOrderDate'", TextView.class);
    target.tvOrderTotal = Utils.findRequiredViewAsType(source, R.id.tv_order_total, "field 'tvOrderTotal'", TextView.class);
    target.tvTotal = Utils.findRequiredViewAsType(source, R.id.tv_total_amount, "field 'tvTotal'", TextView.class);
    target.rvOrderProduct = Utils.findRequiredViewAsType(source, R.id.rv_order, "field 'rvOrderProduct'", RecyclerView.class);
    target.ivClose = Utils.findRequiredViewAsType(source, R.id.iv_close, "field 'ivClose'", ImageView.class);
    target.tvContact = Utils.findRequiredViewAsType(source, R.id.tv_contact, "field 'tvContact'", TextView.class);
    target.tvDowloadInvoice = Utils.findRequiredViewAsType(source, R.id.tv_download_invoice, "field 'tvDowloadInvoice'", TextView.class);
    target.btnChangeStatus = Utils.findRequiredViewAsType(source, R.id.btn_change_status, "field 'btnChangeStatus'", Button.class);
    target.tvAddress = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'tvAddress'", TextView.class);
    target.tvRejectReason = Utils.findRequiredViewAsType(source, R.id.tv_reject_reason, "field 'tvRejectReason'", TextView.class);
    target.tvRejectReasonHeader = Utils.findRequiredViewAsType(source, R.id.tv_reject_reason_header, "field 'tvRejectReasonHeader'", TextView.class);
    target.tvShopNm = Utils.findRequiredViewAsType(source, R.id.tv_shop_nm, "field 'tvShopNm'", TextView.class);
    target.tvCustName = Utils.findRequiredViewAsType(source, R.id.tv_cust_name, "field 'tvCustName'", TextView.class);
    target.tvShopAddress = Utils.findRequiredViewAsType(source, R.id.tv_shop_address, "field 'tvShopAddress'", TextView.class);
    target.tvShopphone = Utils.findRequiredViewAsType(source, R.id.tv_shop_phone, "field 'tvShopphone'", TextView.class);
    target.tvPaymentType = Utils.findRequiredViewAsType(source, R.id.tv_payemnt_type, "field 'tvPaymentType'", TextView.class);
    target.swipeReady = Utils.findRequiredViewAsType(source, R.id.sbtn_ready, "field 'swipeReady'", Swipe_Button_View.class);
    target.swipeConfirm = Utils.findRequiredViewAsType(source, R.id.sbtn_confirm, "field 'swipeConfirm'", Swipe_Button_View.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderDetailPickupPopup target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvOrderId = null;
    target.tvOrderPhone = null;
    target.tvOrderEmail = null;
    target.tvOrder = null;
    target.tvOrderDate = null;
    target.tvOrderTotal = null;
    target.tvTotal = null;
    target.rvOrderProduct = null;
    target.ivClose = null;
    target.tvContact = null;
    target.tvDowloadInvoice = null;
    target.btnChangeStatus = null;
    target.tvAddress = null;
    target.tvRejectReason = null;
    target.tvRejectReasonHeader = null;
    target.tvShopNm = null;
    target.tvCustName = null;
    target.tvShopAddress = null;
    target.tvShopphone = null;
    target.tvPaymentType = null;
    target.swipeReady = null;
    target.swipeConfirm = null;
  }
}
