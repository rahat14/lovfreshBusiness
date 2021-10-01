// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.adapters;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.SwitchCompat;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SlotSelectionAdapter$ViewHolder_ViewBinding implements Unbinder {
  private SlotSelectionAdapter.ViewHolder target;

  @UiThread
  public SlotSelectionAdapter$ViewHolder_ViewBinding(SlotSelectionAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvDate = Utils.findRequiredViewAsType(source, R.id.tv_date, "field 'tvDate'", TextView.class);
    target.tvTimeSlot = Utils.findRequiredViewAsType(source, R.id.tv_time_slot, "field 'tvTimeSlot'", TextView.class);
    target.scSelect = Utils.findRequiredViewAsType(source, R.id.sc_select, "field 'scSelect'", SwitchCompat.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SlotSelectionAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvDate = null;
    target.tvTimeSlot = null;
    target.scSelect = null;
  }
}
