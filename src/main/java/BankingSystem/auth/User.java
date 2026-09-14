package BankingSystem.auth;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;
    private String role;

    public User(){

    }

    public Integer getId(){
        return id;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }


//    public String getPassword(){
//        return password;
//    }
    public String getRole(){
        return role;
    }

    public void setRole(String role){
        this.role = role;
    }
}
