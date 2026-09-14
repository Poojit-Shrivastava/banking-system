package BankingSystem.transaction;

import java.time.LocalDateTime;

public class TransactionResponse {
    private Integer id;
    private Integer fromAccountId;
    private Integer toAccountId;
    private double amount;
    private String type;
    private LocalDateTime timestamp;
    public TransactionResponse(){

    }

    public TransactionResponse(
        Integer id,
        Integer fromAccountId,
        Integer toAccount,
        double amount,
        String type,
        LocalDateTime timestamp){
            this.id = id;
            this.fromAccountId = fromAccountId;
            this.toAccountId = toAccountId;
            this.amount = amount;
            this.type = type;
            this.timestamp = timestamp;
        }
     public Integer getId(){
        return id;
    }
    public Integer getFromAccountId(){
        return fromAccountId;

    }
    public Integer getToAccountId(){
        return toAccountId;

    }
    public double getAmount(){
        return  amount;
    }
    public String getType(){
        return type;
    }
    public LocalDateTime getTimestamp(){
        return timestamp;
    }
}
