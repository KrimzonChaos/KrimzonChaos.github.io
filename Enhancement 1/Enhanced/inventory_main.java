package com.example.cs_360inventoryappproject;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class inventory_main extends AppCompatActivity {

    private final inventory_data repo = new inventory_data();
    private Items_adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // Seed demo items
        repo.add(new item("SKU-1", "USB-C Cable", 24, "1m black"));
        repo.add(new item("SKU-2", "HDMI Switch", 7, "4x1"));
        repo.add(new item("SKU-3", "AA Batteries", 56, "Alkaline"));

        RecyclerView rv = findViewById(R.id.recyclerInventory);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Items_adapter(item -> {
            // TODO: open detail/edit screen if needed
        });
        rv.setAdapter(adapter);
        adapter.submit(repo.all());

        ImageButton addBtn = findViewById(R.id.fabAddItem);
        if (addBtn != null) {
            addBtn.setOnClickListener(v -> {
                int next = repo.all().size() + 1;
                repo.add(new item("SKU-" + (100 + next), "New Item " + next, 1, ""));
                adapter.submit(repo.all());
            });
        }
    }
}
