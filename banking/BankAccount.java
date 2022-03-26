package banking;

public class BankAccount {
    private String accStr;
    private String cardStr;
    private String pin;
    private int balance;

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
