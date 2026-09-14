package BankingSystem.transaction;

import BankingSystem.account.Account;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    private double amount;
    private String type;
    private LocalDateTime timestamp;
    public Transaction(){
    }
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public Account getFromAccount(){
        return fromAccount;
    }
    public Account getToAccount(){return toAccount;}
    public void setFromAccount(Account fromAccount){
        this.fromAccount = fromAccount;
    }
    public void setToAccount(Account toAccount){
        this.toAccount = toAccount;
    }
    public double getAmount(){
        return amount;
    }
    public void setAmount(double amount){
        this.amount = amount;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }
    public LocalDateTime getTimestamp(){
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp){
        this.timestamp = timestamp;
    }


}
