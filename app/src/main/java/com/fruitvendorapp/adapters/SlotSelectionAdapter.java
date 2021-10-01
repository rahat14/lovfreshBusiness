package com.fruitvendorapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fruitvendorapp.R;
import com.fruitvendorapp.activities.AdditionalSettingActivity;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.interfaces.ItemSelectTimeInterface;
import com.fruitvendorapp.interfaces.ItemSelectTimeListInterface;
import com.fruitvendorapp.model.DateAndTimeSlotModel;
import com.fruitvendorapp.model.DeliveryType;
import com.fruitvendorapp.model.SlotSelectionModel;
import com.fruitvendorapp.model.TimeSlotModel;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.DialogUtility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SlotSelectionAdapter extends RecyclerView.Adapter<SlotSelectionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DateAndTimeSlotModel> arrayList;
    //  boolean isActive = false;
    private ItemSelectInterface itemSelectInterface;
    private ItemSelectTimeListInterface itemSelectTimeListInterface;
    private String type = "";

    public SlotSelectionAdapter(Context context, ItemSelectInterface itemSelectInterface, ItemSelectTimeListInterface itemSelectTimeListInterface) {
        this.context = context;
        this.itemSelectInterface = itemSelectInterface;
        this.itemSelectTimeListInterface = itemSelectTimeListInterface;

    }

    public void setData(ArrayList<DateAndTimeSlotModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_addition_setting, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final DateAndTimeSlotModel model = arrayList.get(i);
        viewHolder.tvDate.setText(model.getDate());
        if (model.isIs_selected()) {
            viewHolder.scSelect.setChecked(true);
            model.setIs_selected(true);
        } else {
            viewHolder.scSelect.setChecked(false);
            model.setIs_selected(false);
        }
        viewHolder.scSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isIs_selected()) {
                    model.setIs_selected(false);
                    viewHolder.scSelect.setChecked(false);
                    type = "off";
                    model.setIs_selected(false);
                    Log.e("false", String.valueOf(type));
                } else {
                    model.setIs_selected(true);
                    viewHolder.scSelect.setChecked(true);
                    type = "on";
                    model.setIs_selected(true);
                    Log.e("true", String.valueOf(type));
                }
                itemSelectInterface.itemSelect(model.getDate(), type);
                notifyDataSetChanged();
            }
        });



       /* viewHolder.scSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.e("You are on:", "Checked");
                    type="on";
                    model.setIs_selected(true);
                    viewHolder.scSelect.setChecked(true);
                    itemSelectInterface.itemSelect(model.getDate(),type);
                } else {
                    Log.e("You are off:", " Not Checked");
                    type="off";
                    model.setIs_selected(false);
                    viewHolder.scSelect.setChecked(false);
                    itemSelectInterface.itemSelect(model.getDate(),type);
                }

            }
        });*/
        viewHolder.tvTimeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isIs_selected()) {
                    itemSelectTimeListInterface.itemTime(model.isIs_selected(), model.getTime(), model.getDate());
                } else {
                    BaseUtility.toastMsg(context, context.getResources().getString(R.string.enable_date_settings));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null != arrayList ? arrayList.size() : 0;
    }

    public void updateList(ArrayList<DateAndTimeSlotModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_time_slot)
        TextView tvTimeSlot;
        @BindView(R.id.sc_select)
        SwitchCompat scSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}