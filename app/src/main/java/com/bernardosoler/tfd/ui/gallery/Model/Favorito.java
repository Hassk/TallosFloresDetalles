package com.bernardosoler.tfd.ui.gallery.Model;

public class Favorito {
    private String cliente;
    private String producto;

    public Favorito(String cliente, String producto) {
        this.cliente = cliente;
        this.producto = producto;

    }
    public Favorito(){

    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
