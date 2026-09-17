package com.richfield.smartpantry;

public class Recipe {
    public long id; public String name, ingredients, method;
    public Recipe(long id, String name, String ingredients, String method) { this.id=id; this.name=name; this.ingredients=ingredients; this.method=method; }
}
