package menu;

import java.util.InputMismatchException;

public class MainMenu extends Menu {

    InventoryMenu inventoryMenu;
    LogbookMenu logBookMenu;
    PatientManagementMenu patientMenu;
    ReportMenu reportMenu;

    public MainMenu(
        InventoryMenu inventoryMenu,
        LogbookMenu logBookMenu,
        PatientManagementMenu patientMenu,
        ReportMenu reportMenu
    ){
        this.inventoryMenu = inventoryMenu;
        this.logBookMenu = logBookMenu;
        this.patientMenu = patientMenu;
        this.reportMenu = reportMenu;
    }
    
    public void start(){

        int option;

        while (true){
            System.out.println("=".repeat(15) + "Clinic Management System" + "=".repeat(15));
            System.out.println("""
            [1] Patient Records
            [2] Inventory
            [3] Logbook
            [4] Reports
            [5] Exit
            """);

            try {
                System.out.print("Choose Action: ");
                option = scn.nextInt();scn.nextLine();

                switch (option) {
                    case 1: patientMenu.mainMenu(); break;
                    case 2: inventoryMenu.displayMenu(); break;
                    case 3: logBookMenu.showMenu(); break;
                    case 4: reportMenu.displayMenu(); break;
                    case 5: 
                        System.out.println("Exiting program...");
                        scn.nextLine();
                        return;
                    default:
                        invalidOptionError(5);
                        break;
                }
            } 
            catch (InputMismatchException e){
                invalidOptionError(5);;
                scn.nextLine();
            }
        }
        
    }

}
