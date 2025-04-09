package model;

import enums.*;
import java.time.*;

public class Log {

    private int id;
    private Patient patient;
    private LocalDateTime date;
    private String reason;

    public Log(int id, Patient patient, LocalDateTime date, String reason) {
        this.id = id;
        this.patient = patient;
        setDate(date);
        setReason(reason);
    }

    public String getName() {return patient.getFullName();}
    public int getId(){return id;}
    public int getPatientId() { return patient.getId();}
    public String getDesignation() {return patient.getDesignation();}
    public Category getCategory() {return patient.getCategory();}
    public String getContact() {return patient.getContact();}
    
    public LocalDateTime getDate(){return date;}
    public String getReason(){return reason;}
    public void setDate(LocalDateTime date){this.date = date;}
    public void setReason(String reason){this.reason = reason;}

}
