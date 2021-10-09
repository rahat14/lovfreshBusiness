// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FilterActivity_ViewBinding implements Unbinder {
  private FilterActivity target;

  @UiThread
  public FilterActivity_ViewBinding(FilterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FilterActivity_ViewBinding(FilterActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.sbPrice = Utils.findRequiredViewAsType(source, R.id.sb_price, "field 'sbPrice'", SeekBar.class);
    target.tvPrice = Utils.findRequiredViewAsType(source, R.id.tv_prices, "field 'tvPrice'", TextView.class);
    target.tvClear = Utils.findRequiredViewAsType(source, R.id.tv_clear, "field 'tvClear'", TextView.class);
    target.rvProductList = Utils.findRequiredViewAsType(source, R.id.rv_product_list, "field 'rvProductList'", RecyclerView.class);
    target.llNoRecordFound = Utils.findRequiredViewAsType(source, R.id.ll_no_record, "field 'llNoRecordFound'", LinearLayout.class);
    target.spCategory = Utils.findRequiredViewAsType(source, R.id.sp_category, "field 'spCategory'", Spinner.class);
    target.tvCategory = Utils.findRequiredViewAsType(source, R.id.tv_category, "field 'tvCategory'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FilterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.ivBack = null;
    target.sbPrice = null;
    target.tvPrice = null;
    target.tvClear = null;
    target.rvProductList = null;
    target.llNoRecordFound = null;
    target.spCategory = null;
    target.tvCategory = null;
  }
}
