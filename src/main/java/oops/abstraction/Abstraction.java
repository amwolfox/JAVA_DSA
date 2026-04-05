package oops.abstraction;

// 1. THE ABSTRACT CLASS (The Template)
abstract class SmartDevice {
    String brand;
    boolean isOn;

    // A. Constructor in an Abstract Class? YES!
    // It's used to initialize shared variables for the children.
    SmartDevice(String brand) {
        this.brand = brand;
        this.isOn = false;
        System.out.println(brand + " device initialized in memory.");
    }

    // B. Concrete Method (Shared Logic)
    // All devices turn on the same way.
    void powerButton() {
        isOn = !isOn;
        System.out.println("Power is now: " + (isOn ? "ON" : "OFF"));
    }

    // C. Abstract Method (Forced Logic)
    // Every device performs a "Primary Function", but they do it differently.
    abstract void performPrimaryFunction();

    // D. Another Abstract Method
    abstract void showSpecifications();
}

// 2. THE SUBCLASS (The Implementation)
class Smartphone extends SmartDevice {
    int ram;

    Smartphone(String brand, int ram) {
        super(brand); // Calls the abstract class constructor
        this.ram = ram;
    }

    // Must implement ALL abstract methods or this class will error out
    @Override
    void performPrimaryFunction() {
        System.out.println("Smartphone: Making a VOIP call...");
    }

    @Override
    void showSpecifications() {
        System.out.println("Brand: " + brand + " | RAM: " + ram + "GB");
    }

    // Unique Child Method
    void browseInternet() {
        System.out.println("Browsing the web...");
    }
}

// 3. THE EXECUTION
public class Abstraction {
    public static void main(String[] args) {
        // SmartDevice d = new SmartDevice("Generic"); // ERROR! Cannot instantiate

        // Using Polymorphism (Parent p = new Child())
        SmartDevice myPhone = new Smartphone("Pixel", 12);

        System.out.println("--- Testing Functionality ---");
        myPhone.showSpecifications();     // Abstract method implementation
        myPhone.powerButton();            // Inherited concrete method
        myPhone.performPrimaryFunction(); // Abstract method implementation

        // myPhone.browseInternet(); // ERROR! Parent reference can't see unique Child methods

        if(myPhone instanceof Smartphone) {
            ((Smartphone)myPhone).browseInternet(); // Downcasting to access unique logic
        }
    }
}