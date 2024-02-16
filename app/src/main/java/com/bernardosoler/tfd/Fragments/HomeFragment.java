package com.bernardosoler.tfd.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bernardosoler.tfd.Activity.PrincipalActivity;
import com.bernardosoler.tfd.Adapter.CatAdapter;
import com.bernardosoler.tfd.Adapter.ProductAdapter;
import com.bernardosoler.tfd.R;
import com.bernardosoler.tfd.ui.gallery.Model.Categoria;
import com.bernardosoler.tfd.ui.gallery.Model.Producto;
import com.bernardosoler.tfd.ui.gallery.Service.CategoriaService;
import com.bernardosoler.tfd.ui.gallery.Service.ProductoService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class HomeFragment extends Fragment {
    private RecyclerView.Adapter adapterProductList;
    private ProductAdapter adapterp;
    private CatAdapter adapterc;
    private RecyclerView recyclerViewProduct,recyclerViewCat;
    private TextView name,salir;
    private ImageView profile;
    private String photo;
    private String names,email,provider;
    private View btnRosas;
    private SharedPreferences.Editor prefEditor;


    public HomeFragment() {
        // Required empty public constructor
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.activity_home, container, false);
        SharedPreferences pref = requireActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String photo=pref.getString("photo","no hay foto");
        String names=pref.getString("name","no hay nobre");
        setup( photo != null ? photo : "", names != null ? names : "no recibio nada",view);
        cargarCategorias();
        iniRecyclerView(view);
        cargarProductos1();
        return  view;
    }

    private void setup(String p, String n,View view) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transforms(new CircleCrop());
        requestOptions.override(200, 200);
        salir=view.findViewById(R.id.btnSalir);
        profile=view.findViewById(R.id.profileView);
        name=view.findViewById(R.id.nameHomeTxt);
        name.setText(n);
        Glide.with(this).load(p).apply(requestOptions).into(profile);
        salir.setOnClickListener(v -> {
            prefEditor = requireActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
            prefEditor.clear();
            prefEditor.apply();

            FirebaseAuth.getInstance().signOut();

            requireActivity().onBackPressed();
        });
    }

    private void cargarProductos1() {
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
                recyclerViewProduct.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Producto producto = snapshot.getValue(Producto.class);
                producto.setId(snapshot.getKey());

                if (ProductoService.productos.contains(producto)){
                    ProductoService.updateProducto(producto);
                }
                recyclerViewProduct.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Producto producto = snapshot.getValue(Producto.class);
                producto.setId(snapshot.getKey());

                if (ProductoService.productos.contains(producto)){
                    ProductoService.removeProducto(producto);
                }
                recyclerViewProduct.getAdapter().notifyDataSetChanged();

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

    private void iniRecyclerView(View view) {
        recyclerViewCat=view.findViewById(R.id.rcCat);
        recyclerViewCat.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.HORIZONTAL,false));
        adapterc=new CatAdapter(CategoriaService.categorias,R.layout.viewholder_categoria_list,getActivity());
        recyclerViewCat.setAdapter(adapterc);
        //product
        recyclerViewProduct=view.findViewById(R.id.view1);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        adapterp=new ProductAdapter(ProductoService.productos,R.layout.viewholder_producto_list, getActivity());
        recyclerViewProduct.setAdapter(adapterp);
        
    }

    private void cargarCategorias() {
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
                recyclerViewCat.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Categoria categoria = snapshot.getValue(Categoria.class);
                categoria.setId(snapshot.getKey());

                if (!CategoriaService.categorias.contains(categoria)){
                    CategoriaService.updateCategoria(categoria);
                }
                recyclerViewCat.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Categoria categoria = snapshot.getValue(Categoria.class);
                categoria.setId(snapshot.getKey());

                if (!CategoriaService.categorias.contains(categoria)){
                    CategoriaService.removeCategoria(categoria);
                }
                recyclerViewCat.getAdapter().notifyDataSetChanged();

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
}