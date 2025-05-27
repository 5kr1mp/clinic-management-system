package com.usep.clinic.management.system.service;

import java.time.LocalDate;
import java.util.*;

import com.usep.clinic.management.system.AppContext;
import com.usep.clinic.management.system.model.*;
import com.usep.clinic.management.system.model.enums.*;
import com.usep.clinic.management.system.util.*;

public class ReportService {
    
    private static ReportService instance;

    public static ReportService getInstance(){
        if (instance == null){
            instance = new ReportService();
        }

        return instance;
    }

    /**
     * Empty parameterless constructor to prevent instantiation
     */
    private ReportService(){}
    
    public static class DiagnosisStat{

        private String diagnosis;
        private int count;
        
        public void setDiagnosis(String diagnosis) {this.diagnosis = diagnosis;}
        public void setCount(int count) {this.count = count;}
        public String getDiagnosis() {return diagnosis;}
        public int getCount() {return count;}
        public DiagnosisStat(String diagnosis, int count) {
            this.diagnosis = diagnosis;
            this.count = count;
        }
    }
    
    public static class PatientStat{
        private Category category;
        private int count;
        
        public PatientStat(Category category, int count){
            this.category = category;
            this.count = count;
        }
        public Category getCategory() {return category;}
        public int getCount() {return count;}
        public void setCount(int count) {this.count = count;}
        public void setCategory(Category category) {this.category = category;}
        
        
    }
    
    public static class InventoryStat{

        public void setMedicineName(String medicineName) {this.medicineName = medicineName;}
        public void setManufacturer(String manufacturer) {this.manufacturer = manufacturer;}
        public void setStock(int stock) {this.stock = stock;}
        public void setAmountIssued(int amountIssued) {this.amountIssued = amountIssued;}
        public String getMedicineName() {return medicineName;}
        public String getManufacturer() {return manufacturer;}
        public int getStock() {return stock;}
        public int getAmountIssued() {return amountIssued;}
        public InventoryStat(String medicineName, String manufacturer, int stock, int amountIssued) {
            this.medicineName = medicineName;
            this.manufacturer = manufacturer;
            this.stock = stock;
            this.amountIssued = amountIssued;
        }
        String medicineName;
        String manufacturer;
        int stock;
        int amountIssued;
    }

    public Report generateMonthlyReport(){
        DateRange dateRange = DateRange.ofMonth();
        ArrayList<MedicineBatch> medicines = AppContext.getMedicineService().getMedicineBatches();
        ArrayList<PatientRecord> records = AppContext.getPatientService().getRecords(dateRange);

        return new Report(dateRange,records,medicines);
    }

    public Report generateWeeklyReport(){
        DateRange dateRange = DateRange.ofWeek();
        ArrayList<MedicineBatch> medicines = AppContext.getMedicineService().getMedicineBatches();
        ArrayList<PatientRecord> records = AppContext.getPatientService().getRecords(dateRange);
        
        return new Report(dateRange,records,medicines);
    }
    
    public Report generateDailyReport(){
        LocalDate date = LocalDate.now();
        
        ArrayList<MedicineBatch> medicines = AppContext.getMedicineService().getMedicineBatches();
        ArrayList<PatientRecord> records = AppContext.getPatientService().getRecords(date);

        return new Report(date, records, medicines);
    }
    
    public Report generateAnnualReport(){
        DateRange dateRange = DateRange.ofYear();
        ArrayList<MedicineBatch> medicines = AppContext.getMedicineService().getMedicineBatches();
        ArrayList<PatientRecord> records = AppContext.getPatientService().getRecords(dateRange);
        
        return new Report(dateRange,records,medicines);
    }
    
    public ArrayList<PatientStat> getPatientStats(Report report){
        ArrayList<PatientStat> patientStats = new ArrayList<>();
        
        patientStats.add(
                new PatientStat(Category.FACULTY,
                getFacultyPatientsCount(report))
        );
        
        patientStats.add(
                new PatientStat(Category.STUDENT,
                getStudentPatientsCount(report))
        );
        
        return patientStats;
    }
    
    public ArrayList<DiagnosisStat> getDiagnosisStats(Report report){
        ArrayList<DiagnosisStat> diagnosisStats = new ArrayList<>();
        
        for (String diagnosis : getUniqueDiagnoses(report)){
            diagnosisStats.add(
                new DiagnosisStat(diagnosis, 
                        diagnosisCounter(report, diagnosis)
                )
            );
        }
        
        return diagnosisStats;
    }
    
    public ArrayList<InventoryStat> getInventoryStats(Report report){
        ArrayList<InventoryStat> inventoryStats = new ArrayList<>();
        
        for (Medicine med : getMedicines()){
            InventoryStat stat = new InventoryStat(
                med.getName(),
                med.getManufacturer(),
                getTotalStock(med),
                getMedicineIssuedCount(report, med.getId())
            );
            
            inventoryStats.add(stat);
        }
        
        return inventoryStats;
    }

    private ArrayList<String> getUniqueDiagnoses(Report report){
        ArrayList<String> diagnoses = getAllDiagnoses(report);
        ArrayList<String> uniqueDiagnoses = new ArrayList<>();

        for (String diagnosis : diagnoses) {
            if (!uniqueDiagnoses.contains(diagnosis)){
                uniqueDiagnoses.add(diagnosis);
            }
        }

        return uniqueDiagnoses;
    }

    private ArrayList<String> getAllDiagnoses(Report report){
        ArrayList<String> diagnoses = new ArrayList<>();

        // Creates a list of diagnoses from the records.
        for (PatientRecord record : report.getPatientRecords()) {
            diagnoses.add(record.getDiagnosis().trim());
        }

        return diagnoses;
    }

    private int diagnosisCounter(Report report, String diagnosis){
        int count = 0;

        for (String elem : getAllDiagnoses(report)) {
            if (elem.equals(diagnosis)) count++;
        }

        return count;
    }

    private ArrayList<Integer> getAllPrescribedMedicinesId(Report report){
        ArrayList<Integer> prescriptionsId = new ArrayList<>();

        for (PatientRecord record : report.getPatientRecords()){
           for (IssuedMedicine issuedMedicine : AppContext.getMedicineService().getIssuedMedicinesByRecordId(record.getId())){
            prescriptionsId.add(issuedMedicine.getMedicineId());
           }
        }

        return prescriptionsId;
    }

    @Deprecated
    private ArrayList<Integer> getUniquePrescribedMedicinesId(Report report){
        ArrayList<Integer> prescriptionsId = getAllPrescribedMedicinesId(report);
        ArrayList<Integer> uniquePrescriptionsId = new ArrayList<>();

        for (Integer id : prescriptionsId) {
            if (!uniquePrescriptionsId.contains(id)){
                uniquePrescriptionsId.add(id);
            }
        }

        return uniquePrescriptionsId;
    }

    private int getMedicineIssuedCount(Report report, int medicineId){
        int amount = 0;

        for (PatientRecord record : report.getPatientRecords()){
            for (IssuedMedicine issuedMedicine : AppContext.getMedicineService().getIssuedMedicinesByRecordId(record.getId())){
                if (issuedMedicine.getMedicineId() == medicineId){
                    amount += issuedMedicine.getAmount();
                }
            }
        }

        return amount;
    }

    private ArrayList<Medicine> getMedicines(){
        return AppContext.getMedicineService().getMedicines();
    }

    private Medicine getMedicine(int id){

        try{
            return AppContext.getMedicineService().getMedicine(id);
        }
        catch (Exception e) {
            return null;
        }

    }

    private int getTotalStock(Medicine medicine){
        return AppContext.getMedicineService().getTotalStock(medicine.getId());
    }

    private int getFacultyPatientsCount(Report report){
        int count = 0;

        for (PatientRecord record : report.getPatientRecords()) {

            Patient patient = null;

            // retrieve patient
            try {
                patient = AppContext.getPatientService().getPatient(record.getPatientId());

                if (
                    patient.getCategory() == Category.FACULTY
                ){
                    count++;
                }
            } 
            catch (Exception e){
            }

        }
        return count;
    }

    private int getStudentPatientsCount(Report report){
        int count = 0;

        for (PatientRecord record : report.getPatientRecords()) {

            Patient patient = null;

            // retrieve patient
            try {
                patient = AppContext.getPatientService().getPatient(record.getPatientId());
                if (
                    patient.getCategory() == Category.STUDENT
                ){
                    count++;
                }
            } 
            catch (Exception e){
            }

        }
        return count;
    }

}
