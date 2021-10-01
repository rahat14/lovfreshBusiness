package com.fruitvendorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fruitvendorapp.R;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.model.PromoCodeModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PromoCodeAdapter extends RecyclerView.Adapter<PromoCodeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PromoCodeModel> arrayList;
    private ItemSelectInterface itemSelectInterface;

    public PromoCodeAdapter(Context context,ItemSelectInterface itemSelectInterface) {
        this.context = context;
        this.itemSelectInterface = itemSelectInterface;

    }

    public void setData(ArrayList<PromoCodeModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_promo_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final PromoCodeModel model = arrayList.get(i);
        viewHolder.tvPromoCode.setText(model.getCode());
        viewHolder.tvPromoName.setText(model.getName());
        if(model.getPercentage().equals(true)){
            viewHolder.tvSaveRupees.setText("You Save "+model.getValue()+"%");
        }else {
            viewHolder.tvSaveRupees.setText("You Save $" + model.getValue());
        }

        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemSelectInterface.itemSelect(model.getId(),"");
            }
        });

    }

    @Override
    public int getItemCount() {
        return null != arrayList ? arrayList.size() : 0;
    }


    public void updateList(ArrayList<PromoCodeModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_promo_code)
        TextView tvPromoCode;
        @BindView(R.id.tv_promo_name)
        TextView tvPromoName;
        @BindView(R.id.tv_save_ruppes)
        TextView tvSaveRupees;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}