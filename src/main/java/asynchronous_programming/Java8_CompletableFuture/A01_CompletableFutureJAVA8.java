package async.Java8_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class A01_CompletableFutureJAVA8 {

    public static void main(String[] args) {
        System.out.println("--- App Started on Thread: " + Thread.currentThread().getName());

        // 1. Start an asynchronous task (Supply)
        CompletableFuture<String> flow = CompletableFuture.supplyAsync(() -> {
            simulateDelay(2); // Simulate a network call
            return "Order_#12345";
        });

        // 2. Chain a transformation (Apply)
        // This runs automatically once the supplyAsync finishes
        CompletableFuture<String> processedFlow = flow.thenApply(orderId -> {
            System.out.println("Processing " + orderId + " on Thread: " + Thread.currentThread().getName());
            return orderId + " [PAID]";
        });

        // 3. Final action (Accept)
        processedFlow.thenAccept(finalResult -> {
            System.out.println("Final Receipt: " + finalResult);
        });

        // This shows that the main thread is NOT blocked
        System.out.println("Main thread is free to do other work...");

        // Keep the JVM alive long enough to see the results
        simulateDelay(3);
    }

    private static void simulateDelay(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/*
To explain `CompletableFuture`, it’s best to think of it as a **manager** for a restaurant. Instead of the manager cooking the food themselves (blocking), they give the order to the kitchen (async) and tell the waiter what to do once the food is ready (callback).

Here is a complete, runnable example in Java that demonstrates a multi-step asynchronous workflow.

---

### The Scenario: Processing an Order
We will simulate three steps:
1.  **Fetching** the order (takes time).
2.  **Enriching** the order with payment details.
3.  **Printing** the final receipt.
---

### Key Methods Explained

#### 1. `supplyAsync(Supplier<U>)`
* **What it does:** Starts the background work.
* **Analogy:** You tell the kitchen, "Start cooking this burger." It returns a "Promise" (the CompletableFuture) immediately.

#### 2. `thenApply(Function<T, U>)`
* **What it does:** Transforms the result of the previous step. It takes an input and **returns** a new value.
* **Analogy:** Once the burger is cooked, "Add cheese to it."

#### 3. `thenAccept(Consumer<T>)`
* **What it does:** Consumes the final result. It takes an input but **returns nothing** (`void`).
* **Analogy:** Once the cheeseburger is ready, "Serve it to the customer."

#### 4. `exceptionally(Function<Throwable, T>)`
* **What it does:** Handles errors in the chain. If any step fails, this block catches the exception so the whole app doesn't crash.
* **Code Tip:** `.exceptionally(ex -> "Error: " + ex.getMessage())`

---

### Why use this over a simple `Thread`?

If you were using old-school threads, you would have to manually check `if (thread.isAlive())` or use a `join()`, which blocks the execution. `CompletableFuture` uses a **push model**: it pushes the result to the next stage the moment it is ready, making your CPU usage much more efficient.

In a Spring Boot environment, you would typically use this in a `@Service` class to call multiple external APIs at the same time and combine their results using `CompletableFuture.allOf()`.

Does this code structure make sense, or would you like to see how to handle multiple async tasks running at the same time?
 */