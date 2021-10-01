package com.fruitvendorapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.fruitvendorapp.R;
import com.fruitvendorapp.utilities.ProgressDialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class ShowWebUrlActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = ShowWebUrlActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.wv_url)
    WebView wvUrl;
    private ProgressDialogUtil progressDialogUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_web_url);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing ShowWebUrlActivity");
        tvToolbarTitle.setText("Lovfresh");
        ivBack.setOnClickListener(this);
        progressDialogUtil = new ProgressDialogUtil(this);
        progressDialogUtil.showDialog();
        wvUrl.getSettings().setJavaScriptEnabled(true);
        wvUrl.loadUrl("https://www.lovfresh.com/");
        wvUrl.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                progressDialogUtil.dismissDialog();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialogUtil.dismissDialog();
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }
}