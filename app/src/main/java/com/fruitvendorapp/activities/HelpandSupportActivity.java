package com.fruitvendorapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fruitvendorapp.R;
import com.fruitvendorapp.utilities.BaseUtility;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sentry.Sentry;

public class HelpandSupportActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = HelpandSupportActivity.class.getSimpleName();
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.tv_contact)
    TextView tvContact;
    @BindView(R.id.tv_training)
    TextView tvTraining;
    @BindView(R.id.tv_faq)
    TextView tvFAQ;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpand_support);
        ButterKnife.bind(this);
        Sentry.captureMessage("testing HelpandSupportActivity");
        init();
    }

    private void init() {
        tvToolbarTitle.setText(getString(R.string.help_and_support));
        ivBack.setOnClickListener(this);
        tvTraining.setOnClickListener(this);
        tvContact.setOnClickListener(this);
        tvFAQ.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_contact:
                BaseUtility.sendActivityIntent(this, ContactActivity.class);
                break;
            case R.id.tv_training:
                BaseUtility.sendActivityIntent(this, TrainingActivity.class);
                break;
            case R.id.tv_faq:
                BaseUtility.sendActivityIntent(this, FAQActivity.class);
                break;
        }
    }
}