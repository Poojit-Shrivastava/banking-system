package BankingSystem.account;


import BankingSystem.exception.AccountNotFoundException;
import BankingSystem.exception.InvalidAmountException;
import BankingSystem.transaction.Transaction;
import BankingSystem.transaction.TransactionRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private final AccountRepository accountRepository = mock(AccountRepository.class);

    private final TransactionRepository transactionRepository = mock(TransactionRepository.class);

    private final AccountService accountService = new AccountService(accountRepository, null, transactionRepository);

    @Test
    void depositShouldIncreaseBalance(){
        Account account = new Account();
        account.setId(1);
        account.setBalance(5000);

        when(accountRepository.findById(1))
                .thenReturn(java.util.Optional.of(account));

        when(accountRepository.save(account))
                .thenReturn(account);
        Account result = accountService.deposit(1,1000);
        assertEquals(6000, result.getBalance());
        verify(accountRepository).save(account);


    }

    @Test
    void withdrawShouldDecreaseBalance(){
        Account account = new Account();
        account.setId(1);
        account.setBalance(5000);

        when(accountRepository.findById(1)).thenReturn(java.util.Optional.of(account));

        when(accountRepository.save(account))
                .thenReturn(account);

        Account result = accountService.withdraw(1, 2000);
        assertEquals(3000, result.getBalance());
        verify(accountRepository).save(account);
    }

    @Test
    void withdrawShouldRejectInsufficientBalance(){
        Account account = new Account();
        account.setId(1);
        account.setBalance(5000);

        when(accountRepository.findById(1)).thenReturn (java.util.Optional.of(account));

        assertThrows(BankingSystem.exception.InsufficientBalanceException.class, ()-> accountService.withdraw(1,6000));

        verify(accountRepository, never()).save(account);
    }

    @Test
    void withdrawShouldRejectInvalidAmount(){

        assertThrows(
                InvalidAmountException.class, ()-> accountService.withdraw(1, -500));
        verify(accountRepository, never()).findById(anyInt());
    }

    @Test
    void depositShouldThrowAccountDoesNotExist(){
        when(accountRepository.findById(99))
                .thenReturn(java.util.Optional.empty());
        assertThrows(AccountNotFoundException.class, () -> accountService.deposit(99, 1000));
    }

    @Test
    void depositShouldThrowWHenAccountDoesNotExist(){
        when(accountRepository.findById(99)).thenReturn(java.util.Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.deposit(99, 1000));
    }

    @Test
    void transactionShouldMoveMoneyAndCreateTransaction(){
        Account sender = new Account();
        sender.setId(1);
        sender.setBalance(5000);
        Account receiver = new Account();
        receiver.setId(2);
        receiver.setBalance(3000);

        when(accountRepository.findById(1))
                .thenReturn(java.util.Optional.of(sender));

        when(accountRepository.findById(2))
                .thenReturn(java.util.Optional.of(receiver));

        when(accountRepository.save(sender))
                .thenReturn(sender);
        when(accountRepository.save(receiver))
                .thenReturn(receiver);

        accountService.transfer(1,2,1000);

        assertEquals(4000, sender.getBalance());
        assertEquals(4000, receiver.getBalance());

        verify(accountRepository).save(sender);
        verify(accountRepository).save(receiver);

        verify(transactionRepository).save(any(Transaction.class));

    }

    @Test
    void transferShouldRejectInvalidAmount(){
        assertThrows(InvalidAmountException.class, ()->accountService.transfer(1,2,-500));
        verify(accountRepository, never()).findById(anyInt());
    }

    @Test
    void transferShouldRejectSameAccount(){
        Account account = new Account();
        account.setId(1);
        account.setBalance(5000);

        when(accountRepository.findById(1)).thenReturn(java.util.Optional.of(account));

        assertThrows(InvalidAmountException.class, () -> accountService.transfer(1,1,1000));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void transferShouldRejectInsufficientBalance(){
        Account sender = new Account();
        sender.setId(1);
        sender.setBalance(500);

        Account receiver = new Account();
        receiver.setId(2);
        receiver.setBalance(3000);

        when(accountRepository.findById(1)).thenReturn(java.util.Optional.of(sender));

        when(accountRepository.findById(2)).thenReturn(java.util.Optional.of(receiver));

        assertThrows(BankingSystem.exception.InsufficientBalanceException.class, ()-> accountService.transfer(1, 2, 100000));

        verify(accountRepository, never()).save(any(Account.class));

    }
    @Test
    void transferShouldRejectMissingSender(){
        when(accountRepository.findById(1)).thenReturn(java.util.Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> accountService.transfer(1,2,20011));
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void transferShouldRejectMissingReceiver(){

       Account sender = new Account();
       sender.setId(1);
       sender.setBalance(200);

       when(accountRepository.findById(1)).thenReturn(java.util.Optional.of(sender));

        when(accountRepository.findById(2)).thenReturn(java.util.Optional.empty());

        assertThrows(AccountNotFoundException.class, ()->accountService.transfer(1,2, 1000));

        verify(accountRepository, never()).save(any(Account.class));
    }
}
