// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NotificationAdapter$ViewHolder_ViewBinding implements Unbinder {
  private NotificationAdapter.ViewHolder target;

  @UiThread
  public NotificationAdapter$ViewHolder_ViewBinding(NotificationAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvAddress = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'tvAddress'", TextView.class);
    target.tvShopTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvShopTitle'", TextView.class);
    target.riShopImage = Utils.findRequiredViewAsType(source, R.id.ri_shop_img, "field 'riShopImage'", CircleImageView.class);
    target.rlMainView = Utils.findRequiredViewAsType(source, R.id.frame_view, "field 'rlMainView'", RelativeLayout.class);
    target.ivNotification = Utils.findRequiredViewAsType(source, R.id.iv_noti, "field 'ivNotification'", ImageView.class);
    target.tvOrderStatus = Utils.findRequiredViewAsType(source, R.id.tv_order_status, "field 'tvOrderStatus'", TextView.class);
    target.tvOrderId = Utils.findRequiredViewAsType(source, R.id.tv_order_id, "field 'tvOrderId'", TextView.class);
    target.tvOrderType = Utils.findRequiredViewAsType(source, R.id.tv_order_type, "field 'tvOrderType'", TextView.class);
    target.tvOrderDates = Utils.findRequiredViewAsType(source, R.id.tv_orderdate, "field 'tvOrderDates'", TextView.class);
    target.tvSlotSelected = Utils.findRequiredViewAsType(source, R.id.tv_pickup_slot, "field 'tvSlotSelected'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NotificationAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvAddress = null;
    target.tvShopTitle = null;
    target.riShopImage = null;
    target.rlMainView = null;
    target.ivNotification = null;
    target.tvOrderStatus = null;
    target.tvOrderId = null;
    target.tvOrderType = null;
    target.tvOrderDates = null;
    target.tvSlotSelected = null;
  }
}
