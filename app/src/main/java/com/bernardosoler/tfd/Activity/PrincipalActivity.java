package com.bernardosoler.tfd.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bernardosoler.tfd.Adapter.CatAdapter;
import com.bernardosoler.tfd.Adapter.ProductAdapter;
import com.bernardosoler.tfd.Fragments.CarritoFragment;
import com.bernardosoler.tfd.Fragments.HomeFragment;
import com.bernardosoler.tfd.Fragments.ProfilekFragment;
import com.bernardosoler.tfd.Fragments.UbicacionFragment;
import com.bernardosoler.tfd.R;
import com.google.android.material.bottomappbar.BottomAppBar;

public class PrincipalActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomAppBar bottomAppBar;
    ApdapterPager apdapterPager;
    LinearLayout  bt_home,bt_carrito,bt_ubi,bt_profile;
    private String photo;
    private String names,email,provider;
    private SharedPreferences.Editor prefEditor;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            photo = bundle.getString("photo");
            names = bundle.getString("name");
            email= bundle.getString("email");
            provider= bundle.getString("provider");
        }
        setContentView(R.layout.activity_principal);
        //Guardado de las credenciales
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        prefEditor = sharedPreferences.edit();
        prefEditor.putString("photo", photo);
        prefEditor.putString("name", names);
        prefEditor.putString("email", email);
        prefEditor.putString("provider", provider);
        prefEditor.apply();
        bottomAppBar=findViewById(R.id.view_botonbar);
        viewPager=findViewById(R.id.view_pager);
        setSupportActionBar(bottomAppBar);
        apdapterPager =new ApdapterPager(getSupportFragmentManager());
        viewPager.setAdapter(apdapterPager);
        bt_home=findViewById(R.id.btn_home5);
        bt_carrito=findViewById(R.id.btn_carrito5);
        bt_ubi=findViewById(R.id.btn_ubicacion5);
        bt_profile=findViewById(R.id.btn_perfil5);
        bt_home.setOnClickListener(v->{
            viewPager.setCurrentItem(0);

        });
        bt_carrito.setOnClickListener(v->{
            viewPager.setCurrentItem(1);
        });
        bt_ubi.setOnClickListener(v->{
            viewPager.setCurrentItem(2);
        });
        bt_profile.setOnClickListener(v->{
            viewPager.setCurrentItem(3);
        });

    }

    public class ApdapterPager extends FragmentPagerAdapter {

        public ApdapterPager(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    HomeFragment f1= new HomeFragment();
                    return f1;
                case 1:
                    CarritoFragment f2=new CarritoFragment();
                    return  f2;
                case 2:
                    UbicacionFragment f3=new UbicacionFragment();
                    return f3;
                case 3:
                    ProfilekFragment f4=new ProfilekFragment();
                    return  f4;

            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}