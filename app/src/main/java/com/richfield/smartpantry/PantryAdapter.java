package com.richfield.smartpantry;
import android.view.*; import android.widget.*; import androidx.recyclerview.widget.RecyclerView; import java.util.*;
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.H> {
 public interface Click {void click(Ingredient i);} private final ArrayList<Ingredient> data;private final Click click;
 public PantryAdapter(ArrayList<Ingredient>d,Click c){data=d;click=c;} public H onCreateViewHolder(ViewGroup p,int v){TextView t=new TextView(p.getContext());t.setPadding(36,28,36,28);t.setTextSize(18);return new H(t);} public void onBindViewHolder(H h,int p){Ingredient i=data.get(p);h.t.setText(i.name+"\n"+i.quantity+" "+i.unit+(i.expiry==null||i.expiry.isEmpty()?"":"  • expires "+i.expiry));h.t.setOnClickListener(v->click.click(i));}public int getItemCount(){return data.size();}static class H extends RecyclerView.ViewHolder{TextView t;H(View v){super(v);t=(TextView)v;}}
}
