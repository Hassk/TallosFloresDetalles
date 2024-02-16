package com.bernardosoler.tfd.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bernardosoler.tfd.Activity.Favoritos;
import com.bernardosoler.tfd.R;
import com.bernardosoler.tfd.ui.gallery.InputProducto.CargarProducto;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;


public class ProfilekFragment extends Fragment {
    ImageView foto;
    TextView name,correo;
    LinearLayout btnin,btnlike;




    public ProfilekFragment() {
        // Required empty public constructor
    }




    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profilek, container, false);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transforms(new CircleCrop());
        requestOptions.override(300, 300);
        SharedPreferences pref = requireActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email =pref.getString("email","no hay email");
        String photo=pref.getString("photo","no hay foto");
        String names=pref.getString("name","no hay nobre");
        foto=view.findViewById(R.id.img_profiles1);
        correo=view.findViewById(R.id.textCorreo1);
        name=view.findViewById(R.id.txtNombre1);
        btnin=view.findViewById(R.id.btn_input);
        btnlike=view.findViewById(R.id.btn_heart);
        btnin.setOnClickListener(V->{
            Intent intent=new Intent(getActivity(), CargarProducto.class);
            startActivity(intent);


        });
        btnlike.setOnClickListener(v->{
            Intent inten=new Intent(getActivity(), Favoritos.class);
            inten.putExtra("email",email);
            startActivity(inten);

        });


        Glide.with(this).load(photo).apply(requestOptions).into(foto);
        name.setText(names);
        correo.setText(email);

        return view;


    }
}