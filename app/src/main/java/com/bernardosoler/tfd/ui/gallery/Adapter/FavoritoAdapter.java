package com.bernardosoler.tfd.ui.gallery.Adapter;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bernardosoler.tfd.R;
import com.bernardosoler.tfd.ui.gallery.Model.Favorito;
import com.bernardosoler.tfd.ui.gallery.Model.Producto;

import org.xmlpull.v1.XmlPullParser;

import java.util.List;

public class FavoritoAdapter extends RecyclerView.Adapter<FavoritoAdapter.FavoritoHolder> {
    List<Producto> list;
    int layout;
    Activity activity;

    public FavoritoAdapter(List<Producto> list, int layout, Activity activity) {
        this.list = list;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FavoritoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( layout, parent, false);
        return new FavoritoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritoHolder holder, int position) {
        Producto producto =list.get(position);
        holder.txtprecio.setText(producto.getPrecio().toString());
        holder.txtnombre.setText(producto.getNombre());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class FavoritoHolder extends RecyclerView.ViewHolder{
        TextView txtnombre,txtprecio;

        public FavoritoHolder(@NonNull View itemView) {
            super(itemView);

            txtnombre= itemView.findViewById(R.id.txt_fab_title);
            txtprecio= itemView.findViewById(R.id.txt_fab_pre);

        }
    }
}
