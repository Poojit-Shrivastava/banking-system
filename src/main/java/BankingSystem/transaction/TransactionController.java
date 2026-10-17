package BankingSystem.transaction;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.*;

@RestController
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }
    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions(@AuthenticationPrincipal Jwt jwt){
        String username = jwt.getSubject();
        return transactionService.getAllTransactions(username);
    }
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Integer id,@AuthenticationPrincipal Jwt jwt){

        String username = jwt.getSubject();

        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        if(transaction.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Transaction t = transaction.get();
        boolean ownsTransaction = t.getFromAccount().getCustomer().getUser().getUsername().equals(username)||
                t.getToAccount().getCustomer().getUser().getUsername().equals(username);
        if(!ownsTransaction){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(t);
    }
}
