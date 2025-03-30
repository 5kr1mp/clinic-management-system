package menu;

import service.*;
import model.*;
import java.util.Scanner;

public class PatientMenu {
    public static void main(String [] args) {}

    private PatientRecordService recordService;
    private PatientService patientService;
    private Scanner sc;


    public PatientMenu (PatientRecordService recordService, PatientService patientService, Scanner sc){
        this.recordService = recordService;
        this.patientService = patientService;
        this.sc = sc;
    }

    // menu

    public void displayMenu () {
        System.out.println("==============Patient Records=============");
        System.out.println("[1] List all patient names");
        System.out.println("[2] List all student patient names");
        System.out.println("[3] List all faculty patient names");
        System.out.println("[4] View Patient Records");
        System.out.println("[5] Add Patient Record");
        System.out.println("[6] Visit Records");
        System.out.println("[7] Exit");


        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                System.out.println("Exiting Patient Menu...");
                return;
            default:
                System.out.println("ERROR! Invalid option. Please try again.");
        }
    }




}
