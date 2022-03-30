package src;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class AccountManager {
    static Scanner scanner = new Scanner(System.in);

    static final int ACCNUMINTERVAL = 1_000_000_000;
    static final int ACCPININTERVAL = 10_000;
    static final String BIN = "400000";
    private static String curAccKey;

    // initialize and update map
    static Map<String, BankAccount> allAccountsTmp;


    public static String getCurAccKey() {
        return curAccKey;
    }

    public static void setCurAccKey(String curAccKey) {
        AccountManager.curAccKey = curAccKey;
    }

    // initialize the map
    public static void initAccsFromDB(Map<String, BankAccount> allAccountsDB) {
        allAccountsTmp = allAccountsDB;
    }

    // Create New Account static method
    public static void createNewAcc(AccountsDaoSqlite dao) {

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
    public static void deleteAccount(AccountsDaoSqlite dao) {
        dao.deleteAccount(curAccKey);
        allAccountsTmp.remove(curAccKey);
        curAccKey = "";
    }


    // login entre & check credentials
    public static boolean loginToAcc() {

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
    public static boolean checkIfAccExists(String accStr) {
        boolean exists = false;
        if (allAccountsTmp.containsKey(accStr)) {
            exists = true;
        }
        return exists;
    }

    // get balance from login
    public static int getAccBalance() {
        return allAccountsTmp.get(getAccountString()).getBalance();
    }

    // get account number on login
    public static String getAccountString() {
        return String.valueOf(getCurAccKey());
    }

    // add income from login
    public static void addIncome(int amount, AccountsDaoSqlite dao) {
        // add income in sql
        dao.addIncome(curAccKey, amount);
        // add income to map
        int oldAmount = allAccountsTmp.get(curAccKey).getBalance();
        allAccountsTmp.get(curAccKey).setBalance(oldAmount + amount);
    }

    // get account string from cardstring
    public static String getAccFromCard(String cardString) {
        return cardString.substring(6, cardString.length()-1);
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
