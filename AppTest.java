package koszyk;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private Product[] products;
    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        products = new Product[] {
            new Product("P1", "Apple", 5.0),
            new Product("P2", "Banana", 3.0),
            new Product("P3", "Cherry", 2.0),
            new Product("P4", "Date", 8.0),
            new Product("P5", "Elderberry", 6.0)
        };
        cart = new ShoppingCart(products);
    }

    @Test
    void testFindCheapestProduct() {
        Product cheapest = cart.findCheapestProduct();
        assertNotNull(cheapest);
        assertEquals("Cherry", cheapest.getName());
    }

    @Test
    void testFindMostExpensiveProduct() {
        Product mostExpensive = cart.findMostExpensiveProduct();
        assertNotNull(mostExpensive);
        assertEquals("Date", mostExpensive.getName());
    }

    @Test
    void testFindNCheapestProducts() {
        Product[] cheapestProducts = cart.findNCheapestProducts(3);
        assertEquals(3, cheapestProducts.length);
        assertEquals("Cherry", cheapestProducts[0].getName());
        assertEquals("Banana", cheapestProducts[1].getName());
        assertEquals("Apple", cheapestProducts[2].getName());
    }

    @Test
    void testFindNMostExpensiveProducts() {
        Product[] mostExpensiveProducts = cart.findNMostExpensiveProducts(3);
        assertEquals(3, mostExpensiveProducts.length);
        assertEquals("Date", mostExpensiveProducts[0].getName());
        assertEquals("Elderberry", mostExpensiveProducts[1].getName());
        assertEquals("Apple", mostExpensiveProducts[2].getName());
    }

    @Test
    void testSortProductsByPrice() {
        cart.sortProductsByPrice();
        assertEquals("Cherry", products[0].getName());
        assertEquals("Banana", products[1].getName());
        assertEquals("Apple", products[2].getName());
    }

    @Test
    void testSortProductsByName() {
        cart.sortProductsByName();
        assertEquals("Apple", products[0].getName());
        assertEquals("Banana", products[1].getName());
        assertEquals("Cherry", products[2].getName());
    }

    @Test
    void testCalculateTotalPrice() {
        double totalPrice = cart.calculateTotalPrice();
        assertEquals(24.0, totalPrice);
    }

    @Test
    void testApplyPromotions_TotalDiscount() {
        cart.applyPromotions();
        assertEquals(4.75, products[0].getDiscountPrice()); // Apple after 5% discount
        assertEquals(2.85, products[1].getDiscountPrice()); // Banana after 5% discount
        assertEquals(0.0, products[2].getDiscountPrice()); // Cherry (free)
    }

    @Test
    void testApplyPromotions_CompanyMug() {
        cart.applyPromotions();
        // Check console output manually for "Customer gets a free company mug!"
    }

    @Test
    void testApplyPromotions_CouponApplied() {
        cart.applyPromotions();
        assertEquals(3.5, products[0].getDiscountPrice()); // First product with 30% discount
    }
}
