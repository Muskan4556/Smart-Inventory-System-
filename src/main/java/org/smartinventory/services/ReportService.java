package org.smartinventory.services;

import org.smartinventory.interfaces.Reportable;

import java.sql.*;
import java.text.SimpleDateFormat;

public class ReportService extends BaseService implements Reportable {

    @Override
    public void totalSales() {
        String sql = "SELECT SUM(total) AS total_sales FROM orders";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                System.out.println("Total Sales: ₹" + rs.getDouble("total_sales"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void mostSoldProducts() {
        String sql = "SELECT product_id, SUM(quantity) AS total_qty FROM orders GROUP BY product_id ORDER BY total_qty DESC LIMIT 5";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Most Sold Products (Top 5):");
            while (rs.next()) {
                System.out.println("Product ID: " + rs.getInt("product_id") + " | Quantity Sold: " + rs.getInt("total_qty"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void monthlySales() {
        String sql = "SELECT DATE_TRUNC('month', order_date) AS month, SUM(total) AS sales " +
                "FROM orders GROUP BY month ORDER BY month";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("📅 onthly Sales:");
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");

            while (rs.next()) {
                Date monthDate = rs.getDate("month");
                String monthYear = sdf.format(monthDate);
                double sales = rs.getDouble("sales");
                System.out.println(monthYear + " → ₹" + sales);
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
