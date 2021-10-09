// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderRejectionActivity_ViewBinding implements Unbinder {
  private OrderRejectionActivity target;

  @UiThread
  public OrderRejectionActivity_ViewBinding(OrderRejectionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OrderRejectionActivity_ViewBinding(OrderRejectionActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tool_bar, "field 'toolbar'", Toolbar.class);
    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.spReason = Utils.findRequiredViewAsType(source, R.id.sp_reason, "field 'spReason'", Spinner.class);
    target.edReason = Utils.findRequiredViewAsType(source, R.id.ed_reason, "field 'edReason'", AppCompatEditText.class);
    target.btnSubmit = Utils.findRequiredViewAsType(source, R.id.btn_submit, "field 'btnSubmit'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderRejectionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.tvToolbarTitle = null;
    target.ivBack = null;
    target.spReason = null;
    target.edReason = null;
    target.btnSubmit = null;
  }
}
