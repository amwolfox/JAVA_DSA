package async.Java8_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class A03_catching_error_handle {

    public static void main(String[] args) {
        System.out.println("--- Banking Service Started ---");

        // 1. Start an Async task
        CompletableFuture<Double> checkBalanceTask = CompletableFuture.supplyAsync(() -> {
            simulateDelay(1);

            // Randomly simulate a failure
            if (Math.random() > 0.5) {
                throw new RuntimeException("Core Banking System Offline");
            }

            return 5000.75; // Success: Current Balance
        });

        // 2. The MONITOR (handle)
        // 'res' is the balance (if success), 'ex' is the error (if failure)
        CompletableFuture<String> monitoredTask = checkBalanceTask.handle((res, ex) -> {
            if (ex != null) {
                // LOG the error and return a safe message
                System.err.println("Monitoring Alert: Task failed with -> " + ex.getMessage());
                return "Status: Error (Displaying Last Cached Balance: $0.00)";
            } else {
                // Transform the success result
                return "Status: Success (Available Balance: $" + res + ")";
            }
        });

        // 3. Output the result of the monitor
        monitoredTask.thenAccept(System.out::println);

        // Keep main thread alive
        simulateDelay(2);
    }

    private static void simulateDelay(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
        }
    }
}

/*
Since you're looking for a deep dive into the **"Monitor"** pattern, think of `handle()` as the ultimate safety valve. Unlike `exceptionally()`, which only triggers when things go wrong, `handle()` is like a supervisor that checks the work whether the kitchen succeeded or burned the toast.

Here is a clean, robust example using the `handle()` method to manage both success and failure in a single block.

---

### Why `handle()` is the "Monitor"


* **Bypasses "Silent Failures":** If you use `thenApply()` and an error occurs, the rest of the chain is skipped. `handle()` ensures your code **always** reaches this block.
* **Unified Logic:** You don't need a separate `catch` block. You handle the "Good Path" and the "Bad Path" in the exact same place.
* **Result Transformation:** It allows you to change the type. In the code above, we took a `Double` (the balance) and converted it into a `String` (the status message) regardless of whether the operation succeeded or failed.

---

### Comparison for Clarity

| Feature | `exceptionally()` | `handle()` |
| :--- | :--- | :--- |
| **When it runs** | Only if an error occurs. | **Always** (Success or Error). |
| **Input** | Only the Exception (`ex`). | Both Result (`res`) AND Exception (`ex`). |
| **Best Use Case** | Quick fallback values. | Complex logging, cleanup, or unified response mapping. |

### Pro-Tip for your stress
In a production environment, `ex` is often wrapped in a `CompletionException`. When monitoring, always check the **cause** to see what actually happened:
```java
if (ex != null) {
    Throwable realError = (ex.getCause() != null) ? ex.getCause() : ex;
    System.out.println("The actual problem was: " + realError.getMessage());
}
```
Qn. why thenApply is not used here ?

The reason we don't use `thenApply` for error handling is that **`thenApply` is a "fair-weather" method.** It only executes if everything is going perfectly.

If an exception occurs in a previous step, `thenApply` is completely **skipped**. The exception just flies right over it, looking for the next error-aware block (like `handle` or `exceptionally`).

---

### The Functional Difference

| Method | Behavior on Success | Behavior on Error |
| :--- | :--- | :--- |
| **`thenApply`** | Executes and transforms the result. | **Skipped entirely.** The error continues downstream. |
| **`handle`** | Executes and allows you to process the result. | **Executes** and allows you to catch the error. |

---

### Visualizing the "Skip"

Imagine your code as a pipeline of workers:

1.  **Worker 1 (`supplyAsync`):** Drops the glass (Throws Exception).
2.  **Worker 2 (`thenApply`):** This worker only knows how to clean glass. Since the glass is broken, they are bypassed. They never even see the error.
3.  **Worker 3 (`handle`):** This worker is the "Monitor." They are trained to handle both clean glass and broken shards. They catch the mess and decide what to do next.
---

### Code Comparison

#### Using `thenApply` (The Risk)
If the database fails here, the "Mapping..." message will never print, and your program might hang or fail silently because nothing caught the error.

```java
CompletableFuture.supplyAsync(() -> { throw new RuntimeException("DB Fail"); })
    .thenApply(res -> {
        // THIS CODE NEVER RUNS
        System.out.println("Mapping result...");
        return res + " processed";
    });
```

#### Using `handle` (The Security)
Even if the database fails, this code **will** run, allowing you to log the failure and return a "Safe" value so the rest of the app keeps working.

```java
CompletableFuture.supplyAsync(() -> { throw new RuntimeException("DB Fail"); })
    .handle((res, ex) -> {
        // THIS CODE ALWAYS RUNS
        if (ex != null) {
            return "Fallback Value";
        }
        return res;
    });
```

### Summary for your stress
* Use **`thenApply`** when you are 100% sure the data is there and you just want to change it (e.g., converting a Price to a String).
* Use **`handle`** when you are at a critical junction (like a banking transaction) where you **must** know if it failed so you can take action.

Does that help explain why `thenApply` isn't enough when you're worried about errors?
 */