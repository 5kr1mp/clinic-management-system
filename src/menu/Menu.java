package menu;

import java.util.*;

public class Menu {
    
    static Scanner scn;

    static {
        scn = new Scanner(System.in);
    }

    // methods
    public static void invalidOptionError(int numberOfOptions){
        System.out.println("Please input a number between 1 - " + numberOfOptions);
        System.out.print("Press enter to continue...");
        scn.nextLine();
        System.out.println();

        System.out.println("Press 1 to exit. Pres");
    }


}
