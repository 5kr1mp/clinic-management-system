package service;

import java.time.LocalDate;
import dao.PatientRecordDao;
import java.util.ArrayList;
import model.*;
import util.DateRange;

public class PatientRecordService {

    private PatientRecordDao dao;

    public PatientRecordService(PatientRecordDao patientRecordDao){
        this.dao = patientRecordDao;
    }

    void add(PatientRecord record) throws Exception{

        if (dao.get(record.getRecordId()) != null) throw new Exception("Record with ID " + record.getRecordId() + " already exists.");

        dao.add(record);
        System.out.println("Patient record added successfully.");
    }

    void update(PatientRecord record) throws Exception{
        if (dao.get(record.getRecordId()) == null) throw new Exception("Record with ID " + record.getRecordId() + " not found.");

        dao.update(record);
        System.out.println("Patient record updated successfully.");
    }

    void delete(int id) throws Exception{

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

