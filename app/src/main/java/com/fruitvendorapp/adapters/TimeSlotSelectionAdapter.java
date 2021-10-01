package com.fruitvendorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fruitvendorapp.R;
import com.fruitvendorapp.interfaces.ItemSelectTimeInterface;

import com.fruitvendorapp.model.TimeSlotModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeSlotSelectionAdapter extends RecyclerView.Adapter<TimeSlotSelectionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TimeSlotModel> arrayList;
    boolean isActive = false;
    private ItemSelectTimeInterface itemSelectTimeInterface;
    private int timeSelectedPosition = -1;
    private AlertDialog alertDialog;

    public TimeSlotSelectionAdapter(Context context, ItemSelectTimeInterface itemSelectTimeInterface, AlertDialog alertDialog) {
        this.context = context;
        this.itemSelectTimeInterface = itemSelectTimeInterface;
        this.alertDialog = alertDialog;

    }

    public void setData(ArrayList<TimeSlotModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_time_slot, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final TimeSlotModel model = arrayList.get(i);
        viewHolder.tvTime.setText(model.getStrtTimeEndTime());
        viewHolder.rbTimeSelect.setChecked(model.getIsActive());
        viewHolder.rbTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getIsActive()) {
                    itemSelectTimeInterface.itemTime(model.getStrtTimeEndTime(), model.getStartTime(), model.getEndTime(), "off", alertDialog);
                } else {
                    itemSelectTimeInterface.itemTime(model.getStrtTimeEndTime(), model.getStartTime(), model.getEndTime(), "on", alertDialog);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null != arrayList ? arrayList.size() : 0;
    }

    public void updateList(ArrayList<TimeSlotModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.sc_select)
        SwitchCompat scSelect;
        @BindView(R.id.rb_time)
        RadioButton rbTimeSelect;
        @BindView(R.id.ll_view)
        LinearLayout llView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            /*View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timeSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                }
            };
            rbTimeSelect.setOnClickListener(clickListener);*/

        }
    }
}