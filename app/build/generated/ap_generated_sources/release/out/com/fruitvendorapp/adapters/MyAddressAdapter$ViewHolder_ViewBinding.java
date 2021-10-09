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

public class MyAddressAdapter$ViewHolder_ViewBinding implements Unbinder {
  private MyAddressAdapter.ViewHolder target;

  @UiThread
  public MyAddressAdapter$ViewHolder_ViewBinding(MyAddressAdapter.ViewHolder target, View source) {
    this.target = target;

    target.tvAddress = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'tvAddress'", TextView.class);
    target.tvEmailId = Utils.findRequiredViewAsType(source, R.id.tv_email, "field 'tvEmailId'", TextView.class);
    target.tvPhone = Utils.findRequiredViewAsType(source, R.id.tv_phone, "field 'tvPhone'", TextView.class);
    target.ivEdit = Utils.findRequiredViewAsType(source, R.id.iv_edit, "field 'ivEdit'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyAddressAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvAddress = null;
    target.tvEmailId = null;
    target.tvPhone = null;
    target.ivEdit = null;
  }
}
