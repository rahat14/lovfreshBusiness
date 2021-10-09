// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OTPVerifyActivity_ViewBinding implements Unbinder {
  private OTPVerifyActivity target;

  @UiThread
  public OTPVerifyActivity_ViewBinding(OTPVerifyActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OTPVerifyActivity_ViewBinding(OTPVerifyActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.tvResend = Utils.findRequiredViewAsType(source, R.id.tv_resend, "field 'tvResend'", TextView.class);
    target.edOtp = Utils.findRequiredViewAsType(source, R.id.ed_otp, "field 'edOtp'", AppCompatEditText.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.btnSubmit = Utils.findRequiredViewAsType(source, R.id.btn_submit, "field 'btnSubmit'", Button.class);
    target.llLayout = Utils.findRequiredViewAsType(source, R.id.ll_layout, "field 'llLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OTPVerifyActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.tvResend = null;
    target.edOtp = null;
    target.ivBack = null;
    target.btnSubmit = null;
    target.llLayout = null;
  }
}
