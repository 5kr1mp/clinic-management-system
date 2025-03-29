package service;

import dao.PatientDao;
import java.util.ArrayList;
import model.*;

public class PatientService {

    private PatientDao dao;

    public PatientService(PatientDao patientDao){
        this.dao = patientDao;
    }

    // methods

    public int generateId() {
        ArrayList<Patient> patients = dao.getAll();
        int maxId = 0;
    
        for (Patient patient : patients) {
            if (patient.getId() > maxId) {
                maxId = patient.getId();
            }
        }
    
        return maxId + 1;
    }

    public void add(Patient patient) throws IllegalArgumentException{
        // Check if patient already exists
        if (dao.get(patient.getId()) != null) throw new IllegalArgumentException("This patient already exists.");

        dao.add(patient); // Add if no exact match
        System.out.println("Patient added successfully.");
    }

    public void update(Patient patient) {
        try {
            if (dao.get(patient.getId()) == null) {
                throw new IllegalArgumentException("Patient with ID " + patient.getId() + " not found.");
            }
            dao.update(patient);
            System.out.println("Patient updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id) {
        try {
            Patient patient = dao.get(id); // Get the patient

            if (patient == null) {
                throw new IllegalArgumentException("Patient with ID " + id + " not found.");
            }

            dao.delete(id); // Delete by ID
            System.out.println("Patient with ID " + id + " deleted successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Patient> getPatients() {
        return dao.getAll();
    }

    public Patient getPatient(int id) throws Exception {

        Patient patient = dao.get(id);
        
        if (patient == null) {
            throw new IllegalArgumentException("Patient with ID " + id + " not found.");
        }

        System.out.println("Patient found.");
        return patient;
    }

    public ArrayList<Patient> getPatientsByName(String name) {
        ArrayList<Patient> patientNames = new ArrayList<>();

        for (Patient patient : dao.getAll()) {
            if (patient.getName().equalsIgnoreCase(name)) { /* kani ako gi gamit kay mao ako kabalo
            og d ko nahan magpa tabang chat gpt mag away rami kay lisodon niya! -_- */
                patientNames.add(patient);
            }
        }

        if (patientNames.isEmpty()) { /* diria nag lalis mi kadali ni gpt
         abi kog nag himo2x siyag method or sumthing but built deay ni karon pako kabalo tnx ig gpt!:D*/
            throw new IllegalArgumentException("Patient does not exist.");
        }

        return patientNames;
    }
}
