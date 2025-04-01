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

    public static void confirmRetry(){

    }

    public static void enterToContinue(){
        System.out.print("Press enter to continue...");
        scn.nextLine();
    }
    
    public static void enterToRetry(){
        System.out.print("Press enter to retry...");
        scn.nextLine();
    }

    public static void patientDoesNotExist(int id){
        System.out.println("Error: Patient with id " + id + "does not exist.");
        enterToRetry();
    }

    public static void medicineDoesNotExist(String medName){
        System.out.println("Error: " + medName + " does not exist.");
        enterToRetry();
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
            }
        }
    }

}
