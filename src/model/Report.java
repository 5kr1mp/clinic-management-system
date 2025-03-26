package model;

import java.time.*;
import java.util.*;

import enums.*;
import util.DateRange;

public class Report {
    
    // Variables
    private DateRange dateRange;
    private ArrayList<PatientRecord> patientRecords;
    private ArrayList<Medicine> medicines;

    // Constructors
    public Report(DateRange range, ArrayList<PatientRecord> records, ArrayList<Medicine> medicines){
        this.dateRange = range;
        this.patientRecords = records;
        this.medicines = medicines;
    }

    // Methods
    public ArrayList<Medicine> getMedicines() {return medicines;}
    public ArrayList<PatientRecord> getPatientRecords() {return patientRecords;}
    public ArrayList<String> getDiagnoses(){
        ArrayList<String> diagnoses = new ArrayList<>();
        ArrayList<Object[]> diagnosesCountPair = new ArrayList<>();

        for (PatientRecord record : patientRecords) {
            diagnoses.add(record.getDiagnosis());
        }

        diagnosesCountPair.add(
            new Object[]{"Fever", Integer.valueOf(10)}
        );

        return diagnoses;
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
