// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReferAFriendActivity_ViewBinding implements Unbinder {
  private ReferAFriendActivity target;

  @UiThread
  public ReferAFriendActivity_ViewBinding(ReferAFriendActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ReferAFriendActivity_ViewBinding(ReferAFriendActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.tvDescipation = Utils.findRequiredViewAsType(source, R.id.tv_descripation, "field 'tvDescipation'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.btnSend = Utils.findRequiredViewAsType(source, R.id.btn_send, "field 'btnSend'", Button.class);
    target.tvRefercode = Utils.findRequiredViewAsType(source, R.id.tv_refer_code, "field 'tvRefercode'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReferAFriendActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.tvDescipation = null;
    target.ivBack = null;
    target.btnSend = null;
    target.tvRefercode = null;
  }
}
