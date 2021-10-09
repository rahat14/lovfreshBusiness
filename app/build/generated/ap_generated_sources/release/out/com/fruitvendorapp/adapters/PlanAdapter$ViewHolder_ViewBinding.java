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

public class PlanAdapter$ViewHolder_ViewBinding implements Unbinder {
  private PlanAdapter.ViewHolder target;

  @UiThread
  public PlanAdapter$ViewHolder_ViewBinding(PlanAdapter.ViewHolder target, View source) {
    this.target = target;

    target.tvPlanNm = Utils.findRequiredViewAsType(source, R.id.tv_plan_name, "field 'tvPlanNm'", TextView.class);
    target.tvIntervalPlan = Utils.findRequiredViewAsType(source, R.id.tv_interval, "field 'tvIntervalPlan'", TextView.class);
    target.tvDescripation = Utils.findRequiredViewAsType(source, R.id.tv_descripation, "field 'tvDescripation'", TextView.class);
    target.tvSelect = Utils.findRequiredViewAsType(source, R.id.tv_select, "field 'tvSelect'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PlanAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvPlanNm = null;
    target.tvIntervalPlan = null;
    target.tvDescripation = null;
    target.tvSelect = null;
  }
}
