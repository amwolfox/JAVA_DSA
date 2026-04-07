package oops;

import java.util.*;
import java.util.function.*;

public class lambda {
    public static void main(String[] args) {

        // 1. RUNNABLE: Just starts a task (No input, No output)
        Runnable bootSystem = () -> System.out.println(">>> System Booting Up...");
        bootSystem.run();

        // 2. SUPPLIER: Provides data (No input, gives Output)
        // Generates a random Order ID
        Supplier<String> idGenerator = () -> "ORD-" + UUID.randomUUID().toString().substring(0, 8);
        String newOrderId = idGenerator.get();

        // 3. PREDICATE: Tests a condition (Input -> Boolean)
        // Checks if an order amount is eligible for a discount
        Predicate<Double> isHighValue = (amount) -> amount > 1000.0;

        // 4. FUNCTION: Transforms data (Input T -> Output R)
        // Applies a 10% discount to the price
        Function<Double, Double> applyDiscount = (price) -> price * 0.9;

        // 5. CONSUMER: Processes data (Input -> No output)
        // Prints the final receipt
        Consumer<String> logger = (message) -> System.out.println("[LOG]: " + message);

        // --- SIMULATING THE WORKFLOW ---
        double orderAmount = 1200.0;
        logger.accept("Processing order " + newOrderId + " for amount: " + orderAmount);

        if (isHighValue.test(orderAmount)) {
            double finalPrice = applyDiscount.apply(orderAmount);
            logger.accept("High value detected! Final discounted price: " + finalPrice);
        } else {
            logger.accept("Standard order. No discount applied.");
        }

        System.out.println(">>> Task Completed.");
    }
}