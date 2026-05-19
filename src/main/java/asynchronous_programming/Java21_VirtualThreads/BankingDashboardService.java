package asynchronous_programming.Java21_VirtualThreads;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class BankingDashboardService {
    private static final Logger logger = Logger.getLogger(BankingDashboardService.class.getName());

    // In Production, this Executor would be a Managed Bean (e.g., @Bean in Spring)
    private final ExecutorService virtualExecutor = Executors.newVirtualThreadPerTaskExecutor();

    public static void main(String[] args) {
        BankingDashboardService service = new BankingDashboardService();

        // Execute and handle the final result
        service.getDashboardData("USR_9901")
                .thenAccept(result -> System.out.println("PRODUCTION RESULT: " + result))
                .join();
    }

    public CompletableFuture<String> getDashboardData(String userId) {
        logger.info("Starting dashboard fetch for user: " + userId);

        // 1. Define Tasks with individual safety nets
        var balanceTask = fetchBalance(userId);
        var txnTask = fetchTransactions(userId);

        // 2. Combine with a hard Global Timeout
        return CompletableFuture.allOf(balanceTask, txnTask)
                .orTimeout(5, TimeUnit.SECONDS)
                .handle((voidResult, ex) -> {
                    if (ex != null) {
                        logger.severe("Dashboard partial failure for " + userId + ": " + ex.getMessage());
                        // Fallback: Combine whatever survived
                    }

                    return String.format(
                            "User: %s | %s | %s",
                            userId,
                            balanceTask.getNow("Balance: N/A"),
                            txnTask.getNow("Transactions: N/A")
                    );
                });
    }

    private CompletableFuture<String> fetchBalance(String userId) {
        return CompletableFuture.supplyAsync(() -> {
                    simulateNetworkIO(1); // Real DB/API call
                    return "₹85,000.00";
                }, virtualExecutor)
                .exceptionally(ex -> {
                    logger.warning("Balance service failed for " + userId);
                    return "Unavailable";
                });
    }

    private CompletableFuture<String> fetchTransactions(String userId) {
        return CompletableFuture.supplyAsync(() -> {
                    simulateNetworkIO(2);
                    // Simulate a random failure
                    if (Math.random() > 0.9) throw new RuntimeException("Service Timeout");
                    return "Last 3: Rent, Coffee, Petrol";
                }, virtualExecutor)
                .exceptionally(ex -> "No recent data");
    }

    private void simulateNetworkIO(int seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/*
Approach,"Is it ""Good""?",Use Case
With .join(),Yes (Synchronous),"When you must have the result before the next line (e.g., verifying a password before allowing a login)."
Without .join(),Yes (Asynchronous),"When you want to ""Fire and Forget"" or ""Trigger and Notify"" (e.g., sending an email, logging, or background fraud checks)."
 */