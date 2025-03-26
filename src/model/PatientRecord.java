package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PatientRecord {

    private int recordId;
    private int patientId;
    private Patient patient;
    private LocalDateTime date;
    private String desc;
    private String diagnosis;
    private ArrayList<Medicine> medicineIssued;

    public PatientRecord(Patient patient, LocalDateTime date, String desc, String diagnosis, ArrayList<Medicine> medicines) {
        this.patient = patient;
        this.date = date;
        this.desc = desc;
        this.diagnosis = diagnosis;
        this.medicineIssued = medicines;
    }

    public PatientRecord(Patient patient, LocalDateTime date, String desc, String diagnosis) {
        this.patient = patient;
        this.date = date;
        this.desc = desc;
        this.diagnosis = diagnosis;
        this.medicineIssued = new ArrayList<Medicine>();
    }

    public PatientRecord(
        int recordId,
        int patientId,
        Patient patient,
        LocalDateTime date,
        String desc,
        String diagnosis,
        ArrayList<Medicine> medicines
    ){
        this.recordId = recordId;
        this.patientId = patientId;
        this.patient = patient;
        this.date = date;
        this.desc = desc;
        this.diagnosis = diagnosis;
        this.medicineIssued = medicines;
    }

    public int getPatientId() {return patientId;}

    public int getRecordId() {
        return recordId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public ArrayList<Medicine> getMedicineIssued() {
        return medicineIssued;
    }

    public void setMedicineIssued(ArrayList<Medicine> medicineIssued) {
        this.medicineIssued = medicineIssued;
    }

    void display () {
        System.out.println("Patient Records:");

        if (patient != null) {
            System.out.println("Patient Name: " + patient.getName());
            System.out.println("Patient ID: " + patient.getId());
            System.out.println("Designation: " + patient.getDesignation());
            System.out.println("Category: " + patient.getCategory());
            System.out.println("Contact: " + patient.getContact());
        } else {
            System.out.println("No patient information available.");
        }

        System.out.println("Date: " + date);
        System.out.println("Description: " + desc);
        System.out.println("Diagnosis: " + diagnosis);

        if (medicineIssued != null) {
            System.out.println("Medicines Issued:");
            for(int i=0; i<medicineIssued.size(); i++) {
                System.out.println(medicineIssued.get(i));
            }

        } else {
            System.out.println("No medicines issued.");
        }
    }

}

