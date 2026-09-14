package com.example.smartpantry.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.smartpantry.model.Ingredient;
import com.example.smartpantry.model.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DB_NAME = "smartpantry.db";
    private static final int DB_VERSION = 1;

    // tables
    private static final String TABLE_INGREDIENTS = "ingredients";
    private static final String TABLE_RECIPES = "recipes";
    private static final String TABLE_RECIPE_ING = "recipe_ingredients";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_INGREDIENTS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT, expiry TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_RECIPES + " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, instructions TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_RECIPE_ING + " (id INTEGER PRIMARY KEY AUTOINCREMENT, recipe_id INTEGER NOT NULL, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT, FOREIGN KEY(recipe_id) REFERENCES recipes(id) ON DELETE CASCADE)");

        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database; dropping and recreating tables");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_ING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        onCreate(db);
    }

    // Ingredient CRUD
    public long addIngredient(Ingredient ing) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", ing.getName());
        cv.put("quantity", ing.getQuantity());
        cv.put("unit", ing.getUnit());
        cv.put("expiry", ing.getExpiry());
        return db.insert(TABLE_INGREDIENTS, null, cv);
    }

    public int updateIngredient(Ingredient ing) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", ing.getName());
        cv.put("quantity", ing.getQuantity());
        cv.put("unit", ing.getUnit());
        cv.put("expiry", ing.getExpiry());
        return db.update(TABLE_INGREDIENTS, cv, "id = ?", new String[]{String.valueOf(ing.getId())});
    }

    public int deleteIngredient(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_INGREDIENTS, "id = ?", new String[]{String.valueOf(id)});
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, name, quantity, unit, expiry FROM " + TABLE_INGREDIENTS + " ORDER BY name COLLATE NOCASE", null);
        try {
            while (c.moveToNext()) {
                Ingredient ing = new Ingredient(
                        c.getLong(0),
                        c.getString(1),
                        c.getDouble(2),
                        c.getString(3),
                        c.getString(4)
                );
                list.add(ing);
            }
        } finally {
            c.close();
        }
        return list;
    }

    public Ingredient getIngredientById(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, name, quantity, unit, expiry FROM " + TABLE_INGREDIENTS + " WHERE id = ?", new String[]{String.valueOf(id)});
        try {
            if (c.moveToFirst()) {
                return new Ingredient(c.getLong(0), c.getString(1), c.getDouble(2), c.getString(3), c.getString(4));
            }
        } finally {
            c.close();
        }
        return null;
    }

    // Recipes and seeding
    private void seedRecipes(SQLiteDatabase db) {
        // Add simple seeded recipes (15). Quantities in units as plain numbers; units are flexible strings.
        // Example recipe: Tomato Omelette: 2 tomatoes, 2 eggs, 1 onion
        addSeedRecipe(db, "Tomato Omelette", "Beat eggs, add chopped tomatoes and onion, fry.", new Object[][]{
                {"tomato", 2.0, "pc"},
                {"egg", 2.0, "pc"},
                {"onion", 1.0, "pc"}
        });
        addSeedRecipe(db, "Chicken Stir Fry", "Stir fry chicken with vegetables and sauce.", new Object[][]{
                {"chicken", 500.0, "g"},
                {"onion", 1.0, "pc"},
                {"tomato", 1.0, "pc"}
        });
        addSeedRecipe(db, "Tomato Soup", "Cook tomatoes and blend.", new Object[][]{
                {"tomato", 4.0, "pc"},
                {"onion", 1.0, "pc"}
        });
        addSeedRecipe(db, "Pasta with Tomato", "Cook pasta, add tomato sauce.", new Object[][]{
                {"pasta", 200.0, "g"},
                {"tomato", 3.0, "pc"}
        });
        addSeedRecipe(db, "Grilled Cheese", "Toast bread with cheese.", new Object[][]{
                {"bread", 2.0, "slices"},
                {"cheese", 2.0, "slices"}
        });
        addSeedRecipe(db, "Chicken Salad", "Mix chicken with greens.", new Object[][]{
                {"chicken", 200.0, "g"},
                {"lettuce", 1.0, "pc"},
                {"tomato", 2.0, "pc"}
        });
        addSeedRecipe(db, "Onion Bhaji", "Fry spiced onion batter.", new Object[][]{
                {"onion", 2.0, "pc"},
                {"chickpea flour", 100.0, "g"}
        });
        addSeedRecipe(db, "Scrambled Eggs", "Scramble and serve.", new Object[][]{
                {"egg", 3.0, "pc"},
                {"butter", 10.0, "g"}
        });
        addSeedRecipe(db, "Veggie Sandwich", "Assemble vegetables in bread.", new Object[][]{
                {"bread", 2.0, "slices"},
                {"lettuce", 1.0, "pc"},
                {"tomato", 1.0, "pc"}
        });
        addSeedRecipe(db, "Fried Rice", "Stir-fry rice with egg and veggies.", new Object[][]{
                {"rice", 200.0, "g"},
                {"egg", 1.0, "pc"},
                {"onion", 1.0, "pc"}
        });
        addSeedRecipe(db, "Pancakes", "Mix batter and fry.", new Object[][]{
                {"flour", 200.0, "g"},
                {"egg", 1.0, "pc"},
                {"milk", 200.0, "ml"}
        });
        addSeedRecipe(db, "Chicken Curry", "Cook chicken with spices.", new Object[][]{
                {"chicken", 600.0, "g"},
                {"onion", 2.0, "pc"},
                {"tomato", 2.0, "pc"}
        });
        addSeedRecipe(db, "Tomato Sandwich", "Use tomato slices between bread.", new Object[][]{
                {"bread", 2.0, "slices"},
                {"tomato", 2.0, "pc"}
        });
        addSeedRecipe(db, "Garlic Pasta", "Pasta with garlic oil.", new Object[][]{
                {"pasta", 200.0, "g"},
                {"garlic", 2.0, "cloves"}
        });
        addSeedRecipe(db, "Chicken Wrap", "Wrap chicken with veggies.", new Object[][]{
                {"chicken", 300.0, "g"},
                {"tortilla", 1.0, "pc"},
                {"lettuce", 1.0, "pc"}
        });
    }

    private void addSeedRecipe(SQLiteDatabase db, String name, String instructions, Object[][] ingredients) {
        ContentValues rv = new ContentValues();
        rv.put("name", name);
        rv.put("instructions", instructions);
        long recipeId = db.insert(TABLE_RECIPES, null, rv);
        if (recipeId == -1) return;
        for (Object[] ing : ingredients) {
            ContentValues iv = new ContentValues();
            iv.put("recipe_id", recipeId);
            iv.put("name", ((String) ing[0]));
            iv.put("quantity", ((Double) ing[1]));
            iv.put("unit", ((String) ing[2]));
            db.insert(TABLE_RECIPE_ING, null, iv);
        }
    }

    // Retrieve recipes fully populated with ingredients
    public List<Recipe> getAllRecipes() {
        List<Recipe> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id, name, instructions FROM " + TABLE_RECIPES, null);
        try {
            while (c.moveToNext()) {
                long id = c.getLong(0);
                String name = c.getString(1);
                String instructions = c.getString(2);
                Recipe r = new Recipe(id, name, instructions);
                r.setIngredients(getIngredientsForRecipe(id));
                list.add(r);
            }
        } finally {
            c.close();
        }
        return list;
    }

    public List<Recipe.IngredientRequirement> getIngredientsForRecipe(long recipeId) {
        List<Recipe.IngredientRequirement> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT name, quantity, unit FROM " + TABLE_RECIPE_ING + " WHERE recipe_id = ?", new String[]{String.valueOf(recipeId)});
        try {
            while (c.moveToNext()) {
                list.add(new Recipe.IngredientRequirement(c.getString(0), c.getDouble(1), c.getString(2)));
            }
        } finally {
            c.close();
        }
        return list;
    }

    // Matching algorithm: strict - every required ingredient must be present in pantry with >= quantity
    public List<Recipe> getSuggestedRecipes() {
        List<Recipe> suggested = new ArrayList<>();
        List<Recipe> recipes = getAllRecipes();
        List<Ingredient> pantry = getAllIngredients();

        // build a lookup map: normalized name -> total quantity
        Map<String, Double> pantryMap = new HashMap<>();
        for (Ingredient ing : pantry) {
            String key = normalizeName(ing.getName());
            pantryMap.put(key, pantryMap.getOrDefault(key, 0.0) + ing.getQuantity());
        }

        for (Recipe r : recipes) {
            boolean ok = true;
            for (Recipe.IngredientRequirement req : r.getIngredients()) {
                String reqKey = normalizeName(req.name);
                double have = pantryMap.getOrDefault(reqKey, 0.0);
                if (have < req.quantity) { ok = false; break; }
            }
            if (ok) suggested.add(r);
        }
        return suggested;
    }

    private String normalizeName(String s) {
        if (s == null) return "";
        s = s.trim().toLowerCase(Locale.ROOT);
        // simple plural handling: remove trailing 's' if plural
        if (s.endsWith("es") && s.length() > 2) {
            // e.g., tomatoes -> tomatoes? but simplest: replace common plural 'es' for 'ches' etc not robust
        }
        if (s.endsWith("s") && !s.endsWith("ss") && s.length() > 1) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }
}
