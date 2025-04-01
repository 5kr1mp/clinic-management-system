package menu;

import model.*;
import service.LogService;
import service.PatientService;
import util.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;


public class LogbookMenu extends Menu {
    private LogService logService;
    private Scanner scanner;
    private PatientService patientService;

    public LogbookMenu(LogService logService, PatientService patientService) {
        this.logService = logService;
        this.patientService = patientService;
        this.scanner = new Scanner(System.in);
    }

    public void display(ArrayList<Log> logsToDisplay) {
        if (logsToDisplay.isEmpty()) {
            System.out.println("No logs available.");
            return;
        }
    
        System.out.println("=================================================================== Visit Records ==================================================================");
        System.out.printf("%-20s | %-40s | %-10s | %-30s | %-15s | %-30s\n",
                "Date", "Name", "Patient ID", "Designation", "Contact", "Purpose");
        System.out.println("====================================================================================================================================================");
        
        for (Log log : logsToDisplay) {
            String formattedDate = (log.getDate() != null) ? DateTimeFormat.formatDateTime(log.getDate()) : "N/A";
            String patientName = (log.getName() != null) ? log.getName() : "N/A";
            String patientId = String.valueOf(log.getPatientId());
            String designation = (log.getDesignation() != null) ? log.getDesignation().toString() : "N/A";
            String category = (log.getCategory() != null) ? log.getCategory().toString() : "N/A";
            String contact = (log.getContact() != null) ? log.getContact() : "N/A";
            String purpose = (log.getReason() != null) ? log.getReason() : "N/A";
    
            String designationAndCategory = designation + " " + category;
    
            System.out.printf("%-20s | %-40s | %-10s | %-30s | %-15s | %-30s\n",
                    formattedDate,
                    patientName,
                    patientId,
                    designationAndCategory,
                    contact,
                    purpose
                    );
                    
        }
        System.out.println("====================================================================================================================================================");
    }
    

    public void showMenu() {
        try {
            display(logService.getLogs());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        boolean running = true;
        while (running) {
            try {
                System.out.println("\n==================== Choose Action ====================");
                System.out.println("[1] Add New Log");
                System.out.println("[2] Search by Name");
                System.out.println("[3] Search by ID");
                System.out.println("[4] Filter by Date");
                System.out.println("[5] Back to Menu");
                System.out.print("\nEnter option: ");
                
                String input = scanner.nextLine().trim();
                switch (input) {
                    case "1": addNewLog(); break;
                    case "2": searchByName(); break;
                    case "3": searchById(); break;
                    case "4": filterByDate(); break;
                    case "5": System.out.println("Returning to Menu..."); running = false; return;
                    default: System.out.println("Invalid option. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void addNewLog() {
        while(true){
            try {
                System.out.print("Enter Patient ID: ");
                int id = validateIntegerInput();
    
                System.out.print("Enter purpose: ");
                String purpose = scanner.nextLine();
    
                Patient patient = patientService.getPatient(id);
                Log newLog = new Log(logService.generateId(),patient, LocalDateTime.now(),purpose);
                logService.add(newLog);
                display(logService.getLogs());
                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Press 1 to return to menu or any other key to continue.");
                
                String input = scanner.nextLine();
                if (input.trim().equals("1")){
                    return;
                };
            }
        }
    }

    private void searchByName() {
        while(true){
            System.out.print("Enter Name to search (Lastname, Firstname Middlename): ");
            String name = scanner.nextLine().trim();
            try {
                ArrayList<Log> logs = logService.getLogsByName(name);
                display(logs);
                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Name not found.");
                e.printStackTrace();
            }
        }
    }

    private void searchById() {
        while(true){
            System.out.print("Enter Patient ID: ");
            int id = validateIntegerInput();
            try {
                ArrayList<Log> logs = logService.getLogsByPatientId(id);
                display(logs);
                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Press 1 to return to menu or any other key to continue.");
                e.printStackTrace();
                
            }
        }
    }

    private void filterByDate() {
        while(true){
            System.out.print("Enter Date (MM-DD-YYYY): ");
            String dateInput = scanner.nextLine().trim();
            try {
                LocalDate date = DateTimeFormat.parseDate(dateInput);
                ArrayList<Log> filteredLogs = new ArrayList<>(logService.getLogsByDate(date));
                display(filteredLogs);
                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private int validateIntegerInput() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }
}
