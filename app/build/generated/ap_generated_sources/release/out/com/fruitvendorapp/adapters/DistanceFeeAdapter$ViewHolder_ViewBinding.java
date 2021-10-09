// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.adapters;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DistanceFeeAdapter$ViewHolder_ViewBinding implements Unbinder {
  private DistanceFeeAdapter.ViewHolder target;

  @UiThread
  public DistanceFeeAdapter$ViewHolder_ViewBinding(DistanceFeeAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.txtFee = Utils.findRequiredViewAsType(source, R.id.txt_fee, "field 'txtFee'", TextView.class);
    target.tvDistance = Utils.findRequiredViewAsType(source, R.id.tv_dist, "field 'tvDistance'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DistanceFeeAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtFee = null;
    target.tvDistance = null;
  }
}
