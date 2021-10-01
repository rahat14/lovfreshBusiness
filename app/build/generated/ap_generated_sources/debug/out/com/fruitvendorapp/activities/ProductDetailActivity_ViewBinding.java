// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import com.makeramen.roundedimageview.RoundedImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ProductDetailActivity_ViewBinding implements Unbinder {
  private ProductDetailActivity target;

  @UiThread
  public ProductDetailActivity_ViewBinding(ProductDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ProductDetailActivity_ViewBinding(ProductDetailActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tool_bar, "field 'toolbar'", Toolbar.class);
    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.tvProductTitle = Utils.findRequiredViewAsType(source, R.id.tv_product_title, "field 'tvProductTitle'", TextView.class);
    target.tvProductDescription = Utils.findRequiredViewAsType(source, R.id.tv_des_detail, "field 'tvProductDescription'", TextView.class);
    target.tvDescripationTitle = Utils.findRequiredViewAsType(source, R.id.tv_descripation, "field 'tvDescripationTitle'", TextView.class);
    target.tvProductAmt = Utils.findRequiredViewAsType(source, R.id.tv_product_amount, "field 'tvProductAmt'", TextView.class);
    target.vpImage = Utils.findRequiredViewAsType(source, R.id.vp_image_slider, "field 'vpImage'", ViewPager.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.ivLeftArrow = Utils.findRequiredViewAsType(source, R.id.left_nav, "field 'ivLeftArrow'", ImageButton.class);
    target.ivRightArrow = Utils.findRequiredViewAsType(source, R.id.right_nav, "field 'ivRightArrow'", ImageButton.class);
    target.llMainView = Utils.findRequiredViewAsType(source, R.id.ll_mainView, "field 'llMainView'", LinearLayout.class);
    target.riHolderImg = Utils.findRequiredViewAsType(source, R.id.iv_holder_image, "field 'riHolderImg'", RoundedImageView.class);
    target.btnEdit = Utils.findRequiredViewAsType(source, R.id.btn_edit, "field 'btnEdit'", Button.class);
    target.btnDelete = Utils.findRequiredViewAsType(source, R.id.btn_delete, "field 'btnDelete'", Button.class);
    target.btnHide = Utils.findRequiredViewAsType(source, R.id.btn_hide, "field 'btnHide'", Button.class);
    target.tvStockPrice = Utils.findRequiredViewAsType(source, R.id.tv_stock_price, "field 'tvStockPrice'", TextView.class);
    target.tvProductSale = Utils.findRequiredViewAsType(source, R.id.product_sale, "field 'tvProductSale'", TextView.class);
    target.tvPromoPrice = Utils.findRequiredViewAsType(source, R.id.tv_promo_price, "field 'tvPromoPrice'", TextView.class);
    target.tvPromoAmt = Utils.findRequiredViewAsType(source, R.id.tv_cal_amt, "field 'tvPromoAmt'", TextView.class);
    target.tvStandPrice = Utils.findRequiredViewAsType(source, R.id.tv_stand_price, "field 'tvStandPrice'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProductDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.tvToolbarTitle = null;
    target.tvProductTitle = null;
    target.tvProductDescription = null;
    target.tvDescripationTitle = null;
    target.tvProductAmt = null;
    target.vpImage = null;
    target.ivBack = null;
    target.ivLeftArrow = null;
    target.ivRightArrow = null;
    target.llMainView = null;
    target.riHolderImg = null;
    target.btnEdit = null;
    target.btnDelete = null;
    target.btnHide = null;
    target.tvStockPrice = null;
    target.tvProductSale = null;
    target.tvPromoPrice = null;
    target.tvPromoAmt = null;
    target.tvStandPrice = null;
  }
}
