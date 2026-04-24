package com.pluralsight.cart;

import com.pluralsight.product.Product;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> products;
    private ArrayList<Integer> quantities;

    public Cart(){
        products = new ArrayList<>();
        quantities = new ArrayList<>();

    }

    public void addProduct(Product product){
        int index = products.indexOf(product);

        if (index != -1){
            quantities.set(index, quantities.get(index) + 1);
        } else {
            products.add(product);
            quantities.add(1);
        }
    }

    public void removeProduct(Product product){
        int index = products.indexOf(product);

        if (index != -1){
            int qty = quantities.get(index);

            if ( qty > 1) {
                quantities.set(index, qty - 1);
            } else {
                products.remove(index);
                quantities.remove(index);
            }
        }
    }

    public void displayCart(){
        if (products.isEmpty()){
            System.out.println("Your cart is empty.");
        }
        System.out.println("Shopping Cart:");
        System.out.println("--------------------------------");

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            int qty = quantities.get(i);

            System.out.printf(
                    "%s | Qty: %d | Price: $%.2f | Total: $%.2f%n",
                    p.getProductName(),
                    qty,
                    p.getPrice(),
                    p.getPrice() * qty
            );
        }

        System.out.println("--------------------------------");
        System.out.printf("Cart Total: $%.2f%n", getTotal());
    }

        public double getTotal() {
            double total = 0;

            for (int i = 0; i < products.size(); i++) {
                total += products.get(i).getPrice() * quantities.get(i);
            }

            return total;
        }

        public void clearCart() {
            products.clear();
            quantities.clear();
        }

        public boolean isEmpty() {
            return products.isEmpty();
        }
}
