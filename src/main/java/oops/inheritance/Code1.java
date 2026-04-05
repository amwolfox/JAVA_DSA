package oops.inheritance;

class Parent {
    String name = "Parent-Name"; // Shadowed variable
    String parentUnique = "Only in Parent";

    void show() { // Overridden method
        System.out.println("Method: Inside Parent Class");
    }

    void parentOnlyMethod() { // Unique to Parent
        System.out.println("Method: Parent's unique logic");
    }
}

class Child extends Parent {
    String name = "Child-Name"; // Shadows Parent's 'name'
    String childUnique = "Only in Child";

    @Override
    void show() { // Overriding Parent's 'show'
        System.out.println("Method: Inside Child Class");
    }

    void childOnlyMethod() { // Unique to Child
        System.out.println("Method: Child's unique logic");
    }
}

public class Code1 {
    public static void main(String[] args) {
        // UPCASTING: Parent reference, Child object
        Parent p = new Child();

        System.out.println("--- VARIABLES (Determined by Reference Type) ---");
        // It picks the Parent's variable because the reference 'p' is Type Parent
        System.out.println("Name: " + p.name);
        System.out.println("Parent Unique: " + p.parentUnique);
        // System.out.println(p.childUnique); // ERROR: Parent reference can't see Child's unique fields

        System.out.println("\n--- METHODS (Determined by Actual Object) ---");
        // It picks the Child's method because the actual object is a Child
        p.show();
        p.parentOnlyMethod(); // Parent method is inherited, so it works
        // p.childOnlyMethod(); // ERROR: Parent reference can't see Child's unique methods

        System.out.println("\n--- ACCESSING CHILD UNIQUE MEMBERS ---");
        // To see Child's unique stuff, we must DOWNCAST
        if (p instanceof Child) {
            Child c = (Child) p;
            System.out.println("Child Unique: " + c.childUnique);
            c.childOnlyMethod();
        }
    }
}