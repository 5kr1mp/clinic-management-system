package model;

import java.util.*;
import enums.*;

/**
 * Changes:
 * <ul>
 *  <li>Removed String name attribute</li>
 *  <li>Modified constructors; removed name</li>
 *  <li>Modified {@link #getName()} method</li>
 * </ul>
 */

public class Patient {

    private int id;
    private String firstname;
    private String lastname;
    private String middlename;
    private Designation designation;
    private Category category;
    private String contact;
    private ArrayList<PatientRecord> records;

    public Patient(
        int id,
        String lastname,
        String firstname,
        String middlename,
        Designation designation,
        Category category,
        String contact,
        ArrayList<PatientRecord> records
    ){
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.middlename = middlename;
        this.designation = designation;
        this.category = category;
        this.contact = contact;
        this.records = records;
    }

    public Category getCategory() {return category;}
    public String getContact() {return contact;}
    public Designation getDesignation() {return designation;}
    public int getId() {return id;}
    public String getLastname() {return lastname;}
    public String getFirstname() {return firstname;}
    public String getMiddlename() {return middlename;}
    public String getName() {return firstname + " " + middlename + " " + lastname;}
    public List<PatientRecord> getRecords() {return records;}
    public void setCategory(Category category) {this.category = category;}
    public void setContact(String contact) {this.contact = contact;}
    public void setDesignation(Designation designation) {this.designation = designation; }
    public void setId(int id) {this.id = id;}
    public void setLastname(String lastname) {this.lastname = lastname;}
    public void setFirstname(String firstname) {this.firstname = firstname;}
    public void setMiddlename(String middlename) {this.middlename = middlename;}
    public void setRecords(ArrayList<PatientRecord> records) {this.records = records;}

}
