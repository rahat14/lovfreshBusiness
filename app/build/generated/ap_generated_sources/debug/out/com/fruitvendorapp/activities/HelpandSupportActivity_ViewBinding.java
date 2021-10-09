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

public class HelpandSupportActivity_ViewBinding implements Unbinder {
  private HelpandSupportActivity target;

  @UiThread
  public HelpandSupportActivity_ViewBinding(HelpandSupportActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HelpandSupportActivity_ViewBinding(HelpandSupportActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.tvContact = Utils.findRequiredViewAsType(source, R.id.tv_contact, "field 'tvContact'", TextView.class);
    target.tvTraining = Utils.findRequiredViewAsType(source, R.id.tv_training, "field 'tvTraining'", TextView.class);
    target.tvFAQ = Utils.findRequiredViewAsType(source, R.id.tv_faq, "field 'tvFAQ'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HelpandSupportActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.tvContact = null;
    target.tvTraining = null;
    target.tvFAQ = null;
    target.ivBack = null;
  }
}
