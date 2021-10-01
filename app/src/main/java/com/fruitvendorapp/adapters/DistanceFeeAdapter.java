package com.fruitvendorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fruitvendorapp.R;
import com.fruitvendorapp.interfaces.ItemSelectTimeInterface;
import com.fruitvendorapp.model.DeliveryFeeModel;
import com.fruitvendorapp.model.DistanceFeeModel;
import com.fruitvendorapp.model.TimeSlotModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DistanceFeeAdapter extends RecyclerView.Adapter<DistanceFeeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DistanceFeeModel> distanceList;

    public DistanceFeeAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<DistanceFeeModel> arrayList) {
        this.distanceList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_distance_fee, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final DistanceFeeModel model = distanceList.get(i);
        viewHolder.tvDistance.setText(model.getDistance());
        viewHolder.txtFee.setText(model.getDeliveryFee());
    }

    @Override
    public int getItemCount() {
        return null != distanceList ? distanceList.size() : 0;
    }

    public void updateList(ArrayList<DistanceFeeModel> list) {
        distanceList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_fee)
        TextView txtFee;
        @BindView(R.id.tv_dist)
        TextView tvDistance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}