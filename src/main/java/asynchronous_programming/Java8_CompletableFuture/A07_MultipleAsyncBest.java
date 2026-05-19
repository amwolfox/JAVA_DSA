package async.Java8_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class A07_MultipleAsyncBest {

    public static void main(String[] args) {
        System.out.println("--- Loading Resilient Dashboard ---");

        // 1. Task A: Essential Service (Balance)
        CompletableFuture<String> balanceTask = CompletableFuture.supplyAsync(() -> {
            simulateDelay(1);
            return "Balance: ₹50,000";
        }).exceptionally(ex -> "Balance: Service Unavailable");

        // 2. Task B: Essential Service (Profile)
        CompletableFuture<String> profileTask = CompletableFuture.supplyAsync(() -> {
            simulateDelay(1);
            return "User: Alex";
        }).exceptionally(ex -> "User: Unknown");

        // 3. Task C: Non-Essential Service (Simulating a Failure)
        CompletableFuture<String> txnTask = CompletableFuture.supplyAsync(() -> {
            simulateDelay(2);
            if (true) throw new RuntimeException("Database Timeout in Txn Service");
            return "Recent: No transactions";
        }).exceptionally(ex -> {
            // We catch the error here so allOf() never sees it
            System.err.println("Log: Transaction service failed, providing fallback.");
            return "Recent: Info currently unavailable";
        });

        // 4. Combine them
        // Because we used .exceptionally() above, allOf will ALWAYS succeed
        CompletableFuture<Void> allOf = CompletableFuture.allOf(balanceTask, profileTask, txnTask);

        // 5. Build the Final View
        allOf.thenAccept(v -> {
            String finalDashboard = String.format(
                    "=== DASHBOARD ===\n%s\n%s\n%s",
                    profileTask.join(),
                    balanceTask.join(),
                    txnTask.join()
            );
            System.out.println(finalDashboard);
        }).join(); // join() here just to wait for the final print in this demo
    }

    private static void simulateDelay(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
        }
    }
}

/*
This is a critical pattern in resilient system design. In banking, if the "Rewards Points" service is down, you still want to show the user their "Main Balance." You don't want the whole screen to crash just because one non-essential service failed.

By wrapping each task in `.exceptionally()`, you "neutralize" the error early, allowing `allOf` to proceed as if everything went fine.

---

### Why this is the "Best Practice"



#### 1. Preventing "All-or-Nothing" Failure
Without the individual `.exceptionally()` calls, if `txnTask` fails, the `allOf` future immediately completes with an exception. Even if `balanceTask` was successful, you wouldn't be able to easily access its data in the final step because the whole chain would be "broken."

#### 2. Graceful Degradation
This allows your UI to show partial data. In a banking app, showing a "Service Unavailable" message for a specific widget is much better than showing a generic `500 Internal Server Error` page.

#### 3. Predictable Flow
By returning a "Fallback Value" (like an empty string or a default object) inside `exceptionally`, you ensure that the `.join()` calls in your final step never throw an exception.

### Summary of the Flow
1.  **Isolate:** Each task handles its own disaster.
2.  **Standardize:** Each task returns a valid object (either real data or fallback data).
3.  **Aggregate:** `allOf` collects these "guaranteed" results.
4.  **Display:** The UI renders whatever it received.

 */