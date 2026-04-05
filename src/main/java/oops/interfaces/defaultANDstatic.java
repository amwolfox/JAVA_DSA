package oops.interfaces;

// THE INTERFACE
interface GPS {
    // 1. Abstract Method: MUST be implemented by any concrete class
    void getCoordinates();

    // 2. Default Method: Optional to override. Provides a base "behavior"
    default void turnOnGps() {
        System.out.println("GPS: Acquiring Satellite Link from generic interface...");
    }

    // 3. Static Method: Belongs to the Interface itself. Cannot be overridden.
    static void displaySafetyWarning() {
        System.out.println("STATIC WARNING: Do not use GPS while driving!");
    }
}

// THE IMPLEMENTATION
class SmartCar implements GPS {
    String model;

    SmartCar(String model) {
        this.model = model;
    }

    // Implementation of the mandatory Abstract Method
    @Override
    public void getCoordinates() {
        System.out.println(model + " Coordinates: 17.3850° N, 78.4867° E (Hyderabad)");
    }

    // Overriding the Default Method (Optional)
    @Override
    public void turnOnGps() {
        System.out.println(model + ": Connecting to high-speed private satellite...");
    }
}

class OldHandheldGPS implements GPS {
    // This class chooses NOT to override turnOnGps(), using the default instead.
    @Override
    public void getCoordinates() {
        System.out.println("Handheld: Coordinates: 0.0000° N, 0.0000° E");
    }
}

// THE MAIN EXECUTION
public class defaultANDstatic {
    public static void main(String[] args) {
        System.out.println("--- 1. Testing Static Method ---");
        // Static methods are called on the INTERFACE name, not the object.
        GPS.displaySafetyWarning();
        // myCar.displaySafetyWarning(); // ERROR! Static methods in interfaces are not inherited by objects.

        System.out.println("\n--- 2. Testing SmartCar (Overridden Default) ---");
        SmartCar myCar = new SmartCar("Tesla");
        myCar.getCoordinates();
        myCar.turnOnGps(); // Runs the SmartCar version

        System.out.println("\n--- 3. Testing OldHandheld (Using Default) ---");
        OldHandheldGPS handheld = new OldHandheldGPS();
        handheld.getCoordinates();
        handheld.turnOnGps(); // Runs the "GPS" Interface version automatically

        System.out.println("\n--- 4. Polmorphism Stress ---");
        GPS genericGps = new SmartCar("Generic-Car");
        genericGps.turnOnGps(); // Even with a GPS reference, it runs the SmartCar version (Polymorphism)
    }
}