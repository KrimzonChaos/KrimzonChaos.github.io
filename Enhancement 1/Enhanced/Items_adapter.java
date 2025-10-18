package com.example.cs_360inventoryappproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Items_adapter extends RecyclerView.Adapter<Items_adapter.ItemVH> {

    public interface OnItemClick {
        void onClick(item item);
    }

    private final List<item> items = new ArrayList<>();
    private final OnItemClick onItemClick;

    public Items_adapter(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void submit(List<item> newItems) {
        items.clear();
        if (newItems != null) items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ItemVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVH holder, int position) {
        item item = items.get(position);
        holder.name.setText(item.getName());
        holder.qty.setText(String.valueOf(item.getQuantity()));
        holder.itemView.setOnClickListener(v -> {
            if (onItemClick != null) onItemClick.onClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ItemVH extends RecyclerView.ViewHolder {
        TextView name;
        TextView qty;
        ItemVH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product);
            qty = itemView.findViewById(R.id.fullCase);
        }
    }
}
