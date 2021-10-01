package com.fruitvendorapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fruitvendorapp.R;
import com.fruitvendorapp.model.OrderModel;
import com.fruitvendorapp.popups.OrderDetailDeliverPopup;
import com.fruitvendorapp.popups.OrderDetailPickupPopup;
import com.fruitvendorapp.popups.OrderNotificationPopup;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.Urls;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderModel> arrayList;
    private int lastSelectedPosition = 0;
    private String arraylistsize = "";

    public NotificationAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<OrderModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification_store, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final OrderModel model = arrayList.get(i);
        viewHolder.tvShopTitle.setText("");
        if (model.getVendor() != null && model.getVendor().getTitle() != null && model.getVendor().getTitle().length() > 0) {
            viewHolder.tvShopTitle.setText(model.getVendor().getTitle());
        }
        viewHolder.tvAddress.setText("");
        if (model.getVendor() != null && model.getVendor().getAddress() != null && model.getVendor().getAddress().getAddress().length() > 0) {
            viewHolder.tvAddress.setText(model.getVendor().getAddress().getAddress());
        }
        viewHolder.tvOrderStatus.setText("");
        if (model.getGetStatus() != null && model.getGetStatus().length() > 0) {
            viewHolder.tvOrderStatus.setText(model.getGetStatus());
        }
       /* viewHolder.tvOrderDates.setText("");
        if (model.getCreatedAt() != null && model.getCreatedAt().length() > 0) {
            String order_date = model.getCreatedAt();
            String splitDate = order_date.substring(0, 10);
            viewHolder.tvOrderDates.setText(splitDate);
        }*/
        viewHolder.tvOrderDates.setText(BaseUtility.toDefaultFormattedDateStr(model.getCreatedAt()));
        viewHolder.tvOrderType.setText("");
        if (model.getOrderType() != null && model.getOrderType().length() > 0) {
            viewHolder.tvOrderType.setText(model.getOrderType());
        }
        viewHolder.tvOrderId.setText("");
        if (model.getOrderNumber() != null && model.getOrderNumber().length() > 0) {
            viewHolder.tvOrderId.setText(model.getOrderNumber());
        }
        viewHolder.tvSlotSelected.setText("");
        if (model.getDeliverDate() != null && model.getDeliverDate().length() > 0) {
            viewHolder.tvSlotSelected.setText(BaseUtility.convertDateFormat(model.getDeliverDate()) + "-" + BaseUtility.parseDateToddMMyyyy(model.getStartTime())+ "-" + BaseUtility.parseDateToddMMyyyy(model.getEndTime()));
        }
        if (model.getVendor().getImageUrl() != null && model.getVendor().getImageUrl().length() > 0)
            Glide.with(context).load(Urls.IMAGE_URL + model.getVendor().getImageUrl()).apply(new RequestOptions().placeholder(R.drawable.blank_img).error(R.drawable.blank_img)).into(viewHolder.riShopImage);
        if (model.getStatus().equals("completed")) {
            viewHolder.ivNotification.setBackgroundResource(R.drawable.ic_notification_complete);
        } else {
            viewHolder.ivNotification.setBackgroundResource(R.drawable.ic_notification_black);
        }
        viewHolder.rlMainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getStatus().equals("created")){
                    Intent intent1 = new Intent(context, OrderNotificationPopup.class);
                    intent1.putExtra(Constant.ORDER_ID, model.getId());
                    intent1.putExtra(Constant.FLAG, "1");
                    context.startActivity(intent1);
                } else {
                    if (model.getOrderType().equals("pickup")) {
                        Intent intent = new Intent(context, OrderDetailPickupPopup.class);
                        intent.putExtra(Constant.ORDER_ID, model.getId());
                        intent.putExtra(Constant.FLAG, "1");
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, OrderDetailDeliverPopup.class);
                        intent.putExtra(Constant.ORDER_ID, model.getId());
                        intent.putExtra(Constant.FLAG, "1");
                        context.startActivity(intent);
                    }
                } }
        });
    }


    @Override
    public int getItemCount() {
        return null != arrayList ? arrayList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_title)
        TextView tvShopTitle;
        @BindView(R.id.ri_shop_img)
        CircleImageView riShopImage;
        @BindView(R.id.frame_view)
        RelativeLayout rlMainView;
        @BindView(R.id.iv_noti)
        ImageView ivNotification;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.tv_order_id)
        TextView tvOrderId;
        @BindView(R.id.tv_order_type)
        TextView tvOrderType;
        @BindView(R.id.tv_orderdate)
        TextView tvOrderDates;
        @BindView(R.id.tv_pickup_slot)
        TextView tvSlotSelected;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
           /* View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            };
            rbSelection.setOnClickListener(clickListener);
        }*/
            //}
        }
    }
}