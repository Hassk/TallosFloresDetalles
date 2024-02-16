package com.bernardosoler.tfd.ui.gallery.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Categoria implements Serializable {
    private String id;
    private String nombreCat;
    private String urlCat;
    private int estado;

    public Categoria(){

    }

    public Categoria(String id, String nombreCat, String urlCat, int estado) {
        this.id = id;
        this.nombreCat = nombreCat;
        this.urlCat = urlCat;
        this.estado = estado;
    }

    public Categoria(String id, String nombreCat, int estado) {
        this.id = id;
        this.nombreCat = nombreCat;

        this.estado = estado;
    }

    public String getUrlCat() {
        return urlCat;
    }

    public void setUrlCat(String urlCat) {
        this.urlCat = urlCat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreCat() {
        return nombreCat;
    }

    public void setNombreCat(String nombreCat) {
        this.nombreCat = nombreCat;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return id.equals(((Categoria)obj).id);
    }

    @NonNull
    @Override
    public String toString() {
        return nombreCat;
    }
}
