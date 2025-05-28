package com.usep.clinic.management.system.model;

public class Medicine {

    private int id;
    private String name;
    private String manufacturer;

    // constructor
    public Medicine(int id, String name, String manufacturer){
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public Medicine(int id, String name){
        this.id = id;
        this.name = name;
        this.manufacturer = "Unknown";
    }

    public int getId() {return id;}
    public String getManufacturer() {return manufacturer;}
    public String getName() {return name;}
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public void setName(String name) {
        this.name = name;
    }

}
