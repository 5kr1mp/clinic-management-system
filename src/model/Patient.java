package model;

import java.util.*;
import enums.*;

public class Patient {

    private int id;
    private String name;
    private Designation designation;
    private Category category;
    private String contact;
    private ArrayList<PatientRecord> records;

    public Patient(
        int id,
        String name,
        Designation designation,
        Category category,
        String contact,
        ArrayList<PatientRecord> records
    ){
        this.id = id;
        this.name = name;
        this.designation = designation;
        this.category = category;
        this.contact = contact;
        this.records = records;
    }

    public Category getCategory() {return category;}
    public String getContact() {return contact;}
    public Designation getDesignation() {return designation;}
    public int getId() {return id;}
    public String getName() {return name;}
    public List<PatientRecord> getRecords() {return records;}

    public void setCategory(Category category) {this.category = category;}
    public void setContact(String contact) {this.contact = contact;}
    public void setDesignation(Designation designation) {this.designation = designation; }
    public void setId(int id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setRecords(ArrayList<PatientRecord> records) {this.records = records;}

}
