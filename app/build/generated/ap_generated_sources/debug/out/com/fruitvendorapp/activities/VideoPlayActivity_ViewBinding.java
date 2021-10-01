// Generated code from Butter Knife. Do not modify!
package com.fruitvendorapp.activities;

import android.view.View;
import android.widget.VideoView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.fruitvendorapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VideoPlayActivity_ViewBinding implements Unbinder {
  private VideoPlayActivity target;

  @UiThread
  public VideoPlayActivity_ViewBinding(VideoPlayActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public VideoPlayActivity_ViewBinding(VideoPlayActivity target, View source) {
    this.target = target;

    target.videoView = Utils.findRequiredViewAsType(source, R.id.videoView, "field 'videoView'", VideoView.class);
    target.youtubeView = Utils.findRequiredViewAsType(source, R.id.youtube_player_view, "field 'youtubeView'", YouTubePlayerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    VideoPlayActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.videoView = null;
    target.youtubeView = null;
  }
}
