package oops.inheritance;

class Parent3 {
    String role;

    // Parent has a constructor that needs a String
    Parent3(String role) {
        this.role = role;
        System.out.println("Parent initialized as: " + role);
    }
}

class Child3 extends Parent3 {
    String name;

    Child3(String name){
        this(name,"SE");
        System.out.println("Ended Child initialisation");
    }

    Child3(String name, String role) {
        super(role); // Must be the VERY FIRST line
        this.name = name;
        System.out.println("Child initialized as: " + name);
    }
}

public class ConstructorInheritance {
    public static void main(String[] args) {
        Child3 c = new Child3("Arjun");
    }
}