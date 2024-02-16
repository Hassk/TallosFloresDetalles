package com.bernardosoler.tfd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.bernardosoler.tfd.Adapter.ProductAdapter;
import com.bernardosoler.tfd.R;
import com.bernardosoler.tfd.ui.gallery.Adapter.FavoritoAdapter;
import com.bernardosoler.tfd.ui.gallery.Model.Categoria;
import com.bernardosoler.tfd.ui.gallery.Model.Favorito;
import com.bernardosoler.tfd.ui.gallery.Model.Producto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Favoritos extends AppCompatActivity {
    EditText txt_buscar;

    RecyclerView recyclerViewProducto;
    FavoritoAdapter adapterp;
    private  String idcliente;
    List<Favorito> produ;
    List<Producto> produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        produ= new ArrayList<>();
        produto=new ArrayList<>();
        txt_buscar= findViewById(R.id.txt_BuscarFab);
        GetBundle();
        iniRecyclerView();
        CargarPro();

    }
    private void favoritos(Favorito favorito){
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference reference =database.getReference("PRODUCTO");
        Query query = reference.orderByKey().equalTo(favorito.getProducto());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Producto pro=null ;
                    for (DataSnapshot d : snapshot.getChildren()) {
                        pro = d.getValue(Producto.class);
                        produto.add(pro);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



    }

    private void CargarPro() {
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference reference =database.getReference("FAVORITOS");
        Query query =reference.orderByChild("cliente").equalTo(idcliente);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Favorito p=null ;
                for(DataSnapshot ds:snapshot.getChildren()){
                    p=ds.getValue(Favorito.class);
                    favoritos(p);


                }


                recyclerViewProducto.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void iniRecyclerView() {
        recyclerViewProducto=findViewById(R.id.rcListFav);
        recyclerViewProducto.setLayoutManager(new GridLayoutManager(this,1));
        adapterp= new FavoritoAdapter(produto,R.layout.viewholder_favoritos,this);
        recyclerViewProducto.setAdapter(adapterp);
        
    }

    private void GetBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idcliente= bundle.getString("email");

        }



    }
}