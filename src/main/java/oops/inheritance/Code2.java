package oops.inheritance;

class Parent2 {
    String name = "Parent-Name";

    void show() {
        System.out.println("Method: Inside Parent Class");
    }

    void parentOnlyMethod() {
        System.out.println("Method: Parent's unique logic");
    }
}

class Child2 extends Parent2 {
    String name = "Child-Name"; // Shadows Parent's 'name'
    String childUnique = "Only in Child";

    @Override
    void show() {
        System.out.println("Method: Inside Child Class");
    }

    void childOnlyMethod() {
        System.out.println("Method: Child's unique logic");
    }
}

public class Code2 {
    public static void main(String[] args) {
        // DIRECT INSTANTIATION: Child reference, Child object
        Child2 c = new Child2();

        System.out.println("--- VARIABLES (No ambiguity) ---");
        // It picks the Child's variable because the reference 'c' is Type Child
        System.out.println("Name: " + c.name);

        // To see the Parent's shadowed variable, you MUST cast it
        System.out.println("Parent Name (via cast): " + ((Parent2)c).name);

        System.out.println("\n--- METHODS ---");
        c.show();             // Runs Child's version (Overridden)
        c.parentOnlyMethod(); // Runs Parent's version (Inherited)
        c.childOnlyMethod();  // Runs Child's version (Unique) - NO ERROR HERE!

        System.out.println("\n--- ACCESSING EVERYTHING ---");
        System.out.println("Child Unique Field: " + c.childUnique);
    }
}