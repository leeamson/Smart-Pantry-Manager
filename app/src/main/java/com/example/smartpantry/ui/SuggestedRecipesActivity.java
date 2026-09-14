package com.example.smartpantry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantry.R;
import com.example.smartpantry.db.DBHelper;
import com.example.smartpantry.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {
    private DBHelper db;
    private ListView lv;
    private List<Recipe> suggested;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);
        db = new DBHelper(this);
        lv = findViewById(R.id.listSuggested);
        load();
    }

    private void load() {
        suggested = db.getSuggestedRecipes();
        List<String> names = new ArrayList<>();
        for (Recipe r: suggested) names.add(r.getName());
        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names));
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Recipe r = suggested.get(position);
            Intent it = new Intent(this, RecipeDetailActivity.class);
            it.putExtra("recipeId", r.getId());
            startActivity(it);
        });
    }
}
