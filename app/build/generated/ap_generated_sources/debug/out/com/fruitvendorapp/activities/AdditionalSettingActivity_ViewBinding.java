// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

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

public class AdditionalSettingActivity_ViewBinding implements Unbinder {
  private AdditionalSettingActivity target;

  @UiThread
  public AdditionalSettingActivity_ViewBinding(AdditionalSettingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AdditionalSettingActivity_ViewBinding(AdditionalSettingActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.llDeliverySettingView = Utils.findRequiredViewAsType(source, R.id.ll_delivery_setting, "field 'llDeliverySettingView'", LinearLayout.class);
    target.llPickupSettingView = Utils.findRequiredViewAsType(source, R.id.ll_pickup_setting, "field 'llPickupSettingView'", LinearLayout.class);
    target.tvDeliMiniOrder = Utils.findRequiredViewAsType(source, R.id.tv_deli_mini_order, "field 'tvDeliMiniOrder'", TextView.class);
    target.tvPickMiniOrder = Utils.findRequiredViewAsType(source, R.id.tv_pic_mini_order, "field 'tvPickMiniOrder'", TextView.class);
    target.tvDeliveryDist = Utils.findRequiredViewAsType(source, R.id.tv_dist, "field 'tvDeliveryDist'", TextView.class);
    target.tvDeliSlot = Utils.findRequiredViewAsType(source, R.id.tv_deli_slot, "field 'tvDeliSlot'", TextView.class);
    target.tvPickupSlot = Utils.findRequiredViewAsType(source, R.id.tv_pic_slot, "field 'tvPickupSlot'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AdditionalSettingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.ivBack = null;
    target.llDeliverySettingView = null;
    target.llPickupSettingView = null;
    target.tvDeliMiniOrder = null;
    target.tvPickMiniOrder = null;
    target.tvDeliveryDist = null;
    target.tvDeliSlot = null;
    target.tvPickupSlot = null;
  }
}
