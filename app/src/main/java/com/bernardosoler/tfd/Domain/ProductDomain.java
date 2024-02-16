package com.bernardosoler.tfd.Domain;

import java.io.Serializable;

public class ProductDomain implements Serializable {
    private String tittle;
    private String description;
    private String picurl;
    private double price;
    private int time;
    private int energy;
    private double score;
    private  int numberinCart;

    public ProductDomain(String tittle, String description, String picurl, double price, int time, int energy, double score) {
        this.tittle = tittle;
        this.description = description;
        this.picurl = picurl;
        this.price = price;
        this.time = time;
        this.energy = energy;
        this.score = score;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getNumberinCart() {
        return numberinCart;
    }

    public void setNumberinCart(int numberinCart) {
        this.numberinCart = numberinCart;
    }
}
