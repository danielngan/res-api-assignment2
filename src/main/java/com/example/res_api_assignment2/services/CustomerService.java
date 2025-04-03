package com.example.res_api_assignment2.services;

//import com.example.library.model.User;
//import com.example.library.repository.UserRepository;
import com.example.res_api_assignment2.model.Customer;
import com.example.res_api_assignment2.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Customer> getCustomerById(String id) {
        return customerRepository.findById(id);
    }

    public boolean authenticateCustomer(String email, String rawPassword) {
        Optional<Customer> customerOpt = customerRepository.findByEmail(email);

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();

            // This checks if the password matches the one for THIS email
            return passwordEncoder.matches(rawPassword, customer.getPassword());
        }

        return false;
    }


    public boolean emailExists(String email) {
        return customerRepository.findByEmail(email).isPresent();
    }

    public Customer registerCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }
}
