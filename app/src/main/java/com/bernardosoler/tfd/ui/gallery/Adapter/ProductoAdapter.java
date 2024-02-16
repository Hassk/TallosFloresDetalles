package com.bernardosoler.tfd.ui.gallery.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bernardosoler.tfd.R;
import com.bernardosoler.tfd.ui.gallery.Model.Producto;

import java.util.List;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoHolder> {
    List<Producto> lista;
    int layout;
    Activity activity;

    public ProductoAdapter(List<Producto> lista, int layout, Activity activity) {
        this.lista = lista;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ProductoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        return new ProductoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoHolder holder, int position) {
          Producto producto = lista.get(position);
          holder.txtid.setText(producto.getId());
          holder.txtnombre.setText(producto.getNombre());
          holder.txtprecio.setText(String.valueOf(producto.getPrecio()));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ProductoHolder extends RecyclerView.ViewHolder{
        TextView txtid,txtnombre,txtprecio;
        public ProductoHolder(@NonNull View itemView) {
            super(itemView);
            txtid= itemView.findViewById(R.id.item_id);
            txtnombre= itemView.findViewById(R.id.item_nombre);
            txtprecio= itemView.findViewById(R.id.item_precio);
        }
    }
}
