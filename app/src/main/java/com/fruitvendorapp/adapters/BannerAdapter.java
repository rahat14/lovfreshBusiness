package com.fruitvendorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.fruitvendorapp.R;
import com.fruitvendorapp.model.OnBoardModel;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<OnBoardModel> arrayList;

    public BannerAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<OnBoardModel> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null != arrayList ? arrayList.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.layout_slide_screen, container, false);
        TextView title = itemview.findViewById(R.id.slider_heading);
        TextView descripation = itemview.findViewById(R.id.slider_desc);
        ImageView ivHeader = itemview.findViewById(R.id.iv_header_image);
        final OnBoardModel model = arrayList.get(position);
        title.setText("");
        if (model.getTitle1() != null && model.getTitle1().length() > 0) {
            title.setText(model.getTitle1());
        }
        descripation.setText("");
        if (model.getTitle2() != null && model.getTitle2().length() > 0) {
            descripation.setText(model.getTitle2());
        }
        ivHeader.setImageResource(model.getHeader_img());
        container.addView(itemview);
        return itemview;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}