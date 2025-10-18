package com.example.cs_360inventoryappproject;

import java.util.Objects;

public class item {
    private final String id;
    private String name;
    private int quantity;
    private String description;

    public item(String id, String name, int quantity, String description) {
        if (id == null || id.isEmpty()) throw new IllegalArgumentException("id required");
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        item item = (com.example.cs_360inventoryappproject.item) o;
        return id.equals(item.id);
    }
    @Override public int hashCode() { return Objects.hash(id); }
}
