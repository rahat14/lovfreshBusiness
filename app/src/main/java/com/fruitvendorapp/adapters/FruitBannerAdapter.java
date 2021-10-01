package com.fruitvendorapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
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
import com.fruitvendorapp.model.ImageModel;
import com.fruitvendorapp.utilities.Urls;

import java.util.ArrayList;

public class FruitBannerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<BannerImage> arrayList;
    private String flag = "";

    public FruitBannerAdapter(Context context, ArrayList<BannerImage> arrayList, String flag) {
        this.context = context;
        this.arrayList = arrayList;
        this.flag = flag;
    }

  /*  public void setData(ArrayList<OnBoardModel> arrayList) {

       // notifyDataSetChanged();
    }

*/

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vp_image_slider, null);
        view.setTag(position);
        ImageView ivHeader = view.findViewById(R.id.iv_thumb_image);
        final BannerImage model = arrayList.get(position);
        Log.e("imageffff", model.getImage());
        if (flag.equals("1")) {
            if (model.getImage() != null && !TextUtils.isEmpty(model.getImage())) {
                Glide.with(context).load(Urls.IMAGE_URL + model.getImage()).apply(new RequestOptions().placeholder(R.drawable.banner_img).error(R.drawable.banner_img)).into(ivHeader);
            }
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(model.getImage());
            ivHeader.setImageBitmap(bitmap);
        }
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        Log.e("arraysize------", String.valueOf(arrayList.size()));
        return null != arrayList ? arrayList.size() : 0;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }


}