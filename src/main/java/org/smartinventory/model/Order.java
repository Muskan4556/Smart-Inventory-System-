package org.smartinventory.model;

import java.time.LocalDateTime;

public class Order extends BaseEntity {
    private int productId;
    private int quantity;
    private double total;

    public Order(int id, int productId, int quantity, double total) {
        super(id);
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
    }

    public Order(int productId, int quantity, double total) {
        this(0, productId, quantity, total);
    }

    // Getters
    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getTotal() { return total; }

    @Override
    public String toString() {
        return String.format("Order #%d | Product ID: %d | Qty: %d | Total: %.2f | Date: %s",
                id, productId, quantity, total, createdDate.toLocalDate());
    }
}
