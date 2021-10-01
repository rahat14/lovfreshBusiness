package com.fruitvendorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fruitvendorapp.R;
import com.fruitvendorapp.model.OrderProductModel;
import com.fruitvendorapp.utilities.BaseUtility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderProductModel> arrayList;

    public OrderProductAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<OrderProductModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_or_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final OrderProductModel model = arrayList.get(i);
        viewHolder.tvProductNm.setText("");
        if (model.getProduct() != null && model.getProduct().getTitle() != null && model.getProduct().getTitle().length() > 0) {
            viewHolder.tvProductNm.setText(model.getProduct().getTitle());
        }
        viewHolder.tvQuantity.setText("");
        if (model.getQuantity() != null && model.getQuantity().length() > 0 && model.getPrice() != null && model.getPrice().length() > 0) {
            viewHolder.tvQuantity.setText(model.getQuantity() + "x" + model.getPrice());
        }
        viewHolder.tvAmount.setText("");
        if (model.getPrice() != null && model.getPrice().length() > 0) {
            float totalAmount = BaseUtility.totalPrice(model.getPrice(), model.getQuantity());
            viewHolder.tvAmount.setText("$" + String.format("%.2f", totalAmount));
        }
    }

    @Override
    public int getItemCount() {
        return null != arrayList ? arrayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_product_nm)
        TextView tvProductNm;
        @BindView(R.id.tv_quantity)
        TextView tvQuantity;
        @BindView(R.id.tv_amount)
        TextView tvAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}