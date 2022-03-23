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

            switch (mainChoice) {
                case 1:
                    AccountManager.createNewAcc();
                    break;

                case 5:
                    System.out.println("\nNumber of available Acc slots: " + AccountManager.getNumOfFreeSlots() + "\n");
                    break;

                case 0:
                    isMainMenu = false;
                    System.out.println("exiting...");
                    break;
            }
        } while (isMainMenu);
        

    }
}