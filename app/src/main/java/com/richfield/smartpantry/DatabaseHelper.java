package com.richfield.smartpantry;

import android.content.*; import android.database.Cursor; import android.database.sqlite.*; import java.util.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB="smart_pantry.db";
    public DatabaseHelper(Context c){super(c,DB,null,1);}
    @Override public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE pantry(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,quantity REAL NOT NULL,unit TEXT NOT NULL,expiry TEXT)");
        db.execSQL("CREATE TABLE recipes(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,ingredients TEXT NOT NULL,method TEXT NOT NULL)"); seed(db);
    }
    @Override public void onUpgrade(SQLiteDatabase db,int oldV,int newV){}
    public long saveIngredient(Ingredient x){ ContentValues v=new ContentValues(); v.put("name",x.name);v.put("quantity",x.quantity);v.put("unit",x.unit);v.put("expiry",x.expiry); if(x.id<0)return getWritableDatabase().insert("pantry",null,v); getWritableDatabase().update("pantry",v,"id=?",new String[]{""+x.id});return x.id; }
    public void deleteIngredient(long id){getWritableDatabase().delete("pantry","id=?",new String[]{""+id});}
    public ArrayList<Ingredient> ingredients(){ArrayList<Ingredient> a=new ArrayList<>(); Cursor c=getReadableDatabase().rawQuery("SELECT * FROM pantry ORDER BY name",null); while(c.moveToNext()) a.add(new Ingredient(c.getLong(0),c.getString(1),c.getDouble(2),c.getString(3),c.getString(4)));c.close();return a;}
    public Ingredient ingredient(long id){for(Ingredient x:ingredients())if(x.id==id)return x;return null;}
    public ArrayList<Recipe> recipes(){ArrayList<Recipe>a=new ArrayList<>();Cursor c=getReadableDatabase().rawQuery("SELECT * FROM recipes ORDER BY name",null);while(c.moveToNext())a.add(new Recipe(c.getLong(0),c.getString(1),c.getString(2),c.getString(3)));c.close();return a;}
    public Recipe recipe(long id){for(Recipe r:recipes())if(r.id==id)return r;return null;}
    // Ingredient format: name|quantity|unit; entries are separated with semicolons.
    public ArrayList<Recipe> suggestions(){ ArrayList<Recipe> out=new ArrayList<>(); ArrayList<Ingredient> p=ingredients(); for(Recipe r:recipes()) if(matches(r,p))out.add(r); return out; }
    private boolean matches(Recipe r,ArrayList<Ingredient> pantry){for(String need:r.ingredients.split(";")){String[] n=need.split("\\|"); boolean found=false;for(Ingredient have:pantry)if(normal(n[0]).equals(normal(have.name))&&compatible(n[2],have.unit)&&toBase(Double.parseDouble(n[1]),n[2])<=toBase(have.quantity,have.unit)+0.0001){found=true;break;}if(!found)return false;}return true;}
    private String normal(String s){s=s.toLowerCase().trim();if(s.endsWith("es"))s=s.substring(0,s.length()-2);else if(s.endsWith("s"))s=s.substring(0,s.length()-1);if(s.equals("egg"))return "egg";if(s.equals("tomatoe"))return "tomato";return s;}
    private boolean compatible(String a,String b){return group(a).equals(group(b));} private String group(String u){u=u.toLowerCase();if(u.startsWith("g")||u.startsWith("kg"))return "mass";if(u.equals("ml")||u.equals("l"))return "volume";return "count";} private double toBase(double q,String u){u=u.toLowerCase();if(u.equals("kg")||u.equals("l"))return q*1000;return q;}
    private void seed(SQLiteDatabase db){
      add(db,"Tomato Omelette","egg|2|each;tomato|1|each;oil|5|ml","Beat eggs. Dice tomato. Cook in oiled pan until set.");
      add(db,"Cheese Omelette","egg|2|each;cheese|30|g;oil|5|ml","Beat eggs, add cheese, and cook in oil.");
      add(db,"Scrambled Eggs","egg|2|each;butter|10|g;milk|30|ml","Whisk eggs with milk. Cook gently in butter.");
      add(db,"Grilled Cheese Toastie","bread|2|each;cheese|40|g;butter|10|g","Butter bread, fill with cheese, and toast both sides.");
      add(db,"Tomato Pasta","pasta|100|g;tomato|2|each;oil|10|ml;garlic|1|each","Boil pasta. Fry garlic and tomato in oil; toss together.");
      add(db,"Garlic Rice","rice|100|g;garlic|1|each;oil|5|ml","Cook rice. Fry garlic in oil and stir through.");
      add(db,"Banana Smoothie","banana|1|each;milk|250|ml","Blend banana and milk until smooth.");
      add(db,"Peanut Banana Toast","bread|2|each;peanut butter|30|g;banana|1|each","Toast bread, spread peanut butter and top with banana.");
      add(db,"Vegetable Fried Rice","rice|150|g;egg|1|each;carrot|1|each;peas|80|g;oil|10|ml","Cook rice. Stir-fry vegetables, add egg and rice.");
      add(db,"Cheesy Pasta","pasta|100|g;cheese|50|g;milk|100|ml;butter|10|g","Cook pasta. Melt butter, milk and cheese; combine.");
      add(db,"Potato Hash","potato|2|each;onion|1|each;oil|10|ml","Dice vegetables and fry in oil until golden.");
      add(db,"Tomato Soup","tomato|4|each;onion|1|each;garlic|1|each;stock|500|ml","Simmer chopped ingredients with stock; blend.");
      add(db,"Bean Salad","beans|200|g;tomato|1|each;onion|1|each;oil|10|ml","Drain beans and toss all ingredients together.");
      add(db,"Pancakes","flour|120|g;egg|1|each;milk|250|ml;butter|10|g","Mix flour, egg and milk. Cook spoonfuls in butter.");
      add(db,"French Toast","bread|2|each;egg|1|each;milk|60|ml;butter|10|g","Dip bread in egg and milk. Fry in butter.");
      add(db,"Tuna Sandwich","bread|2|each;tuna|100|g;mayonnaise|20|g","Mix tuna with mayonnaise and fill bread.");
      add(db,"Chicken Rice Bowl","chicken|150|g;rice|150|g;carrot|1|each;oil|10|ml","Cook rice. Fry chicken and carrot in oil; serve together.");
      add(db,"Cheese Quesadilla","tortilla|2|each;cheese|60|g;tomato|1|each","Fill tortillas with cheese and tomato; toast in a pan.");
    }
    private void add(SQLiteDatabase db,String n,String i,String m){ContentValues v=new ContentValues();v.put("name",n);v.put("ingredients",i);v.put("method",m);db.insert("recipes",null,v);}
}
