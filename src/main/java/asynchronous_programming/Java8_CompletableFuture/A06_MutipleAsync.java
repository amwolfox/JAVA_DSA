package async.Java8_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class A06_MutipleAsync {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        System.out.println("--- Dashboard Request Started ---");

        // 1. Define the Tasks (They start running immediately)
        CompletableFuture<String> balanceTask = CompletableFuture.supplyAsync(() -> {
            simulateDelay(2); // Slow DB call
            return "Balance: $5,000";
        });

        CompletableFuture<String> profileTask = CompletableFuture.supplyAsync(() -> {
            simulateDelay(1); // Fast API call
            return "User: John Doe";
        });

        CompletableFuture<String> txnTask = CompletableFuture.supplyAsync(() -> {
            simulateDelay(3); // Very slow service call
            return "Recent: Rent Paid, Groceries";
        });

        // 2. Combine them using allOf()
        // allOf() returns a "void" future that completes only when ALL tasks are done
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(balanceTask, profileTask, txnTask);

        // 3. Final Processing
        // We use .thenApply() to extract the results once the 'allTasks' manager is done
        CompletableFuture<String> dashboard = allTasks.thenApply(v -> {
            // Join simply retrieves the result (it won't block here because we know they are done)
            String res1 = balanceTask.join();
            String res2 = profileTask.join();
            String res3 = txnTask.join();

            return "--- DASHBOARD DATA ---\n" + res2 + "\n" + res1 + "\n" + res3;
        });

        // Print the result
        System.out.println(dashboard.join());

        long endTime = System.currentTimeMillis();
        System.out.println("Total Time Taken: " + (endTime - startTime) / 1000 + " seconds");
    }

    private static void simulateDelay(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
        }
    }
}

/*
When you have multiple independent tasks (like fetching a user's balance, credit score, and transaction history), you don't want to run them one after another. You want to run them **simultaneously** and then "join" them at the end.

In Java, we use `CompletableFuture.allOf()` to manage this.

---

### The Scenario: Banking Dashboard
We need to fetch three things to show a user their dashboard:
1.  **Account Balance** (from the Ledger DB)
2.  **User Profile** (from the Identity Service)
3.  **Recent Transactions** (from the Transaction Service)

---

### Deep Dive Explanation

#### 1. Parallel Execution

In synchronous code, the total time would be $2 + 1 + 3 = 6$ seconds. Because these are asynchronous, they run at the same time. The total time taken will only be equal to the **slowest task** (in this case, 3 seconds).

#### 2. `CompletableFuture.allOf()`
This is the "Waiting Room." It takes multiple futures and creates a single future that monitors them all.
* **Note:** `allOf` returns `CompletableFuture<Void>`, meaning it doesn't actually give you the results directly. It just tells you **when** they are ready.

#### 3. The `.join()` method
Inside the `thenApply` block, we call `.join()`. Usually, `.join()` is a blocking call, but because we are inside a block that only runs *after* `allOf` is finished, `.join()` returns the value **instantly** without waiting.

#### 4. Handling Errors in Multiple Tasks
If **any** task in `allOf` fails, the whole `allTasks` future will complete with an exception.
> **Best Practice:** Wrap each individual task in an `.exceptionally()` block before putting them in `allOf`. That way, if the Transaction Service fails, you can still show the Balance and Profile instead of showing a total error page.



### Summary Checklist for Multiple Tasks:
1.  **Define** each task using `supplyAsync`.
2.  **Combine** them using `CompletableFuture.allOf()`.
3.  **Access** the results using `.join()` inside a completion stage.
4.  **Recover** using `.exceptionally()` on the individual tasks to ensure partial success.

Does this parallel approach make sense for the high-scale banking logic you're thinking about?
 */