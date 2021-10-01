// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.fragments;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyProfileFragment_ViewBinding implements Unbinder {
  private MyProfileFragment target;

  @UiThread
  public MyProfileFragment_ViewBinding(MyProfileFragment target, View source) {
    this.target = target;

    target.tvReferFriend = Utils.findRequiredViewAsType(source, R.id.tv_refer_friend, "field 'tvReferFriend'", TextView.class);
    target.tvNotification = Utils.findRequiredViewAsType(source, R.id.tv_notification, "field 'tvNotification'", TextView.class);
    target.tvMyProfile = Utils.findRequiredViewAsType(source, R.id.tv_my_profile, "field 'tvMyProfile'", TextView.class);
    target.tvOrders = Utils.findRequiredViewAsType(source, R.id.tv_orders, "field 'tvOrders'", TextView.class);
    target.tvHelpSupport = Utils.findRequiredViewAsType(source, R.id.tv_help_and_support, "field 'tvHelpSupport'", TextView.class);
    target.tvSetting = Utils.findRequiredViewAsType(source, R.id.tv_setting, "field 'tvSetting'", TextView.class);
    target.tvLogout = Utils.findRequiredViewAsType(source, R.id.tv_logout, "field 'tvLogout'", TextView.class);
    target.tvShopTitle = Utils.findRequiredViewAsType(source, R.id.tv_vendor_shop, "field 'tvShopTitle'", TextView.class);
    target.tvUrl = Utils.findRequiredViewAsType(source, R.id.tv_url, "field 'tvUrl'", TextView.class);
    target.tvArrowComplete = Utils.findRequiredViewAsType(source, R.id.tv_arrow_order_comp, "field 'tvArrowComplete'", TextView.class);
    target.tvArrowPending = Utils.findRequiredViewAsType(source, R.id.tv_arrow_order_pend, "field 'tvArrowPending'", TextView.class);
    target.cmvShopImage = Utils.findRequiredViewAsType(source, R.id.cmv_profile, "field 'cmvShopImage'", CircleImageView.class);
    target.llOrderView = Utils.findRequiredViewAsType(source, R.id.ll_order_view, "field 'llOrderView'", LinearLayout.class);
    target.llSettingView = Utils.findRequiredViewAsType(source, R.id.ll_setting_view, "field 'llSettingView'", LinearLayout.class);
    target.llHelpView = Utils.findRequiredViewAsType(source, R.id.ll_help_view, "field 'llHelpView'", LinearLayout.class);
    target.llReferView = Utils.findRequiredViewAsType(source, R.id.ll_refer_view, "field 'llReferView'", LinearLayout.class);
    target.rlPendingView = Utils.findRequiredViewAsType(source, R.id.rl_pend_view, "field 'rlPendingView'", RelativeLayout.class);
    target.rlCompletedView = Utils.findRequiredViewAsType(source, R.id.rl_compl_view, "field 'rlCompletedView'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyProfileFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvReferFriend = null;
    target.tvNotification = null;
    target.tvMyProfile = null;
    target.tvOrders = null;
    target.tvHelpSupport = null;
    target.tvSetting = null;
    target.tvLogout = null;
    target.tvShopTitle = null;
    target.tvUrl = null;
    target.tvArrowComplete = null;
    target.tvArrowPending = null;
    target.cmvShopImage = null;
    target.llOrderView = null;
    target.llSettingView = null;
    target.llHelpView = null;
    target.llReferView = null;
    target.rlPendingView = null;
    target.rlCompletedView = null;
  }
}
