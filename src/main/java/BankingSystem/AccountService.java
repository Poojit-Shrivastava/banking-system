package BankingSystem;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(
            AccountRepository accountRepository,
            CustomerRepository customerRepository, TransactionRepository transactionRepository) {

        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
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
                                new AccountNotFoundException(
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
                                new AccountNotFoundException(
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

    //MONEY TRANSFER
    @Transactional
    public void transfer(
            Integer fromAccountId,
            Integer toAccountId,
            double amount){
        if(amount <= 0){
            throw new InvalidAmountException("Amount must be greater than zero");
        }

        Account fromAccount = accountRepository.findById(fromAccountId).orElseThrow(()-> new AccountNotFoundException("Sender account not found"));

        Account toAccount = accountRepository.findById(toAccountId).orElseThrow(()-> new AccountNotFoundException("Receiver account not found"));

        if(fromAccount.getId().equals(toAccount.getId())) throw new InvalidAmountException("Cannot transfer to the same account");

        if(amount>fromAccount.getBalance()) throw new InsufficientBalanceException("insufficient balance");

        fromAccount.setBalance(fromAccount.getBalance() - amount);

        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();

        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setType("TRANSFER");
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
    }



}