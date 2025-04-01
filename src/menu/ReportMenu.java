package menu;

import java.util.*;
import model.*;
import service.*;
import util.*;

public class ReportMenu extends Menu{


    private ReportService service;

    public ReportMenu(ReportService service){
        this.service = service;
    }

    // methods
    public void displayMenu(){
        
        String option;

        while(true){
            System.out.println("=".repeat(15)+" Reports " + "=".repeat(15));
            System.out.println("""
            [1] View Daily Report
            [2] View Weekly Report
            [3] View Monthly Report
            [4] Exit 
            """);
            try {
                System.out.print("Enter option: ");
                option = scn.nextLine();

                switch (option) {
                    case "1": dailyReport(); break;
                    case "2": weeklyReport(); break;
                    case "3": monthlyReport(); break;
                    case "4": return;
                    default: invalidOptionError(4); break;
                }
            }
            catch (InputMismatchException e){
                invalidOptionError(4);
            }
        }
    }

    public void dailyReport(){
        Report report = service.generateDailyReport();

        System.out.printf("Report for date: %s\n", DateTimeFormat.formatDate(report.getDate()));

        patientStatistics(report);
        diagnosisStatistics(report);
        inventoryStatistics(report);
        issuedMedicineStatistics(report);
        

    }

    public void weeklyReport(){
        Report report = service.generateWeeklyReport();

        DateRange range = report.getDateRange();

        System.out.printf("Report for date: %s - %s\n", 
            DateTimeFormat.formatDate(range.getLowerDateRange()),
            DateTimeFormat.formatDate(range.getUpperDateRange()));


        patientStatistics(report);
        diagnosisStatistics(report);
        inventoryStatistics(report);
        issuedMedicineStatistics(report);
        
    }

    public void monthlyReport(){
        Report report = service.generateMonthlyReport();

        DateRange range = report.getDateRange();

        System.out.printf("Report for date: %s - %s\n", 
            DateTimeFormat.formatDate(range.getLowerDateRange()),
            DateTimeFormat.formatDate(range.getUpperDateRange()));

        patientStatistics(report);
        diagnosisStatistics(report);
        inventoryStatistics(report);
        issuedMedicineStatistics(report);
        
    }

    public void patientStatistics(Report report){
        int facultyCount = service.getFacultyPatientsCount(report);
        int studentCount = service.getStudentPatientsCount(report);

        System.out.printf("""
        ========== Patient Statistics ==========
        Type                           | Count 
        ========================================
        Student                        | %-5s 
        Faculty                        | %-5s 
        ========================================
        \n\n""",
        studentCount,
        facultyCount);
    }

    public void diagnosisStatistics(Report report){
        ArrayList<String> uniqueDiagnoses = service.getUniqueDiagnoses(report);

        System.out.printf("""
        =============================== Diagnoses Statistics ===============================
        %-75s | %-5s
        ====================================================================================       
        ""","Diagnosis","Count");

        if (uniqueDiagnoses.isEmpty()){
            System.out.println("Nothing to report");
        }

        for (String diagnosis : uniqueDiagnoses) {
            System.out.printf("""
            %-75s | %-5s        
            """,
            diagnosis,
            service.diagnosisCounter(report, diagnosis)
            );            
        }

        System.out.print("""
        ====================================================================================    
        \n\n""");
    }

    public void inventoryStatistics(Report report){
        ArrayList<Medicine> medicines = service.getMedicines();
        
        System.out.printf("""
        =============================== Inventory Statistics ===============================
        %-36s | %-36s | %-5s
        ====================================================================================       
        ""","Medicine Name","Manufacturer","Count");

        if (medicines.isEmpty()){
            System.out.println("Nothing to report");
        }

        for (Medicine medicine : medicines){
            System.out.printf("""
            %-36s | %-36s | %-5s     
            """,
            medicine.getName(),
            medicine.getManufacturer(),
            service.getTotalStock(medicine));
        }

        System.out.print("""
        ====================================================================================    
        \n\n""");
    }

    public void issuedMedicineStatistics(Report report){
        ArrayList<Integer> uniquePrescribedMedicinesId = service.getUniquePrescribedMedicinesId(report);
    
        System.out.printf("""
        ============================ Issued Medicine Statistics ============================
        %-75s | %-5s
        ====================================================================================       
        ""","Issued Medicine","Amount");

        if (uniquePrescribedMedicinesId.isEmpty()){
            System.out.println("Nothing to report");
        }

        for (Integer id : uniquePrescribedMedicinesId) {
            System.out.printf("""
            %-75s | %-5s        
            """,
            service.getMedicine(id).getName(),
            service.getMedicineIssuedCount(report, id)
            );     
        }

        System.out.print("""
        ====================================================================================    
        \n\n""");
    }


}
