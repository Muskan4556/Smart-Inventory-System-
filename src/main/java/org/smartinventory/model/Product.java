package org.smartinventory.model;

public class Product extends BaseEntity {
    private String name;
    private String category;
    private double price;
    private int stock;

    public Product(int id, String name, String category, double price, int stock) {
        super(id);
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public Product(String name, String category, double price, int stock) {
        this(0, name, category, price, stock);
    }

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("%-5d %-15s %-10s %-10.2f %-5d", id, name, category, price, stock);
    }
}
