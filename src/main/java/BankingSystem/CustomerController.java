package BankingSystem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // CREATE CUSTOMER
    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(
            @RequestBody Customer customer) {

        Customer created =
                customerService.createCustomer(customer);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    // GET ALL CUSTOMERS
    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // GET CUSTOMER BY ID
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable Integer id) {

        Optional<Customer> customer =
                customerService.getCustomerById(id);

        if (customer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer.get());
    }

    // UPDATE CUSTOMER
    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Integer id,
            @RequestBody Customer updatedCustomer) {

        Optional<Customer> updated =
                customerService.updateCustomer(
                        id,
                        updatedCustomer
                );

        if (updated.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updated.get());
    }

    // DELETE CUSTOMER
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Integer id) {

        int result =
                customerService.deleteCustomer(id);

        // Customer doesn't exist
        if (result == 0) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        // Customer has accounts
        if (result == 2) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }

        // Successfully deleted
        return ResponseEntity
                .noContent()
                .build();
    }
}