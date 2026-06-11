package com.tcs.ilp.ParcelManagement.config;

import com.tcs.ilp.ParcelManagement.model.User;
import com.tcs.ilp.ParcelManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Seeds default accounts on startup so the app works out-of-the-box.
 *
 * Default Officer : email = officer@shipzen.com  / password = Officer@123
 * Default Customer: email = customer@shipzen.com / password = Customer@123
 */
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {

        // Seed Officer account
        if (userRepository.findByEmail("officer@shipzen.com") == null) {
            User officer = new User();
            officer.setCustomerName("ShipZen Officer");
            officer.setEmail("officer@shipzen.com");
            officer.setMobile("9000000001");
            officer.setAddress("ShipZen HQ, Bengaluru");
            officer.setUserId("officer01");
            officer.setPassword("Officer@123");
            officer.setRole("OFFICER");
            userRepository.save(officer);
            System.out.println("[DataSeeder] Officer account created: officer@shipzen.com / Officer@123");
        }

        // Seed Customer account
        if (userRepository.findByEmail("customer@shipzen.com") == null) {
            User customer = new User();
            customer.setCustomerName("John Customer");
            customer.setEmail("customer@shipzen.com");
            customer.setMobile("9000000002");
            customer.setAddress("123 Main Street, Mumbai - 400001");
            customer.setUserId("customer01");
            customer.setPassword("Customer@123");
            customer.setRole("CUSTOMER");
            userRepository.save(customer);
            System.out.println("[DataSeeder] Customer account created: customer@shipzen.com / Customer@123");
        }
    }
}
