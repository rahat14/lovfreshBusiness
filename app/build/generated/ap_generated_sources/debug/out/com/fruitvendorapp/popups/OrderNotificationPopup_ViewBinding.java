// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.popups;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderNotificationPopup_ViewBinding implements Unbinder {
  private OrderNotificationPopup target;

  @UiThread
  public OrderNotificationPopup_ViewBinding(OrderNotificationPopup target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OrderNotificationPopup_ViewBinding(OrderNotificationPopup target, View source) {
    this.target = target;

    target.tvOrderId = Utils.findRequiredViewAsType(source, R.id.tv_order_id, "field 'tvOrderId'", TextView.class);
    target.tvOrderDate = Utils.findRequiredViewAsType(source, R.id.tv_order_date, "field 'tvOrderDate'", TextView.class);
    target.tvOrderTotal = Utils.findRequiredViewAsType(source, R.id.tv_order_total, "field 'tvOrderTotal'", TextView.class);
    target.tvCustName = Utils.findRequiredViewAsType(source, R.id.tv_cust_name, "field 'tvCustName'", TextView.class);
    target.tvOrderPhone = Utils.findRequiredViewAsType(source, R.id.tv_phone_no, "field 'tvOrderPhone'", TextView.class);
    target.tvOrderEmail = Utils.findRequiredViewAsType(source, R.id.tv_email_id, "field 'tvOrderEmail'", TextView.class);
    target.tvAddress = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'tvAddress'", TextView.class);
    target.tvTotal = Utils.findRequiredViewAsType(source, R.id.tv_total_amount, "field 'tvTotal'", TextView.class);
    target.rvOrderProduct = Utils.findRequiredViewAsType(source, R.id.rv_order, "field 'rvOrderProduct'", RecyclerView.class);
    target.tvAccept = Utils.findRequiredViewAsType(source, R.id.btn_accept, "field 'tvAccept'", TextView.class);
    target.tvReject = Utils.findRequiredViewAsType(source, R.id.btn_reject, "field 'tvReject'", TextView.class);
    target.tvOrderType = Utils.findRequiredViewAsType(source, R.id.tv_order_type, "field 'tvOrderType'", TextView.class);
    target.tvOrderStatus = Utils.findRequiredViewAsType(source, R.id.tv_order_status, "field 'tvOrderStatus'", TextView.class);
    target.tvOrderSlot = Utils.findRequiredViewAsType(source, R.id.tv_pickup_slot, "field 'tvOrderSlot'", TextView.class);
    target.tvShowAccepted = Utils.findRequiredViewAsType(source, R.id.tv_show_accept, "field 'tvShowAccepted'", TextView.class);
    target.llBtnView = Utils.findRequiredViewAsType(source, R.id.ll_btn_view, "field 'llBtnView'", LinearLayout.class);
    target.btnCustCall = Utils.findRequiredViewAsType(source, R.id.btn_cust_call, "field 'btnCustCall'", Button.class);
    target.tvDeliveryDate = Utils.findRequiredViewAsType(source, R.id.tv_ship_date, "field 'tvDeliveryDate'", TextView.class);
    target.tvDeliveryTime = Utils.findRequiredViewAsType(source, R.id.tv_pickup_time, "field 'tvDeliveryTime'", TextView.class);
    target.tvDistanceFeeAmount = Utils.findRequiredViewAsType(source, R.id.tv_distance_fee_amount, "field 'tvDistanceFeeAmount'", TextView.class);
    target.ivClose = Utils.findRequiredViewAsType(source, R.id.iv_close, "field 'ivClose'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderNotificationPopup target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvOrderId = null;
    target.tvOrderDate = null;
    target.tvOrderTotal = null;
    target.tvCustName = null;
    target.tvOrderPhone = null;
    target.tvOrderEmail = null;
    target.tvAddress = null;
    target.tvTotal = null;
    target.rvOrderProduct = null;
    target.tvAccept = null;
    target.tvReject = null;
    target.tvOrderType = null;
    target.tvOrderStatus = null;
    target.tvOrderSlot = null;
    target.tvShowAccepted = null;
    target.llBtnView = null;
    target.btnCustCall = null;
    target.tvDeliveryDate = null;
    target.tvDeliveryTime = null;
    target.tvDistanceFeeAmount = null;
    target.ivClose = null;
  }
}
