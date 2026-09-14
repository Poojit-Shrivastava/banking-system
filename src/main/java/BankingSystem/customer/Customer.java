package BankingSystem.customer;

import BankingSystem.account.Account;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import BankingSystem.auth.User;
import java.util.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Account> accounts = new ArrayList<>();

    public Customer() {
    }


    public Customer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}