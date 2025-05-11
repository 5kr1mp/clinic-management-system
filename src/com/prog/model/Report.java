package com.prog.model;

import java.util.*;
import java.time.*;
import com.prog.util.DateRange;

public class Report {
    
    // Variables
    private DateRange dateRange;
    private LocalDate date;
    private ArrayList<PatientRecord> patientRecords;
    private ArrayList<MedicineBatch> medicineBatches;

    // Constructors
    public Report(DateRange range, ArrayList<PatientRecord> records, ArrayList<MedicineBatch> medicines){
        this.dateRange = range;
        this.patientRecords = records;
        this.medicineBatches = medicines;
    }

    public Report(LocalDate date, ArrayList<PatientRecord> records, ArrayList<MedicineBatch> medicines){
        this.date = date;
        this.patientRecords = records;
        this.medicineBatches = medicines;
    }

    // Methods
    public DateRange getDateRange() {return dateRange;}
    public LocalDate getDate() {return date;}
    public ArrayList<MedicineBatch> getMedicineBatches() {return medicineBatches;}
    public ArrayList<PatientRecord> getPatientRecords() {return patientRecords;}

}
