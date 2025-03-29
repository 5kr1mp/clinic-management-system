package service;

import java.time.LocalDate;
import dao.PatientRecordDao;
import java.util.ArrayList;
import model.*;
import util.DateRange;

/**
 * Changes:
 * <ul>
 *  <li>Updated method calls for {@link model.PatientRecord#getId()}; previously "getRecordId()"</li>
 *  <li>Added {@link #generateId()} method</li>
 * </ul>
 */

public class PatientRecordService {

    private PatientRecordDao dao;

    public PatientRecordService(PatientRecordDao patientRecordDao){
        this.dao = patientRecordDao;
    }

    public int generateId() {
        ArrayList<PatientRecord> records = dao.getAll();
        int maxId = 0;
    
        for (PatientRecord record : records) {
            if (record.getId() > maxId) {
                maxId = record.getId();
            }
        }
    
        return maxId + 1;
    }

    public void add(PatientRecord record) throws Exception{

        if (dao.get(record.getId()) != null) throw new Exception("Record with ID " + record.getId() + " already exists.");

        dao.add(record);
        System.out.println("Patient record added successfully.");
    }

    public void update(PatientRecord record) throws Exception{
        if (dao.get(record.getId()) == null) throw new Exception("Record with ID " + record.getId() + " not found.");

        dao.update(record);
        System.out.println("Patient record updated successfully.");
    }

    public void delete(int id) throws Exception{

        PatientRecord record = dao.get(id);

        if (record == null) throw new Exception("Record with ID " + id + " not found.");

        dao.delete(id);
        System.out.println("Patient record deleted successfully.");
    }


    public PatientRecord getRecord(int recordId) {
        return dao.get(recordId);
    }

    public ArrayList<PatientRecord> getRecords() {
        return dao.getAll();
    }

    public ArrayList<PatientRecord> getRecords(DateRange range){

        ArrayList<PatientRecord> filteredRecords = new ArrayList<>();

        for (PatientRecord record : dao.getAll()) {
            if (range.isWithinRange(record.getDate().toLocalDate())) filteredRecords.add(record);
        }

        return filteredRecords;
    }

    public ArrayList<PatientRecord> getRecords(LocalDate date){
        ArrayList<PatientRecord> filteredRecords = new ArrayList<>();

        for (PatientRecord record : dao.getAll()){
            if (date.equals(record.getDate().toLocalDate())) filteredRecords.add(record);
        }

        return filteredRecords;
    }

    public ArrayList<PatientRecord> getRecordsByPatientId(int patientId) {
        ArrayList<PatientRecord> recordsOfPatient = new ArrayList<>();

        for (PatientRecord record : dao.getAll()) { // Ensure correct type
            if (record.getPatientId() == patientId) {
                recordsOfPatient.add(record);
            }
        }
        return recordsOfPatient;
    }

}

