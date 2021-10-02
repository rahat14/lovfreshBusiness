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
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import com.google.android.material.tabs.TabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrdersActivity_ViewBinding implements Unbinder {
  private OrdersActivity target;

  @UiThread
  public OrdersActivity_ViewBinding(OrdersActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OrdersActivity_ViewBinding(OrdersActivity target, View source) {
    this.target = target;

    target.tabOrderCate = Utils.findRequiredViewAsType(source, R.id.tab_order_cate, "field 'tabOrderCate'", TabLayout.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.rvMyOrder = Utils.findRequiredViewAsType(source, R.id.rv_order, "field 'rvMyOrder'", RecyclerView.class);
    target.llNoFound = Utils.findRequiredViewAsType(source, R.id.ll_no_data, "field 'llNoFound'", LinearLayout.class);
    target.tvNoRecord = Utils.findRequiredViewAsType(source, R.id.tv_no_data, "field 'tvNoRecord'", TextView.class);
    target.edSearchOrder = Utils.findRequiredViewAsType(source, R.id.auto_search, "field 'edSearchOrder'", EditText.class);
    target.ivSearchOrder = Utils.findRequiredViewAsType(source, R.id.iv_search_order, "field 'ivSearchOrder'", ImageView.class);
    target.pbar = Utils.findRequiredViewAsType(source, R.id.progress_circular, "field 'pbar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrdersActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tabOrderCate = null;
    target.ivBack = null;
    target.tvToolbarTitle = null;
    target.rvMyOrder = null;
    target.llNoFound = null;
    target.tvNoRecord = null;
    target.edSearchOrder = null;
    target.ivSearchOrder = null;
    target.pbar = null;
  }
}
