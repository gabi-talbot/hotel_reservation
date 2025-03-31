package com.gabi.service;

import com.gabi.models.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private final CustomerService customerService = CustomerService.getInstance();

    @BeforeEach
    void setUp() {
        customerService.getCustomerMap().put("johndoe@gmail.com", new Customer("John", "Doe", "johndoe@gmail.com"));
        customerService.getCustomerMap().put("jane@hotmail.co.uk", new Customer("Jane", "Doe", "jane@hotmail.co.uk"));
    }

    @AfterEach
    void tearDown() {
        customerService.getCustomerMap().clear();
    }


    @Test
    @DisplayName("Add customer with the correct details")
    void testAddCustomer_whenDetailsCorrect() {

        Customer customer = new Customer("Hotel", "Admin", "admin@hotel.org");
        customerService.addCustomer(customer);

        assertAll(
                () -> assertTrue(customerService.getCustomerMap().containsValue(customer)),
                () -> assertEquals("admin@hotel.org", customerService.getCustomerMap().get("admin@hotel.org").getEmail()),
                () -> assertEquals(3, customerService.getCustomerMap().size())
        );
    }


    @Test
    @DisplayName("Return customer when the exist in the collection")
    void testGetCustomer_whenCustomerExists() {

        String email = "johndoe@gmail.com";
        Optional<Customer> customer = customerService.getCustomer(email);

        assertAll(
                () -> assertTrue(customer.isPresent()),
                () -> assertEquals(Customer.class, customer.get().getClass()),
                () -> assertEquals(email, customer.get().getEmail())
        );
    }

    @Test
    @DisplayName("Return empty Optional when email is incorrect")
    void testGetCustomer_whenCustomerDoesNotExist() {

        String email = "doe@gmail.com";

        Optional<Customer> customer = customerService.getCustomer(email);

        assertTrue(customer.isEmpty());
    }


    @Test
    @DisplayName("Return all customers from collection")
    void testGetAllCustomers_whenCustomersExists() {

    List<Customer> customers = customerService.getAllCustomers();

    assertEquals(2, customers.size());

    }
}