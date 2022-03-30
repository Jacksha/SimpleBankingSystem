package src;

public class BankAccount {
    private String accStr;
    private String cardStr;
    private String pin;
    private int balance;

    public BankAccount() {
    }

    public BankAccount(String accStr, String cardStr, String pin, int balance) {
        this.accStr = accStr;
        this.cardStr = cardStr;
        this.pin = pin;
        this.balance = balance;
    }

    public String getAccStr() {
        return this.accStr;
    }

    public void setAccStr(String accStr) {
        this.accStr = accStr;
    }

    public String getCardStr() {
        return this.cardStr;
    }

    public void setCardStr(String cardStr) {
        this.cardStr = cardStr;
    }

    public String getPin() {
        return this.pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return this.balance;
    }

}
