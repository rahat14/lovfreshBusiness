package com.fruitvendorapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.fruitvendorapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class FAQActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = FAQActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.wv_faq)
    WebView wv_faq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing FAQActivity");
        init();
    }

    private void init() {
        tvToolbarTitle.setText(getString(R.string.FAQ));
        ivBack.setOnClickListener(this);

        wv_faq.getSettings().setJavaScriptEnabled(true);
        wv_faq.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        wv_faq.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv_faq.loadUrl("file:///android_asset/faqs.html");
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;

        }
    }
}