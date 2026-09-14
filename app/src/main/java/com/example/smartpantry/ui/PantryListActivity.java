package com.example.smartpantry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantry.R;
import com.example.smartpantry.db.DBHelper;
import com.example.smartpantry.model.Ingredient;

import java.util.List;

public class PantryListActivity extends AppCompatActivity {
    private DBHelper db;
    private RecyclerView rv;
    private IngredientAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry_list);
        db = new DBHelper(this);
        rv = findViewById(R.id.recyclerIngredients);
        rv.setLayoutManager(new LinearLayoutManager(this));
        loadData();

        ImageButton fabAdd = findViewById(R.id.btnAddIngredient);
        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, AddEditIngredientActivity.class));
        });

        findViewById(R.id.btnSuggested).setOnClickListener(v -> startActivity(new Intent(this, SuggestedRecipesActivity.class)));
        findViewById(R.id.btnSettings).setOnClickListener(v -> startActivity(new Intent(this, SettingsActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    void loadData() {
        List<Ingredient> list = db.getAllIngredients();
        adapter = new IngredientAdapter(list, this, db);
        rv.setAdapter(adapter);
    }
}
