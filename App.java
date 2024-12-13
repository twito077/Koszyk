package koszyk;

import java.util.Arrays;
import java.util.Comparator;

class Product {
    private String code;
    private String name;
    private double price;
    private double discountPrice;

    public Product(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = price;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }
}

public class ShoppingCart {
    private Product[] products;
    private boolean couponApplied = false;

    public ShoppingCart(Product[] products) {
        this.products = products;
    }

    public Product findCheapestProduct() {
        return Arrays.stream(products)
                .min(Comparator.comparingDouble(Product::getPrice))
                .orElse(null);
    }

    public Product findMostExpensiveProduct() {
        return Arrays.stream(products)
                .max(Comparator.comparingDouble(Product::getPrice))
                .orElse(null);
    }

    public Product[] findNCheapestProducts(int n) {
        return Arrays.stream(products)
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .limit(n)
                .toArray(Product[]::new);
    }

    public Product[] findNMostExpensiveProducts(int n) {
        return Arrays.stream(products)
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .limit(n)
                .toArray(Product[]::new);
    }

    public void sortProductsByPrice() {
        Arrays.sort(products, Comparator.comparingDouble(Product::getPrice));
    }

    public void sortProductsByName() {
        Arrays.sort(products, Comparator.comparing(Product::getName));
    }

    public double calculateTotalPrice() {
        return Arrays.stream(products)
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public void applyPromotions() {
        double totalPrice = calculateTotalPrice();

        // Apply 5% discount if total order value is greater than 300 zł
        if (totalPrice > 300) {
            for (Product product : products) {
                product.setDiscountPrice(product.getPrice() * 0.95);
            }
        }

        // Apply "buy 2 get 3rd cheapest free" promotion
        if (products.length >= 3) {
            Product[] sortedProducts = findNCheapestProducts(products.length);
            sortedProducts[2].setDiscountPrice(0);
        }

        // Apply free company mug if total order value exceeds 200 zł
        if (totalPrice > 200) {
            System.out.println("Customer gets a free company mug!");
        }

        // Apply one-time 30% discount coupon on a selected product
        if (!couponApplied && products.length > 0) {
            Product selectedProduct = products[0]; // Assuming the first product is selected
            selectedProduct.setDiscountPrice(selectedProduct.getPrice() * 0.7);
            couponApplied = true;
        }
    }
}