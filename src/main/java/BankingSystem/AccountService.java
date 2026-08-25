package BankingSystem;

import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class AccountService {

   private final AccountRepository accountRepository;

   public AccountService(AccountRepository accountRepository){
       this.accountRepository = accountRepository;
   }
   public Account createAccount(Account account){
       return accountRepository.save(account);
   }
   public List<Account> getAllAccounts(){
       return accountRepository.findAll();
   }
   public Optional<Account> getAccountById(Integer id){
       return accountRepository.findById(id);
   }
   public Optional<Account> updateAccount(Integer id, Account updatedAccount){
       Optional<Account> existingAccount = accountRepository.findById(id);
       if(existingAccount.isEmpty()){
           return Optional.empty();
       }
       Account account = existingAccount.get();
       account.setName(updatedAccount.getName());
       account.setBalance(updatedAccount.getBalance());

       return  Optional.of(accountRepository.save(account));
   }
   public boolean deleteAccount(Integer id){
       if(!accountRepository.existsById(id))return false;
       accountRepository.deleteById(id);
       return true;
   }
}
