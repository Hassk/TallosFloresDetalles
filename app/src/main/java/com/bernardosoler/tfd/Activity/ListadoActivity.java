package com.bernardosoler.tfd.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bernardosoler.tfd.Adapter.ProductAdapter;
import com.bernardosoler.tfd.R;
import com.bernardosoler.tfd.lectorQR.IntentIntegrator;
import com.bernardosoler.tfd.lectorQR.IntentResult;
import com.bernardosoler.tfd.ui.gallery.Model.Categoria;
import com.bernardosoler.tfd.ui.gallery.Model.Producto;
import com.bernardosoler.tfd.ui.gallery.Service.ProductoService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListadoActivity extends AppCompatActivity {
    EditText txt_buscar;
    Button bt_buscar ,bt_scan;

    RecyclerView recyclerViewProducto;
    ProductAdapter adapterp;
    private Categoria object;
    private  String idcat;
    List<Producto> produ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        produ= new ArrayList<>();
        txt_buscar= findViewById(R.id.txt_Buscar);
        //bt_buscar=findViewById(R.id.bt_Buscar);
        //bt_scan=findViewById(R.id.bt_Scan);
        /*
        bt_buscar.setOnClickListener(v -> {
            FirebaseDatabase database =FirebaseDatabase.getInstance();
            DatabaseReference reference=database.getReference("PRODUCTO");
            Query query =reference.orderByChild("nombre").equalTo(txt_buscar.getText().toString());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for(DataSnapshot ds:snapshot.getChildren()){
                        Producto p=ds.getValue(Producto.class);
                        p.setId(ds.getKey());
                        Log.e("Info","------------------->"+p.getNombre());
                        Toast.makeText(ListadoActivity.this, p.getNombre()+" "+p.getPrecio(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });
        bt_scan.setOnClickListener(v -> {
            IntentIntegrator can =new IntentIntegrator(ListadoActivity.this);
            can.initiateScan();


        });*/
        GetBundle();
        iniRecyclerView();
        CargarPro();


    }
    public  void GetBundle(){
        object =(Categoria) getIntent().getSerializableExtra("object");
        idcat=object.getId();
    }

    private void CargarPro(){
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference reference =database.getReference("PRODUCTO");
        Query query =reference.orderByChild("idCategoria").equalTo(idcat);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Producto p = null;
                for(DataSnapshot ds:snapshot.getChildren()){
                    p=ds.getValue(Producto.class);
                    p.setId(ds.getKey());
                    produ.add(p);

                }

                recyclerViewProducto.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
    private void iniRecyclerView() {
        recyclerViewProducto=findViewById(R.id.rcList);
        recyclerViewProducto.setLayoutManager(new GridLayoutManager(this,2));
        adapterp=new ProductAdapter(produ,R.layout.viewholder_producto_list,this);
        recyclerViewProducto.setAdapter(adapterp);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            String contenido = intentResult.getContents();
            txt_buscar.setText(contenido);
            Toast.makeText(this, contenido, Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Hubo un problema ", Toast.LENGTH_SHORT).show();
        }


    }




}