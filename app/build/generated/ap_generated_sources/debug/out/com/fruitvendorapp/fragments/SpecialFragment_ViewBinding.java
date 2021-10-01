// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import com.google.android.material.tabs.TabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SpecialFragment_ViewBinding implements Unbinder {
  private SpecialFragment target;

  @UiThread
  public SpecialFragment_ViewBinding(SpecialFragment target, View source) {
    this.target = target;

    target.edProductSearch = Utils.findRequiredViewAsType(source, R.id.auto_search, "field 'edProductSearch'", EditText.class);
    target.tabView = Utils.findRequiredViewAsType(source, R.id.tab_view, "field 'tabView'", TabLayout.class);
    target.rvSchedule = Utils.findRequiredViewAsType(source, R.id.rv_schedule, "field 'rvSchedule'", RecyclerView.class);
    target.rvDayProduct = Utils.findRequiredViewAsType(source, R.id.rv_day_product, "field 'rvDayProduct'", RecyclerView.class);
    target.rlSpecialView = Utils.findRequiredViewAsType(source, R.id.rl_special_view, "field 'rlSpecialView'", RelativeLayout.class);
    target.rlScheduleView = Utils.findRequiredViewAsType(source, R.id.rl_schedule_view, "field 'rlScheduleView'", RelativeLayout.class);
    target.llNoRecordFound = Utils.findRequiredViewAsType(source, R.id.ll_no_record, "field 'llNoRecordFound'", LinearLayout.class);
    target.llBtnView = Utils.findRequiredViewAsType(source, R.id.ll_btn_view, "field 'llBtnView'", LinearLayout.class);
    target.ivFilter = Utils.findRequiredViewAsType(source, R.id.iv_filter, "field 'ivFilter'", ImageView.class);
    target.btnHide = Utils.findRequiredViewAsType(source, R.id.btn_hide, "field 'btnHide'", Button.class);
    target.btnDelete = Utils.findRequiredViewAsType(source, R.id.btn_delete, "field 'btnDelete'", Button.class);
    target.llFilterView = Utils.findRequiredViewAsType(source, R.id.ivFilterView, "field 'llFilterView'", LinearLayout.class);
    target.tvClear = Utils.findRequiredViewAsType(source, R.id.tv_clear, "field 'tvClear'", TextView.class);
    target.spPopularityMenu = Utils.findRequiredViewAsType(source, R.id.sp_popularity, "field 'spPopularityMenu'", Spinner.class);
    target.spCategory = Utils.findRequiredViewAsType(source, R.id.sp_category, "field 'spCategory'", Spinner.class);
    target.tvCategory = Utils.findRequiredViewAsType(source, R.id.tv_category, "field 'tvCategory'", TextView.class);
    target.tabCategory = Utils.findRequiredViewAsType(source, R.id.tab_category, "field 'tabCategory'", TabLayout.class);
    target.tab_sch_category = Utils.findRequiredViewAsType(source, R.id.tab_sch_category, "field 'tab_sch_category'", TabLayout.class);
    target.tvProductTitle = Utils.findRequiredViewAsType(source, R.id.tv_product_title, "field 'tvProductTitle'", TextView.class);
    target.tvSpecialtitle = Utils.findRequiredViewAsType(source, R.id.tv_special_title, "field 'tvSpecialtitle'", TextView.class);
    target.tvScheduletitle = Utils.findRequiredViewAsType(source, R.id.tv_schedule_title, "field 'tvScheduletitle'", TextView.class);
    target.swipe_layout = Utils.findRequiredViewAsType(source, R.id.swipe_layout, "field 'swipe_layout'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SpecialFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edProductSearch = null;
    target.tabView = null;
    target.rvSchedule = null;
    target.rvDayProduct = null;
    target.rlSpecialView = null;
    target.rlScheduleView = null;
    target.llNoRecordFound = null;
    target.llBtnView = null;
    target.ivFilter = null;
    target.btnHide = null;
    target.btnDelete = null;
    target.llFilterView = null;
    target.tvClear = null;
    target.spPopularityMenu = null;
    target.spCategory = null;
    target.tvCategory = null;
    target.tabCategory = null;
    target.tab_sch_category = null;
    target.tvProductTitle = null;
    target.tvSpecialtitle = null;
    target.tvScheduletitle = null;
    target.swipe_layout = null;
  }
}
