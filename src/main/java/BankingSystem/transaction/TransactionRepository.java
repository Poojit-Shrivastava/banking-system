package BankingSystem.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;


public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

    List<Transaction> findByFromAccountCustomerUserUsernameOrToAccountCustomerUserUsername(String  fromUsername, String toUsername);
}
