// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.adapters;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OrderProductAdapter$ViewHolder_ViewBinding implements Unbinder {
  private OrderProductAdapter.ViewHolder target;

  @UiThread
  public OrderProductAdapter$ViewHolder_ViewBinding(OrderProductAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvProductNm = Utils.findRequiredViewAsType(source, R.id.tv_product_nm, "field 'tvProductNm'", TextView.class);
    target.tvQuantity = Utils.findRequiredViewAsType(source, R.id.tv_quantity, "field 'tvQuantity'", TextView.class);
    target.tvAmount = Utils.findRequiredViewAsType(source, R.id.tv_amount, "field 'tvAmount'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OrderProductAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvProductNm = null;
    target.tvQuantity = null;
    target.tvAmount = null;
  }
}
