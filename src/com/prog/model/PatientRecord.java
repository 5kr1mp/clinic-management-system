package com.prog.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PatientRecord {

    private int id;
    private int patientId;
    private LocalDateTime dateTime;
    private String desc;
    private String diagnosis;
    private ArrayList<IssuedMedicine> medicinesIssued;

    public PatientRecord(
        int id, 
        int patientId, 
        LocalDateTime dateTime, 
        String desc, 
        String diagnosis, 
        ArrayList<IssuedMedicine> medicinesIssued
    ) {
        this.id= id;
        this.patientId = patientId;
        this.dateTime = dateTime;
        this.desc = desc;
        this.diagnosis = diagnosis;
        this.medicinesIssued = medicinesIssued;
    }

    public PatientRecord(int id, int patientId, LocalDateTime date, String desc, String diagnosis) {
        this.patientId = patientId;
        this.dateTime = date;
        this.desc = desc;
        this.diagnosis = diagnosis;
        this.medicinesIssued = new ArrayList<IssuedMedicine>();
    }

    public PatientRecord(int id, Patient patient, LocalDateTime date, String desc, String diagnosis, ArrayList<IssuedMedicine> medicinesIssued) {
        this.patientId = patient.getId();
        this.dateTime = date;
        this.desc = desc;
        this.diagnosis = diagnosis;
        this.medicinesIssued = medicinesIssued;
    }

    public PatientRecord(int id, Patient patient, LocalDateTime date, String desc, String diagnosis){
        this.patientId = patient.getId();
        this.dateTime = date;
        this.desc = desc;
        this.diagnosis = diagnosis;
        this.medicinesIssued = new ArrayList<IssuedMedicine>();
    }

    public int getPatientId() {return patientId;}
    public int getId() {return id;}
    public LocalDateTime getDateTime() {return dateTime;}
    public void setDateTime(LocalDateTime date) {this.dateTime = date;}
    public String getDesc() {return desc;}
    public void setDesc(String desc) {this.desc = desc;}
    public String getDiagnosis() {return diagnosis;}
    public void setDiagnosis(String diagnosis) {this.diagnosis = diagnosis;}
    public ArrayList<IssuedMedicine> getMedicineIssued() {return medicinesIssued;}
    public void setMedicinesIssued(ArrayList<IssuedMedicine> medicinesIssued) {this.medicinesIssued = medicinesIssued;}

    // void display () {
    //     System.out.println("Patient Records:");

    //     // if (patient != null) {
    //     //     System.out.println("Patient Name: " + patient.getName());
    //     //     System.out.println("Patient ID: " + patient.getId());
    //     //     System.out.println("Designation: " + patient.getDesignation());
    //     //     System.out.println("Category: " + patient.getCategory());
    //     //     System.out.println("Contact: " + patient.getContact());
    //     // } else {
    //     //     System.out.println("No patient information available.");
    //     // }

    //     System.out.println("Date: " + date);
    //     System.out.println("Description: " + desc);
    //     System.out.println("Diagnosis: " + diagnosis);

    //     if (medicinesIssued != null) {
    //         System.out.println("Medicines Issued:");
    //         for(int i=0; i<medicinesIssued.size(); i++) {
    //             System.out.println(medicinesIssued.get(i).getMedicine().getName());
    //         }

    //     } else {
    //         System.out.println("No medicines issued.");
    //     }
    // }

}

