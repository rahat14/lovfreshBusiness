// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FAQActivity_ViewBinding implements Unbinder {
  private FAQActivity target;

  @UiThread
  public FAQActivity_ViewBinding(FAQActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FAQActivity_ViewBinding(FAQActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.wv_faq = Utils.findRequiredViewAsType(source, R.id.wv_faq, "field 'wv_faq'", WebView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FAQActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.ivBack = null;
    target.wv_faq = null;
  }
}
