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
            System.out.println("======= Patient Menu ======");
            System.out.println("[1] Add Patient Record");
            System.out.println("[2] Register Patient");
            System.out.println("[3] View Patient");
            System.out.println("[4] View Student Patients");
            System.out.println("[5] View Faculty Patients");
            System.out.println("[6] Exit");
            System.out.print("\nChoose an option: ");

            option = scn.nextLine();

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
                    break;
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
                System.out.println("\n========= Add Record =========");

                System.out.print("Enter patient id: ");
                patientId = inputNumber();
                patient = patientService.getPatient(patientId);

                if (patient == null){
                    System.out.println("Patient with ID " + patientId + " does not exist");
                    System.out.print("Enter 1 to retry. Otherwise enter any key... ");
                    if (scn.nextLine().trim().equals("1")){
                        continue;
                    }
                    return;
                }
                
                System.out.print("Enter notes: ");
                desc = scn.nextLine().trim();
                
                System.out.print("Enter diagnosis: ");
                diagnosis = scn.nextLine().trim();

                System.out.print("Press 1 to enter issued medicines: ");
                
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

                System.out.println("Do you want to back to previous menu? [Press 1. Any other key to restart]");
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

        if (record.getMedicineIssued().isEmpty()){
            System.out.println("No medicines issued");
            System.out.println();
            return;
        }

        System.out.printf("""

        ------------------------------
        %-20s | %-5s 
        ------------------------------ 
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
                
                System.out.println("====================================");
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
                System.out.print("Enter amount: ");
                amount = inputNumber();

                ArrayList<MedicineBatch> batches = medService.getMedicineBatchesByMedicineId(med.getId(), false);

                // if wa nastock
                if (batches.isEmpty()){
                    System.out.printf("No stock for %s. [Enter 1 to retry. Any other key to confirm and continue]",medName);
                    String input = scn.nextLine().trim();
    
                    if (input.equals("1")){
                        continue;
                    }
                    
                    return issuedMedicines;
                }

                int remainingAmountNeeded = amount;
                for (MedicineBatch nonExpiredBatch : batches){

                    // if batch stock is less than amount, huruton ang stock tapos continue sa next non expired batch
                    if (nonExpiredBatch.getStock() < remainingAmountNeeded){

                        int remainingStock = nonExpiredBatch.getStock();
                        remainingAmountNeeded -= remainingStock;
                        medService.decreaseStock(nonExpiredBatch.getId(), remainingStock);
                        continue;
                    }

                    // batch stock is greater than amount, diri nalang mukuha
                    if (nonExpiredBatch.getStock() >= remainingAmountNeeded){
                        medService.decreaseStock(nonExpiredBatch.getId(), remainingAmountNeeded);
                        remainingAmountNeeded = 0;
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

                System.out.printf("%d %s succesfully issued...\n",amount, medName);
                
                
                // update stuff
                IssuedMedicine issuedMedicine = new IssuedMedicine(
                    issuedMedicineService.generateId(),
                    recordId,
                    med,
                    amount
                );

                issuedMedicineService.add(issuedMedicine);
                issuedMedicines.add(issuedMedicine);

                // retry or nah
                System.out.print("\nDo you want to add another entry? [Enter 1 or any key to skip] ");
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
                System.out.println("\n=== Add Patient ===");

                System.out.print("Enter ID: ");
                int id = inputNumber();

                if (patientService.patientExists(id)){
                    retryOrContinue("Patient with id already exists");
                    continue;
                }

                System.out.print("Enter Last Name: ");
                String lastname = scn.nextLine();

                System.out.print("Enter First Name: ");
                String firstname = scn.nextLine();

                System.out.print("Enter Middle Name: ");
                String middlename = scn.nextLine();

                Designation designation;
                while (true) {

                    System.out.print("Confirm Designation (BSIT, BSABE, etc.): ");
                    String input1 = scn.nextLine().toUpperCase();
                    try {
                        designation = Designation.from(input1);
                        System.out.println("Designation confirmed: " + designation);
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid designation. Please enter a valid one.");
                    }
                }

                Category category;
                while (true) {
                    System.out.print("Confirm Category (STUDENT / FACULTY): ");
                    String input2 = scn.nextLine().toUpperCase();
                    try {
                        category = Category.valueOf(input2);
                        System.out.println("Category confirmed: " + category);
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid category. Please enter a valid one.");
                    }
                }

                System.out.print("Enter Contact Number: ");
                String contact = scn.nextLine();

                ArrayList<PatientRecord> records = new ArrayList<>();


                Patient patient = new Patient(id, lastname, firstname, middlename, designation , category , contact, records);
                patientService.add(patient);
                System.out.println("\n==================================");
                System.out.println("Patient added successfully!\n");

                System.out.println("ID: " + patient.getId());
                System.out.println("Name: " + patient.getLastname() + ", " + patient.getFirstname() + " " + patient.getMiddlename());
                System.out.println("Designation: " + patient.getDesignation());
                System.out.println("Category: " + patient.getCategory());
                System.out.println("Contact: " + patient.getContact());

                System.out.println("Do you want to go back to the previous menu? (Press 1 to return)");
                String input = scn.nextLine();
                if (input.trim().equals("1")) {
                    return;
                }

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
            System.out.print("\nEnter Patient ID / Name (Lastname, Firstname Middlename): ");

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

                        viewRecords(id);
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

                        viewRecords(patient.getId());
                    } catch (Exception e) {
                        System.out.println("Error: Patient with name '" + name + "' not found.");
                    }
                }

                System.out.println("Do you want to go back to the previous menu? (Press 1 to return)");
                String input = scn.nextLine();
                if (input.trim().equals("1")) {
                    return;
                }

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

                System.out.println("====== Student Patients List ======");
                ArrayList<Patient> studentPatients = patientService.getStudentPatients();

                // Sort alphabetically by last name
                studentPatients.sort(Comparator.comparing(Patient::getLastname));

                int count = 1;
                for (Patient patient : studentPatients) {
                    System.out.print(count + ". ");
                    System.out.print(patient.getFullName());
                    System.out.println();
                    count++;
                }

                System.out.println("Press 1 to go back to the previous menu.");
                String input = scn.nextLine();
                if (input.trim().equals("1")) {
                    return;
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void viewFacultyPatients() {
        while (true) {
            try {

                System.out.println("====== Faculty Patients List ======");
                ArrayList<Patient> facultyPatients = patientService.getFacultyPatients();

                // Sort alphabetically by last name
                facultyPatients.sort(Comparator.comparing(Patient::getLastname));

                int count = 1;
                for (Patient patient : facultyPatients) {
                    System.out.print(count + ". ");
                    System.out.print(patient.getFullName());
                    System.out.println();
                    count++;
                }

                System.out.println("Press 1 to go back to the previous menu.");
                String input = scn.nextLine();
                if (input.trim().equals("1")) {
                    return;
                }

            } catch (Exception e) {
                System.out.println("ERROR: Something went wrong. Please try again.");
            }
        }
    }

    public void viewRecords(int patientId){
        ArrayList<PatientRecord> records = recordService.getRecordsByPatientId(patientId);

        // if records are empty 
        if (records.isEmpty()){
            System.out.println("No records available...");
            return;
        }

        System.out.printf("""
        ------------------------------------------------------------------------ Records ------------------------------------------------------------------------
        %-19s | %-30s | %-50s | %-30s        
        ---------------------------------------------------------------------------------------------------------------------------------------------------------
        ""","Date & Time","Description","Diagnosis", "Medicines Issued");

        for (PatientRecord record : records) {

            ArrayList<IssuedMedicine> issuedMedicines = new ArrayList<>();
            IssuedMedicine firstIssuedMedicine = null;
            String firstMedicineName = "";

            try{
                issuedMedicines = issuedMedicineService.getIssuedMedicinesByRecordId(record.getId());
                firstIssuedMedicine = issuedMedicines.get(0);
                firstMedicineName = medService.getMedicine(
                        firstIssuedMedicine.getMedicineId()
                    ).getName();
            }
            catch (Exception e){

            }
            
            System.out.printf("""
            %-19s | %-30s | %-50s | %-30s        
            """,
            DateTimeFormat.formatDateTime(record.getDate()),
            record.getDesc(),
            record.getDiagnosis(),
            firstMedicineName+ " x"+ firstIssuedMedicine.getAmount());

            for (int i = 1; i < issuedMedicines.size(); i++) {
                IssuedMedicine med = issuedMedicines.get(i);

                String medName = "Unknown"; 
                try {
                    medName = medService.getMedicine(med.getMedicineId()).getName();
                }
                catch(Exception e){

                }
                
                System.out.printf("""
                    %-19s | %-30s | %-50s | %-30s        
                    """,
                    " ",
                    " ",
                    " ",
                    medName + " x"+med.getAmount()
                ); 
            }
        }
    }

}