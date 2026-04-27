package async.Java8_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class A08_MultipleAsync_anyof {

    public static void main(String[] args) {
        System.out.println("--- Racing for the fastest result ---");

        // Service 1: Might be slow
        CompletableFuture<String> serviceA = CompletableFuture.supplyAsync(() -> {
            simulateDelay(3);
            return "Result from Service A";
        });

        // Service 2: Might be fast
        CompletableFuture<String> serviceB = CompletableFuture.supplyAsync(() -> {
            simulateDelay(1);
            return "Result from Service B";
        });

        // The Race
        CompletableFuture<Object> fastest = CompletableFuture.anyOf(serviceA, serviceB);

        // Handle the winner
        fastest.thenAccept(result -> {
            System.out.println("Winner: " + result);
        }).join();
    }

    private static void simulateDelay(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
        }
    }
}


/*
While `allOf` waits for **every** task to finish, `anyOf` is the "race" version. It returns a new `CompletableFuture` that completes as soon as **any** one of the provided tasks finishes (either successfully or with an exception).

The result of the `anyOf` future is the result of whichever task "crossed the finish line" first.

---

### 1. The Scenario: Redundancy / Faster Response
In high-performance systems (like high-frequency trading or a banking gateway), you might ask three different "Exchange Rate" services for a price. You don't care which one answers, you just want the **fastest** one.



### 2. The Code Example

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AnyOfExample {

    public static void main(String[] args) {
        System.out.println("--- Racing for the fastest result ---");

        // Service 1: Might be slow
        CompletableFuture<String> serviceA = CompletableFuture.supplyAsync(() -> {
            simulateDelay(3);
            return "Result from Service A";
        });

        // Service 2: Might be fast
        CompletableFuture<String> serviceB = CompletableFuture.supplyAsync(() -> {
            simulateDelay(1);
            return "Result from Service B";
        });

        // The Race
        CompletableFuture<Object> fastest = CompletableFuture.anyOf(serviceA, serviceB);

        // Handle the winner
        fastest.thenAccept(result -> {
            System.out.println("Winner: " + result);
        }).join();
    }

    private static void simulateDelay(int sec) {
        try { TimeUnit.SECONDS.sleep(sec); } catch (InterruptedException e) {}
    }
}
```

---

### 3. Key Characteristics of `anyOf`

* **Return Type:** It returns `CompletableFuture<Object>`. Since the input tasks might return different types (e.g., one returns a `String`, another an `Integer`), the result is generalized to the common denominator, `Object`. You may need to cast it later.
* **The First One Wins:** Once the first task finishes, the `anyOf` future completes. The other tasks **keep running** in the background (Java does not automatically kill them), but their results are simply ignored by the `anyOf` chain.
* **Failure counts as finishing:** If the fastest task **fails**, the `anyOf` future completes with that exception. It doesn't wait for a successful one from the remaining tasks unless you handle the error inside the individual tasks first.

---

### 4. `allOf` vs. `anyOf` (The "Stress-Free" Comparison)

| Feature | `allOf` | `anyOf` |
| :--- | :--- | :--- |
| **Goal** | **Synchronization** (Wait for all). | **Redundancy** (Wait for the first). |
| **Logic** | "I need all the parts before I build the car." | "I need a taxi; whichever gets here first." |
| **Return Type** | `CompletableFuture<Void>` | `CompletableFuture<Object>` |
| **Failure** | Fails if **any** task fails (unless handled). | Fails if the **fastest** task fails. |

### When to use `anyOf` in Banking?
* **Redundant API Calls:** Fetching currency conversion rates from two different providers (e.g., Bloomberg vs. Reuters) to ensure the fastest possible UI update.
* **Search:** Searching for a customer record in a fast "Hot Cache" (Redis) and a slower "Main DB" simultaneously. If Redis responds first, you use it and move on.
 */
