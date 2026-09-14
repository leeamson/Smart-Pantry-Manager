package com.example.smartpantry.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantry.R;
import com.example.smartpantry.db.DBHelper;
import com.example.smartpantry.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.VH> {
    private List<Ingredient> items;
    private Context ctx;
    private DBHelper db;

    public IngredientAdapter(List<Ingredient> items, Context ctx, DBHelper db) {
        this.items = items; this.ctx = ctx; this.db = db;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Ingredient i = items.get(position);
        holder.txtName.setText(i.getName());
        holder.txtQty.setText(String.valueOf(i.getQuantity()) + " " + (i.getUnit()==null?"":i.getUnit()));
        holder.btnEdit.setOnClickListener(v -> {
            Intent it = new Intent(ctx, AddEditIngredientActivity.class);
            it.putExtra("id", i.getId());
            ctx.startActivity(it);
        });
        holder.btnDelete.setOnClickListener(v -> new AlertDialog.Builder(ctx)
                .setTitle("Delete")
                .setMessage("Delete ingredient?")
                .setPositiveButton("Delete", (d, w) -> { db.deleteIngredient(i.getId()); ((PantryListActivity)ctx).runOnUiThread(((PantryListActivity)ctx)::loadData); })
                .setNegativeButton("Cancel", null)
                .show());
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView txtName, txtQty; ImageButton btnEdit, btnDelete;
        VH(View v) {
            super(v);
            txtName = v.findViewById(R.id.txtIngredientName);
            txtQty = v.findViewById(R.id.txtIngredientQty);
            btnEdit = v.findViewById(R.id.btnEdit);
            btnDelete = v.findViewById(R.id.btnDelete);
        }
    }
}
