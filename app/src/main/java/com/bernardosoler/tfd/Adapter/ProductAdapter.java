package com.bernardosoler.tfd.Adapter;

import android.app.Activity;
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
import com.bernardosoler.tfd.R;
import com.bernardosoler.tfd.ui.gallery.Model.Producto;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;

import java.io.Serializable;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    List<Producto> lista;
    int layout;
    Activity activity;
    Context context;

    public ProductAdapter(List<Producto> lista, int layout, Activity activity) {
        this.lista = lista;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_producto_list,parent,false);
        context = parent.getContext();
        return new ProductAdapter.ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        holder.titleText.setText(lista.get(position).getNombre());
        holder.timeText.setText(lista.get(position).getPrecio().toString());
        holder.ScoreText.setText("5.0");
        Glide.with(holder.itemView.getContext())
                .load(lista.get(position).getUrl())
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.pic);
        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("object", (Serializable) lista.get(position));
            holder.itemView.getContext().startActivity(intent);

        });


    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder{
        TextView titleText,timeText,ScoreText;
        ImageView pic;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            titleText=itemView.findViewById(R.id.titleText);
            timeText=itemView.findViewById(R.id.timeText);
            ScoreText=itemView.findViewById(R.id.scoreText);
            pic=itemView.findViewById(R.id.pic);
        }
    }
}
