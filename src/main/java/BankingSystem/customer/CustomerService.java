package BankingSystem.customer;

import BankingSystem.account.AccountRepository;
import org.springframework.stereotype.Service;
import java.util.*;
import BankingSystem.auth.User;
import BankingSystem.auth.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public CustomerService(
            CustomerRepository customerRepository,
            AccountRepository accountRepository,
            UserRepository userRepository) {

        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }
    // CREATE
    public Customer createCustomer(Customer customer) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));
        customer.setUser(user);

        return customerRepository.save(customer);
    }

    // GET BY ID
    public Optional<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    // GET ALL
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // UPDATE
    public Optional<Customer> updateCustomer(
            Integer id,
            Customer updatedCustomer) {

        Optional<Customer> existingCustomer =
                customerRepository.findById(id);

        if (existingCustomer.isEmpty()) {
            return Optional.empty();
        }

        Customer customer = existingCustomer.get();

        customer.setName(updatedCustomer.getName());

        return Optional.of(
                customerRepository.save(customer)
        );
    }

    // DELETE
    public int deleteCustomer(Integer id) {

        // Customer doesn't exist
        Optional<Customer> customer =
                customerRepository.findById(id);

        if (customer.isEmpty()) {
            return 0;
        }

        // Check whether customer has accounts
        boolean hasAccounts = accountRepository.findAll()
                .stream()
                .anyMatch(account ->
                        account.getCustomer() != null &&
                                account.getCustomer()
                                        .getId()
                                        .equals(id)
                );

        // Customer cannot be deleted while accounts exist
        if (hasAccounts) {
            return 2;
        }

        // Safe to delete
        customerRepository.deleteById(id);

        return 1;
    }
}