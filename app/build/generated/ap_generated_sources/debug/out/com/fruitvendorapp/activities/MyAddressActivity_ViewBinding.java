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

public class MyAddressActivity_ViewBinding implements Unbinder {
  private MyAddressActivity target;

  @UiThread
  public MyAddressActivity_ViewBinding(MyAddressActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MyAddressActivity_ViewBinding(MyAddressActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.rvMyAddress = Utils.findRequiredViewAsType(source, R.id.rv_address, "field 'rvMyAddress'", RecyclerView.class);
    target.btnAddress = Utils.findRequiredViewAsType(source, R.id.btn_add_address, "field 'btnAddress'", Button.class);
    target.llNoData = Utils.findRequiredViewAsType(source, R.id.ll_no_data, "field 'llNoData'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyAddressActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.ivBack = null;
    target.rvMyAddress = null;
    target.btnAddress = null;
    target.llNoData = null;
  }
}
