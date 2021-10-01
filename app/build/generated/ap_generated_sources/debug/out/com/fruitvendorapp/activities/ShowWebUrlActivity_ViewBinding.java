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

public class ShowWebUrlActivity_ViewBinding implements Unbinder {
  private ShowWebUrlActivity target;

  @UiThread
  public ShowWebUrlActivity_ViewBinding(ShowWebUrlActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ShowWebUrlActivity_ViewBinding(ShowWebUrlActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.wvUrl = Utils.findRequiredViewAsType(source, R.id.wv_url, "field 'wvUrl'", WebView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ShowWebUrlActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.ivBack = null;
    target.wvUrl = null;
  }
}
