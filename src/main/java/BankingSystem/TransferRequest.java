package BankingSystem;

public class TransferRequest {
    private Integer toAccountId;
    private double amount;

    public TransferRequest(){
    }

    public Integer getToAccountId(){
        return toAccountId;
    }

    public void setAccountId(Integer toAccountId){
        this.toAccountId = toAccountId;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
