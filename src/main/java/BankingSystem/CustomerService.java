package BankingSystem;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public CustomerService(
            CustomerRepository customerRepository,
            AccountRepository accountRepository) {

        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    // CREATE
    public Customer createCustomer(Customer customer) {
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