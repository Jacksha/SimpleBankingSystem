package banking;

import java.util.Random;

public class AccountManager {
    static int numOfAccs;
    static final int MAXNUMOFACCS = 20;
    static BankAccount[] bankAccount = new BankAccount[AccountManager.MAXNUMOFACCS];

    // Create New Account static method
    public static void createNewAcc() {
        if (numOfAccs < MAXNUMOFACCS) {
            numOfAccs++;
            bankAccount[numOfAccs] = new BankAccount();

            // generating acc number & card number and message
            Random random = new Random();
            bankAccount[numOfAccs].setAccNum(random.nextInt(1000000000));
            bankAccount[numOfAccs].setCardNum("400000" + bankAccount[numOfAccs].getAccNum() + "5");
            System.out.println("");
            System.out.println("Your card has been created\n" +
                    "Your card number:\n" +
                    bankAccount[numOfAccs].getCardNum());

            // generating pin and message
            bankAccount[numOfAccs].setPin(String.valueOf(random.nextInt(10000)));
            System.out.println("Your card PIN:\n" +
                    bankAccount[numOfAccs].getPin());
            System.out.println("");

            bankAccount[numOfAccs].setBalance(0);
        } else {
            System.out.println("Out of space for new Accounts");
        }
    }

    // num of free account slots
    public static int getNumOfFreeSlots() {
        return MAXNUMOFACCS - numOfAccs;
    }
}
