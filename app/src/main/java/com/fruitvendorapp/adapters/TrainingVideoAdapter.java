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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fruitvendorapp.R;
import com.fruitvendorapp.activities.VideoPlayActivity;
import com.fruitvendorapp.model.TrainingModel;
import com.fruitvendorapp.utilities.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainingVideoAdapter extends RecyclerView.Adapter<TrainingVideoAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TrainingModel> arrayList;


    public TrainingVideoAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<TrainingModel> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_taining, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final TrainingModel model = arrayList.get(i);
        viewHolder.tvHeadLine.setText(model.getName());
        Glide.with(context).load(model.getImage()).apply(new RequestOptions().placeholder(R.drawable.ic_video).error(R.drawable.ic_video)).into(viewHolder.ivVideoImage);
        viewHolder.ivPlay.setVisibility(View.VISIBLE);
        viewHolder.ivVideoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoPlayActivity.class);
                intent.putExtra(Constant.LINK,model.getLink());
                context.startActivity(intent);
            }
        });
    }

    public void updateList(ArrayList<TrainingModel> list) {
        arrayList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return null != arrayList ? arrayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_head_line)
        TextView tvHeadLine;
        @BindView(R.id.iv_play)
        ImageView ivPlay;
        @BindView(R.id.iv_video_img)
        ImageView ivVideoImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}