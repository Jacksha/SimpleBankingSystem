package banking;

import java.util.Random;
import java.util.Scanner;

public class AccountManager {
    static Scanner scanner = new Scanner(System.in);

    static int numOfAccs;
    static final int MAXNUMOFACCS = 20;
    static final int ACCNUMINTERVAL = 1_000_000_000;
    static final int ACCPININTERVAL = 10_000;
    static BankAccount[] bankAccountArray = new BankAccount[AccountManager.MAXNUMOFACCS];
    static int curAccIndex = 0;


    // Create New Account static method
    public static void createNewAcc() {
        if (numOfAccs < MAXNUMOFACCS) {

            // making instances
            bankAccountArray[numOfAccs] = new BankAccount();
            Random random = new Random();

            // generating acc number & card number and a message
            String generatedAccStr = String.valueOf(random.nextInt(ACCNUMINTERVAL) + ACCNUMINTERVAL);
            generatedAccStr = generatedAccStr.substring(1);
            bankAccountArray[numOfAccs].setAccStr(generatedAccStr);
            bankAccountArray[numOfAccs].setCardStr("400000" + bankAccountArray[numOfAccs].getAccStr() + "5");
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
            numOfAccs++;
        } else {
            System.out.println("Out of space for new Accounts");
        }
    }

    // num of free account slots
    public static int getNumOfFreeSlots() {
        return MAXNUMOFACCS - numOfAccs;
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

        // check if given card number and pin exist on same bank account
        for (int i = 0; i < numOfAccs; i++) {
            if (crdNumEntry.equals(bankAccountArray[i].getCardStr()) && pinEntry.equals(bankAccountArray[i].getPin())) {
                isChecked = true;
                curAccIndex = i;
            } else {
                isChecked = false;
            }
        }
        return isChecked;
    }

    // get balance from login
    public static int getAccBalance() {
        return bankAccountArray[curAccIndex].getBalance();
    }
}
