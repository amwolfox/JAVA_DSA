package async.Java8_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class A09_runAsync {

    public static void main(String[] args) {
        System.out.println("Processing Payment...");

        // 1. runAsync: Just "Fire and Forget"
        CompletableFuture<Void> logTask = CompletableFuture.runAsync(() -> {
            simulateDelay(2);
            System.out.println("Log saved: Transaction #9988 stored in Audit DB.");
        });

        // 2. Chaining after runAsync
        // Since there is no result, we use .thenRun()
        logTask.thenRun(() -> {
            System.out.println("Audit process complete. Thread freed.");
        });

        System.out.println("Main thread: Payment screen showing 'Success' to user.");

        // Wait so we can see the background log print
        simulateDelay(3);
    }

    private static void simulateDelay(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
        }
    }
}

/*
While `supplyAsync` is for tasks that **return a result** (like fetching a balance), `runAsync` is for tasks that **return nothing** (like sending a log, updating a cache, or triggering an email).

In Java terms, `supplyAsync` takes a `Supplier`, while `runAsync` takes a `Runnable`.

---

### 1. The Core Difference

| Method | Task Type | Return Value | Analogy |
| :--- | :--- | :--- | :--- |
| **`supplyAsync`** | Functional | `CompletableFuture<T>` | "Go to the store and **get me milk**." |
| **`runAsync`** | Action-based | `CompletableFuture<Void>` | "Go to the store and **drop off this letter**." |

---

### 3. When to use `runAsync`?



* **Logging:** Writing to a file or a remote logging server (ELK).
* **Analytics:** Sending a "User Clicked Buy" event to a tracking service.
* **Notifications:** Triggering an SMS or Email gateway.
* **Cache Invalidation:** Telling Redis to clear a specific key because the data changed.

---

### 4. Important Constraints

1.  **Void Result:** Because it returns `CompletableFuture<Void>`, you cannot use `.thenApply()` to transform data (because there is no data). You must use `.thenRun()`.
2.  **Error Handling:** Even though it doesn't return a value, it can still **fail**. If your logging DB is down, `runAsync` will throw an exception. You should still use `.exceptionally()` if you don't want the background failure to cause issues.
3.  **Thread Pool:** By default, it runs in the `ForkJoinPool.commonPool`. In a high-traffic banking app, you would likely pass a custom **Executor** to keep your logging tasks from slowing down your transaction tasks.

> **Example with Executor:**
> `CompletableFuture.runAsync(() -> doWork(), myCustomExecutor);`

### Summary for your stress
* Need data back? $\rightarrow$ **`supplyAsync`**
* Just need to execute a command? $\rightarrow$ **`runAsync`**

 */
