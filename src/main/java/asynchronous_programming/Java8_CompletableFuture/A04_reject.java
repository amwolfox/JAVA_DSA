package async.Java8_CompletableFuture;

import java.util.concurrent.CompletableFuture;

public class A04_reject {
    public static void main(String[] args) {
        // Create a 'blank' future
        CompletableFuture<String> paymentTask = new CompletableFuture<>();

        // Simulate a rejection logic
        boolean isAccountFrozen = true;

        if (isAccountFrozen) {
            // REJECT the task manually
            paymentTask.completeExceptionally(new RuntimeException("Account is frozen. Transaction rejected."));
        } else {
            paymentTask.complete("Payment Successful");
        }

        // Handling the rejection downstream
        paymentTask.handle((res, ex) -> {
            if (ex != null) {
                System.out.println("Payment Failed: " + ex.getMessage());
                return "FAIL";
            }
            return res;
        }).thenAccept(System.out::println);
    }
}
/*
If you want to "reject" a task manually—meaning you want to explicitly mark a `CompletableFuture` as failed without necessarily throwing an exception from inside a lambda—you use the **`completeExceptionally`** method.

This is very common in event-driven systems (like a banking gateway) where you might decide to fail a task because a timeout occurred or a specific validation rule was broken.

### 1. Manual Rejection with `completeExceptionally`

When you create a `CompletableFuture` manually (not using `supplyAsync`), it stays in "Pending" status forever until you tell it otherwise. You have two choices:
* `future.complete(value)` -> Resolves it successfully.
* `future.completeExceptionally(ex)` -> Rejects it with an error.

---

### 2. The "Short-Circuit" Rejection
If you are mid-chain and want to "reject" the rest of the process based on a condition, you can throw a custom exception. This immediately skips all subsequent `thenApply` steps and jumps straight to the nearest `exceptionally` or `handle` block.



```java
CompletableFuture.supplyAsync(() -> "Order_Ready")
    .thenApply(status -> {
        if (inventoryIsLow()) {
            // This "rejects" the rest of the chain
            throw new RuntimeException("Out of Stock");
        }
        return "Order_Confirmed";
    })
    .thenApply(s -> s + " [Updating Ledger...]") // This will be SKIPPED
    .exceptionally(ex -> "Order Rejected: " + ex.getMessage()); // This will CATCH it
```

---

### 3. Comparison: When to use which?

| Approach | Best Use Case |
| :--- | :--- |
| **`completeExceptionally()`** | When you have a "naked" Future and need to fail it based on an external event (like a callback from a different system). |
| **`throw new RuntimeException()`** | When you are inside a `.thenApply` or `.supplyAsync` and encounter a business rule violation. |
| **`CompletableFuture.failedFuture(ex)`** | (Java 9+) When you want to return an already-failed future immediately without any processing. |

### Summary of the "Reject" Mindset
In Java's `CompletableFuture`, "rejection" isn't a separate keyword like in JavaScript Promises (`resolve/reject`). Instead:
1.  **Success** = `complete()` or returning a value.
2.  **Rejection** = `completeExceptionally()` or throwing an exception.
 */