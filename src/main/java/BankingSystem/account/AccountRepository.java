package BankingSystem.account;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository
        extends JpaRepository<Account, Integer> {
    List<Account> findByCustomerUserUsername(String username);
}