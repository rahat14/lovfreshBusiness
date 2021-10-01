// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.adapters;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import com.makeramen.roundedimageview.RoundedImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProductListAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ProductListAdapter.ViewHolder target;

  @UiThread
  public ProductListAdapter$ViewHolder_ViewBinding(ProductListAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvProductTitle = Utils.findRequiredViewAsType(source, R.id.tv_product_title, "field 'tvProductTitle'", TextView.class);
    target.tvProductAmt = Utils.findRequiredViewAsType(source, R.id.tv_amount, "field 'tvProductAmt'", TextView.class);
    target.riProductImg = Utils.findRequiredViewAsType(source, R.id.ri_product_imgs, "field 'riProductImg'", RoundedImageView.class);
    target.rvMain = Utils.findRequiredViewAsType(source, R.id.rv_main, "field 'rvMain'", RelativeLayout.class);
    target.rlViewTrans = Utils.findRequiredViewAsType(source, R.id.rl_view_trans, "field 'rlViewTrans'", RelativeLayout.class);
    target.tvPromoPrice = Utils.findRequiredViewAsType(source, R.id.tv_promo_price, "field 'tvPromoPrice'", TextView.class);
    target.tvPromoAmt = Utils.findRequiredViewAsType(source, R.id.tv_cal_amt, "field 'tvPromoAmt'", TextView.class);
    target.tvStandPrice = Utils.findRequiredViewAsType(source, R.id.tv_stand_price, "field 'tvStandPrice'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductListAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvProductTitle = null;
    target.tvProductAmt = null;
    target.riProductImg = null;
    target.rvMain = null;
    target.rlViewTrans = null;
    target.tvPromoPrice = null;
    target.tvPromoAmt = null;
    target.tvStandPrice = null;
  }
}
