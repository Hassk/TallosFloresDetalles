package com.bernardosoler.tfd.ui.gallery.Service;

import com.bernardosoler.tfd.ui.gallery.Model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    public static List<Producto> productos= new ArrayList<>();
    public static void addProducto(Producto producto){
        productos.add(producto);

    }
    public static void removeProducto(Producto producto){

        productos.remove(producto);

    }
    public static void updateProducto(Producto producto){

        productos.set(productos.indexOf(producto),producto);

    }
}
