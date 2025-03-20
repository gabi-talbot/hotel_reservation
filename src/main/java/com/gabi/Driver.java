package com.gabi;

import com.gabi.models.Customer;

public class Driver {

    public static void main(String[] args) {

        // Test Customer toString() method with valid input
        Customer customer = new Customer("first", "second", "j@domain.com");
        System.out.println(customer);

        // Test Customer constructor throws IllegalArgumentException with invalid email
        Customer customer2 = new Customer("first", "second", "email");
        System.out.println(customer2);

    }
}
