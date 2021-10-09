// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import com.hbb20.CountryCodePicker;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.btnLogin = Utils.findRequiredViewAsType(source, R.id.btn_login, "field 'btnLogin'", Button.class);
    target.btnSignup = Utils.findRequiredViewAsType(source, R.id.btn_signup, "field 'btnSignup'", TextView.class);
    target.btnChangeLogin = Utils.findRequiredViewAsType(source, R.id.btn_change_login, "field 'btnChangeLogin'", Button.class);
    target.edUserName = Utils.findRequiredViewAsType(source, R.id.ed_email, "field 'edUserName'", AppCompatEditText.class);
    target.edPassword = Utils.findRequiredViewAsType(source, R.id.ed_password, "field 'edPassword'", AppCompatEditText.class);
    target.edMobile = Utils.findRequiredViewAsType(source, R.id.ed_mobile, "field 'edMobile'", AppCompatEditText.class);
    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.tvForgotPassword = Utils.findRequiredViewAsType(source, R.id.tv_forgot_pass, "field 'tvForgotPassword'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.ccp = Utils.findRequiredViewAsType(source, R.id.ccp, "field 'ccp'", CountryCodePicker.class);
    target.rvMobileView = Utils.findRequiredViewAsType(source, R.id.rv_mobile, "field 'rvMobileView'", RelativeLayout.class);
    target.llLayout = Utils.findRequiredViewAsType(source, R.id.ll_layout, "field 'llLayout'", LinearLayout.class);
    target.llGmailLogin = Utils.findRequiredViewAsType(source, R.id.ll_gmail_login, "field 'llGmailLogin'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnLogin = null;
    target.btnSignup = null;
    target.btnChangeLogin = null;
    target.edUserName = null;
    target.edPassword = null;
    target.edMobile = null;
    target.tvToolbarTitle = null;
    target.tvForgotPassword = null;
    target.ivBack = null;
    target.ccp = null;
    target.rvMobileView = null;
    target.llLayout = null;
    target.llGmailLogin = null;
  }
}
