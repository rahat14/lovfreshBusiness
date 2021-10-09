// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.adapters;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.SwitchCompat;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TimeSlotSelectionAdapter$ViewHolder_ViewBinding implements Unbinder {
  private TimeSlotSelectionAdapter.ViewHolder target;

  @UiThread
  public TimeSlotSelectionAdapter$ViewHolder_ViewBinding(TimeSlotSelectionAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'tvTime'", TextView.class);
    target.scSelect = Utils.findRequiredViewAsType(source, R.id.sc_select, "field 'scSelect'", SwitchCompat.class);
    target.rbTimeSelect = Utils.findRequiredViewAsType(source, R.id.rb_time, "field 'rbTimeSelect'", RadioButton.class);
    target.llView = Utils.findRequiredViewAsType(source, R.id.ll_view, "field 'llView'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TimeSlotSelectionAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTime = null;
    target.scSelect = null;
    target.rbTimeSelect = null;
    target.llView = null;
  }
}
