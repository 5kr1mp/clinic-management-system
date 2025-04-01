package menu;

import java.util.*;

class Menu {
    
    static Scanner scn;

    static {
        scn = new Scanner(System.in);
    }

    // methods
    public static void invalidOptionError(int numberOfOptions){
        System.out.println("Please input a number between 1 - " + numberOfOptions);
        enterToRetry();
    }

    
    public static void enterToRetry(){
        System.out.print("Press enter to retry...");
        scn.nextLine();
    }

    public static String retryOrContinue(String messaage){
        System.out.println(messaage);
        System.out.print("[Enter 1 to retry. Enter any other key to return] ");
        return scn.nextLine();
    }


    public static int inputNumber(){
        int number;
        while (true){
            try {
                number = scn.nextInt();scn.nextLine();
                return number;
            }
            catch (Exception e){
                System.err.println("Error: Please enter a valid number");
                scn.nextLine();
                System.out.print("Enter number: ");
            }
        }
    }

}
