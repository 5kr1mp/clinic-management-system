package menu;

import dao.*;
import model.*;
import service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class LogBookMenu {
    private LogService logService;
    private PatientService patientService;
    private Scanner scanner;
    private DateTimeFormatter formatter;

    public LogBookMenu(LogService logService, PatientService patientService) throws Exception {
        this.logService = logService;
        this.patientService = patientService;
        this.scanner = new Scanner(System.in);
        this.formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    }

    public void display(ArrayList<Log> logsToDisplay) {
        if (logsToDisplay.isEmpty()) {
            System.out.println("No logs available.");
            return;
        }
    
        System.out.println("==================================================== Visit Records ====================================================");
        System.out.printf("%-20s | %-10s | %-10s | %-15s | %-15s | %-15s%n",
                "Date", "Name", "Patient ID", "Designation", "Category", "Contact");
        System.out.println("====================================================================================================================");
        
        for (Log log : logsToDisplay) {
            String formattedDate = (log.getDate() != null) ? log.getDate().format(formatter) : "N/A";
            String patientName = (log.getName() != null) ? log.getName() : "N/A";
            String patientId = String.valueOf(log.getPatientId());
            String designation = (log.getDesignation() != null) ? log.getDesignation().toString() : "N/A";
            String category = (log.getCategory() != null) ? log.getCategory().toString() : "N/A";
            String contact = (log.getContact() != null) ? log.getContact(): "N/A";
    
            System.out.printf("%-20s | %-10s | %-10s | %-15s | %-15s | %-15s%n",
                    formattedDate,
                    patientName,
                    patientId,
                    designation,
                    category,
                    contact);
        }
        System.out.println("====================================================================================================================");
    }

    public void showMenu() {
        boolean running = true;

        try {
            display(logService.getLogs());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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
        int id;
        String purpose;

        while(true){
            try {
                System.out.print("Enter Patient ID: ");
                id = validateIntegerInput();
    
                System.out.print("Enter purpose: ");
                purpose = scanner.nextLine();
    
                // searches for the patient from the database using the input id
                Patient patient = patientService.getPatient(id);
        
                Log newLog = new Log(logService.generateId(),patient, LocalDateTime.now(),purpose);
                logService.add(newLog);
                System.out.println("Log added successfully!\n");
                display(logService.getLogs());
                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.print("Retry the kuan kana");
            }
        }
    }

    private void searchByName() {
        System.out.print("Enter Name to search: ");
        String name = scanner.nextLine().trim();
        try {
            ArrayList<Log> nameLogs = new ArrayList<>(logService.getLogsByName(name));
            display(nameLogs);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void searchById() {
        
        while(true){
            System.out.print("Enter Patient ID: ");
            int id = validateIntegerInput();
            
            try {
                Log log = logService.getLog(id);
                if (log != null) {
                    ArrayList<Log> singleLog = new ArrayList<>();
                    singleLog.add(log);
                    display(singleLog);
                } else {
                    System.out.println("No log found with that ID.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();

                System.out.println("Press 1 to return to main menu");

                if (scanner.nextInt() == 1){
                    return;
                }
            }
        }
    }

    private void filterByDate() {
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine().trim();
        try {
            LocalDate date = LocalDate.parse(dateInput,formatter);
            ArrayList<Log> filteredLogs = new ArrayList<>(logService.getLogsByDate(date));
            display(filteredLogs);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
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

    public static void main(String[] args) throws Exception {
        LogService logService = new LogService(new LogDao()); 
        PatientService patientService = new PatientService(new PatientDao());
        LogBookMenu menu = new LogBookMenu(logService,patientService);
        menu.showMenu();
    }
}
