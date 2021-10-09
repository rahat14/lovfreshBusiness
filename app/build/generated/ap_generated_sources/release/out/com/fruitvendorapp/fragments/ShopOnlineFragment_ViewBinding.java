// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.fragments;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import com.google.android.material.tabs.TabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ShopOnlineFragment_ViewBinding implements Unbinder {
  private ShopOnlineFragment target;

  @UiThread
  public ShopOnlineFragment_ViewBinding(ShopOnlineFragment target, View source) {
    this.target = target;

    target.rvCateProduct = Utils.findRequiredViewAsType(source, R.id.rv_cate_product, "field 'rvCateProduct'", RecyclerView.class);
    target.tabCategory = Utils.findRequiredViewAsType(source, R.id.tab_category, "field 'tabCategory'", TabLayout.class);
    target.llNoRecordFound = Utils.findRequiredViewAsType(source, R.id.ll_no_record, "field 'llNoRecordFound'", LinearLayout.class);
    target.ivFilter = Utils.findRequiredViewAsType(source, R.id.iv_filter, "field 'ivFilter'", ImageView.class);
    target.edAutoSearch = Utils.findRequiredViewAsType(source, R.id.auto_search, "field 'edAutoSearch'", EditText.class);
    target.llFilterView = Utils.findRequiredViewAsType(source, R.id.ivFilterView, "field 'llFilterView'", LinearLayout.class);
    target.tvClear = Utils.findRequiredViewAsType(source, R.id.tv_clear, "field 'tvClear'", TextView.class);
    target.spPopularityMenu = Utils.findRequiredViewAsType(source, R.id.sp_popularity, "field 'spPopularityMenu'", Spinner.class);
    target.spCategory = Utils.findRequiredViewAsType(source, R.id.sp_category, "field 'spCategory'", Spinner.class);
    target.tvCategory = Utils.findRequiredViewAsType(source, R.id.tv_category, "field 'tvCategory'", TextView.class);
    target.tvProductTitle = Utils.findRequiredViewAsType(source, R.id.tv_product_title, "field 'tvProductTitle'", TextView.class);
    target.swipe_layout = Utils.findRequiredViewAsType(source, R.id.swipe_layout, "field 'swipe_layout'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ShopOnlineFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvCateProduct = null;
    target.tabCategory = null;
    target.llNoRecordFound = null;
    target.ivFilter = null;
    target.edAutoSearch = null;
    target.llFilterView = null;
    target.tvClear = null;
    target.spPopularityMenu = null;
    target.spCategory = null;
    target.tvCategory = null;
    target.tvProductTitle = null;
    target.swipe_layout = null;
  }
}
