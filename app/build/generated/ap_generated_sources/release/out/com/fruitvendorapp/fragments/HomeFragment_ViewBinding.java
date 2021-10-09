// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.fragments;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import com.google.android.material.tabs.TabLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment_ViewBinding implements Unbinder {
  private HomeFragment target;

  @UiThread
  public HomeFragment_ViewBinding(HomeFragment target, View source) {
    this.target = target;

    target.rvProduct = Utils.findRequiredViewAsType(source, R.id.rv_products, "field 'rvProduct'", RecyclerView.class);
    target.llNoRecordFound = Utils.findRequiredViewAsType(source, R.id.ll_no_record, "field 'llNoRecordFound'", LinearLayout.class);
    target.llViewMain = Utils.findRequiredViewAsType(source, R.id.ll_view_main, "field 'llViewMain'", LinearLayout.class);
    target.tvShopTitle = Utils.findRequiredViewAsType(source, R.id.tv_shop_title, "field 'tvShopTitle'", TextView.class);
    target.tvProductTitle = Utils.findRequiredViewAsType(source, R.id.tv_product_title, "field 'tvProductTitle'", TextView.class);
    target.cmvUserImage = Utils.findRequiredViewAsType(source, R.id.iv_img, "field 'cmvUserImage'", CircleImageView.class);
    target.rlSliderView = Utils.findRequiredViewAsType(source, R.id.rl_slider, "field 'rlSliderView'", RelativeLayout.class);
    target.vpImageSlider = Utils.findRequiredViewAsType(source, R.id.vp_image_slider, "field 'vpImageSlider'", ViewPager.class);
    target.cIndicator = Utils.findRequiredViewAsType(source, R.id.indicator, "field 'cIndicator'", CircleIndicator.class);
    target.edAutoSearch = Utils.findRequiredViewAsType(source, R.id.auto_search, "field 'edAutoSearch'", EditText.class);
    target.tabCategory = Utils.findRequiredViewAsType(source, R.id.tab_category, "field 'tabCategory'", TabLayout.class);
    target.swipe_layout = Utils.findRequiredViewAsType(source, R.id.swipe_layout, "field 'swipe_layout'", SwipeRefreshLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvProduct = null;
    target.llNoRecordFound = null;
    target.llViewMain = null;
    target.tvShopTitle = null;
    target.tvProductTitle = null;
    target.cmvUserImage = null;
    target.rlSliderView = null;
    target.vpImageSlider = null;
    target.cIndicator = null;
    target.edAutoSearch = null;
    target.tabCategory = null;
    target.swipe_layout = null;
  }
}
