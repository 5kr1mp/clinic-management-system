package com.usep.clinic.management.system.service;

import com.usep.clinic.management.system.dao.PatientDao;
import com.usep.clinic.management.system.dao.PatientRecordDao;
import com.usep.clinic.management.system.model.*;
import com.usep.clinic.management.system.util.DateRange;
import java.time.LocalDate;
import java.util.ArrayList;

public class PatientService {

    private static PatientService instance;
    private PatientDao patientDao;
    private PatientRecordDao recordDao;

    public static PatientService getInstance(){
        if (instance == null){
            instance = new PatientService(new PatientDao(), new PatientRecordDao());
        }

        return instance;
    }

    private PatientService(
        PatientDao patientDao,
        PatientRecordDao recordDao
    ){
        this.patientDao = patientDao;
        this.recordDao = recordDao;
    }

    // methods


    public int generatePatientId() {
        ArrayList<Patient> patients = patientDao.getAll();
        int maxId = 0;
    
        for (Patient patient : patients) {
            if (patient.getId() > maxId) {
                maxId = patient.getId();
            }
        }
    
        return maxId + 1;
    }

    public int generateRecordId() {
        ArrayList<PatientRecord> records = recordDao.getAll();
        int maxId = 0;
    
        for (PatientRecord record : records) {
            if (record.getId() > maxId) {
                maxId = record.getId();
            }
        }
    
        return maxId + 1;
    }

    public boolean patientExists(int id){
        if (patientDao.get(id) == null){
            return false;
        }
        return true;
    }

    // CREATE
    public void add(Patient patient) throws Exception{
        // Check if patient already exists
        if (patientDao.get(patient.getId()) != null) throw new DuplicateEntityException("This patient already exists.");

        patientDao.add(patient); // Add if no exact match
    }

    public void add(PatientRecord record) throws Exception{

        if (recordDao.get(record.getId()) != null) throw new DuplicateEntityException("Record with ID " + record.getId() + " already exists.");

        recordDao.add(record);
    }


    // RETRIEVE
    public ArrayList<Patient> getPatients() {
        return patientDao.getAll();
    }

    public Patient getPatient(int id) {
        return patientDao.get(id);
    }

    public ArrayList<Patient> getPatientsByName(String name){
        return patientDao.getPatientsByName(name);
    }

    public ArrayList<Patient> getFacultyPatients(){
        return patientDao.getFacultyPatients();
    }

    public ArrayList<Patient> getStudentPatients(){
        return patientDao.getStudentPatients();
    }

    public PatientRecord getRecord(int recordId) {
        return recordDao.get(recordId);
    }

    public ArrayList<PatientRecord> getRecords() {
        return recordDao.getAll();
    }

    public ArrayList<PatientRecord> getRecords(DateRange range){

        ArrayList<PatientRecord> filteredRecords = new ArrayList<>();

        for (PatientRecord record : recordDao.getAll()) {
            if (range.isWithinRange(record.getDateTime().toLocalDate())) filteredRecords.add(record);
        }

        return filteredRecords;
    }

    public ArrayList<PatientRecord> getRecords(LocalDate date){
        ArrayList<PatientRecord> filteredRecords = new ArrayList<>();

        for (PatientRecord record : recordDao.getAll()){
            if (date.equals(record.getDateTime().toLocalDate())) filteredRecords.add(record);
        }

        return filteredRecords;
    }

    public ArrayList<PatientRecord> getRecordsByPatientId(int patientId) {
        return recordDao.getRecordsByPatientId(patientId);
    }

    // UPDATE
    public void update(PatientRecord record) throws Exception{
        if (recordDao.get(record.getId()) == null) throw new Exception("Record with ID " + record.getId() + " not found.");

        recordDao.update(record);
    }

    public void update(Patient patient) throws Exception {
        if (patientDao.get(patient.getId()) == null) {
            throw new Exception("Patient with ID " + patient.getId() + " not found.");
        }

        patientDao.update(patient);
    }

    //  DELETE
    public void delete(int id) throws Exception {
        Patient patient = patientDao.get(id); // Get the patient

        if (patient == null) {
            throw new Exception("Patient with ID " + id + " not found.");
        }

        patientDao.delete(id); // Delete by ID
    }

    public void deleteRecord(int id) throws Exception{

        PatientRecord record = recordDao.get(id);

        if (record == null) throw new Exception("Record with ID " + id + " not found.");

        recordDao.delete(id);
    }

}
