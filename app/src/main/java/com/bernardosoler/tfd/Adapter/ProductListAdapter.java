package com.bernardosoler.tfd.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bernardosoler.tfd.Activity.DetailActivity;
import com.bernardosoler.tfd.Domain.ProductDomain;
import com.bernardosoler.tfd.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import java.util.ArrayList;

public class ProductListAdapter  extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
   ArrayList<ProductDomain> items;
   Context context;

    public ProductListAdapter(ArrayList<ProductDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_producto_list,parent,false);
        context=parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleText.setText(items.get(position).getTittle());
        holder.timeText.setText(items.get(position).getTime()+" min");
        holder.ScoreText.setText(""+items.get(position).getScore());
        int drawableResorceId=holder.itemView.getResources().getIdentifier(items.get(position).getPicurl(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResorceId)
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("object",items.get(position));
            holder.itemView.getContext().startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
         TextView titleText,timeText,ScoreText;
         ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText=itemView.findViewById(R.id.titleText);
            timeText=itemView.findViewById(R.id.timeText);
            ScoreText=itemView.findViewById(R.id.scoreText);
            pic=itemView.findViewById(R.id.pic);

        }
    }
















}
