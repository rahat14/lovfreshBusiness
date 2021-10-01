package com.fruitvendorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fruitvendorapp.R;
import com.fruitvendorapp.model.ImageModel;
import com.makeramen.roundedimageview.RoundedImageView;


import java.util.ArrayList;

public class ProductSliderAdapter extends PagerAdapter {
   private Context context;
   private ArrayList<ImageModel> arrayList;
    public ProductSliderAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<ImageModel> arrayList){
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
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.item_vp_product_slider, container, false);
        RoundedImageView ivHeader = itemview.findViewById(R.id.iv_thumb_image);
        final ImageModel model =arrayList.get(position);
        Glide.with(context).load(model.getImage()).apply(new RequestOptions().placeholder(R.drawable.img_place_holder).error(R.drawable.img_place_holder)).into(ivHeader);
        container.addView(itemview);
        return itemview;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

 }