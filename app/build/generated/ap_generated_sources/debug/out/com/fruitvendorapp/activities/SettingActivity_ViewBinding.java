// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingActivity_ViewBinding implements Unbinder {
  private SettingActivity target;

  @UiThread
  public SettingActivity_ViewBinding(SettingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SettingActivity_ViewBinding(SettingActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.tvOtherS = Utils.findRequiredViewAsType(source, R.id.tv_other_s, "field 'tvOtherS'", TextView.class);
    target.tvBuildType = Utils.findRequiredViewAsType(source, R.id.tv_build_type, "field 'tvBuildType'", TextView.class);
    target.tvTermCondi = Utils.findRequiredViewAsType(source, R.id.tv_term_condition, "field 'tvTermCondi'", TextView.class);
    target.tvNotificationSetting = Utils.findRequiredViewAsType(source, R.id.tv_notification_s, "field 'tvNotificationSetting'", TextView.class);
    target.tvPromoCode = Utils.findRequiredViewAsType(source, R.id.tv_promo_code, "field 'tvPromoCode'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SettingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.tvOtherS = null;
    target.tvBuildType = null;
    target.tvTermCondi = null;
    target.tvNotificationSetting = null;
    target.tvPromoCode = null;
    target.ivBack = null;
  }
}
