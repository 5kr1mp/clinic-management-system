package dao;

import java.util.List;
import java.util.ArrayList;
import enums.*;
import model.*;

public class PatientDao {
    
    /**
     * Placeholder database
     */
    private ArrayList<Patient> patients = new ArrayList<>( List.of(
        // Assuming inani ang constructor sa Patient
        // Patient {
        //     int id,
        //     String lastname,
        //     String firstname,
        //     Sting middlename,
        //     String name,
        //     Designation designation,
        //     Category Category,
        //     String contact,
        //     ArrayList<PatientRecord> records
        // }
        new Patient(
            20240143,
            "Cookie",
            "Shadow",
            "Milk",
            Designation.BSIT_1IT,
            Category.STUDENT,
            "09123124234",
            new ArrayList<>() // no records
        ),
        new Patient(
            202400045,
            "Salada",
            "Trixy",
            "D.",
            Designation.BSIT_1IT,
            Category.STUDENT,
            "09124321234",
            new ArrayList<>()
        )
    ));
    
    /**
     * Inserts a {@code Patient} at the end of {@link #patients} 
     * 
     * @param patient the {@code Patient} to insert
     */
    public void add(Patient patient){
        patients.add(patient);
    }

    /**
     * Returns the whole {@code ArrayList} {@link patients}
     * 
     * @return {@link PatientDao#patients}, list of patients
     */
    public ArrayList<Patient> getAll(){
        return patients;
    }

    /**
     * Retrieves the {@code Patient} object by id
     * 
     * @param id of the {@code Patient} to retrieve
     * @return {@code Patient} object
     */
    public Patient get(int id){

        for (Patient patient : patients) {
            if (patient.getId() == id){
                return patient;
            }   
        }

        return null;
    }

    /**
     * Updates the patient object with the same {@code patient.id}
     * 
     * @param patient the updated patient
     */
    public void update(Patient patient){
        for (int i = 0; i < patients.size(); i++){

            if (patients.get(i).getId() == patient.getId()){
                patients.set(i, patient);
            }

        }
    }

    /**
     * Removes a patient object from {@link PatientDao#patients}
     * 
     * @param id of the patient to be removed
     */
    public void delete(int id){
        for (Patient patient : patients){
            if (patient.getId() == id){
                patients.remove(patient);
            }
        }
    }

}
