package com.bernardosoler.tfd.ui.gallery.InputProducto;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.bernardosoler.tfd.R;

import com.bernardosoler.tfd.ui.gallery.Adapter.CategoriaAdapter;
import com.bernardosoler.tfd.ui.gallery.Adapter.ProductoAdapter;
import com.bernardosoler.tfd.ui.gallery.Model.Categoria;
import com.bernardosoler.tfd.ui.gallery.Model.Producto;
import com.bernardosoler.tfd.ui.gallery.Service.CategoriaService;
import com.bernardosoler.tfd.ui.gallery.Service.ProductoService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CargarProducto extends AppCompatActivity {
    EditText txtNombre,txtPrecio,txtNombrecat,txtdes;
    RecyclerView rc,Rp;
    Spinner sp;
    String idcategoria="";
    //Constantes de accion
    public static final int Code_Camera=21;
    public static final int Code_Gallery=22;

    public static  final int Code_Gallery_cat =23;
    public static  final int Code_Camera_cat =24;
    //Uri
    private Uri uriImagen;
    Button btn_gallery,btn_camera,btn_gallery_cat;
    ImageView img,imgcat;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_producto);
        txtNombre=findViewById(R.id.txtnombre);
        txtPrecio=findViewById(R.id.txtprecio);
        txtNombrecat=findViewById(R.id.txtnombrecat);
        rc=findViewById(R.id.rc);
        Button btnpro=findViewById(R.id.btnaddpro);
        Button btncat=findViewById(R.id.btnaddcat);
        btn_gallery=findViewById(R.id.bt_abrir_Galeria);
        btn_camera=findViewById(R.id.bt_abrir_Camara);
        btn_gallery_cat=findViewById(R.id.bt_GalleryCat);

        img=findViewById(R.id.producto_img);
        imgcat=findViewById(R.id.categoria_img);
         sp=findViewById(R.id.spinner);
         Rp=findViewById(R.id.rPro);
         txtdes=findViewById(R.id.txtDes);
        LinearLayoutManager lm= new LinearLayoutManager(this);
        lm.setOrientation(RecyclerView.VERTICAL);
        LinearLayoutManager lp= new LinearLayoutManager(this);
        lp.setOrientation(RecyclerView.VERTICAL);
        rc.setLayoutManager(lm);
        Rp.setLayoutManager(lp);
        ProductoAdapter adapterp=new ProductoAdapter(ProductoService.productos,R.layout.item,this);
        CategoriaAdapter adapter= new  CategoriaAdapter(CategoriaService.categorias, R.layout.itemcat,this);
        rc.setAdapter(adapter);
        Rp.setAdapter(adapterp);
        cargarDatos();
        listarSpiner();
        cargarProductos();




        //guarda en firebase
        btnpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                agregar();
            }
        });
        btncat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregar2();
            }
        });
        btn_gallery.setOnClickListener(v->{
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,Code_Gallery);
        } );
        btn_gallery_cat.setOnClickListener(v->{
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,Code_Gallery_cat);

        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Code_Gallery:
                if (data!=null){
                    uriImagen=data.getData();
                    try {
                        Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uriImagen);
                        img.setImageBitmap(bitmap);

                    }catch (Exception e){
                          e.printStackTrace();
                    }
                }
                break;
            case Code_Gallery_cat:
                uriImagen=data.getData();
                try {
                    Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uriImagen);
                    imgcat.setImageBitmap(bitmap);

                }catch (Exception e){
                    e.printStackTrace();
                }

                break;

        }
    }

    public  void  listarSpiner(){
       final List<Categoria> categoris= new ArrayList<>();
        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference reference =database.getReference();
        reference.child("CATEGORIA").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                   for (DataSnapshot ds: snapshot.getChildren()){
                       String id=ds.getKey();
                       String nombrecat=ds.child("nombreCat").getValue().toString();
                       int est =Integer.parseInt(ds.child("estado").getValue().toString());
                       categoris.add(new Categoria(id,nombrecat,est));
                   }
                   ArrayAdapter<Categoria> adapter =new ArrayAdapter<>(CargarProducto.this,android.R.layout.simple_dropdown_item_1line, categoris);
                   sp.setAdapter(adapter);

                    sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            idcategoria =categoris.get(position).getId();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });{;


                    };
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


    public void cargarProductos(){
        final FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference reference =database.getReference("PRODUCTO");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Producto producto = snapshot.getValue(Producto.class);
                producto.setId(snapshot.getKey());

                if (!ProductoService.productos.contains(producto)){
                    ProductoService.addProducto(producto);
                }
                Rp.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Producto producto = snapshot.getValue(Producto.class);
                producto.setId(snapshot.getKey());

                if (ProductoService.productos.contains(producto)){
                    ProductoService.updateProducto(producto);
                }
                Rp.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Producto producto = snapshot.getValue(Producto.class);
                producto.setId(snapshot.getKey());

                if (ProductoService.productos.contains(producto)){
                    ProductoService.removeProducto(producto);
                }
                Rp.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });{



        }



    }
    public void cargarDatos(){
        final FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference reference =database.getReference("CATEGORIA");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Categoria categoria = snapshot.getValue(Categoria.class);
                categoria.setId(snapshot.getKey());

                if (!CategoriaService.categorias.contains(categoria)){
                    CategoriaService.addCategoria(categoria);
                }
                rc.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Categoria categoria = snapshot.getValue(Categoria.class);
                categoria.setId(snapshot.getKey());
                if (CategoriaService.categorias.contains(categoria)){
                    CategoriaService.updateCategoria(categoria);
                }
                rc.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Categoria categoria = snapshot.getValue(Categoria.class);
                categoria.setId(snapshot.getKey());
                if (CategoriaService.categorias.contains(categoria)){
                    CategoriaService.removeCategoria(categoria);
                }
                rc.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void agregar2(){
        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference folderRef=storageReference.child("CATEGORIAS_IMG");
        StorageReference fotoRef=folderRef.child(new Date().toString());
        fotoRef.putFile(uriImagen).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri>  uriTask= taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri downLoadUri= uriTask.getResult();
                Categoria categoria =new Categoria();
                categoria.setNombreCat(txtNombrecat.getText().toString());
                categoria.setEstado(1);
                categoria.setUrlCat(downLoadUri.toString());
                FirebaseDatabase database =FirebaseDatabase.getInstance();
                DatabaseReference reference =database.getReference("CATEGORIA");
                reference.push().setValue(categoria);
                txtNombrecat.setText("");
            }
        });




    }
    public void agregar(){

        StorageReference storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference folderRef=storageReference.child("PRODUCTOS_IMG");
        StorageReference fotoRef=folderRef.child(new Date().toString());
        fotoRef.putFile(uriImagen).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri>  uriTask= taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri downLoadUri= uriTask.getResult();
                Producto producto= new Producto();
                producto.setNombre(txtNombre.getText().toString());
                producto.setPrecio(Double.parseDouble(txtPrecio.getText().toString()));
                producto.setIdCategoria(idcategoria);
                producto.setDescripcion(txtdes.getText().toString());
                producto.setEstado(1);
                producto.setUrl(downLoadUri.toString());
                FirebaseDatabase database= FirebaseDatabase.getInstance();
                DatabaseReference reference=database.getReference("PRODUCTO");
                reference.push().setValue(producto);
                txtNombre.setText("");
                txtPrecio.setText("");
                txtdes.setText("");
            }
        });

        //insert




    }



}