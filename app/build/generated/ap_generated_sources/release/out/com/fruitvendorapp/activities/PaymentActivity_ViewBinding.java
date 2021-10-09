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

public class PaymentActivity_ViewBinding implements Unbinder {
  private PaymentActivity target;

  @UiThread
  public PaymentActivity_ViewBinding(PaymentActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PaymentActivity_ViewBinding(PaymentActivity target, View source) {
    this.target = target;

    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.btnSubmit = Utils.findRequiredViewAsType(source, R.id.btn_submit, "field 'btnSubmit'", Button.class);
    target.edCardNumber = Utils.findRequiredViewAsType(source, R.id.ed_card_number, "field 'edCardNumber'", AppCompatEditText.class);
    target.edExperiyDate = Utils.findRequiredViewAsType(source, R.id.ed_expery_date, "field 'edExperiyDate'", AppCompatEditText.class);
    target.edCVC = Utils.findRequiredViewAsType(source, R.id.ed_cvc, "field 'edCVC'", AppCompatEditText.class);
    target.llViewPaypal = Utils.findRequiredViewAsType(source, R.id.ll_view_paypal, "field 'llViewPaypal'", LinearLayout.class);
    target.llCreditView = Utils.findRequiredViewAsType(source, R.id.ll_credit_view, "field 'llCreditView'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PaymentActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivBack = null;
    target.tvToolbarTitle = null;
    target.btnSubmit = null;
    target.edCardNumber = null;
    target.edExperiyDate = null;
    target.edCVC = null;
    target.llViewPaypal = null;
    target.llCreditView = null;
  }
}
