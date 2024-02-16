package com.bernardosoler.tfd.ui.gallery.Model;

import androidx.annotation.Nullable;

public class Usuario {
    private String idCo;
    private String nombre;
    private String foto;

    public Usuario(String idCo, String nombre, String foto) {
        this.idCo = idCo;
        this.nombre = nombre;
        this.foto = foto;
    }

    public Usuario(){

    }

    public String getIdCo() {
        return idCo;
    }

    public void setIdCo(String idCo) {
        this.idCo = idCo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    @Override
    public boolean equals(@Nullable Object obj) {
        return idCo.equals(((Usuario)obj).idCo);
    }
}
