package BankingSystem;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    public Customer(){}
    public Customer(Integer id, String name){
        this.id = id;
        this.name = name;
    }
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public List<Account> getAccounts(){
        return accounts;
    }
    public void setAccounts(List<Account> accounts){
        this.accounts = accounts;
    }

}
