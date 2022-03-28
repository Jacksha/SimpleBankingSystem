package banking;

import java.util.Random;
import java.util.Scanner;

public class AccountManager {
    static Scanner scanner = new Scanner(System.in);

    static int numOfAccs;
    static final int MAXNUMOFACCS = 100;
    static final int ACCNUMINTERVAL = 1_000_000_000;
    static final int ACCPININTERVAL = 10_000;
    static final String BIN = "400000";

    static BankAccount[] bankAccountArray = new BankAccount[AccountManager.MAXNUMOFACCS];

    private static int curAccKey = 0;


    public static int getCurAccKey() {
        return curAccKey;
    }

    public static void setCurAccKey(int curAccKey) {
        AccountManager.curAccKey = curAccKey;
    }

    // Create New Account static method
    public static void createNewAcc(AccountsDaoSqlite dao) {
        if (numOfAccs < MAXNUMOFACCS) {

            // making instances
            bankAccountArray[numOfAccs] = new BankAccount();
            Random random = new Random();
            String accountNum;

            // generating unique acc number & card number and a message
            do {
                String generatedAccStr = String.valueOf(random.nextInt(ACCNUMINTERVAL) + ACCNUMINTERVAL);
                generatedAccStr = generatedAccStr.substring(1);
                bankAccountArray[numOfAccs].setAccStr(generatedAccStr);
                accountNum = bankAccountArray[numOfAccs].getAccStr();
            } while (checkIfAccExists(accountNum));

            bankAccountArray[numOfAccs].setCardStr(BIN + accountNum + getChecksumAcc(accountNum));
            System.out.println("");
            System.out.println("Your card has been created\n" +
                    "Your card number:\n" +
                    bankAccountArray[numOfAccs].getCardStr());

            // generating pin and a message
            String generatedAccPin = String.valueOf(random.nextInt(ACCPININTERVAL) + ACCPININTERVAL);
            generatedAccPin = generatedAccPin.substring(1);
            bankAccountArray[numOfAccs].setPin(generatedAccPin);
            System.out.println("Your card PIN:\n" +
                    bankAccountArray[numOfAccs].getPin());
            System.out.println("");

            // setting acc balance to 0 and rising number of used accounts
            bankAccountArray[numOfAccs].setBalance(0);
            dao.saveAccount(bankAccountArray[numOfAccs]);
            numOfAccs++;
        } else {
            System.out.println("\nOut of space for new Accounts!\n");
        }
    }

    // num of free account slots
    public static int getNumOfFreeSlots() {
        return MAXNUMOFACCS - numOfAccs;
    }

    // login entre & check credentials
    public static boolean loginToAcc(AccountsDaoSqlite dao) {

        System.out.println("Enter your card number: ");
        String crdNumEntry = scanner.next();
        scanner.nextLine();

        System.out.println("Enter your card PIN: ");
        String pinEntry = scanner.next();
        scanner.nextLine();

        boolean isChecked = false;

        allAccounts = dao.mapAllAccounts();

        String key = getAccFromCard(crdNumEntry);
        System.out.println(key);
        // check if given card number and pin exist on same bank account
        if (crdNumEntry.equals(allAccounts.get(key).getCardStr()) && pinEntry.equals(allAccounts.get(key).getPin())) {
            isChecked = true;
            setCurAccKey(Integer.parseInt(key));
        } else {
            isChecked = false;
        }
        return isChecked;
    }

    // check if account exits
    public static boolean checkIfAccExists(String accStr) {
        boolean exists = false;
        if (allAccounts.containsKey(accStr)) {
            exists = true;
        }
        return exists;
    }

    // get balance from login
    public static int getAccBalance() {
        return allAccounts.get(getCurAccKey()).getBalance();
    }

    // get account number from login
    public static String getAccountString() {
        return String.valueOf(getCurAccKey());
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
