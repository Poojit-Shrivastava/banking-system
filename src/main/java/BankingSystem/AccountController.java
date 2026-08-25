package BankingSystem;

import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
@RestController
public class AccountController {

    private final AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/accounts")
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Integer id){
        Optional<Account> account = accountService.getAccountById(id);
        if(account.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(account.get());
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {

       // System.out.println(
               // "Creating account for: " + account.getName()
        //);
        Account created = accountService.createAccount(account);
        if(created == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updatedAccount( @PathVariable Integer id, @RequestBody Account updatedAccount){
        Optional<Account> updated = accountService.updateAccount(id,updatedAccount);
        if(updated.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated.get());
    }
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id){
        boolean deleted = accountService.deleteAccount(id);
        if(!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}