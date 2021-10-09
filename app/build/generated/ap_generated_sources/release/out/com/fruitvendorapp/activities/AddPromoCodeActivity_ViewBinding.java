// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddPromoCodeActivity_ViewBinding implements Unbinder {
  private AddPromoCodeActivity target;

  @UiThread
  public AddPromoCodeActivity_ViewBinding(AddPromoCodeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AddPromoCodeActivity_ViewBinding(AddPromoCodeActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.btnSave = Utils.findRequiredViewAsType(source, R.id.btn_save, "field 'btnSave'", Button.class);
    target.edAmountOff = Utils.findRequiredViewAsType(source, R.id.ed_amount_off, "field 'edAmountOff'", AppCompatEditText.class);
    target.edPromoCode = Utils.findRequiredViewAsType(source, R.id.ed_promo_code, "field 'edPromoCode'", AppCompatEditText.class);
    target.edOfferName = Utils.findRequiredViewAsType(source, R.id.ed_offer_nm, "field 'edOfferName'", AppCompatEditText.class);
    target.edLimitUse = Utils.findRequiredViewAsType(source, R.id.ed_limit, "field 'edLimitUse'", AppCompatEditText.class);
    target.edStartDate = Utils.findRequiredViewAsType(source, R.id.ed_start_date, "field 'edStartDate'", TextView.class);
    target.edEndDate = Utils.findRequiredViewAsType(source, R.id.ed_end_date, "field 'edEndDate'", TextView.class);
    target.cbPercentage = Utils.findRequiredViewAsType(source, R.id.cb_percentage, "field 'cbPercentage'", CheckBox.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddPromoCodeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.ivBack = null;
    target.btnSave = null;
    target.edAmountOff = null;
    target.edPromoCode = null;
    target.edOfferName = null;
    target.edLimitUse = null;
    target.edStartDate = null;
    target.edEndDate = null;
    target.cbPercentage = null;
  }
}
