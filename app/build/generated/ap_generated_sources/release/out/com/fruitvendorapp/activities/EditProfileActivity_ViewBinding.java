// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import com.hbb20.CountryCodePicker;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EditProfileActivity_ViewBinding implements Unbinder {
  private EditProfileActivity target;

  @UiThread
  public EditProfileActivity_ViewBinding(EditProfileActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public EditProfileActivity_ViewBinding(EditProfileActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.tvEditBtn = Utils.findRequiredViewAsType(source, R.id.iv_edit, "field 'tvEditBtn'", TextView.class);
    target.edName = Utils.findRequiredViewAsType(source, R.id.ed_name, "field 'edName'", AppCompatEditText.class);
    target.edEmail = Utils.findRequiredViewAsType(source, R.id.ed_email, "field 'edEmail'", AppCompatEditText.class);
    target.edPhone = Utils.findRequiredViewAsType(source, R.id.ed_phone, "field 'edPhone'", AppCompatEditText.class);
    target.edAddress = Utils.findRequiredViewAsType(source, R.id.ed_address, "field 'edAddress'", AppCompatEditText.class);
    target.edWebSite = Utils.findRequiredViewAsType(source, R.id.ed_website, "field 'edWebSite'", AppCompatEditText.class);
    target.edAbn = Utils.findRequiredViewAsType(source, R.id.ed_abn, "field 'edAbn'", AppCompatEditText.class);
    target.ivAddImage = Utils.findRequiredViewAsType(source, R.id.iv_add_image, "field 'ivAddImage'", ImageView.class);
    target.cmvUserImage = Utils.findRequiredViewAsType(source, R.id.cmv_profile, "field 'cmvUserImage'", ImageView.class);
    target.ivHolderImage = Utils.findRequiredViewAsType(source, R.id.iv_holder_image, "field 'ivHolderImage'", ImageView.class);
    target.ivAddBanner = Utils.findRequiredViewAsType(source, R.id.iv_add_banner, "field 'ivAddBanner'", TextView.class);
    target.deliveryTitle = Utils.findRequiredViewAsType(source, R.id.delivery_title, "field 'deliveryTitle'", TextView.class);
    target.btnUpdate = Utils.findRequiredViewAsType(source, R.id.btn_update, "field 'btnUpdate'", Button.class);
    target.rvDeliver = Utils.findRequiredViewAsType(source, R.id.rv_deliver, "field 'rvDeliver'", RecyclerView.class);
    target.tvCountryCode = Utils.findRequiredViewAsType(source, R.id.tv_country_code, "field 'tvCountryCode'", AppCompatEditText.class);
    target.ccp = Utils.findRequiredViewAsType(source, R.id.ccp, "field 'ccp'", CountryCodePicker.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EditProfileActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.ivBack = null;
    target.tvEditBtn = null;
    target.edName = null;
    target.edEmail = null;
    target.edPhone = null;
    target.edAddress = null;
    target.edWebSite = null;
    target.edAbn = null;
    target.ivAddImage = null;
    target.cmvUserImage = null;
    target.ivHolderImage = null;
    target.ivAddBanner = null;
    target.deliveryTitle = null;
    target.btnUpdate = null;
    target.rvDeliver = null;
    target.tvCountryCode = null;
    target.ccp = null;
  }
}
