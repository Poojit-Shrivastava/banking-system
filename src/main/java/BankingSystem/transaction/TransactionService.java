package BankingSystem.transaction;

import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService( TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }
    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Integer id){
        return transactionRepository.findById(id);
    }
}
