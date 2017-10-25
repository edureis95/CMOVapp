package com.cmov.acme.api.model;

/**
 * Created by Ricardo on 24-Oct-17.
 */

public class Product {
    private String name;
    private int price;
    private long bar_code;
    private String maker;
    private String model;

    public Product(String name, int price, long bar_code, String maker, String model) {
        this.name = name;
        this.price = price;
        this.bar_code = bar_code;
        this.maker = maker;
        this.model = model;
    }
}
