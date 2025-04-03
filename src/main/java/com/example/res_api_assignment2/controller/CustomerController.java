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
import java.util.Optional;

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

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginCustomer(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        String email = request.get("email");
        String password = request.get("password");

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            response.put("message", "Email and password must not be empty.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Authenticate by checking if email-password pair is correct
        boolean authenticated = customerService.authenticateCustomer(email, password);

        if (authenticated) {
            response.put("message", "Authentication successful.");
            return ResponseEntity.ok(response); // 200 OK
        } else {
            response.put("message", "Invalid email or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); // 401
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getCustomerInfo(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();

        // Validate ID
        if (id == null || id.trim().isEmpty()) {
            response.put("message", "Customer ID is missing or invalid.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<Customer> customerOpt = customerService.getCustomerById(id);

        if (customerOpt.isEmpty()) {
            response.put("message", "Customer not found for ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Customer customer = customerOpt.get();

        // Return only first name, last name, email
        Map<String, Object> customerData = new HashMap<>();
        customerData.put("firstName", customer.getFirstName());
        customerData.put("lastName", customer.getLastName());
        customerData.put("email", customer.getEmail());

        response.put("message", "Customer retrieved successfully.");
        response.put("customer", customerData);

        return ResponseEntity.ok(response);
    }

}
