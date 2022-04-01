package src;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class AccountManager {
    static Scanner scanner = new Scanner(System.in);

    static final int ACCNUMINTERVAL = 1_000_000_000;
    static final int ACCPININTERVAL = 10_000;
    static final String BIN = "400000";
    private String curAccKey;

    // connect to (or) create database
    AccountsDaoSqlite dao = new AccountsDaoSqlite("doc/accounts.db");

    // define and initialize the map of the BankAccount type class
    Map<String, BankAccount> allAccountsTmp = dao.mapAllAccountsDB();



    public String getCurAccKey() {
        return curAccKey;
    }

    public void setCurAccKey(String curAccKey) {
        this.curAccKey = curAccKey;
    }

    // Create New Account method
    public void createNewAcc() {

        // making instances
        Random random = new Random();
        String accountNumStr;
        String cardStr;
        String generatedAccPin;

        // generating unique acc number & card number and a message
        do {
            accountNumStr = String.valueOf(random.nextInt(ACCNUMINTERVAL) + ACCNUMINTERVAL);
            accountNumStr = accountNumStr.substring(1);
        } while (checkIfAccExists(accountNumStr));

        cardStr = BIN + accountNumStr + getChecksumAcc(accountNumStr);

        System.out.println("");
        System.out.println("Your card has been created\n" +
                "Your card number:\n" +
                cardStr);

        // generating pin and a message
        generatedAccPin = String.valueOf(random.nextInt(ACCPININTERVAL) + ACCPININTERVAL);
        generatedAccPin = generatedAccPin.substring(1);
        System.out.println("Your card PIN:\n" +
                generatedAccPin);
        System.out.println("");

        // add account to temp map and set balance to 0
        BankAccount newAcc = new BankAccount(accountNumStr, cardStr, generatedAccPin, 0);
        allAccountsTmp.put(accountNumStr, newAcc);

        // save acc to db
        dao.saveAccount(newAcc);
    }


    // delete an account
    public void deleteAccount() {
        dao.deleteAccount(curAccKey);
        allAccountsTmp.remove(curAccKey);
        curAccKey = "";
    }


    // login entre & check credentials
    public boolean loginToAcc() {

        System.out.println("Enter your card number: ");
        String crdNumEntry = scanner.next();
        scanner.nextLine();

        System.out.println("Enter your card PIN: ");
        String pinEntry = scanner.next();
        scanner.nextLine();

        boolean isChecked = false;

        // getting the account number from card number for the map key
        String key = getAccFromCard(crdNumEntry);
        System.out.println(key);

        // check if given card number and pin exist on same bank account
        if (crdNumEntry.equals(allAccountsTmp.get(key).getCardStr()) && pinEntry.equals(allAccountsTmp.get(key).getPin())) {
            isChecked = true;
            setCurAccKey(key);
        } else {
            isChecked = false;
        }
        return isChecked;
    }


    // check if account exits
    public boolean checkIfAccExists(String accStr) {
        boolean exists = false;
        if (allAccountsTmp.containsKey(accStr)) {
            exists = true;
        }
        return exists;
    }


    // check if card number is valid
    public boolean checkIfCardStrIsvalid(String cardStr) {
        String accStr = getAccFromCard(cardStr);
        if (cardStr.equals(BIN + accStr + getChecksumAcc(accStr))) {
            return true;
        } else {
            return false;
        }
    }


    // get account number on login
    public String getAccountString() {
        return String.valueOf(getCurAccKey());
    }


    // get account string from card string
    public String getAccFromCard(String cardString) {
        return cardString.substring(6, cardString.length()-1);
    }


    // get balance from login
    public int getAccBalance() {
        return allAccountsTmp.get(getAccountString()).getBalance();
    }


    // add income from login
    public void addIncome(int amount) {
        int oldAmount = allAccountsTmp.get(curAccKey).getBalance();
        // add income in sql
        dao.addIncome(curAccKey, amount);
        // add income to Hash Map
        allAccountsTmp.get(curAccKey).setBalance(oldAmount + amount);
    }

    // make transaction from login
    public boolean makeTrans(String cardStrDest) {
        boolean repeatInput = true;
        String accDestKey = getAccFromCard(cardStrDest);
        int transAmount;
        int oldAmount;

        // repeat loop until valid input
        do {
            System.out.println("Enter how much money you want to transfer:");
            transAmount = scanner.nextInt();
            scanner.nextLine();
            if (transAmount < 0) {
                System.out.println("Invalid input! Try again.\n");
            } else if (transAmount <= allAccountsTmp.get(curAccKey).getBalance()) {
                repeatInput = false;
            } else if (transAmount > allAccountsTmp.get(curAccKey).getBalance()) {
                System.out.println("Not enough funds on your account!\n");
            } else {
                System.out.println("Invalid input! Try again.\n");
            }
        } while (repeatInput);

        // remove funds from curr user in sql
        dao.addIncome(curAccKey, -transAmount);
        // add funds to target user in sql
        dao.addIncome(accDestKey, transAmount);

        // remove funds from current acc in Hash Map
        oldAmount = allAccountsTmp.get(curAccKey).getBalance();
        allAccountsTmp.get(curAccKey).setBalance(oldAmount - transAmount);
        // add funds to target user in Hash Map
        oldAmount = allAccountsTmp.get(accDestKey).getBalance();
        allAccountsTmp.get(accDestKey).setBalance(oldAmount + transAmount);

        return true;
    }


    // get checksum using Luhn algorithm from account number
    public static int getChecksumAcc(String accStr) {
        int nDigits = BIN.length() + accStr.length();
        String fullNum = BIN + accStr;
        int sum = 0;
        boolean isOdd = true;
        for (int i = nDigits - 1; i >= 0; i--)
        {
            int d = fullNum.charAt(i) - '0';
            if (isOdd == true) {
                d = d * 2;
            }
            if (d > 9) {
                d -= 9;
            }
            sum += d;
            isOdd = !isOdd;
        }
        if (sum % 10 == 0) {
            return 0;
        } else {
            return 10 - sum % 10;
        }
    }
}
