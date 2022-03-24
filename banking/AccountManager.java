package banking;

import java.util.Random;
import java.util.Scanner;

public class AccountManager {
    static int numOfAccs;
    static final int MAXNUMOFACCS = 20;
    static BankAccount[] bankAccountArray = new BankAccount[AccountManager.MAXNUMOFACCS];
    static Scanner scanner = new Scanner(System.in);
    static int curAcc = 0;


    // Create New Account static method
    public static void createNewAcc() {
        if (numOfAccs < MAXNUMOFACCS) {

            bankAccountArray[numOfAccs] = new BankAccount();

            // generating acc number & card number and message
            Random random = new Random();
            bankAccountArray[numOfAccs].setAccNum(random.nextInt(1000000000));
            bankAccountArray[numOfAccs].setCardNum("400000" + bankAccountArray[numOfAccs].getAccNum() + "5");
            System.out.println("");
            System.out.println("Your card has been created\n" +
                    "Your card number:\n" +
                    bankAccountArray[numOfAccs].getCardNum());

            // generating pin and message
            bankAccountArray[numOfAccs].setPin(String.valueOf(random.nextInt(10000)));
            System.out.println("Your card PIN:\n" +
                    bankAccountArray[numOfAccs].getPin());
            System.out.println("");

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

        for (int i = 0; i < numOfAccs; i++) {
            if (crdNumEntry.equals(bankAccountArray[i].getCardNum()) && pinEntry.equals(bankAccountArray[i].getPin())) {
                isChecked = true;
                curAcc = i;
            } else {
                isChecked = false;
            }
        }
        return isChecked;
    }

    // get balance from login
    public static int getAccBalance() {
        return bankAccountArray[curAcc].getBalance();
    }
}
