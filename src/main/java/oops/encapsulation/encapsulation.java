package oops.encapsulation;

class BankAccount {
    // 1. DATA HIDING: No one can see or touch this directly
    private double balance;
    private String accountNumber;
    public BankAccount(String accountNumber, double initialDeposit) {
        this.accountNumber = accountNumber;
        // Using the setter even in the constructor to ensure validation
        setBalance(initialDeposit);
    }

    // 2. GETTER: Read-only access
    public double getBalance() {
        return balance;
    }

    // 3. SETTER: Controlled write access (The "Stress" point)
    public void setBalance(double amount) {
        // VALIDATION: This is the power of encapsulation
        if (amount >= 0) {
            this.balance = amount;
        } else {
            System.out.println("ERROR: Balance cannot be negative!");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        }
    }
}

public class encapsulation {
    public static void main(String[] args) {
        // 1. Creating a valid account
        BankAccount myAccount = new BankAccount("SAV-12345", 500.0);

        System.out.println("--- Initial State ---");
        // We use the Getter to see the balance
        System.out.println("Account Balance: $" + myAccount.getBalance());

        System.out.println("\n--- Testing Valid Deposit ---");
        myAccount.deposit(150.0);
        System.out.println("New Balance: $" + myAccount.getBalance());

        System.out.println("\n--- Testing Data Integrity (The Stress Test) ---");
        // Direct access is impossible:
        // myAccount.balance = -1000.0; // ERROR: balance has private access in BankAccount

        // Attempting to set an invalid balance via the Setter
        System.out.println("Attempting to set balance to -$500...");
        myAccount.setBalance(-500.0);

        // Notice the balance remains unchanged because the Setter blocked the invalid data
        System.out.println("Current Balance after failed attempt: $" + myAccount.getBalance());

        System.out.println("\n--- Testing Valid Update ---");
        myAccount.setBalance(1000.0);
        System.out.println("Final Balance: $" + myAccount.getBalance());
    }
}