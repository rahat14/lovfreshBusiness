package com.fruitvendorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fruitvendorapp.R;
import com.fruitvendorapp.interfaces.ItemSelectInterface;
import com.fruitvendorapp.model.CategoryModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CategoryModel> arrayList;
    private ArrayList<Integer> categoryList;
    private ItemSelectInterface itemSelectInterface;
    int category_id;
    private int row_index;

    public CategoryAdapter(Context context) {
        this.context = context;

    }

    public void setData(ArrayList<CategoryModel> arrayList, ArrayList<Integer> categoryList, ItemSelectInterface itemSelectInterface) {
        this.arrayList = arrayList;
        this.categoryList = categoryList;
        this.itemSelectInterface = itemSelectInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_spinner, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final CategoryModel model = arrayList.get(i);
        viewHolder.tvTitle.setText("");
        if (model.getName() != null && model.getName().length() > 0) {
            viewHolder.tvTitle.setText(model.getName());
        }
       /* if (model.isSelected()){
            viewHolder.rbSelect.setChecked(true);
        }else {
            viewHolder.rbSelect.setChecked(false);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.isSelected()) {
                    viewHolder.rbSelect.setChecked(false);
                    model.setSelected(false);
                } else {
                    viewHolder.rbSelect.setChecked(true);
                    model.setSelected(true);
                }
                itemSelectInterface.itemSelect(model.getId(), "");

            }
        });*/

        viewHolder.rbSelect.setOnClickListener(arg0 -> {
            final boolean isChecked = viewHolder.rbSelect.isChecked();
            if (isChecked) {
                model.setSelected(true);
            } else {
                model.setSelected(false);
            }
        });
        viewHolder.rbSelect.setChecked(model.isSelected());
    }

    @Override
    public int getItemCount() {
        return null != arrayList ? arrayList.size() : 0;
    }

    public void updateList(ArrayList<CategoryModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.rb_select)
        CheckBox rbSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}