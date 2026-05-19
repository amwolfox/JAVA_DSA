package asynchronous_programming.Java8_CompletableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class A10_Atomic {
    public static void main(String[] args) {

        CompletableFuture<List<Integer>> task1 = CompletableFuture.supplyAsync(() -> {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < 1000; i++) list.add(i);
            return list;
        });

        CompletableFuture<List<Integer>> task2 = CompletableFuture.supplyAsync(() -> {
            List<Integer> list = new ArrayList<>();
            for (int i = 1000; i < 2000; i++) list.add(i);
            return list;
        });

        // Use thenCombine to merge results safely
        CompletableFuture<List<Integer>> combinedResult = task1.thenCombine(task2, (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        });

        System.out.println("Safe Combined Size: " + combinedResult.join().size());
    }
}

/*
When two threads attempt to extract or modify data from the same source simultaneously in Java, you encounter **Race Conditions** and **Thread Interference**.

If the source is not "thread-safe" (like a standard `ArrayList` or a simple `int` counter), the data will likely become corrupted, or updates will be lost.

---

### 1. The Problem: Race Condition (Java 8/11)
In this example, two threads try to increment a shared counter. Because `count++` is not an atomic operation (it involves reading, incrementing, and writing), the threads will overwrite each other's work.

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SharedSourceProblem {
    private static int sharedCounter = 0; // The shared source

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Task: Increment the counter 10,000 times
        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                sharedCounter++; // NOT THREAD SAFE
            }
        };

        System.out.println("Starting threads...");
        executor.submit(task);
        executor.submit(task);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Expected: 20,000 | Actual: Usually much less (e.g., 14,320)
        System.out.println("Final Counter Value: " + sharedCounter);
    }
}
```



---

### 2. The Solution: Synchronized (The "Lock" approach)
To fix this in Java 8/11, you must ensure only one thread can access the source at a time using the `synchronized` keyword. This creates a "monitor lock."

```java
public class SharedSourceFixed {
    private static int sharedCounter = 0;

    // The 'synchronized' keyword ensures only 1 thread enters at a time
    private static synchronized void increment() {
        sharedCounter++;
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                increment();
            }
        };

        executor.submit(task);
        executor.submit(task);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Result will always be 20,000
        System.out.println("Safe Counter Value: " + sharedCounter);
    }
}
```

---

### 3. Modern Java 8+ Solution: Atomic Variables
For simple data like numbers, using `synchronized` is heavy. Java provides `AtomicInteger`, which uses low-level CPU instructions (Compare-And-Swap) to update data without traditional locking.

```java
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicExample {
    private static AtomicInteger atomicCounter = new AtomicInteger(0);

    public static void main(String[] args) {
        // ... (inside thread loop)
        atomicCounter.incrementAndGet(); // Thread-safe and faster than synchronized
    }
}
```

---

### What happens at the Memory Level?
1.  **Visibility Issues:** Thread A might update the value in its local CPU cache, but Thread B might still be reading the old value from main memory.
2.  **Atomicity Issues:** As shown in the code, the `++` operation is actually three steps. If Thread B interrupts Thread A between step 1 and step 3, the data is lost.



### Summary for your stress:
* **Default Behavior:** Threads will "step on each other," leading to inconsistent data.
* **Java 8/11 fix:** Use `synchronized` blocks or `ReentrantLock`.
* **Best Practice:** Use **Atomic** classes (`AtomicLong`, `AtomicReference`) or **Concurrent Collections** (`ConcurrentHashMap`) so you don't have to manage locks manually.

Great catch. In the previous code, I used the classic `ExecutorService` and `Runnable` approach to show you how "raw" threads clash. But you can—and should—see how `CompletableFuture` behaves when pointing at the same source.

When using `CompletableFuture`, the same **Race Condition** rules apply. If multiple futures try to modify one variable, you still get corrupted data unless you synchronize the access.

---

### The Code: Race Condition using CompletableFuture (Java 8/11)

In this version, we launch two asynchronous tasks that both target the same `List`. Since `ArrayList` is not thread-safe, this code will likely throw a `ConcurrentModificationException` or end up with missing data.

```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class CompletableFutureRace {

    // Shared Source: Not Thread-Safe
    private static List<Integer> sharedList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Starting two tasks extracting to the same list...");

        // Task 1: Add 1000 items
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            IntStream.range(0, 1000).forEach(sharedList::add);
        });

        // Task 2: Add 1000 items
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            IntStream.range(0, 1000).forEach(sharedList::add);
        });

        // Wait for both to finish
        CompletableFuture.allOf(task1, task2).join();

        // Expected size: 2000 | Actual: Likely less, or an Exception occurred
        System.out.println("Final List Size: " + sharedList.size());
    }
}
```



---

### How to fix it using CompletableFuture?

#### Option A: Use a Thread-Safe Collection
Instead of `ArrayList`, use `CopyOnWriteArrayList` or wrap it in `Collections.synchronizedList`. This is the easiest fix.

#### Option B: Use `thenCombine` (The Functional Way)
The "best practice" with `CompletableFuture` is to **avoid sharing a source entirely**. Instead of both tasks writing to one list, let each task return its *own* list, and combine them at the end. This is "Parallel Functional Programming."

```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureFunctional {
    public static void main(String[] args) {

        CompletableFuture<List<Integer>> task1 = CompletableFuture.supplyAsync(() -> {
            List<Integer> list = new ArrayList<>();
            for(int i=0; i<1000; i++) list.add(i);
            return list;
        });

        CompletableFuture<List<Integer>> task2 = CompletableFuture.supplyAsync(() -> {
            List<Integer> list = new ArrayList<>();
            for(int i=1000; i<2000; i++) list.add(i);
            return list;
        });

        // Use thenCombine to merge results safely
        CompletableFuture<List<Integer>> combinedResult = task1.thenCombine(task2, (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        });

        System.out.println("Safe Combined Size: " + combinedResult.join().size());
    }
}
```

---

### Summary for your stress:
1.  **Shared State is the Enemy:** If you use `runAsync` to modify a shared variable (like an `int` or a `List`), you will have the same thread-safety issues as old Java threads.
2.  **`CompletableFuture` Power:** Use `supplyAsync` so each thread works on its own data, then use `thenCombine` or `allOf` to merge them. This eliminates the need for `synchronized` entirely.
3.  **The "Monitor" Rule:** If you *must* use a shared source, you must use `Atomic` classes or `Concurrent` collections inside the `CompletableFuture` lambdas.


In a high-scale banking or enterprise environment, the **"Monitor" Rule** is your insurance policy. When multiple threads (via `CompletableFuture`) access a shared resource like a Database, you cannot rely on simple variables.

Here is the breakdown of how to apply this rule to **Atomic classes**, **Concurrent collections**, and **Database connections**.

---

### 1. The Atomic Rule (For Counters/Flags)
If multiple `CompletableFutures` are updating a shared status (e.g., counting total processed transactions), a standard `int` will fail. `AtomicInteger` uses a CPU-level trick called **Compare-And-Swap (CAS)** to update the value without locking the entire thread.



```java
AtomicInteger successCount = new AtomicInteger(0);

CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
    // Thread-safe increment without 'synchronized'
    successCount.incrementAndGet();
});
```

---

### 2. The Concurrent Rule (For Data Structures)
If you are extracting data into a shared Map (e.g., a cache of User IDs and their Balances), a standard `HashMap` will crash with a `ConcurrentModificationException`.

**The Solution:** Use `ConcurrentHashMap`. It uses "Bucket-level locking," meaning Thread A can write to "User 1" while Thread B writes to "User 10" simultaneously without waiting for each other.



---

### 3. The Database Connection Rule
This is where it gets real. You **never** share a single `Connection` object across multiple `CompletableFutures`. Most Database Drivers (like JDBC) are **not thread-safe**. If two threads use the same `Connection` to run different SQL queries at the same time, the results will mix up, or the connection will close unexpectedly.

#### The Best Practice: Connection Pooling (HikariCP)
Instead of sharing one connection, you use a **Connection Pool**. Each `CompletableFuture` "borrows" its own private connection from the pool, uses it, and returns it.



```java
// Simulated Database Service using a Connection Pool
public class BankDatabaseService {

    // HikariCP DataSource is thread-safe
    private HikariDataSource dataSource = new HikariDataSource();

    public CompletableFuture<Void> updateBalance(String userId, double amount) {
        return CompletableFuture.runAsync(() -> {
            // Each async task gets its OWN connection from the pool
            try (Connection conn = dataSource.getConnection()) {
                String sql = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setDouble(1, amount);
                pstmt.setString(2, userId);
                pstmt.executeUpdate();
                System.out.println("Balance updated for: " + userId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
```

---

### Summary of the "Monitor" Rule for Shared Sources

| Shared Source | The Wrong Way | The "Monitor" Rule Way |
| :--- | :--- | :--- |
| **Simple Counter** | `int count++` | `AtomicInteger` / `LongAdder` |
| **Data List/Map** | `ArrayList` / `HashMap` | `CopyOnWriteArrayList` / `ConcurrentHashMap` |
| **Database** | Shared `Connection` object | **Connection Pool** (Hikari/Druid) |
| **External API** | Shared `HttpClient` (sometimes) | Thread-safe Client (e.g., `java.net.http.HttpClient`) |

### Why this saves you from "Deep Stress":
By following the Monitor Rule, you don't have to write `synchronized` blocks. `synchronized` is slow because it stops all other threads. Atomic classes and Concurrent collections are "non-blocking" or "smart-locking," which means your application stays fast even when millions of requests are hitting the same database pool.

 */
