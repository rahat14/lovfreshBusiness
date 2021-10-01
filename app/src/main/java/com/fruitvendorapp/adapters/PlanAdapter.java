package com.fruitvendorapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fruitvendorapp.R;
import com.fruitvendorapp.activities.PaymentActivity;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.model.StoreModel;
import com.fruitvendorapp.model.UpgradePlanModel;
import com.fruitvendorapp.utilities.BaseUtility;
import com.fruitvendorapp.utilities.Constant;
import com.fruitvendorapp.utilities.SessionManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    private Context context;
    private ArrayList<UpgradePlanModel> arrayList;
    private int lastSelectedPosition = 0;
    private ItemSelectInterface itemSelectInterface;
    private String vendor_id = "";

    public PlanAdapter(Context context, String vendor_id) {
        this.context = context;
        this.vendor_id = vendor_id;
        new SessionManager(context).setVendorId(vendor_id.toString());
    }

    public void setData(ArrayList<UpgradePlanModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pick_plan, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final UpgradePlanModel model = arrayList.get(i);
        viewHolder.tvPlanNm.setText("");
        if (model.getProduct() != null && model.getProduct().getName() != null && model.getProduct().getName().length() > 0 && model.getAmount() != null && model.getAmount().length() > 0) {
            viewHolder.tvPlanNm.setText(model.getProduct().getName() + "$" + model.getAmount());
        }
        viewHolder.tvIntervalPlan.setText("");
        if (model.getIntervalCount() != null && model.getIntervalCount().length() > 0 && model.getInterval() != null && model.getInterval().length() > 0) {
            viewHolder.tvIntervalPlan.setText(model.getIntervalCount() + " " + model.getInterval());
        }
        viewHolder.tvDescripation.setText("");
        if (model.getDescription() != null && model.getDescription().length() > 0) {
            viewHolder.tvDescripation.setText(model.getDescription());
        }
        viewHolder.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra(Constant.STRIPE_ID, model.getId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return null != arrayList ? arrayList.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_plan_name)
        TextView tvPlanNm;
        @BindView(R.id.tv_interval)
        TextView tvIntervalPlan;
        @BindView(R.id.tv_descripation)
        TextView tvDescripation;
        @BindView(R.id.tv_select)
        TextView tvSelect;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}