package model;

import java.time.*;
import java.util.*;

import enums.*;
import util.DateRange;

public class Report {
    
    // Variables
    private DateRange dateRange;
    private ArrayList<PatientRecord> patientRecords;
    private ArrayList<MedicineBatch> medicineBatches;

    // Constructors
    public Report(DateRange range, ArrayList<PatientRecord> records, ArrayList<MedicineBatch> medicines){
        this.dateRange = range;
        this.patientRecords = records;
        this.medicineBatches = medicines;
    }

    // Methods
    public ArrayList<MedicineBatch> getMedicineBatches() {return medicineBatches;}
    public ArrayList<PatientRecord> getPatientRecords() {return patientRecords;}

    public ArrayList<String> getUniqueDiagnoses(){
        ArrayList<String> diagnoses = new ArrayList<>();

        // Creates a list of diagnoses from the records.
        for (PatientRecord record : patientRecords) {
            diagnoses.add(record.getDiagnosis().trim());
        }

        for (String diagnosis : diagnoses) {
            if (diagnoses.contains(diagnosis)) diagnoses.remove(diagnoses);
        }

        return diagnoses;
    }

    public ArrayList<String> getAllDiagnoses(){
        ArrayList<String> diagnoses = new ArrayList<>();

        // Creates a list of diagnoses from the records.
        for (PatientRecord record : patientRecords) {
            diagnoses.add(record.getDiagnosis().trim());
        }

        return diagnoses;
    }

    public int diagnosisCounter(String diagnosis){
        int count = 0;

        for (String elem : getAllDiagnoses()) {
            if (elem.equals(diagnosis)) count++;
        }

        return count;
    }

    public ArrayList<Integer> getAllPrescribedMedicinesId(){
        ArrayList<Integer> prescriptionsId = new ArrayList<>();

        for (PatientRecord record : patientRecords){
            for (Medicine medicine : record.getMedicineIssued()){
                prescriptionsId.add(medicine.getId());
            }
        }

        return prescriptionsId;
    }

    public ArrayList<Integer> getUniquePrescribedMedicinesId(){
        ArrayList<Integer> prescriptionsId = new ArrayList<>();

        for (PatientRecord record : patientRecords){
            for (Medicine medicine : record.getMedicineIssued()){
                prescriptionsId.add(medicine.getId());
            }
        }

        for (Integer id : prescriptionsId) {
            if (prescriptionsId.contains(id)) prescriptionsId.remove(id);
        }

        return prescriptionsId;
    }

    public int getMedicineIssuedCount(int medicineId){
        int count = 0;

        for(Integer id : getAllPrescribedMedicinesId()){
            if (id.equals(medicineId)) count++;
        }

        return count;
    }

    public int getFacultyPatientsCount(){
        int count = 0;

        for (PatientRecord record : patientRecords) {
            if (
                record.getPatient()
                      .getCategory() == Category.FACULTY
            ){
                count++;
            }
        }
        return count;
    }

    public int getStudentPatientsCount(){
        int count = 0;

        for (PatientRecord record : patientRecords) {
            if (
                record.getPatient()
                      .getCategory() == Category.STUDENT
            ){
                count++;
            }
        }
        return count;

    }

    public Medicine getMedicine(int index){return medicines.get(index);}
    public PatientRecord getPatientRecord(int index){return patientRecords.get(index);}

}
