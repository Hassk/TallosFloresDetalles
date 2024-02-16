package com.bernardosoler.tfd.ui.gallery.Service;

import com.bernardosoler.tfd.ui.gallery.Model.Categoria;
import com.bernardosoler.tfd.ui.gallery.Model.Producto;

import java.util.ArrayList;
import java.util.List;

public class CategoriaService {
    public static List<Categoria> categorias= new ArrayList<>();
    public static void addCategoria(Categoria categoria){
        categorias.add(categoria);

    }
    public static void  removeCategoria(Categoria categoria){
        categorias.remove(categoria);

    }
    public static void  updateCategoria(Categoria categoria){
        categorias.set(categorias.indexOf(categoria),categoria);

    }
}
