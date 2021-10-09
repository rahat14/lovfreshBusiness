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

public class ContactActivity_ViewBinding implements Unbinder {
  private ContactActivity target;

  @UiThread
  public ContactActivity_ViewBinding(ContactActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ContactActivity_ViewBinding(ContactActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.tvCallTitle = Utils.findRequiredViewAsType(source, R.id.tv_call_title, "field 'tvCallTitle'", TextView.class);
    target.tvCallDes = Utils.findRequiredViewAsType(source, R.id.tv_call_descripation, "field 'tvCallDes'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.ivReqCall = Utils.findRequiredViewAsType(source, R.id.iv_req_call, "field 'ivReqCall'", ImageView.class);
    target.ivCustCall = Utils.findRequiredViewAsType(source, R.id.iv_cust_call, "field 'ivCustCall'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ContactActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.tvCallTitle = null;
    target.tvCallDes = null;
    target.ivBack = null;
    target.ivReqCall = null;
    target.ivCustCall = null;
  }
}
