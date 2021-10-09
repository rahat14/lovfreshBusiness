// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ForgotPassActivity_ViewBinding implements Unbinder {
  private ForgotPassActivity target;

  @UiThread
  public ForgotPassActivity_ViewBinding(ForgotPassActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ForgotPassActivity_ViewBinding(ForgotPassActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.btnSubmit = Utils.findRequiredViewAsType(source, R.id.btn_submit, "field 'btnSubmit'", Button.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.edEmail = Utils.findRequiredViewAsType(source, R.id.ed_email, "field 'edEmail'", EditText.class);
    target.llLayout = Utils.findRequiredViewAsType(source, R.id.ll_layout, "field 'llLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ForgotPassActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.btnSubmit = null;
    target.ivBack = null;
    target.edEmail = null;
    target.llLayout = null;
  }
}
