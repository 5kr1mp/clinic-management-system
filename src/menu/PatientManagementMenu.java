package menu;

import service.*;
import model.*;
import java.util.*;

public class PatientManagementMenu {
    
    private PatientRecordService recordService;
    private PatientService patientService;
    private Scanner scn = new Scanner(System.in);

    public PatientManagementMenu(
        PatientRecordService recordService, 
        PatientService patientService
    ){
        this.recordService = recordService;
        this.patientService = patientService;
    }

    public void mainMenu(){
        int option;

        while(true){
            // display table
            // display options

            option = scn.nextInt(); 

            switch (option) {
                case 1: addRecord(); break;
                case 2: return; // exit
                default:
            }
        }
    }

    public void addRecord(){
        // define variables

        while(true){
            try {
                // code
                // at the end of the code, if succesful, add the following:
                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("You should kuan ano retry ig");

                // ask if muretry or go back to previous menu (ex: press 1 to return to previous menu)
                String input = scn.nextLine();
                if (input.trim().equals("1")){
                    return; // will stop looping
                }
                
            }
        }
    }

}
