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
import com.bernardosoler.tfd.Activity.ListadoActivity;
import com.bernardosoler.tfd.R;
import com.bernardosoler.tfd.ui.gallery.Model.Categoria;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.Serializable;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatHolder> {
    List<Categoria> lista;
    int layout;
    Activity activity;
    Context context;
    public CatAdapter(List<Categoria> lista, int layout, Activity activity) {
        this.lista = lista;
        this.layout = layout;
        this.activity = activity;
    }
    @NonNull
    @Override
    public CatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_categoria_list,parent,false);
        context = parent.getContext();
        return new CatAdapter.CatHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CatHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(90, 90);
       // holder.titleText.setText(lista.get(position).getNombreCat());

        Glide.with(holder.itemView.getContext())
                .load(lista.get(position).getUrlCat())
                .apply(requestOptions)
                .into(holder.pic);
        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(holder.itemView.getContext(), ListadoActivity.class);
            intent.putExtra("object", (Serializable) lista.get(position));
            holder.itemView.getContext().startActivity(intent);

        });


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class CatHolder extends RecyclerView.ViewHolder{
        TextView titleText;
        ImageView pic;

        public CatHolder(@NonNull View itemView) {
            super(itemView);
            //titleText=itemView.findViewById(R.id.text_cat_title);
            pic=itemView.findViewById(R.id.img_item_cat);
        }
    }
}
