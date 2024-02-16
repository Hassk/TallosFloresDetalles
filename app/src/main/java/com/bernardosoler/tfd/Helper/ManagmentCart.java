package com.bernardosoler.tfd.Helper;

import android.content.Context;
import android.widget.Toast;

import com.bernardosoler.tfd.Domain.ProductDomain;

import java.util.ArrayList;

public class ManagmentCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }
    public  void insertProduct(ProductDomain item){
        ArrayList<ProductDomain> listProduct= getListCart();
        boolean existAlready= false;
        int n=0;
        for (int y = 0; y<listProduct.size();y++){
            if (listProduct.get(y).getTittle().equals(item.getTittle())){
                existAlready=true;
                n-=y;
                break;
            }
        }
        if (existAlready){
            listProduct.get(n).setNumberinCart(item.getNumberinCart());
        }else{
            listProduct.add(item);


        }
        tinyDB.putListObject("Cartlist",listProduct);
        Toast.makeText(context, "Añadido a tu carrito", Toast.LENGTH_SHORT).show();

    }

    public  ArrayList<ProductDomain> getListCart(){

        return tinyDB.getListObject("CartList");
    }
}
