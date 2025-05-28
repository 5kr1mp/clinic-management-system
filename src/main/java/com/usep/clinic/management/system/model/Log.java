package com.usep.clinic.management.system.model;

import java.time.*;

import com.usep.clinic.management.system.model.enums.*;

public class Log {

    private int id;
    private LocalDateTime dateTime;
    private String reason;
    
    private int patientId;
    private String name;
    private String designation;
    private Category category;
    private String contact;
    
    public Log(
        int id,
        LocalDateTime dateTime,
        String reason,
        int patientId,
        String name,
        String designation,
        Category category,
        String contact
    ){
        this.id = id;
        this.dateTime = dateTime;
        this.reason = reason;
        this.patientId = patientId;
        this.name = name;
        this.designation = designation;
        this.category = category;
        this.contact = contact;
    }

    public int getId(){return id;}
    public int getPatientId() {return patientId;}
    public Category getCategory() {return category;}
    public String getContact() {return contact;}
    public String getDesignation() {return designation;}
    public String getName() {return name;}
    public LocalDateTime getDateTime(){return dateTime;}
    public String getReason(){return reason;}
    public void setDateTime(LocalDateTime date){this.dateTime = date;}
    public void setReason(String reason){this.reason = reason;}

}