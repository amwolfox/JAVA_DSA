package asynchronous_programming.Java21_VirtualThreads;

import java.time.Duration;
import java.util.concurrent.*;

public class A01_VirtualThread {

    public static void main(String[] args) {
        System.out.println("--- App Started on Thread: " + Thread.currentThread());

        // 1. We create the executor without try-with-resources to avoid the 'auto-close' barrier
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        // 2. Start Tasks on Virtual Threads
        CompletableFuture<String> balanceTask = CompletableFuture.supplyAsync(() -> {
            simulateDelay(2);
            return "Balance: ₹75,000";
        }, executor);

        CompletableFuture<String> profileTask = CompletableFuture.supplyAsync(() -> {
            simulateDelay(1);
            return "User: Alex";
        }, executor);

        // 3. Chain the final processing
        CompletableFuture<Void> dashboard = CompletableFuture.allOf(balanceTask, profileTask)
                .thenAccept(v -> {
                    System.out.println("\n--- FINAL DASHBOARD (Async) ---");
                    System.out.println(profileTask.join());
                    System.out.println(balanceTask.join());
                    System.out.println("Finished on: " + Thread.currentThread());
                });

        // 4. IMPORTANT: Notice NO .join() here.
        // This allows the main thread to immediately proceed to the next line.

        System.out.println(">>> Main thread is NOW free and doing other work! <<<");

        // Simulating the main thread doing something else
        for (int i = 1; i <= 3; i++) {
            System.out.println("Main thread working on Task " + i + "...");
            simulateDelay(1);
        }

        // Clean up the executor at the very end
        executor.shutdown();
        System.out.println("--- Main App Finished ---");
    }

    private static void simulateDelay(int seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/*
To make the main thread truly "free" while the Virtual Threads are working, we need to remove the "barrier" created by the `join()` call and the `try-with-resources` block.

In this corrected version, I move the "Main thread is free" message to where it actually executes asynchronously, and we use a more traditional executor management style to demonstrate the non-blocking behavior.

---

### Why this code behaves differently


#### 1. Removal of `try-with-resources`
In your previous code, the `try(...) { ... }` block was a "wall." The main thread was physically unable to pass the `}` until the executor finished every task. By defining the `executor` as a standard variable, the main thread can zip right past the task definitions.

#### 2. No `.join()` on the Main Thread
The `.join()` method is a "stop-everything-and-wait" command. In this new version, we use `.thenAccept()`. This tells the JVM: "Whenever you're done, run this code on your own time. I'm moving on to the next line of code."

#### 3. Execution Order
When you run this, you will see:
1.  "App Started"
2.  **"Main thread is NOW free..."** (This happens immediately)
3.  "Main thread working..."
4.  "FINAL DASHBOARD" (This pops up later when the virtual threads finish)

---

### The "Deep Stress" Takeaway
In a real-world **Spring Boot** application, you rarely call `.join()`. Instead, the framework manages the "future." You return the `CompletableFuture` to Spring, and Spring handles the non-blocking response to the user. This keeps your server's threads free to handle thousands of other incoming users while the database is still fetching the "Balance" for the first user.

Does seeing the "Main thread working" messages print *before* the dashboard make the non-blocking concept clearer?
 */