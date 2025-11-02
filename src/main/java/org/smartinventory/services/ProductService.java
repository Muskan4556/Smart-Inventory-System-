package org.smartinventory.services;

import org.smartinventory.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService extends BaseService {

    public void addProduct(Product p) {
        String sql = "INSERT INTO products (name, category, price, stock) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getCategory());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.executeUpdate();
            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    public void updateProduct(int id, String name, String category, String priceStr, String stockStr) {
        try (Connection conn = getConnection()) {

            String selectSql = "SELECT * FROM products WHERE id=?";
            Product current = null;
            try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    current = new Product(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getDouble("price"),
                            rs.getInt("stock")
                    );
                } else {
                    System.out.println("Product not found!");
                    return;
                }
            }

            String newName = name.isBlank() ? current.getName() : name;
            String newCategory = category.isBlank() ? current.getCategory() : category;
            double newPrice = priceStr.isBlank() ? current.getPrice() : Double.parseDouble(priceStr);
            int newStock = stockStr.isBlank() ? current.getStock() : Integer.parseInt(stockStr);

            String updateSql = "UPDATE products SET name=?, category=?, price=?, stock=? WHERE id=?";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setString(1, newName);
                ps.setString(2, newCategory);
                ps.setDouble(3, newPrice);
                ps.setInt(4, newStock);
                ps.setInt(5, id);
                ps.executeUpdate();
                System.out.println("Product ID " + id + " updated successfully!");
            }

        } catch (SQLException e) {
            System.out.println(" Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format for price or stock.");
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int deleted = ps.executeUpdate();
            if (deleted > 0) System.out.println("🗑Product deleted!");
            else System.out.println("Product not found!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showLowStock() {
        getAllProducts().stream()
                .filter(p -> p.getStock() < 5)
                .forEach(System.out::println);
    }
}
