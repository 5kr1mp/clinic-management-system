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
     * Empty private constructor to prevent instantiation
     */
    private ReportService(){}

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

    public ArrayList<String> getUniqueDiagnoses(Report report){
        ArrayList<String> diagnoses = getAllDiagnoses(report);
        ArrayList<String> uniqueDiagnoses = new ArrayList<>();

        for (String diagnosis : diagnoses) {
            if (!uniqueDiagnoses.contains(diagnosis)){
                uniqueDiagnoses.add(diagnosis);
            }
        }

        return uniqueDiagnoses;
    }

    public ArrayList<String> getAllDiagnoses(Report report){
        ArrayList<String> diagnoses = new ArrayList<>();

        // Creates a list of diagnoses from the records.
        for (PatientRecord record : report.getPatientRecords()) {
            diagnoses.add(record.getDiagnosis().trim());
        }

        return diagnoses;
    }

    public int diagnosisCounter(Report report, String diagnosis){
        int count = 0;

        for (String elem : getAllDiagnoses(report)) {
            if (elem.equals(diagnosis)) count++;
        }

        return count;
    }

    public ArrayList<Integer> getAllPrescribedMedicinesId(Report report){
        ArrayList<Integer> prescriptionsId = new ArrayList<>();

        for (PatientRecord record : report.getPatientRecords()){
           for (IssuedMedicine issuedMedicine : AppContext.getMedicineService().getIssuedMedicinesByRecordId(record.getId())){
            prescriptionsId.add(issuedMedicine.getMedicineId());
           }
        }

        return prescriptionsId;
    }

    public ArrayList<Integer> getUniquePrescribedMedicinesId(Report report){
        ArrayList<Integer> prescriptionsId = getAllPrescribedMedicinesId(report);
        ArrayList<Integer> uniquePrescriptionsId = new ArrayList<>();

        for (Integer id : prescriptionsId) {
            if (!uniquePrescriptionsId.contains(id)){
                uniquePrescriptionsId.add(id);
            }
        }

        return uniquePrescriptionsId;
    }

    public int getMedicineIssuedCount(Report report, int medicineId){
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

    public ArrayList<Medicine> getMedicines(){
        return AppContext.getMedicineService().getMedicines();
    }

    public Medicine getMedicine(int id){

        try{
            return AppContext.getMedicineService().getMedicine(id);
        }
        catch (Exception e) {
            return null;
        }

    }

    public int getTotalStock(Medicine medicine){
        return AppContext.getMedicineService().getTotalStock(medicine.getId());
    }

    public int getFacultyPatientsCount(Report report){
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

    public int getStudentPatientsCount(Report report){
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
