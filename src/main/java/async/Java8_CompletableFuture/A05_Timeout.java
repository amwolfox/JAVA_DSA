package async.Java8_CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class A05_Timeout {
    public static void main(String[] args) {

        CompletableFuture.supplyAsync(() -> {
                    simulateDelay(5); // Task takes 5 seconds
                    return "Payment Processed";
                })
                // If the task isn't done in 2 seconds, "Reject" it
                .orTimeout(2, TimeUnit.SECONDS)
                .exceptionally(ex -> {
                    // ex will be a TimeoutException
                    return "Task Rejected: Took too long!";
                })
                .thenAccept(System.out::println);

        simulateDelay(6); // Keep JVM alive
    }

    private static void simulateDelay(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
        }
    }
}

/*
Method	Outcome	Best For
complete(value)	-> Success	Resolving a task normally.
completeExceptionally(ex)	-> Rejection	Manual failure (e.g., account frozen).
orTimeout(time, unit)	-> Rejection	Hard cutoff for performance (SLA).
completeOnTimeout(val, t, u)	-> Fallback	Returning a safe default if slow.
 */