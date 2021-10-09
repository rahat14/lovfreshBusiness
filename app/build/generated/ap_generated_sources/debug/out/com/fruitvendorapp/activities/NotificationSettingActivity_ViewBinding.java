// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.SwitchCompat;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NotificationSettingActivity_ViewBinding implements Unbinder {
  private NotificationSettingActivity target;

  @UiThread
  public NotificationSettingActivity_ViewBinding(NotificationSettingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NotificationSettingActivity_ViewBinding(NotificationSettingActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.btnSave = Utils.findRequiredViewAsType(source, R.id.btn_save, "field 'btnSave'", Button.class);
    target.scSound = Utils.findRequiredViewAsType(source, R.id.sc_sound, "field 'scSound'", SwitchCompat.class);
    target.scVibrate = Utils.findRequiredViewAsType(source, R.id.sc_vibrate, "field 'scVibrate'", SwitchCompat.class);
    target.scSms = Utils.findRequiredViewAsType(source, R.id.sc_sms, "field 'scSms'", SwitchCompat.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NotificationSettingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.ivBack = null;
    target.btnSave = null;
    target.scSound = null;
    target.scVibrate = null;
    target.scSms = null;
  }
}
