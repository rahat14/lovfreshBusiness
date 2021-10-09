// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PromoCodeActivity_ViewBinding implements Unbinder {
  private PromoCodeActivity target;

  @UiThread
  public PromoCodeActivity_ViewBinding(PromoCodeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PromoCodeActivity_ViewBinding(PromoCodeActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.btnPromoCode = Utils.findRequiredViewAsType(source, R.id.btn_promo_code, "field 'btnPromoCode'", Button.class);
    target.rvCoupans = Utils.findRequiredViewAsType(source, R.id.rv_coupans, "field 'rvCoupans'", RecyclerView.class);
    target.llNoRecordFound = Utils.findRequiredViewAsType(source, R.id.ll_no_data, "field 'llNoRecordFound'", LinearLayout.class);
    target.tvNoRecord = Utils.findRequiredViewAsType(source, R.id.tv_no_data, "field 'tvNoRecord'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PromoCodeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.ivBack = null;
    target.btnPromoCode = null;
    target.rvCoupans = null;
    target.llNoRecordFound = null;
    target.tvNoRecord = null;
  }
}
