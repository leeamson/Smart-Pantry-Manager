package com.example.smartpantry.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantry.R;
import com.example.smartpantry.db.DBHelper;
import com.example.smartpantry.model.Ingredient;

public class AddEditIngredientActivity extends AppCompatActivity {
    private EditText etName, etQty, etUnit, etExpiry;
    private DBHelper db;
    private long id = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);
        db = new DBHelper(this);
        etName = findViewById(R.id.etName);
        etQty = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiry = findViewById(R.id.etExpiry);
        Button btnSave = findViewById(R.id.btnSave);

        if (getIntent() != null && getIntent().hasExtra("id")) {
            id = getIntent().getLongExtra("id", -1);
            Ingredient ing = db.getIngredientById(id);
            if (ing != null) {
                etName.setText(ing.getName());
                etQty.setText(String.valueOf(ing.getQuantity()));
                etUnit.setText(ing.getUnit());
                etExpiry.setText(ing.getExpiry());
            }
        }

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String qtyS = etQty.getText().toString().trim();
            if (TextUtils.isEmpty(name)) { Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show(); return; }
            double qty = 0;
            try { qty = Double.parseDouble(qtyS); } catch (Exception e) { Toast.makeText(this, "Quantity must be a number", Toast.LENGTH_SHORT).show(); return; }
            String unit = etUnit.getText().toString().trim();
            String expiry = etExpiry.getText().toString().trim();
            Ingredient ing = new Ingredient(id, name, qty, unit, expiry);
            if (id == -1) db.addIngredient(ing); else db.updateIngredient(ing);
            finish();
        });
    }
}
