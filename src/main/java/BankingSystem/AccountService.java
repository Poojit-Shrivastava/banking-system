package BankingSystem;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountService(
            AccountRepository accountRepository,
            CustomerRepository customerRepository) {

        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    // CREATE ACCOUNT
    public Account createAccount(AccountRequest request) {

        Optional<Customer> customer =
                customerRepository.findById(request.getCustomerId());

        if (customer.isEmpty()) {
            return null;
        }

        Account account = new Account();

        account.setName(request.getName());
        account.setBalance(request.getBalance());
        account.setCustomer(customer.get());

        return accountRepository.save(account);
    }

    // GET ALL ACCOUNTS
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // GET ACCOUNT BY ID
    public Optional<Account> getAccountById(Integer id) {
        return accountRepository.findById(id);
    }

    // UPDATE ACCOUNT
    public Optional<Account> updateAccount(
            Integer id,
            Account updatedAccount) {

        Optional<Account> existingAccount =
                accountRepository.findById(id);

        if (existingAccount.isEmpty()) {
            return Optional.empty();
        }

        Account account = existingAccount.get();

        account.setName(updatedAccount.getName());
        account.setBalance(updatedAccount.getBalance());

        return Optional.of(
                accountRepository.save(account)
        );
    }

    // DELETE ACCOUNT
    public boolean deleteAccount(Integer id) {

        if (!accountRepository.existsById(id)) {
            return false;
        }

        accountRepository.deleteById(id);

        return true;
    }

    // DEPOSIT
    public Account deposit(Integer id, double amount) {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Amount must be greater than zero"
            );
        }

        Account account =
                accountRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Account not found"
                                )
                        );

        account.setBalance(
                account.getBalance() + amount
        );

        return accountRepository.save(account);
    }

    // WITHDRAWAL
    public Account withdraw(Integer id, double amount) {

        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Amount must be greater than zero"
            );
        }

        Account account =
                accountRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Account not found"
                                )
                        );

        if (amount > account.getBalance()) {
            throw new InsufficientBalanceException(
                    "Insufficient balance"
            );
        }

        account.setBalance(
                account.getBalance() - amount
        );

        return accountRepository.save(account);
    }
}