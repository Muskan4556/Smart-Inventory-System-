package org.smartinventory;

import org.smartinventory.model.Product;
import org.smartinventory.services.OrderService;
import org.smartinventory.services.ProductService;
import org.smartinventory.services.ReportService;

import java.util.Scanner;

public class Main {

    private static void simulateConcurrentOrders() {
        OrderService os = new OrderService();

        Thread t1 = new Thread(() -> os.placeOrder(5, 2), "Thread-1: ");
        Thread t2 = new Thread(() -> os.placeOrder(7, 3), "Thread-2: ");
        Thread t3 = new Thread(() -> os.placeOrder(2, 1), "Thread-3: ");

        System.out.println("\nStarting multi-threaded order simulation...\n");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted: " + e.getMessage());
        }

        System.out.println("\n Multi-threaded order simulation complete!\n");
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductService ps = new ProductService();
        OrderService os = new OrderService();
        ReportService rs = new ReportService();

        while (true) {
            System.out.println("\n=== Smart Inventory Menu ===");
            System.out.println("1️ Add Product");
            System.out.println("2️ View Products");
            System.out.println("3️ Update Product (leave blank to keep current value)");
            System.out.println("4️ Delete Product");
            System.out.println("5️ Place Order");
            System.out.println("6️ View Low Stock");
            System.out.println("7️ Total Sales");
            System.out.println("8️ Most Sold Products");
            System.out.println("9️ Monthly Sales Report");
            System.out.println("10 Simulate Multi-Threaded Orders");
            System.out.println("0️ Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(sc.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Category: ");
                        String cat = sc.nextLine();
                        System.out.print("Price: ");
                        double price = Double.parseDouble(sc.nextLine());
                        System.out.print("Stock: ");
                        int stock = Integer.parseInt(sc.nextLine());
                        ps.addProduct(new Product(name, cat, price, stock));
                        break;

                    case 2:
                        System.out.println("ID   Name            Category   Price      Stock");
                        for (Product p : ps.getAllProducts()) {
                            System.out.println(p);
                        }

                        break;

                    case 3:
                        System.out.print("Product ID to update: ");
                        int uid = Integer.parseInt(sc.nextLine());
                        System.out.print("New Name (leave blank to keep current): ");
                        String nname = sc.nextLine();
                        System.out.print("New Category (leave blank to keep current): ");
                        String ncat = sc.nextLine();
                        System.out.print("New Price (leave blank to keep current): ");
                        String npriceStr = sc.nextLine();
                        System.out.print("New Stock (leave blank to keep current): ");
                        String nstockStr = sc.nextLine();

                        ps.updateProduct(uid, nname, ncat, npriceStr, nstockStr);
                        break;

                    case 4:
                        System.out.print("Product ID to delete: ");
                        int did = Integer.parseInt(sc.nextLine());
                        ps.deleteProduct(did);
                        break;

                    case 5:
                        System.out.print("Product ID: ");
                        int pid = Integer.parseInt(sc.nextLine());
                        System.out.print("Quantity: ");
                        int qty = Integer.parseInt(sc.nextLine());
                        os.placeOrder(pid, qty);
                        break;

                    case 6:
                        ps.showLowStock();
                        break;

                    case 7:
                        rs.totalSales();
                        break;

                    case 8:
                        rs.mostSoldProducts();
                        break;

                    case 9:
                        rs.monthlySales();
                        break;

                    case 10:
                        simulateConcurrentOrders();
                        break;


                    case 0:
                        System.out.println("Exiting...");
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Input Error: " + e.getMessage());
            }
        }
    }

}

