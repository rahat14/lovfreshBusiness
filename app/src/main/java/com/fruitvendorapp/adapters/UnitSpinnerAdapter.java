package com.fruitvendorapp.adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.fruitvendorapp.R;
import com.fruitvendorapp.model.UnitModel;

import java.util.ArrayList;
import java.util.List;

public class UnitSpinnerAdapter extends ArrayAdapter<UnitModel> {
    private LayoutInflater flater;
    ArrayList<UnitModel> arrayList;

    public UnitSpinnerAdapter(Activity context, int resouceId, int textviewId, ArrayList<UnitModel> arrayList) {
        super(context, resouceId, textviewId, arrayList);
        flater = context.getLayoutInflater();
        this.arrayList =arrayList;

    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
   @Override
    public int getCount(){
        return arrayList.size();
    }

 @Override
    public UnitModel getItem(int position){
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    private View getCustomView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = flater.inflate(R.layout.unit_spinner, parent, false);
        }
        UnitModel rowItem = getItem(position);
        TextView txtTitle = convertView.findViewById(R.id.tv_title);
        txtTitle.setText(rowItem.getName());
        return convertView;
    }

   /* public int getItemIndexById(String id, ArrayList<UnitModel> arrayList) {
        for (UnitModel item : arrayList) {
            if(item.getId().equals(id)){
                return id.indexOf(String.valueOf(item));
            }
        }
        return 0;
    }*/
}