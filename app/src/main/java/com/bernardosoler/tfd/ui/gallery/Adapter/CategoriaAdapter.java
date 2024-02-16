package com.bernardosoler.tfd.ui.gallery.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bernardosoler.tfd.R;
import com.bernardosoler.tfd.ui.gallery.Model.Categoria;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaHolder> {
    List<Categoria> list;
    int layout;
    Activity activity;

    public List<Categoria> getList() {
        return list;
    }

    public CategoriaAdapter(List<Categoria> list, int layout, Activity activity) {
        this.list = list;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CategoriaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        return new CategoriaHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull CategoriaHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transforms(new CircleCrop());
        requestOptions.override(60, 60);
        Categoria categoria = list.get(position);
        holder.txtid.setText(categoria.getId());
        holder.txtnombre.setText(categoria.getNombreCat());
        holder.txtestado.setText(String.valueOf(categoria.getEstado()));
        Glide.with(holder.itemView.getContext())
                .load(categoria.getUrlCat()).apply(requestOptions)
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.img_Cat);

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class CategoriaHolder extends RecyclerView.ViewHolder{
        TextView txtid,txtnombre,txtestado;
        ImageView img_Cat;

        public CategoriaHolder(@NonNull View itemView) {
            super(itemView);
            txtid= itemView.findViewById(R.id.item_idcat);
            txtnombre= itemView.findViewById(R.id.item_nombrecat);
            txtestado= itemView.findViewById(R.id.item_estado);
            img_Cat=itemView.findViewById(R.id.img_cat);
        }
    }

}
