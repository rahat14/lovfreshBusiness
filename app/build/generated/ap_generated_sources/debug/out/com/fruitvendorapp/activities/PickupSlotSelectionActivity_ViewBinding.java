// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PickupSlotSelectionActivity_ViewBinding implements Unbinder {
  private PickupSlotSelectionActivity target;

  @UiThread
  public PickupSlotSelectionActivity_ViewBinding(PickupSlotSelectionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PickupSlotSelectionActivity_ViewBinding(PickupSlotSelectionActivity target, View source) {
    this.target = target;

    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.rvSlotSelection = Utils.findRequiredViewAsType(source, R.id.rv_slot_selection, "field 'rvSlotSelection'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PickupSlotSelectionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvToolbarTitle = null;
    target.ivBack = null;
    target.rvSlotSelection = null;
  }
}
