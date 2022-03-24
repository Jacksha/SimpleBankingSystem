package banking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isMainMenu = true;
        int mainChoice;
        
        do {
            System.out.println(
                    "1. Create an account\n" +
                    "2. Log into account\n" +
                    "5. Number of free slots\n" +
                    "0. Exit");
            mainChoice = scanner.nextInt();

            // main menu
            switch (mainChoice) {
                // create account
                case 1:
                    AccountManager.createNewAcc();
                    break;
                // login
                case 2:
                    if (AccountManager.loginToAcc()) {
                        System.out.println("\nYou have successfully logged in!\n");
                        System.out.println("1. Balance\n" +
                                "2. Log out\n" +
                                "0. Exit");
                        int subChoice = scanner.nextInt();
                        scanner.nextLine();

                        // submenu
                        switch (subChoice) {
                            case 1:
                                System.out.println("\nBalance: " + AccountManager.getAccBalance());
                                break;
                            case 2:
                                break;
                            case 3:
                                isMainMenu = false;
                                break;
                            default:
                                System.out.println("Wrong input");
                        }

                    } else {
                        System.out.println("Wrong credentials");
                    }
                    break;

                case 5:
                    System.out.println("\nNumber of available Acc slots: " + AccountManager.getNumOfFreeSlots() + "\n");
                    break;

                case 0:
                    isMainMenu = false;
                    System.out.println("exiting...");
                    break;
                default:
                    System.out.println("Wrong input");
            }
        } while (isMainMenu);
        

    }
}