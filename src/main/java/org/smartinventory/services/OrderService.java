package org.smartinventory.services;

import java.sql.*;

public class OrderService extends BaseService {

    private final ProductService productService = new ProductService();

    public void placeOrder(int productId, int quantity) {
        String selectProductSql = "SELECT * FROM products WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(selectProductSql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stock");
                double price = rs.getDouble("price");

                if (stock >= quantity) {
                    double total = price * quantity;

                    // Insert order
                    String insertOrderSql = "INSERT INTO orders (product_id, quantity, total) VALUES (?, ?, ?)";
                    try (PreparedStatement psOrder = conn.prepareStatement(insertOrderSql)) {
                        psOrder.setInt(1, productId);
                        psOrder.setInt(2, quantity);
                        psOrder.setDouble(3, total);
                        psOrder.executeUpdate();
                    }

                    // Update product stock
                    String updateStockSql = "UPDATE products SET stock = stock - ? WHERE id=?";
                    try (PreparedStatement psUpdate = conn.prepareStatement(updateStockSql)) {
                        psUpdate.setInt(1, quantity);
                        psUpdate.setInt(2, productId);
                        psUpdate.executeUpdate();
                    }

                    System.out.println("🛒 Order placed! Total = ₹" + total);

                } else {
                    System.out.println("Not enough stock!");
                }

            } else {
                System.out.println("Product not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
