package async.Java8_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class A02_catching_error_exceptionally {

    public static void main(String[] args) {
        System.out.println("--- App Started on Thread: " + Thread.currentThread().getName());

        CompletableFuture.supplyAsync(() -> {
                    simulateDelay(1);

                    // SIMULATED FAILURE: 50% chance of throwing an error
                    if (Math.random() > 0.5) {
                        System.out.println("!! Error occurred in supplyAsync !!");
                        throw new RuntimeException("Database Timeout");
                    }

                    return "Order_#12345";
                })
                // 1. THE SAFETY NET: Handle errors from any previous step
                .exceptionally(ex -> {
                    System.err.println("Exception caught: " + ex.getMessage());
                    return "FALLBACK_ORDER_#00000"; // Return a default value to keep the chain alive
                })
                // 2. This will run regardless of whether we got the real order or the fallback
                .thenApply(orderId -> {
                    System.out.println("Processing " + orderId + " on Thread: " + Thread.currentThread().getName());
                    return orderId + " [PROCESSED]";
                })
                .thenAccept(finalResult -> {
                    System.out.println("Final Receipt: " + finalResult);
                });

        System.out.println("Main thread is free to do other work...");
        simulateDelay(3); // Keep JVM alive
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
To add a failure scenario, you need to handle the fact that exceptions in async threads don't bubble up to the `main` thread. If you don't handle them, your chain just stops silently.

I've updated your code to include a **random failure** and the `exceptionally` block, which acts as your safety net.

---

### Why this works


1.  **Isolation:** When the `RuntimeException` is thrown, the standard flow is interrupted. Instead of crashing the program, the `CompletableFuture` transitions to an **"Exceptionally Completed"** state.
2.  **Recovery:** The `.exceptionally()` method intercepts that state. It’s like a `try-catch` for the pipeline. It allows you to return a "fallback" value (like an empty list or a cached result) so that the subsequent `.thenApply()` and `.thenAccept()` steps don't crash.
3.  **The `ex` Parameter:** The `ex` provided to the handler is usually a `CompletionException`. To get your original message (like "Database Timeout"), you often look at `ex.getMessage()` or `ex.getCause()`.

### Common "Real World" Strategy
In a professional setup (like a banking API), you wouldn't just return a string. You might:
* **Log the error** to a monitoring system (like ELK or Splunk).
* **Trigger a retry** (using a loop or a library like Resilience4j).
* **Return an Error Object** that the UI can understand (e.g., `Result.failure("Internal Server Error")`).

Does this make sense for your use case, or do you want to see the `handle()` method which lets you process **both** the result and the error in one block?
 */