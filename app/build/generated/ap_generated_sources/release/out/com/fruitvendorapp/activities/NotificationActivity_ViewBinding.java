// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NotificationActivity_ViewBinding implements Unbinder {
  private NotificationActivity target;

  @UiThread
  public NotificationActivity_ViewBinding(NotificationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NotificationActivity_ViewBinding(NotificationActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tool_bar, "field 'toolbar'", Toolbar.class);
    target.rvStore = Utils.findRequiredViewAsType(source, R.id.rv_store, "field 'rvStore'", RecyclerView.class);
    target.edVendorSearch = Utils.findRequiredViewAsType(source, R.id.auto_vendor_search, "field 'edVendorSearch'", EditText.class);
    target.llNoFound = Utils.findRequiredViewAsType(source, R.id.ll_no_data, "field 'llNoFound'", LinearLayout.class);
    target.tvNoRecord = Utils.findRequiredViewAsType(source, R.id.tv_no_data, "field 'tvNoRecord'", TextView.class);
    target.pbar = Utils.findRequiredViewAsType(source, R.id.progress_circular, "field 'pbar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NotificationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.ivBack = null;
    target.toolbar = null;
    target.rvStore = null;
    target.edVendorSearch = null;
    target.llNoFound = null;
    target.tvNoRecord = null;
    target.pbar = null;
  }
}
