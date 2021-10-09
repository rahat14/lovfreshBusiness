// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private OrderListAdapter.ViewHolder target;

  @UiThread
  public OrderListAdapter$ViewHolder_ViewBinding(OrderListAdapter.ViewHolder target, View source) {
    this.target = target;

    target.tvOrderId = Utils.findRequiredViewAsType(source, R.id.order_id, "field 'tvOrderId'", TextView.class);
    target.tvOrderDate = Utils.findRequiredViewAsType(source, R.id.order_date, "field 'tvOrderDate'", TextView.class);
    target.tvOrderStatus = Utils.findRequiredViewAsType(source, R.id.order_status, "field 'tvOrderStatus'", TextView.class);
    target.tvOrderComplete = Utils.findRequiredViewAsType(source, R.id.tv_comp_order_date, "field 'tvOrderComplete'", TextView.class);
    target.ivDetail = Utils.findRequiredViewAsType(source, R.id.iv_detail, "field 'ivDetail'", ImageView.class);
    target.llView = Utils.findRequiredViewAsType(source, R.id.ll_view, "field 'llView'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvOrderId = null;
    target.tvOrderDate = null;
    target.tvOrderStatus = null;
    target.tvOrderComplete = null;
    target.ivDetail = null;
    target.llView = null;
  }
}
