package com.example.cs_360inventoryappproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class inventory_data {
    private final Map<String, item> byId = new ConcurrentHashMap<>();

    public boolean add(item item) {
        if (item == null) return false;
        return byId.putIfAbsent(item.getId(), item) == null;
    }

    public boolean upsert(item item) {
        if (item == null) return false;
        byId.put(item.getId(), item);
        return true;
    }

    public boolean updateQuantity(String id, int newQty) {
        item existing = byId.get(id);
        if (existing == null) return false;
        existing.setQuantity(newQty);
        return true;
    }

    public boolean remove(String id) {
        return byId.remove(id) != null;
    }

    public item get(String id) {
        return byId.get(id);
    }

    public List<item> all() {
        return Collections.unmodifiableList(new ArrayList<>(byId.values()));
    }

    public void clear() {
        byId.clear();
    }
}
