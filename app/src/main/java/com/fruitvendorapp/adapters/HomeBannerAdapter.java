package com.fruitvendorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fruitvendorapp.R;
import com.fruitvendorapp.model.BannerImage;
import com.fruitvendorapp.utilities.Urls;

import java.util.ArrayList;


public class HomeBannerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<BannerImage> arrayList;

    public HomeBannerAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<BannerImage> arrayList) {
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
        View itemview = inflater.inflate(R.layout.item_vp_slider, container, false);
        ImageView ivHeader = itemview.findViewById(R.id.iv_thumb_image);
        final BannerImage model = arrayList.get(position);
        Glide.with(context).load(Urls.IMAGE_URL + model.getImage()).apply(new RequestOptions().placeholder(R.drawable.fruit_placeholder).error(R.drawable.fruit_placeholder)).into(ivHeader);
        container.addView(itemview);
        return itemview;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}