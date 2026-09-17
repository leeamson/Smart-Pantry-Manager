package com.richfield.smartpantry;
import android.content.*;import android.graphics.Color;import android.view.*;import android.widget.*;import androidx.appcompat.app.AppCompatActivity;
public abstract class BaseActivity extends AppCompatActivity{
 protected LinearLayout page(String title){LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(28,24,28,16);root.setBackgroundColor(Color.rgb(247,243,232));TextView h=new TextView(this);h.setText(title);h.setTextSize(27);h.setTextColor(Color.rgb(28,43,39));h.setPadding(8,4,8,18);root.addView(h);return root;}
 protected Button button(String s){Button b=new Button(this);b.setText(s);return b;} protected void nav(LinearLayout root){LinearLayout n=new LinearLayout(this);n.setWeightSum(3);String[] names={"Pantry","Recipes","Settings"};Class[] dest={MainActivity.class,SuggestedRecipesActivity.class,SettingsActivity.class};for(int i=0;i<3;i++){Button b=button(names[i]);final int x=i;b.setOnClickListener(v->startActivity(new Intent(this,dest[x])));n.addView(b,new LinearLayout.LayoutParams(0,-2,1));}root.addView(n);}
}
