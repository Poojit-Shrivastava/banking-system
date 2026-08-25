
package BankingSystem;
import jakarta.persistence.*;

@Entity
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String  name;
    private double balance;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    public Account(){

    }
    public Account(Integer id, String name, double balance){
        this.id = id;
        this.name = name;
        this.balance = balance;
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
    public double getBalance(){
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public Customer getCustomer(){
        return customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }
}