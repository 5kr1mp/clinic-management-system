package menu;

import enums.Category;
import enums.Designation;
import service.*;
import util.DateTimeFormat;
import model.*;

import java.time.LocalDateTime;
import java.util.*;

public class PatientManagementMenu extends Menu {

    private PatientRecordService recordService;
    private PatientService patientService;
    private MedicineService medService;
    private IssuedMedicineService issuedMedicineService;

    public PatientManagementMenu(
        PatientRecordService recordService,
        PatientService patientService,
        MedicineService medService,
        IssuedMedicineService issuedMedicineService
    ){
        this.recordService = recordService;
        this.patientService = patientService;
        this.medService = medService;
        this.issuedMedicineService = issuedMedicineService;
    }

    public void mainMenu() {
        String option;

        while (true) {
            System.out.println("=======Patient Menu======");
            System.out.println("[1] Add Patient Record");
            System.out.println("[2] View Patient");
            System.out.println("[3] View Student Patients");
            System.out.println("[4] View Faculty Patients");
            System.out.println("[5] Exit");
            System.out.print("Choose an option: ");

            option = scn.nextLine();
            scn.nextLine(); // Fix scanner issue

            switch (option) {
                case "1":
                    addRecordView();
                    break;
                case "2":
                    addPatientView();
                    break;
                case "3":
                    viewPatient();
                    break;
                case "4":
                    viewStudentPatients();
                    break;
                case "5":
                    viewFacultyPatients();
                case "6":
                    return; // Exit
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void addRecordView () {
        PatientRecord record;
        Patient patient;
        
        int patientId;
        int recordId = recordService.generateId();
        String desc;
        String diagnosis;
        ArrayList<IssuedMedicine> issuedMedicines = new ArrayList<>();

        while (true) {
            try{
                System.out.println("========= Add Record =========");

                System.out.print("Enter patient id: ");
                patientId = inputNumber();
                patient = patientService.getPatient(patientId);

                if (patient == null){
                    patientDoesNotExist(patientId);
                    continue;
                }
                
                System.out.print("Enter notes: ");
                desc = scn.nextLine().trim();
                
                System.out.print("Enter diagnosis");
                diagnosis = scn.nextLine().trim();

                System.out.println("Press 1 to enter issued medicines: ");
                
                issuedMedicines = scn.nextLine().trim().equals("1") ? 
                                    inputIssuedMedicines(recordId) :
                                    new ArrayList<>();

                record = new PatientRecord(
                    recordId, 
                    patientId, 
                    LocalDateTime.now(),
                    desc,
                    diagnosis,
                    issuedMedicines
                );

                // confirm
                System.out.println();
                viewRecord(record);

                System.out.print("Press 1 to confirm and any other key to reenter: ");
                
                if (!scn.nextLine().trim().equals("1")){
                    continue;
                }

                recordService.add(record);
                return;
            }
            catch(Exception e){
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");

                System.out.println("Do you want to back to previous menu?");
                String input = scn.nextLine();
                if (input.trim().equals("1")) {
                    return;
                }
            }
        }
    }

    public void viewRecord(PatientRecord record){

        System.out.printf("""
        Record ID: %d
        Patient ID: %d
        Date & Time: %s
        Description: %s
        Diagnosis: %s
        
        """,
        record.getId(),
        record.getPatientId(),
        DateTimeFormat.formatDateTime(record.getDate()),
        record.getDesc(),
        record.getDiagnosis()
        );

        System.out.printf("""
        -------------
        %-20s | %-5s 
        -------------    
        """,
        "Medicine",
        "Amount");

        for (IssuedMedicine issuedMedicine : record.getMedicineIssued()){
            System.out.printf("""
            %-20s | %-5s  
            """,
            issuedMedicine.getMedicineName(),
            issuedMedicine.getAmount()); 
        }
        System.out.println();

    }

    public ArrayList<IssuedMedicine> inputIssuedMedicines(int recordId){
        ArrayList<IssuedMedicine> issuedMedicines = new ArrayList<>();

        while(true){
            try {
                Medicine med;
                String medName;
                int amount;

                // medicine not found
                System.out.print("Enter medicine name: ");
                medName = scn.nextLine().trim();
                med = medService.getMedicineByName(medName);

                if (med == null){
                    System.out.printf("\n%s does not exist. [Enter 1 to retry] ",medName);
                    String input = scn.nextLine().trim();
    
                    if (input.equals("1")){
                        continue;
                    }

                    break;
                }

                // no stock
                System.out.print("\nEnter amount: ");
                amount = inputNumber();

                ArrayList<MedicineBatch> batches = medService.getMedicineBatchesByMedicineId(med.getId(), false);

                // if wa nastock
                if (batches.isEmpty()){
                    System.out.println("No available stock for " + medName);
                    enterToRetry();
                    continue;
                }

                int remainingAmountNeeded = amount;
                for (MedicineBatch nonExpiredBatch : batches){

                    // if batch stock is less than amount, huruton ang stock tapos continue sa next non expired batch
                    if (nonExpiredBatch.getStock() < remainingAmountNeeded){
                        remainingAmountNeeded -= nonExpiredBatch.getStock();
                        medService.decreaseStock(nonExpiredBatch.getId(), nonExpiredBatch.getStock());
                        continue;
                    }

                    // batch stock is greater than amount, diri nalang mukuha
                    if (nonExpiredBatch.getStock() >= remainingAmountNeeded){
                        medService.decreaseStock(nonExpiredBatch.getId(), remainingAmountNeeded);
                        break;
                    }

                    // if way stock 
                    if(nonExpiredBatch.getStock() <= 0){
                        continue;
                    }

                }

                // if kulang ang stock pero naay mahatag gamay
                System.out.println();
                if (remainingAmountNeeded > 0){
                    amount -= remainingAmountNeeded;
                    System.out.printf("Only issued %d %s\n",amount,medName);
                }

                // update stuff
                System.out.println("Medicine succesfully issued...");

                IssuedMedicine issuedMedicine = new IssuedMedicine(
                    issuedMedicineService.generateId(),
                    recordId,
                    med.getId(),
                    amount
                );

                issuedMedicineService.add(issuedMedicine);
                issuedMedicines.add(issuedMedicine);

                // retry or nah
                System.out.println("\nDo you want to add another entry? [Enter 1]");
                String input = scn.nextLine().trim();

                if (input.equals("1")){
                    continue;
                }

                break;
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.printf("\nEnter 1 to retry... ");
                String input = scn.nextLine().trim();

                if (input.equals("1")){
                    continue;
                }
            }
        }

        return issuedMedicines;

    }


    public void addPatientView() {
        while (true) {
            try {
                System.out.println("\n=== Add Patient Record ===");

                System.out.print("Enter ID: ");
                int id = scn.nextInt();
                scn.nextLine();

                System.out.print("Enter Last Name: ");
                String lastname = scn.nextLine();

                System.out.print("Enter First Name: ");
                String firstname = scn.nextLine();

                System.out.print("Enter Middle Name: ");
                String middlename = scn.nextLine();

                System.out.print("Confirm Designation (STUDENT, FACULTY, etc.): ");
                String input1 = scn.nextLine().toUpperCase();
                Designation designation;
                try {
                    designation = Designation.valueOf(input1);
                    System.out.println("Designation confirmed: " + designation);
                } catch (Exception e) {
                    System.out.println("Invalid designation. Please enter a valid one.");
                    continue;
                }

                System.out.print("Confirm Category (STUDENT / TEACHER): ");
                String input2 = scn.nextLine().toUpperCase();
                Category category;
                try {
                    category = Category.valueOf(input2);
                    System.out.println("Category confirmed: " + category);
                } catch (Exception e) {
                    System.out.println("Invalid category. Please enter a valid one.");
                    continue;
                }

                System.out.print("Enter Contact Number: ");
                String contact = scn.nextLine();

                ArrayList<PatientRecord> records = new ArrayList<>();


                Patient patient = new Patient(id, lastname, firstname, middlename, designation , category , contact, records);
                patientService.add(patient);
                System.out.println("Patient added successfully!");

                patientService.add(patient);

                System.out.println("ID: " + patient.getId());
                System.out.println("Name: " + patient.getLastname() + ", " + patient.getFirstname() + " " + patient.getMiddlename());
                System.out.println("Designation: " + patient.getDesignation());
                System.out.println("Category: " + patient.getCategory());
                System.out.println("Contact: " + patient.getContact());

                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");

                System.out.println("Do you want to back to previous menu?");
                String input = scn.nextLine();
                if (input.trim().equals("1")) {
                    return;
                }
            }
        }
    }


    public void viewPatient() {
        while (true) {
            System.out.print("Enter Patient ID / Name: ");

            Patient patient = null;
            try {
                if (scn.hasNextInt()) {
                    int id = scn.nextInt();
                    scn.nextLine(); // Consume newline

                    try {
                        patient = patientService.getPatient(id);

                        System.out.println("======= Patient Details ======");
                        System.out.println("ID: " + patient.getId());
                        System.out.println("Name: " + patient.getLastname() + ", " + patient.getFirstname() + " " + patient.getMiddlename());
                        System.out.println("Designation: " + patient.getDesignation());
                        System.out.println("Category: " + patient.getCategory());
                        System.out.println("Contact: " + patient.getContact());
                    } catch (Exception e) {
                        System.out.println("Error: Patient with ID " + id + " not found.");
                    }
                } else {
                    String name = scn.nextLine().trim();

                    try {

                        patient = patientService.getPatientByName(name);

                        System.out.println("======= Patient Details ======");
                        System.out.println("ID: " + patient.getId());
                        System.out.println("Name: " + patient.getLastname() + ", " + patient.getFirstname() + " " + patient.getMiddlename());
                        System.out.println("Designation: " + patient.getDesignation());
                        System.out.println("Category: " + patient.getCategory());
                        System.out.println("Contact: " + patient.getContact());
                    } catch (Exception e) {
                        System.out.println("Error: Patient with name '" + name + "' not found.");
                    }
                }


                return;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid Patient ID or Name.");
                scn.nextLine(); // Clear scanner buffer to prevent infinite loop
            }

            System.out.println("Do you want to go back to the previous menu? (Press 1 to return)");
            String input = scn.nextLine();
            if (input.trim().equals("1")) {
                return;
            }
        }
    }


    public void viewStudentPatients() {
        while (true) {
            try {
                ArrayList<Patient> studentPatients = patientService.getStudentPatients();

                // Sort alphabetically by last name
                studentPatients.sort(Comparator.comparing(Patient::getLastname));

                int count = 1;
                for (Patient patient : studentPatients) {
                    System.out.println(patient);
                    System.out.print(count + ". "); // Add numbering
                    count++;
                }

                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("Press 1 to go back to the previous menu.");
            String input = scn.nextLine();
            if (input.trim().equals("1")) {
                return;
            }
        }
    }

    public void viewFacultyPatients() {
        while (true) {
            try {
                ArrayList<Patient> facultyPatients = patientService.getFacultyPatients();

                // Sort alphabetically by last name
                facultyPatients.sort(Comparator.comparing(Patient::getLastname));

                int count = 1;
                for (Patient patient : facultyPatients) {
                    System.out.println(patient);
                    System.out.print(count + ". "); // Add numbering
                    count++;
                }

                return;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("Press 1 to go back to the previous menu.");
            String input = scn.nextLine();
            if (input.trim().equals("1")) {
                return;
            }
        }
    }

}