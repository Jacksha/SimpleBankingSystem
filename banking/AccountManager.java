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
    static int curAccIndex = 0;


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

        dao.mapAllAccounts();

        // check if given card number and pin exist on same bank account
        for (int i = 0; i < numOfAccs; i++) {
            if (crdNumEntry.equals(bankAccountArray[i].getCardStr()) && pinEntry.equals(bankAccountArray[i].getPin())) {
                isChecked = true;
                curAccIndex = i;
                break;
            } else {
                isChecked = false;
            }
        }
        return isChecked;
    }

    // check if account exits
    public static boolean checkIfAccExists(String accStr) {
        boolean exists = false;
        for (int i = 0; i < numOfAccs; i++) {
            if (accStr.equals(bankAccountArray[i].getAccStr())) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    // get balance from login
    public static int getAccBalance() {
        return bankAccountArray[curAccIndex].getBalance();
    }

    // get account number from login
    public static String getAccountString() {
        return bankAccountArray[curAccIndex].getAccStr();
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
