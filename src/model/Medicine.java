package model;

import java.time.LocalDate;

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

    public int getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }
    
    public String getName() {
        return name;
    }

    // getters and setters

}
