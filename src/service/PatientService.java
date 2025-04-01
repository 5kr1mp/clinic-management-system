package service;

import dao.PatientDao;
import java.util.ArrayList;

import enums.Category;
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

    public void add(Patient patient) throws Exception{
        // Check if patient already exists
        if (dao.get(patient.getId()) != null) throw new Exception("This patient already exists.");

        dao.add(patient); // Add if no exact match
    }

    public void update(Patient patient) throws Exception {
        if (dao.get(patient.getId()) == null) {
            throw new Exception("Patient with ID " + patient.getId() + " not found.");
        }

        dao.update(patient);
    }

    public boolean patientExists(int id){
        if (dao.get(id) == null){
            return false;
        }
        return true;
    }

    public void delete(int id) throws Exception {
        Patient patient = dao.get(id); // Get the patient

        if (patient == null) {
            throw new Exception("Patient with ID " + id + " not found.");
        }

        dao.delete(id); // Delete by ID
    }

    public ArrayList<Patient> getPatients() {
        return dao.getAll();
    }

    public Patient getPatient(int id) throws Exception {

        Patient patient = dao.get(id);
        
        if (patient == null) {
            throw new Exception("Patient with ID " + id + " not found.");
        }

        return patient;
    }

    public ArrayList<Patient> getPatientByLastName(String name) {
        ArrayList<Patient> patientNames = new ArrayList<>();

        for (Patient patient : dao.getAll()) {
            if (patient.getLastname().equalsIgnoreCase(name)) { /* kani ako gi gamit kay mao ako kabalo
            og d ko nahan magpa tabang chat gpt mag away rami kay lisodon niya! -_- */
                patientNames.add(patient);
            }
        }
        return patientNames;
    }

    public Patient getPatientByName(String name){

        for (Patient patient : dao.getAll()){
            if (patient.getFullName().equalsIgnoreCase(name.trim())) return patient;
        }

        return null;

    }


    public ArrayList<Patient> getFacultyPatients(){
        ArrayList<Patient> facultyPatients = new ArrayList<>();

        for (Patient patient : getPatients()) {
            if (patient.getCategory() == Category.FACULTY){
                facultyPatients.add(patient);
            }
        }

        return facultyPatients;
    }

    public ArrayList<Patient> getStudentPatients(){
        ArrayList<Patient> studentPatients = new ArrayList<>();

        for (Patient patient : getPatients()) {
            if (patient.getCategory() == Category.STUDENT){
                studentPatients.add(patient);
            }
        }

        return studentPatients;
    }

}
