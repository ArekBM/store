package com.pluralsight;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Scanner;
import com.pluralsight.product.Product;
import com.pluralsight.cart.Cart;

import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {

    private static ArrayList<Product> inventory = new ArrayList<>();
    private static Cart cart = new Cart();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        loadProducts("products.csv");

        boolean running = true;

        while (running) {
            System.out.println("\n=== HOME SCREEN ===");
            System.out.println("1. Display Products");
            System.out.println("2. Display Cart");
            System.out.println("3. Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayProductsScreen();
                    break;
                case "2":
                    displayCartScreen();
                    break;
                case "3":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // =========================
    // LOAD PRODUCTS FROM CSV
    // =========================
    private static void loadProducts(String s) {
        try {
            InputStream is = Main.class.getClassLoader().getResourceAsStream("products.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\\|");

                String name = parts[0];
                double price = Double.parseDouble(parts[1]);
                String department = parts[2];

                inventory.add(new Product(name, price, department));
            }

        } catch (Exception e) {
            System.out.println("Error loading products.");
            e.printStackTrace();
        }
    }

    // =========================
    // DISPLAY PRODUCTS SCREEN
    // =========================
    private static void displayProductsScreen() {

        boolean inProducts = true;

        while (inProducts) {
            System.out.println("\n=== PRODUCTS ===");

            for (int i = 0; i < inventory.size(); i++) {
                System.out.println((i + 1) + ". " + inventory.get(i));
            }

            System.out.println("\nOptions:");
            System.out.println("1 - Add to Cart");
            System.out.println("2 - Search");
            System.out.println("3 - Back");

            String input = scanner.nextLine();

            switch (input.toUpperCase()) {
                case "1":
                    System.out.print("Enter product number: ");
                    int index = Integer.parseInt(scanner.nextLine()) - 1;

                    if (index >= 0 && index < inventory.size()) {
                        cart.addProduct(inventory.get(index));
                        System.out.println("Added to cart.");
                    } else {
                        System.out.println("Invalid product.");
                    }
                    break;

                case "2":
                    searchProducts();
                    break;

                case "3":
                    inProducts = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // =========================
    // SEARCH PRODUCTS
    // =========================
    private static void searchProducts() {
        System.out.print("Enter search term: ");
        String term = scanner.nextLine().toLowerCase();

        System.out.println("\nSearch Results:");

        for (Product p : inventory) {
            if (p.getProductName().toLowerCase().contains(term)
                    || p.getCategory().toLowerCase().contains(term)
                    || String.valueOf(p.getPrice()).contains(term)) {

                System.out.println(p);
            }
        }
    }

    // =========================
    // DISPLAY CART SCREEN
    // =========================
    private static void displayCartScreen() {

        boolean inCart = true;

        while (inCart) {
            System.out.println("\n=== CART ===");
            cart.displayCart();

            System.out.println("\nOptions:");
            System.out.println("1 - Remove Product");
            System.out.println("2 - Checkout");
            System.out.println("3 - Back");

            String input = scanner.nextLine();

            switch (input.toUpperCase()) {
                case "1":
                    System.out.print("Enter product name to remove: ");
                    String name = scanner.nextLine().toLowerCase();

                    for (Product p : inventory) {
                        if (p.getProductName().toLowerCase().equals(name)) {
                            cart.removeProduct(p);
                            System.out.println("Removed from cart.");
                            break;
                        }
                    }
                    break;

                case "2":
                    checkout();
                    break;

                case "3":
                    inCart = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // =========================
    // CHECKOUT (basic version)
    // =========================
    private static void checkout() {

        double total = cart.getTotal();

        if (total == 0) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.printf("Total: $%.2f%n", total);
        System.out.print("Enter payment amount: ");

        double payment = Double.parseDouble(scanner.nextLine());

        if (payment < total) {
            System.out.println("Not enough money.");
        } else {
            double change = payment - total;

            System.out.printf("Change: $%.2f%n", change);
            System.out.println("Thank you for your purchase!");

            cart.clearCart();
        }
    }
}