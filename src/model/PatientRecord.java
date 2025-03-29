package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 
 * Changes
 *  <ul>
 *      <li>Removed Patient patient attribute</li>
 *      <li>Modified {@link #medicinesIssued}; it now contains IssuedMedicine objects instead of Medicine objects</li>
 *      <li>Added new constructors:</li>
 *      <li>
 *          <ul>
 *              <li>{@link #PatientRecord(int, Patient, LocalDateTime, String, String)}</li>
 *              <li>{@link #PatientRecord(int, Patient, LocalDateTime, String, String, ArrayList)}</li>
 *          </ul>
 *      </li>
 *  </ul>
 *  
 * 
 */

public class PatientRecord {

    private int recordId;
    private int patientId;
    private LocalDateTime date;
    private String desc;
    private String diagnosis;
    private ArrayList<IssuedMedicine> medicinesIssued;

    public PatientRecord(int id, int patientId, LocalDateTime date, String desc, String diagnosis, ArrayList<IssuedMedicine> medicinesIssued) {
        this.recordId= id;
        this.patientId = patientId;
        this.date = date;
        this.desc = desc;
        this.diagnosis = diagnosis;
        this.medicinesIssued = medicinesIssued;
    }

    public PatientRecord(int id, int patientId, LocalDateTime date, String desc, String diagnosis) {
        this.patientId = patientId;
        this.date = date;
        this.desc = desc;
        this.diagnosis = diagnosis;
        this.medicinesIssued = new ArrayList<IssuedMedicine>();
    }

    public PatientRecord(int id, Patient patient, LocalDateTime date, String desc, String diagnosis, ArrayList<IssuedMedicine> medicinesIssued) {
        this.patientId = patient.getId();
        this.date = date;
        this.desc = desc;
        this.diagnosis = diagnosis;
        this.medicinesIssued = medicinesIssued;
    }

    public PatientRecord(int id, Patient patient, LocalDateTime date, String desc, String diagnosis){
        this.patientId = patient.getId();
        this.date = date;
        this.desc = desc;
        this.diagnosis = diagnosis;
        this.medicinesIssued = new ArrayList<IssuedMedicine>();
    }

    public int getPatientId() {return patientId;}
    public int getId() {return recordId;}
    public LocalDateTime getDate() {return date;}
    public void setDate(LocalDateTime date) {this.date = date;}
    public String getDesc() {return desc;}
    public void setDesc(String desc) {this.desc = desc;}
    public String getDiagnosis() {return diagnosis;}
    public void setDiagnosis(String diagnosis) {this.diagnosis = diagnosis;}
    public ArrayList<IssuedMedicine> getMedicineIssued() {return medicinesIssued;}
    public void setMedicinesIssued(ArrayList<IssuedMedicine> medicinesIssued) {this.medicinesIssued = medicinesIssued;}

    void display () {
        System.out.println("Patient Records:");

        // if (patient != null) {
        //     System.out.println("Patient Name: " + patient.getName());
        //     System.out.println("Patient ID: " + patient.getId());
        //     System.out.println("Designation: " + patient.getDesignation());
        //     System.out.println("Category: " + patient.getCategory());
        //     System.out.println("Contact: " + patient.getContact());
        // } else {
        //     System.out.println("No patient information available.");
        // }

        System.out.println("Date: " + date);
        System.out.println("Description: " + desc);
        System.out.println("Diagnosis: " + diagnosis);

        if (medicinesIssued != null) {
            System.out.println("Medicines Issued:");
            for(int i=0; i<medicinesIssued.size(); i++) {
                System.out.println(medicinesIssued.get(i).getMedicine().getName());
            }

        } else {
            System.out.println("No medicines issued.");
        }
    }

}

