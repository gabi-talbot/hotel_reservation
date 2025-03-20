package com.gabi.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String firstName, String lastName, String email) throws IllegalArgumentException {
        this.firstName = firstName;
        this.lastName = lastName;
        if(!emailValidator(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }
    /**
     * Helper method to validate email input
     */
    private boolean emailValidator(String email) {
        Pattern pattern = Pattern.compile("^(.+)@(.+).(com|co.uk|org|net)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;
        return email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
