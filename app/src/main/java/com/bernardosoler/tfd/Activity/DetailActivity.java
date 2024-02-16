package com.bernardosoler.tfd.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bernardosoler.tfd.Domain.ProductDomain;
import com.bernardosoler.tfd.Helper.ManagmentCart;
import com.bernardosoler.tfd.R;
import com.bernardosoler.tfd.ui.gallery.Model.Favorito;
import com.bernardosoler.tfd.ui.gallery.Model.Producto;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {
    private Button addToCartBtn;
    private FloatingActionButton Btnlike;
    private TextView plusBtn,minusBtn,titleText,feeText,descriptionTxt,numberOrderTxt,StartTxt,caloryTxt ,timeTxt;
    private ImageView picProduct,back;
    private Producto object;
    private int numberOrder=1;
    private ManagmentCart managmentCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        managmentCart=new ManagmentCart(DetailActivity.this);
        iniView();
        getBundle();
        
    }

    private void getBundle() {


         object = (Producto) getIntent().getSerializableExtra("object");
         String drawableResourceId=object.getUrl();
        Glide.with(this)
                .load(drawableResourceId)
                .into(picProduct);
        titleText.setText(object.getNombre());
        feeText.setText("$ "+object.getPrecio());
        descriptionTxt.setText(object.getDescripcion()+"");

        addToCartBtn.setText("Add to card -$"+Math.round(numberOrder*object.getPrecio()));
        plusBtn.setOnClickListener(v -> {
            numberOrder=numberOrder+1;
             numberOrderTxt.setText(""+numberOrder);
            addToCartBtn.setText("Add to card -$"+Math.round(numberOrder*object.getPrecio()));

        });
        minusBtn.setOnClickListener(v -> {
            numberOrder=numberOrder-1;
            numberOrderTxt.setText(""+numberOrder);
            addToCartBtn.setText("Add to card -$"+Math.round(numberOrder*object.getPrecio()));

        });
         /*
        addToCartBtn.setOnClickListener(v -> {
               object.setNumberinCart(numberOrder);
               managmentCart.insertProduct(object);
        });*/
        back.setOnClickListener(v -> {
            onBackPressed();
        });
        Btnlike.setOnClickListener(v -> {
            SharedPreferences pref = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
            String emails =pref.getString("email","no hay email");
            Favorito favorito=new Favorito();
            favorito.setCliente(emails);
            favorito.setProducto(object.getId());
            Btnlike.setImageResource(R.drawable.corazonrojo);
            FirebaseDatabase database=FirebaseDatabase.getInstance();
            DatabaseReference reference=database.getReference("FAVORITOS");
            reference.push().setValue(favorito);



        });





    }

    private void iniView() {
        addToCartBtn=findViewById(R.id.addToCartBtn);
        //timeTxt=findViewById(R.id.timeTxt);
        feeText=findViewById(R.id.priceTxt);
        descriptionTxt=findViewById(R.id.descriptionTxt);
        numberOrderTxt=findViewById(R.id.numberItemTxt);
        plusBtn=findViewById(R.id.plusCardBtn);
        minusBtn=findViewById(R.id.MinusCardBtn);
        picProduct =findViewById(R.id.productPic);
       // StartTxt=findViewById(R.id.StarTxt);
        //caloryTxt=findViewById(R.id.calTxt);
        titleText=findViewById(R.id.titleTxt);
        back=findViewById(R.id.backbtn);
        Btnlike=findViewById(R.id.btn_Like);





    }
}