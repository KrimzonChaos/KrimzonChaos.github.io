package com.example.cs_360inventoryappproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Item_View extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory); // <-- your item_view.xml
    }
}