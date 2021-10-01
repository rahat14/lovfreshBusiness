package com.fruitvendorapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fruitvendorapp.R;
import com.fruitvendorapp.activities.AdditionalSettingActivity;
import com.fruitvendorapp.model.DeliveryType;
import com.fruitvendorapp.utilities.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliverTypeAdapter extends RecyclerView.Adapter<DeliverTypeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DeliveryType> arrayList;
    boolean isActive = false;

    public DeliverTypeAdapter(Context context) {
        this.context = context;

    }

    public void setData(ArrayList<DeliveryType> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_deliver_type, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final DeliveryType model = arrayList.get(i);
        viewHolder.tvTitle.setText("");
        if (model.getName() != null && model.getName().length() > 0) {
            viewHolder.tvTitle.setText(model.getName());
        }
        if (model.getIsActive().equals(true)) {
            viewHolder.scSelect.setChecked(true);
            model.setIsActive(true);
            model.setSelected(true);
        } else {
            viewHolder.scSelect.setChecked(false);
            model.setIsActive(false);
            model.setSelected(false);
        }
        viewHolder.scSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.isSelected()) {
                    model.setSelected(false);
                    viewHolder.scSelect.setChecked(false);
                    isActive = false;
                    model.setIsActive(isActive);
                    Log.e("false", String.valueOf(isActive));
                    //  isSelectedWithChild =false;
                } else {
                    model.setSelected(true);
                    viewHolder.scSelect.setChecked(true);
                    isActive = true;
                    model.setIsActive(isActive);
                    Log.e("true", String.valueOf(isActive));
                }
                notifyDataSetChanged();
            }
        });
        viewHolder.tvAdditionalSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AdditionalSettingActivity.class);
                intent.putExtra(Constant.NAME, model.getName());
                intent.putExtra(Constant.DELIVERY_TYPES, model.getId());
                intent.putExtra(Constant.MINIMUM_ORDER_VALUE, model.getMinimumOrderValue());
                context.startActivity(intent);
            }
        });

     /*   viewHolder.scSelect.setOnClickListener(arg0 -> {
            final boolean isChecked = viewHolder.scSelect.isSelected();
            if (isChecked) {
                model.setSelected(true);
                viewHolder.scSelect.setChecked(true);
                isActive = true;
                model.setIsActive(isActive);
                Log.e("true", String.valueOf(isActive));
            } else {
                model.setSelected(false);
                viewHolder.scSelect.setChecked(false);
                isActive =false;
                model.setIsActive(isActive);
                Log.e("false", String.valueOf(isActive));
            }
        });*/
        // viewHolder.scSelect.setSelected(model.isSelected());
    }

    @Override
    public int getItemCount() {
        return null != arrayList ? arrayList.size() : 0;
    }

    public void updateList(ArrayList<DeliveryType> list) {
        arrayList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.rb_select)
        CheckBox rbSelect;
        @BindView(R.id.sc_select)
        SwitchCompat scSelect;
        @BindView(R.id.tv_add_setting)
        TextView tvAdditionalSetting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}