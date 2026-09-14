package com.example.smartpantry.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    public static class IngredientRequirement {
        public String name;
        public double quantity;
        public String unit;
        public IngredientRequirement(String name, double quantity, String unit) {
            this.name = name; this.quantity = quantity; this.unit = unit;
        }
    }

    private long id;
    private String name;
    private String instructions;
    private List<IngredientRequirement> ingredients = new ArrayList<>();

    public Recipe(long id, String name, String instructions) {
        this.id = id; this.name = name; this.instructions = instructions;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getInstructions() { return instructions; }
    public List<IngredientRequirement> getIngredients() { return ingredients; }
    public void setIngredients(List<IngredientRequirement> list) { this.ingredients = list; }
}
