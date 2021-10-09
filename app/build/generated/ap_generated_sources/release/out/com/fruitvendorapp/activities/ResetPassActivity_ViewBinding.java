// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ResetPassActivity_ViewBinding implements Unbinder {
  private ResetPassActivity target;

  @UiThread
  public ResetPassActivity_ViewBinding(ResetPassActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ResetPassActivity_ViewBinding(ResetPassActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.btnSubmit = Utils.findRequiredViewAsType(source, R.id.btn_submit, "field 'btnSubmit'", Button.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.edOTP = Utils.findRequiredViewAsType(source, R.id.ed_otp, "field 'edOTP'", EditText.class);
    target.edPassword = Utils.findRequiredViewAsType(source, R.id.ed_password, "field 'edPassword'", EditText.class);
    target.edConfPassword = Utils.findRequiredViewAsType(source, R.id.ed_conf_password, "field 'edConfPassword'", EditText.class);
    target.tvResend = Utils.findRequiredViewAsType(source, R.id.tv_resend, "field 'tvResend'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ResetPassActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.btnSubmit = null;
    target.ivBack = null;
    target.edOTP = null;
    target.edPassword = null;
    target.edConfPassword = null;
    target.tvResend = null;
  }
}
