// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class SignUpActivity_ViewBinding implements Unbinder {
  private SignUpActivity target;

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.edName = Utils.findRequiredViewAsType(source, R.id.ed_name, "field 'edName'", AppCompatEditText.class);
    target.edEmail = Utils.findRequiredViewAsType(source, R.id.ed_email, "field 'edEmail'", AppCompatEditText.class);
    target.edPhone = Utils.findRequiredViewAsType(source, R.id.ed_phone, "field 'edPhone'", AppCompatEditText.class);
    target.edAddress = Utils.findRequiredViewAsType(source, R.id.ed_address, "field 'edAddress'", AppCompatEditText.class);
    target.edLoyalNo = Utils.findRequiredViewAsType(source, R.id.ed_loyal_no, "field 'edLoyalNo'", AppCompatEditText.class);
    target.edPasswrd = Utils.findRequiredViewAsType(source, R.id.ed_password, "field 'edPasswrd'", AppCompatEditText.class);
    target.edConfPassword = Utils.findRequiredViewAsType(source, R.id.ed_conf_pass, "field 'edConfPassword'", AppCompatEditText.class);
    target.edWebSite = Utils.findRequiredViewAsType(source, R.id.ed_website, "field 'edWebSite'", AppCompatEditText.class);
    target.edAbn = Utils.findRequiredViewAsType(source, R.id.ed_abn, "field 'edAbn'", AppCompatEditText.class);
    target.edACN = Utils.findRequiredViewAsType(source, R.id.ed_acn, "field 'edACN'", AppCompatEditText.class);
    target.ccp = Utils.findRequiredViewAsType(source, R.id.ccp, "field 'ccp'", CountryCodePicker.class);
    target.btnSignUp = Utils.findRequiredViewAsType(source, R.id.btn_signup, "field 'btnSignUp'", Button.class);
    target.tvLogin = Utils.findRequiredViewAsType(source, R.id.tv_already_account, "field 'tvLogin'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.cbTerms = Utils.findRequiredViewAsType(source, R.id.cb_term_condi, "field 'cbTerms'", CheckBox.class);
    target.tvTermCond = Utils.findRequiredViewAsType(source, R.id.tv_term_cond, "field 'tvTermCond'", TextView.class);
    target.llLayout = Utils.findRequiredViewAsType(source, R.id.ll_layout, "field 'llLayout'", LinearLayout.class);
    target.ivShowPass = Utils.findRequiredViewAsType(source, R.id.show_pass_btn, "field 'ivShowPass'", ImageView.class);
    target.ivShowPassConf = Utils.findRequiredViewAsType(source, R.id.show_pass_conf, "field 'ivShowPassConf'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SignUpActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.edName = null;
    target.edEmail = null;
    target.edPhone = null;
    target.edAddress = null;
    target.edLoyalNo = null;
    target.edPasswrd = null;
    target.edConfPassword = null;
    target.edWebSite = null;
    target.edAbn = null;
    target.edACN = null;
    target.ccp = null;
    target.btnSignUp = null;
    target.tvLogin = null;
    target.ivBack = null;
    target.cbTerms = null;
    target.tvTermCond = null;
    target.llLayout = null;
    target.ivShowPass = null;
    target.ivShowPassConf = null;
  }
}
