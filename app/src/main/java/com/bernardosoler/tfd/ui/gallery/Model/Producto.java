package com.bernardosoler.tfd.ui.gallery.Model;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Producto implements Serializable {
    private String id;
    private String idCategoria;
    private String nombre;
    private Double Precio;
    private String Descripcion;
    private String url;



    private  int estado;

    public Producto(){



    }

    public Producto(String id, String idCategoria, String nombre, Double precio, String descripcion,String url, int estado) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        Precio = precio;
        Descripcion = descripcion;
        this.url = url;
        this.estado = estado;
    }

   // public String getUrl() {
     //   return url;
    //}

   // public void setUrl(String url) {
     //   this.url = url;
    //}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(String idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return id.equals(((Producto)obj).id);
    }
}
