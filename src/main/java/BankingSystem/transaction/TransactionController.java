package BankingSystem.transaction;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

@RestController
public class TransactionController {
    private final TransactionService transactionService;
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }
    @GetMapping("/transactions")
    public List<Transaction> getAllTransactions(){
        return transactionService.getAllTransactions();
    }
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Integer id){
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        if(transaction.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(transaction.get());
    }
}
