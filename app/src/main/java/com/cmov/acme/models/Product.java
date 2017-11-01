package com.cmov.acme.models;

import java.io.Serializable;

/**
 * Created by Ricardo on 27-Oct-17.
 */

public class Product implements Serializable{

    private int id;
    private String name;
    private int price;
    private String bar_code;
    private String maker;
    private String model;
    private int quantity;

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(){
        quantity++;
    }

    public void subtractQuantity(){
        if(quantity > 1){
            quantity--;
        }
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getBar_code() {
        return bar_code;
    }

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }

    public Product(int id, String name, int price, String bar_code, String maker, String model) {
        this.id = id;
        this.quantity = 0;
        this.name = name;
        this.price = price;
        this.bar_code = bar_code;
        this.maker = maker;
        this.model = model;
    }
}
