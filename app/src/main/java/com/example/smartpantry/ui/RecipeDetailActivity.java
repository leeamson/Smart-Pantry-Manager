package com.example.smartpantry.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantry.R;
import com.example.smartpantry.db.DBHelper;
import com.example.smartpantry.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {
    private DBHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        db = new DBHelper(this);
        long id = getIntent().getLongExtra("recipeId", -1);
        Recipe r = null;
        for (Recipe rr: db.getAllRecipes()) if (rr.getId()==id) r=rr;
        if (r!=null) {
            ((TextView)findViewById(R.id.txtRecipeName)).setText(r.getName());
            StringBuilder sb = new StringBuilder();
            sb.append("Ingredients:\n");
            for (Recipe.IngredientRequirement ir: r.getIngredients()) sb.append(String.format("- %s: %s %s\n", ir.name, ir.quantity, ir.unit));
            sb.append("\nInstructions:\n").append(r.getInstructions());
            ((TextView)findViewById(R.id.txtRecipeDetail)).setText(sb.toString());
        }
    }
}
