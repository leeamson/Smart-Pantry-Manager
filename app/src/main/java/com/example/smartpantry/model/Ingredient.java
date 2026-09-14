package com.example.smartpantry.model;

public class Ingredient {
    private long id;
    private String name;
    private double quantity;
    private String unit;
    private String expiry; // optional ISO date string

    public Ingredient(long id, String name, double quantity, String unit, String expiry) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiry = expiry;
    }

    public Ingredient(String name, double quantity, String unit, String expiry) {
        this(-1, name, quantity, unit, expiry);
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public String getExpiry() { return expiry; }
    public void setExpiry(String expiry) { this.expiry = expiry; }
}
