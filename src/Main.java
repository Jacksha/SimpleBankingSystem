package src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isMainMenu = true;
        boolean isSubMenu = true;
        String mainChoice;

        AccountManager accMan;

        // check if there is Command-line argument it should contain url to the database
        // if it is a local link and no db exits the program will create it
        // FOR URL TO BE VALID IT HAS TO START WITH: "-"
        if (args.length > 0 && args[0].substring(0, 1).equals("-")) {
            String filename = args[0].substring(1);
            // initialize Account Manager with Command line argument
            accMan = new AccountManager(filename);
        } else {
            // initialize Account Manager with default argument
            accMan = new AccountManager("doc/accounts.db");
        }
        
        do {
            // main menu
            System.out.println(
                    "\n1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");
            mainChoice = scanner.nextLine();

            switch (mainChoice) {

                // create account
                case "1":
                    accMan.createNewAcc();
                    break;

                // login
                case "2":
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
                            String subChoice = scanner.nextLine();


                            switch (subChoice) {

                                // get bank balance for the user
                                case "1":
                                    System.out.println("\nBalance: " + accMan.getAccBalance() + "\n");
                                    break;

                                // add income
                                case "2":
                                    System.out.println("\nEnter income:");
                                    accMan.addIncome(scanner.nextInt());
                                    scanner.nextLine();
                                    System.out.println("Income was added!\n");
                                    break;

                                // do transfer
                                case "3":
                                    System.out.println("\nTransfer");
                                    System.out.println("Enter card number:");
                                    String cardNumEntry = scanner.nextLine();
                                    accMan.callForTransfer(cardNumEntry);
                                    break;

                                // delete account
                                case "4":
                                    accMan.deleteAccount();
                                    System.out.println("\nAccount deleted!\n");
                                    isSubMenu = false;
                                    break;

                                // logout
                                case "5":
                                    isSubMenu = false;
                                    break;

                                // exit
                                case "0":
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
                case "0":
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