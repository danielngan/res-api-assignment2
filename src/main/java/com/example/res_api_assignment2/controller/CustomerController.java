package com.example.res_api_assignment2.controller;

import com.example.res_api_assignment2.model.Customer;
//import com.example.res_api_assignment2.model.User;
import com.example.res_api_assignment2.services.CustomerService;
//import com.example.res_api_assignment2.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerCustomer(@RequestBody Map<String, String> request) {
        String firstName = request.get("firstName");
        String lastName = request.get("lastName");
        String email = request.get("email");
        String password = request.get("password");

        Map<String, Object> response = new HashMap<>();

        if (firstName == null || firstName.trim().isEmpty()
                || lastName == null || lastName.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.length() < 6) {
            response.put("message", "Invalid input. Ensure all fields are present and password is at least 6 characters.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            response.put("message", "Invalid email format.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (customerService.emailExists(email)) {
            response.put("message", "Email already registered.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password); // encryption done in service

        customerService.registerCustomer(customer);

        response.put("message", "Customer registered successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
