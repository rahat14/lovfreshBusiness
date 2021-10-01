// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PromoCodeAdapter$ViewHolder_ViewBinding implements Unbinder {
  private PromoCodeAdapter.ViewHolder target;

  @UiThread
  public PromoCodeAdapter$ViewHolder_ViewBinding(PromoCodeAdapter.ViewHolder target, View source) {
    this.target = target;

    target.tvPromoCode = Utils.findRequiredViewAsType(source, R.id.tv_promo_code, "field 'tvPromoCode'", TextView.class);
    target.tvPromoName = Utils.findRequiredViewAsType(source, R.id.tv_promo_name, "field 'tvPromoName'", TextView.class);
    target.tvSaveRupees = Utils.findRequiredViewAsType(source, R.id.tv_save_ruppes, "field 'tvSaveRupees'", TextView.class);
    target.ivDelete = Utils.findRequiredViewAsType(source, R.id.iv_delete, "field 'ivDelete'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PromoCodeAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvPromoCode = null;
    target.tvPromoName = null;
    target.tvSaveRupees = null;
    target.ivDelete = null;
  }
}
