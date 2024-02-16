package com.bernardosoler.tfd.ui.gallery.Model;

import androidx.annotation.Nullable;

public class Compra {
   private String subtotal;
   private String total;
   private String envio;
   private String cliente;

    public Compra(String subtotal, String total, String envio, String cliente) {
        this.subtotal = subtotal;
        this.total = total;
        this.envio = envio;
        this.cliente = cliente;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getEnvio() {
        return envio;
    }

    public void setEnvio(String envio) {
        this.envio = envio;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }


}
