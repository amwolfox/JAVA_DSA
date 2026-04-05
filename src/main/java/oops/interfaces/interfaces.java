package oops.interfaces;

// Interface 1: A Capability
interface Cleaner {
    int MAX_SPEED = 10; // Automatically: public static final

    void scrubFloor(); // Automatically: public abstract
}

// Interface 2: Another Capability
interface Security {
    void scanForIntruders();
}

// THE IMPLEMENTATION
// A class "signing" two contracts at once
class RoboCop implements Cleaner, Security {
    String model;

    RoboCop(String model) {
        this.model = model;
    }

    // Must implement methods from BOTH interfaces
    @Override
    public void scrubFloor() {
        System.out.println(model + " is mopping with precision.");
    }

    @Override
    public void scanForIntruders() {
        System.out.println(model + " is scanning for red heat signatures.");
    }

    void recharge() {
        System.out.println("Robot is charging...");
    }
}
public class interfaces {
    public static void main(String[] args) {
        // 1. Using the Concrete Class Reference
        // This reference can see EVERYTHING (Cleaner methods, Security methods, and unique methods)
        RoboCop myRobot = new RoboCop("Model-X100");

        System.out.println("--- Using Concrete Reference (RoboCop) ---");
        myRobot.scrubFloor();       // From Cleaner
        myRobot.scanForIntruders(); // From Security
        myRobot.recharge();         // Unique to RoboCop
        System.out.println("Max Speed Constant: " + Cleaner.MAX_SPEED);

        System.out.println("\n--- Using Interface Reference (Cleaner) ---");
        // 2. Upcasting to an Interface
        // This reference is "blind" to Security and unique RoboCop methods
        Cleaner cleaningMode = myRobot;
        cleaningMode.scrubFloor();
        // cleaningMode.scanForIntruders(); // ERROR! Cleaner interface doesn't know about security

        System.out.println("\n--- Using Interface Reference (Security) ---");
        Security securityMode = myRobot;
        securityMode.scanForIntruders();
        // securityMode.scrubFloor(); // ERROR! Security interface doesn't know about cleaning

        System.out.println("\n--- Dynamic Check (Downcasting) ---");
        // If we only have a Security reference, but we NEED it to mop:
        if (securityMode instanceof Cleaner) {
            ((Cleaner) securityMode).scrubFloor(); // Casting back to Cleaner
            System.out.println("Successfully switched from Security to Cleaning logic.");
        }
    }
}