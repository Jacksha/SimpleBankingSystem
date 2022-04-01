package src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isMainMenu = true;
        boolean isSubMenu = true;
        int mainChoice;

        // initialize Account Manager
        AccountManager accMan = new AccountManager();
        
        do {
            // main menu
            System.out.println(
                    "\n1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");
            mainChoice = scanner.nextInt();

            switch (mainChoice) {

                // create account
                case 1:
                    accMan.createNewAcc();
                    break;

                // login
                case 2:
                    if (accMan.loginToAcc()) {
                        System.out.println("\nYou have successfully logged in!\n");

                        do {
                            // submenu
                            System.out.println("User options for account: " + accMan.getAccountString());
                            System.out.println(
                                    "1. Balance\n" +
                                    "2. Add income\n" +
                                    "3. Do transfer\n" +
                                    "4. Close Account\n" +
                                    "5. Log out\n" +
                                    "0. Exit");
                            int subChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (subChoice) {

                                // get bank balance for the user
                                case 1:
                                    System.out.println("\nBalance: " + accMan.getAccBalance() + "\n");
                                    break;

                                // add income
                                case 2:
                                    System.out.println("\nEnter income:");
                                    accMan.addIncome(scanner.nextInt());
                                    scanner.nextLine();
                                    System.out.println("Income was added!\n");
                                    break;

                                // do transfer
                                case 3:
                                    System.out.println("\nTransfer");
                                    System.out.println("Enter card number:");
                                    String cardNumEntry = scanner.nextLine();
                                    if (accMan.checkIfCardStrIsvalid(cardNumEntry)) {
                                        if (accMan.checkIfAccExists(accMan.getAccFromCard(cardNumEntry))) {
                                            if (accMan.makeTrans(cardNumEntry)) {
                                                System.out.println("Success!");
                                            }
                                        } else {
                                            System.out.println("Such a card does not exist");
                                        }
                                    } else {
                                        System.out.println("Probably you made a mistake in the card number. Please try again!");
                                    }
                                    break;

                                // delete account
                                case 4:
                                    accMan.deleteAccount();
                                    System.out.println("\nAccount deleted!\n");
                                    isSubMenu = false;
                                    break;

                                // logout
                                case 5:
                                    isSubMenu = false;
                                    break;

                                // exit
                                case 0:
                                    isSubMenu = false;
                                    isMainMenu = false;
                                    System.out.println("exiting...");
                                    break;

                                // for wrong input
                                default:
                                    System.out.println("Wrong input");
                            }
                        } while (isSubMenu);

                    } else {
                        System.out.println("Wrong credentials");
                    }
                    break;

                // exit program
                case 0:
                    isMainMenu = false;
                    System.out.println("exiting...");
                    break;

                // for wrong input
                default:
                    System.out.println("Wrong input");
            }
        } while (isMainMenu);
    }
}