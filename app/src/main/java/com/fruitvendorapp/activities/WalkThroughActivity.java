package com.fruitvendorapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.fruitvendorapp.R;
import com.fruitvendorapp.adapters.BannerAdapter;
import com.fruitvendorapp.model.OnBoardModel;
import com.fruitvendorapp.utilities.BaseUtility;

import java.util.ArrayList;

import io.sentry.Sentry;
import me.relex.circleindicator.CircleIndicator;

public class WalkThroughActivity extends AppCompatActivity implements View.OnClickListener {
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ArrayList<OnBoardModel> list;
    private ViewPager vpSlider;
    private CircleIndicator circleIndicator;
    Button btnStart;
    TextView tvSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);
        Sentry.captureMessage("testing WalkThroughActivity");
        init();
    }

    private void init(){
        vpSlider = findViewById(R.id.vp_slider);
        circleIndicator = findViewById(R.id.indicator);
        btnStart=findViewById(R.id.btn_start);
        tvSkip =findViewById(R.id.tv_skip);
        btnStart.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
        setBannerAdapter();
    }

    // set Banner Adapter
    private void setBannerAdapter() {
        ArrayList<OnBoardModel> list = new ArrayList<>();
        list.add(new OnBoardModel(R.drawable.bg_header_first, getString(R.string.first_slide_title), "Hello and welcome to a community of everyday shoppers Like you, we love to eat fresh and enjoy the best of what’s in season. We also love good food deals, without compromising the quality.Let’s connect with your local food place."));
        list.add(new OnBoardModel(R.drawable.bg_header_second, "Discover your local food place Be the first to know", "Dont miss out on specials Pick up or home delivery Best price & value, without compromising the quality."));
        list.add(new OnBoardModel(R.drawable.bg_header_third, "Food is a fact of life", "Australia is a lucky country Practice to eat local, shop local and spent local Discover your food source custodian."));
        BannerAdapter bannerAdapter = new BannerAdapter(this);
        bannerAdapter.setData(list);
        vpSlider.setAdapter(bannerAdapter);
        circleIndicator.setViewPager(vpSlider);
        vpSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = position;
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        btnStart.setText(getResources().getString(R.string.connect));
                        break;
                    case 1:
                        btnStart.setText(getResources().getString(R.string.download));
                        break;
                    case 2:
                        btnStart.setText(getResources().getString(R.string.signup3));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    int pageCount = list.size();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {
            BaseUtility.sendActivityIntent(this, LoginActivity.class);
        }
    }
}