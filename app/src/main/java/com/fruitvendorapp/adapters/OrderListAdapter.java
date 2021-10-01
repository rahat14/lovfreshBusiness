package com.fruitvendorapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fruitvendorapp.R;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.model.OrderModel;
import com.fruitvendorapp.popups.OrderDetailDeliverPopup;
import com.fruitvendorapp.popups.OrderDetailPickupPopup;
import com.fruitvendorapp.popups.OrderNotificationPopup;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderModel> arrayList;
    private ItemSelectInterface itemSelectInterface;
    private String status = "";

    public OrderListAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<OrderModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final OrderModel model = arrayList.get(i);
        viewHolder.tvOrderId.setText("");
        if (model.getOrderNumber() != null && model.getOrderNumber().length() > 0) {
            viewHolder.tvOrderId.setText(model.getOrderNumber());
        }
        viewHolder.tvOrderDate.setText("");
        if (model.getCreatedAt() != null && model.getCreatedAt().length() > 0) {
            String order_date = model.getCreatedAt();
            String splitDate = order_date.substring(0, 10);
            viewHolder.tvOrderDate.setText(splitDate);
        }
        viewHolder.tvOrderStatus.setText("");
        if (model.getGetStatus() != null && model.getGetStatus().length() > 0) {
            viewHolder.tvOrderStatus.setText(model.getGetStatus());
        }
        if (!TextUtils.isEmpty(model.getDeliverDate())) {
            viewHolder.tvOrderComplete.setText(model.getDeliverDate() + "\n" + BaseUtility.parseDateToddMMyyyy(model.getStartTime()) + " to " + BaseUtility.parseDateToddMMyyyy(model.getEndTime()));
        } else {
            viewHolder.tvOrderComplete.setText("-");
        }


       /* viewHolder.tvOrderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(model.getGetStatus().equals("created")){
                    status="accepted";
                }else if(model.getGetStatus().equals("packed")){
                    status= "";
                }

                itemSelectInterface.itemSelect(model.getId(),status);
            }
        });*/

        viewHolder.llView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getStatus().equals("created")) {
                    Intent intent1 = new Intent(context, OrderNotificationPopup.class);
                    intent1.putExtra(Constant.ORDER_ID, model.getId());
                    context.startActivity(intent1);
                } else {
                    if (model.getOrderType().equals("pickup")) {
                        Intent intent = new Intent(context, OrderDetailPickupPopup.class);
                        intent.putExtra(Constant.ORDER_ID, model.getId());
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, OrderDetailDeliverPopup.class);
                        intent.putExtra(Constant.ORDER_ID, model.getId());
                        context.startActivity(intent);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return null != arrayList ? arrayList.size() : 0;
    }

    public void updateList(ArrayList<OrderModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.order_id)
        TextView tvOrderId;
        @BindView(R.id.order_date)
        TextView tvOrderDate;
        @BindView(R.id.order_status)
        TextView tvOrderStatus;
        @BindView(R.id.tv_comp_order_date)
        TextView tvOrderComplete;
        @BindView(R.id.iv_detail)
        ImageView ivDetail;
        @BindView(R.id.ll_view)
        LinearLayout llView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}