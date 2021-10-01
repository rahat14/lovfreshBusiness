package com.fruitvendorapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fruitvendorapp.R;
import com.fruitvendorapp.activities.EditAddressActivity;
import com.fruitvendorapp.model.AddressModel;
import com.fruitvendorapp.utilities.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AddressModel> arrayList;
    private int lastSelectedPosition = 0;
    private String arraylistsize = "";

    public MyAddressAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<AddressModel> arrayList) {
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_address, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final AddressModel model = arrayList.get(i);
        viewHolder.tvAddress.setText("");
        if (model.getAddress() != null && model.getAddress().length() > 0) {
            viewHolder.tvAddress.setText(model.getAddress());
        }
        // viewHolder.tvEmailId.setText();
        viewHolder.tvPhone.setText("");
        if (model.getMobile() != null && model.getMobile().length() > 0) {
            viewHolder.tvPhone.setText(model.getMobile());
        }
        viewHolder.tvEmailId.setText("");
        if (model.getEmail() != null && model.getEmail().length() > 0) {
            viewHolder.tvEmailId.setText(model.getMobile());
        }
        viewHolder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditAddressActivity.class);
                intent.putExtra(Constant.ADDRESS_ID, model.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null != arrayList ? arrayList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_email)
        TextView tvEmailId;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.iv_edit)
        ImageView ivEdit;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}