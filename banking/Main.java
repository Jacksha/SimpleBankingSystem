package banking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isMainMenu = true;
        boolean isSubMenu = true;
        int mainChoice;
        
        do {
            // main menu
            System.out.println(
                    "1. Create an account\n" +
                    "2. Log into account\n" +
                    "5. Number of free slots\n" +
                    "0. Exit");
            mainChoice = scanner.nextInt();

            switch (mainChoice) {
                // create account
                case 1:
                    AccountManager.createNewAcc();
                    break;
                // login
                case 2:
                    if (AccountManager.loginToAcc()) {
                        System.out.println("\nYou have successfully logged in!\n");

                        do {
                            // submenu
                            System.out.println("User options: ");
                            System.out.println(
                                    "1. Balance\n" +
                                    "2. Log out\n" +
                                    "0. Exit");
                            int subChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (subChoice) {
                                // get bank balance for the user
                                case 1:
                                    System.out.println("\nBalance: " + AccountManager.getAccBalance());
                                    break;
                                // logout
                                case 2:
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
                // number of available acc slots
                case 5:
                    System.out.println("\nNumber of available Acc slots: " + AccountManager.getNumOfFreeSlots() + "\n");
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