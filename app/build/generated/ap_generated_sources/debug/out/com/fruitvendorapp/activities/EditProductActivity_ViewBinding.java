// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import com.makeramen.roundedimageview.RoundedImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EditProductActivity_ViewBinding implements Unbinder {
  private EditProductActivity target;

  @UiThread
  public EditProductActivity_ViewBinding(EditProductActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public EditProductActivity_ViewBinding(EditProductActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tool_bar, "field 'toolbar'", Toolbar.class);
    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.edTitle = Utils.findRequiredViewAsType(source, R.id.ed_title, "field 'edTitle'", AppCompatEditText.class);
    target.edDescripation = Utils.findRequiredViewAsType(source, R.id.ed_descripation, "field 'edDescripation'", AppCompatEditText.class);
    target.edOtherInfo = Utils.findRequiredViewAsType(source, R.id.ed_other_info, "field 'edOtherInfo'", AppCompatEditText.class);
    target.edPrice = Utils.findRequiredViewAsType(source, R.id.ed_price, "field 'edPrice'", AppCompatEditText.class);
    target.edStockAmt = Utils.findRequiredViewAsType(source, R.id.ed_stock_amt, "field 'edStockAmt'", AppCompatEditText.class);
    target.spUnit = Utils.findRequiredViewAsType(source, R.id.sp_unit, "field 'spUnit'", Spinner.class);
    target.spSpecial = Utils.findRequiredViewAsType(source, R.id.sp_special, "field 'spSpecial'", Spinner.class);
    target.tvCategroy = Utils.findRequiredViewAsType(source, R.id.tv_category, "field 'tvCategroy'", TextView.class);
    target.ivCatgeory = Utils.findRequiredViewAsType(source, R.id.sp_arro, "field 'ivCatgeory'", ImageView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.ivProduct1 = Utils.findRequiredViewAsType(source, R.id.iv_image1, "field 'ivProduct1'", RoundedImageView.class);
    target.ivProduct2 = Utils.findRequiredViewAsType(source, R.id.iv_image2, "field 'ivProduct2'", RoundedImageView.class);
    target.ivProduct3 = Utils.findRequiredViewAsType(source, R.id.iv_image3, "field 'ivProduct3'", RoundedImageView.class);
    target.ivProduct4 = Utils.findRequiredViewAsType(source, R.id.iv_image4, "field 'ivProduct4'", RoundedImageView.class);
    target.btnUpload = Utils.findRequiredViewAsType(source, R.id.btn_upload, "field 'btnUpload'", Button.class);
    target.edStartDate = Utils.findRequiredViewAsType(source, R.id.ed_start_date, "field 'edStartDate'", TextView.class);
    target.edEndDate = Utils.findRequiredViewAsType(source, R.id.ed_end_date, "field 'edEndDate'", TextView.class);
    target.llTypeView = Utils.findRequiredViewAsType(source, R.id.ll_schedule_view, "field 'llTypeView'", LinearLayout.class);
    target.btnDelete = Utils.findRequiredViewAsType(source, R.id.btn_delete, "field 'btnDelete'", Button.class);
    target.btnHide = Utils.findRequiredViewAsType(source, R.id.btn_hide, "field 'btnHide'", Button.class);
    target.edQuantity = Utils.findRequiredViewAsType(source, R.id.ed_quantity, "field 'edQuantity'", AppCompatEditText.class);
    target.cbPromotionPrice = Utils.findRequiredViewAsType(source, R.id.cb_promot_p, "field 'cbPromotionPrice'", CheckBox.class);
    target.rlCatgeory = Utils.findRequiredViewAsType(source, R.id.rl_category, "field 'rlCatgeory'", RelativeLayout.class);
    target.edPromoPrice = Utils.findRequiredViewAsType(source, R.id.ed_pro_price, "field 'edPromoPrice'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EditProductActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.tvToolbarTitle = null;
    target.edTitle = null;
    target.edDescripation = null;
    target.edOtherInfo = null;
    target.edPrice = null;
    target.edStockAmt = null;
    target.spUnit = null;
    target.spSpecial = null;
    target.tvCategroy = null;
    target.ivCatgeory = null;
    target.ivBack = null;
    target.ivProduct1 = null;
    target.ivProduct2 = null;
    target.ivProduct3 = null;
    target.ivProduct4 = null;
    target.btnUpload = null;
    target.edStartDate = null;
    target.edEndDate = null;
    target.llTypeView = null;
    target.btnDelete = null;
    target.btnHide = null;
    target.edQuantity = null;
    target.cbPromotionPrice = null;
    target.rlCatgeory = null;
    target.edPromoPrice = null;
  }
}
