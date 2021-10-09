// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.adapters;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CategoryFilterAdapter$ViewHolder_ViewBinding implements Unbinder {
  private CategoryFilterAdapter.ViewHolder target;

  @UiThread
  public CategoryFilterAdapter$ViewHolder_ViewBinding(CategoryFilterAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.rbSelect = Utils.findRequiredViewAsType(source, R.id.rb_select, "field 'rbSelect'", CheckBox.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CategoryFilterAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.rbSelect = null;
  }
}
