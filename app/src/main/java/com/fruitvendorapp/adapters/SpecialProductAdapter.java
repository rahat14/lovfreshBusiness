package com.fruitvendorapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fruitvendorapp.R;
import com.fruitvendorapp.activities.ProductDetailActivity;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.model.ImageModel;
import com.fruitvendorapp.model.ProductAttributeModel;
import com.fruitvendorapp.model.ProductModel;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.Urls;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecialProductAdapter extends RecyclerView.Adapter<SpecialProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ProductModel> arrayList;
    private ItemSelectInterface itemSelectInterface;


    public SpecialProductAdapter(Context context, ItemSelectInterface itemSelectInterface) {
        this.context = context;
        this.itemSelectInterface = itemSelectInterface;
    }

    public void setData(ArrayList<ProductModel> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_special_product_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final ProductModel model = arrayList.get(i);
        if (!TextUtils.isEmpty(model.getPromotional_price()) && model.getPromotional_price() != null) {
            double price = Double.parseDouble(model.getPrice());
            double promo_price = Double.parseDouble(model.getPromotional_price());
            System.out.println("int  x = " + (float) price);
            viewHolder.tvPromoPrice.setText("$" + (float) promo_price);
            viewHolder.tvPromoAmt.setText("" + (float) price);
            viewHolder.tvStandPrice.setText("was $" + model.getPrice());
            viewHolder.tvProductAmt.setVisibility(View.GONE);
            viewHolder.tvStandPrice.setVisibility(View.VISIBLE);
            viewHolder.tvPromoAmt.setVisibility(View.GONE);
            viewHolder.tvPromoPrice.setVisibility(View.VISIBLE);

        } else {
            viewHolder.tvProductAmt.setVisibility(View.VISIBLE);
            viewHolder.tvStandPrice.setVisibility(View.GONE);
            viewHolder.tvPromoAmt.setVisibility(View.GONE);
            viewHolder.tvPromoPrice.setVisibility(View.GONE);
            viewHolder.tvProductAmt.setText("$ " + model.getPrice());
        }

        if (model.getHide().equals(true)) {
            viewHolder.rlViewTrans.setVisibility(View.VISIBLE);
        } else {
            viewHolder.rlViewTrans.setVisibility(View.GONE);
        }


        ArrayList<ProductAttributeModel> productAttributes = model.getProductAttributes();
        if (productAttributes != null && !productAttributes.isEmpty() && productAttributes.size() > 0) {
            String unitvalue = productAttributes.get(0).getUom().getValue();
            String unit = productAttributes.get(0).getUom().getName();
            //viewHolder.tvUnit.setText(unitvalue + "" + unit);
            viewHolder.tvProductTitle.setText(model.getTitle() + "(" + unitvalue + unit + ")");
        }
        ArrayList<ImageModel> imageModels = model.getProduct_images();
        if (imageModels != null && !imageModels.isEmpty() && imageModels.size() > 0) {
            String image = imageModels.get(0).getImage();
            Glide.with(context).load(Urls.IMAGE_URL + image).apply(new RequestOptions().placeholder(R.drawable.fruit_placeholder).error(R.drawable.fruit_placeholder)).into(viewHolder.riProductImg);
        }
        viewHolder.llMainView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra(Constant.PRODUCT_ID, model.getProduct_id());
            intent.putExtra(Constant.IS_HIDE, model.getHide());
            context.startActivity(intent);
        });
        viewHolder.llMainView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (viewHolder.llMainView.isSelected()) {
                    viewHolder.llMainView.setSelected(false);
                    model.setSelected(false);
                    Log.e("adapter", "false");
                } else {
                    viewHolder.llMainView.setSelected(true);
                    model.setSelected(true);
                    Log.e("adapter", "true");
                }
                itemSelectInterface.itemSelect("", "");
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return null != arrayList ? arrayList.size() : 0;
    }


    public void updateList(ArrayList<ProductModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_product_title)
        TextView tvProductTitle;
        @BindView(R.id.tv_amount)
        TextView tvProductAmt;
        @BindView(R.id.ri_product_imgs)
        RoundedImageView riProductImg;
        @BindView(R.id.rv_main)
        RelativeLayout rvMain;
        @BindView(R.id.ll_main)
        LinearLayout llmain;
        @BindView(R.id.ll_main_view)
        LinearLayout llMainView;
        @BindView(R.id.rl_view_trans)
        RelativeLayout rlViewTrans;

        @BindView(R.id.tv_promo_price)
        TextView tvPromoPrice;
        @BindView(R.id.tv_cal_amt)
        TextView tvPromoAmt;
        @BindView(R.id.tv_stand_price)
        TextView tvStandPrice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}