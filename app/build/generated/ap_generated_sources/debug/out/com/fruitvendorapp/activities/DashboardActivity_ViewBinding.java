// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DashboardActivity_ViewBinding implements Unbinder {
  private DashboardActivity target;

  @UiThread
  public DashboardActivity_ViewBinding(DashboardActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DashboardActivity_ViewBinding(DashboardActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.tool_bar, "field 'toolbar'", Toolbar.class);
    target.tvToolbarTitle = Utils.findRequiredViewAsType(source, R.id.tv_toolbar_title, "field 'tvToolbarTitle'", TextView.class);
    target.contentFrame = Utils.findRequiredViewAsType(source, R.id.content_frame, "field 'contentFrame'", FrameLayout.class);
    target.llHomeView = Utils.findRequiredViewAsType(source, R.id.ll_home, "field 'llHomeView'", LinearLayout.class);
    target.llOfferView = Utils.findRequiredViewAsType(source, R.id.ll_offer, "field 'llOfferView'", LinearLayout.class);
    target.llProductView = Utils.findRequiredViewAsType(source, R.id.ll_list, "field 'llProductView'", LinearLayout.class);
    target.llMyCartView = Utils.findRequiredViewAsType(source, R.id.ll_carts, "field 'llMyCartView'", LinearLayout.class);
    target.llMyProfile = Utils.findRequiredViewAsType(source, R.id.ll_profile, "field 'llMyProfile'", LinearLayout.class);
    target.ivHome = Utils.findRequiredViewAsType(source, R.id.iv_home, "field 'ivHome'", ImageView.class);
    target.ivOffer = Utils.findRequiredViewAsType(source, R.id.iv_offer, "field 'ivOffer'", ImageView.class);
    target.ivMyCart = Utils.findRequiredViewAsType(source, R.id.iv_carts, "field 'ivMyCart'", ImageView.class);
    target.ivMyProfile = Utils.findRequiredViewAsType(source, R.id.iv_profile, "field 'ivMyProfile'", ImageView.class);
    target.ivBack = Utils.findRequiredViewAsType(source, R.id.iv_back, "field 'ivBack'", ImageView.class);
    target.ivProduct = Utils.findRequiredViewAsType(source, R.id.iv_list, "field 'ivProduct'", ImageView.class);
    target.rlNotification = Utils.findRequiredViewAsType(source, R.id.rl_notification, "field 'rlNotification'", RelativeLayout.class);
    target.ivNotification = Utils.findRequiredViewAsType(source, R.id.iv_notification, "field 'ivNotification'", ImageView.class);
    target.tvNotification = Utils.findRequiredViewAsType(source, R.id.tv_notification_count, "field 'tvNotification'", TextView.class);
    target.ivUploadProduct = Utils.findRequiredViewAsType(source, R.id.fab_upload_product, "field 'ivUploadProduct'", ImageButton.class);
    target.tvHome = Utils.findRequiredViewAsType(source, R.id.tv_home, "field 'tvHome'", TextView.class);
    target.tvSpecial = Utils.findRequiredViewAsType(source, R.id.tv_specials, "field 'tvSpecial'", TextView.class);
    target.tvShopOnline = Utils.findRequiredViewAsType(source, R.id.tv_shop_online, "field 'tvShopOnline'", TextView.class);
    target.tvMyAccount = Utils.findRequiredViewAsType(source, R.id.tv_myaccount, "field 'tvMyAccount'", TextView.class);
    target.ivSearchSpecial = Utils.findRequiredViewAsType(source, R.id.iv_search_special, "field 'ivSearchSpecial'", ImageView.class);
    target.ivSearchShopOnline = Utils.findRequiredViewAsType(source, R.id.iv_search_shopOn, "field 'ivSearchShopOnline'", ImageView.class);
    target.ivSearchHome = Utils.findRequiredViewAsType(source, R.id.iv_search_home, "field 'ivSearchHome'", ImageView.class);
    target.ivFilterSpecial = Utils.findRequiredViewAsType(source, R.id.iv_filter_special, "field 'ivFilterSpecial'", ImageView.class);
    target.ivFilterShopOnline = Utils.findRequiredViewAsType(source, R.id.iv_filter_shop, "field 'ivFilterShopOnline'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DashboardActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.tvToolbarTitle = null;
    target.contentFrame = null;
    target.llHomeView = null;
    target.llOfferView = null;
    target.llProductView = null;
    target.llMyCartView = null;
    target.llMyProfile = null;
    target.ivHome = null;
    target.ivOffer = null;
    target.ivMyCart = null;
    target.ivMyProfile = null;
    target.ivBack = null;
    target.ivProduct = null;
    target.rlNotification = null;
    target.ivNotification = null;
    target.tvNotification = null;
    target.ivUploadProduct = null;
    target.tvHome = null;
    target.tvSpecial = null;
    target.tvShopOnline = null;
    target.tvMyAccount = null;
    target.ivSearchSpecial = null;
    target.ivSearchShopOnline = null;
    target.ivSearchHome = null;
    target.ivFilterSpecial = null;
    target.ivFilterShopOnline = null;
  }
}
