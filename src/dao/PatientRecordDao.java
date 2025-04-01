package dao;

import java.time.LocalDateTime;
import java.util.*;
import model.*;

/**
 * Changes:
 * <ul>
 *  <li>Changed getRecordId() to getId()</li>
 * </ul>
 */
public class PatientRecordDao {
    
    /**
     * Placeholder database
     */
    private ArrayList<PatientRecord> records = new ArrayList<PatientRecord>(List.of(
        // Assuming inani ang constructor sa PatientRecord
        // PatientRecord {
        //     int recordId,
        //     int patientId,
        //     Patient pateint,
        //     LocalDateTime date,
        //     String desc,
        //     String diagnosis,
        //     ArrayList<PatientRecord> medicinesIssued
        // }
        new PatientRecord(
            1,
            20240143,
            LocalDateTime.now(),
            "a",
            "fever",
            new ArrayList<>()
        ),
        new PatientRecord(
            2,
            20240143,
            LocalDateTime.of(2025, 3, 27,0,0,0),
            "b",
            "fever again",
            new ArrayList<>()
        ),
        new PatientRecord(
            3,
            202400045,
            LocalDateTime.of(2025,3,25,0,0,0),
            "c",
            "ah",
            new ArrayList<>()
        )
    ));

    /**
     * Inserts a record in {@link #records}
     * 
     * @param record the PatientRecord object to be inserted
     * 
     */
    public void add(PatientRecord record){
        records.add(record);
    }

    /**
     * Retrieves the whole list of records
     * 
     * @return {@link #records}
     */
    public ArrayList<PatientRecord> getAll(){
        return records;
    }

    /**
     * Retrieves a record by id
     * 
     * @param id of the {@code PatientRecord} to retrieve
     * @return {@code PatientRecord} object with of the id.
     *         Will return null if nothing is found
     */
    public PatientRecord get(int id){
        for (PatientRecord patientRecord : records) {
            if (patientRecord.getId() == id) return patientRecord;
        }

        return null;
    }

    /**
     * Updates the record object with the same {@code record.id}
     * 
     * @param record the updated {@code PatientRecord} object
     */
    public void update(PatientRecord record){
        for (int i = 0; i < records.size(); i++){
            if (records.get(i).getId() == record.getId()){
                records.set(i, record);
            }
        }
    }

    /**
     * Removes the {@code PatientRecord} with the given id from {@link #records}
     * 
     * @param id of the record to be removed
     */
    public void delete(int id){
        for (int i = 0; i < records.size(); i++){
            if (records.get(i).getId() == id){
                records.remove(i);
            }
        }
    }
}
