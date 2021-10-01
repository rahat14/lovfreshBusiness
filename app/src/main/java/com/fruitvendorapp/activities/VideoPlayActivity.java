package com.fruitvendorapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.fruitvendorapp.R;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.ProgressDialogUtil;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class VideoPlayActivity extends AppCompatActivity {
    private static final String TAG = "VideoPlayActivity";
    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.youtube_player_view)
    YouTubePlayerView youtubeView;
    private ProgressDialogUtil progressDialogUtil;
    private String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFinishOnTouchOutside(false);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing VideoPlayActivity");
        init();
    }

    private void init() {
        progressDialogUtil = new ProgressDialogUtil(this);
        if(getIntent().hasExtra(Constant.LINK)){
          url = getIntent().getStringExtra(Constant.LINK);
            Log.e("video url = ", "...." + url);
        }
        if (url.matches("(?i).*www.youtube.com.*")){
            // youtube video play
            videoView.setVisibility(View.GONE);
            youtubeView.setVisibility(View.VISIBLE);
            String videoId = getYoutubeVideoId(url);
            getLifecycle().addObserver(youtubeView);
            youtubeView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });
        }else {
            videoView.setVisibility(View.VISIBLE);
            youtubeView.setVisibility(View.GONE);
            videoView.setVideoURI(Uri.parse(url));
            MediaController controller = new MediaController(this);
            controller.setMediaPlayer(videoView);
            controller.setAnchorView(videoView);
            videoView.setMediaController(controller);
            videoView.start();
            progressDialogUtil.showDialog();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                       int arg2) {
                            progressDialogUtil.dismissDialog();
                            mp.start();
                        }
                    });
                }
            });
            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    progressDialogUtil.dismissDialog();
                    onBackPressed();
                    return false;
                }
            });
        } }
    private String getYoutubeVideoId(String url) {
        final String regex = "v=([^\\s&#]*)";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            Log.e(TAG,"Video id = "+matcher.group(1));
        }
        return matcher.group(1);
    }
}