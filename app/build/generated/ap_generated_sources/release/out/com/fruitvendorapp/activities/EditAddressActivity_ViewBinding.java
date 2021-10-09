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

public class EditAddressActivity_ViewBinding implements Unbinder {
  private EditAddressActivity target;

  @UiThread
  public EditAddressActivity_ViewBinding(EditAddressActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public EditAddressActivity_ViewBinding(EditAddressActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.edFullName = Utils.findRequiredViewAsType(source, R.id.ed_full_name, "field 'edFullName'", AppCompatEditText.class);
    target.edStreetAdd = Utils.findRequiredViewAsType(source, R.id.ed_street_add, "field 'edStreetAdd'", AppCompatEditText.class);
    target.edCity = Utils.findRequiredViewAsType(source, R.id.ed_city, "field 'edCity'", AppCompatEditText.class);
    target.edState = Utils.findRequiredViewAsType(source, R.id.ed_state, "field 'edState'", AppCompatEditText.class);
    target.edZipCode = Utils.findRequiredViewAsType(source, R.id.ed_zip_code, "field 'edZipCode'", AppCompatEditText.class);
    target.edPhoneNo = Utils.findRequiredViewAsType(source, R.id.ed_phone, "field 'edPhoneNo'", AppCompatEditText.class);
    target.edFlatNo = Utils.findRequiredViewAsType(source, R.id.ed_flat_no, "field 'edFlatNo'", AppCompatEditText.class);
    target.cbAddress = Utils.findRequiredViewAsType(source, R.id.cb_address, "field 'cbAddress'", CheckBox.class);
    target.btnSubmit = Utils.findRequiredViewAsType(source, R.id.btn_submit, "field 'btnSubmit'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EditAddressActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.ivBack = null;
    target.edFullName = null;
    target.edStreetAdd = null;
    target.edCity = null;
    target.edState = null;
    target.edZipCode = null;
    target.edPhoneNo = null;
    target.edFlatNo = null;
    target.cbAddress = null;
    target.btnSubmit = null;
  }
}
