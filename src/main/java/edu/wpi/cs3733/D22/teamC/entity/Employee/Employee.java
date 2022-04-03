package edu.wpi.cs3733.D22.teamC.entity.Employee;

public class Employee {
    public Employee(String firstName, String lastName, String email, Role role, boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.isAdmin = isAdmin;
    }

    private int employeeID;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private boolean isAdmin;

    public enum Role{
        DOCTOR,
        NURSE,
        LAB,
        SECURITY,
        THERAPIST,
        TECHNICIAN,
        JANITOR,
        IT,
        FOOD_SERVICE,
        PHARMACY
    }

    public Employee(){}


    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

}
