// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TrainingVideoAdapter$ViewHolder_ViewBinding implements Unbinder {
  private TrainingVideoAdapter.ViewHolder target;

  @UiThread
  public TrainingVideoAdapter$ViewHolder_ViewBinding(TrainingVideoAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvHeadLine = Utils.findRequiredViewAsType(source, R.id.tv_head_line, "field 'tvHeadLine'", TextView.class);
    target.ivPlay = Utils.findRequiredViewAsType(source, R.id.iv_play, "field 'ivPlay'", ImageView.class);
    target.ivVideoImage = Utils.findRequiredViewAsType(source, R.id.iv_video_img, "field 'ivVideoImage'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TrainingVideoAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvHeadLine = null;
    target.ivPlay = null;
    target.ivVideoImage = null;
  }
}
