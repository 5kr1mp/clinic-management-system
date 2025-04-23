package com.prog.model;

/**
 * <p>Added IssuedMedicine class</p>
 * <p>
 *  represents an object for issuing medicine, which contains
 *  information about what medicine and the amount were issued.
 * </p>
 */

public class IssuedMedicine {
    private int id;
    private int recordId;
    private int medicineId;
    private int amount;
    private Medicine medicine;

    public IssuedMedicine(
        int id,
        int recordId,
        int medicineId,
        int amount
    ){
        this.id = id;
        this.recordId = recordId;
        this.medicineId = medicineId;
        this.amount = amount;  
    }

    public IssuedMedicine(
        int id,
        int recordId,
        Medicine medicine,
        int amount
    ){
        this.id = id;
        this.recordId = recordId;
        this.medicineId = medicine.getId();
        this.medicine = medicine;
        this.amount = amount;
    }

    public int getAmount() {return amount;}
    public Medicine getMedicine() {return medicine;}
    public String getMedicineName(){return medicine.getName();}
    public String getMedicineManufacturer(){return medicine.getManufacturer();}
    public int getId() {return id;}
    public int getMedicineId() {return medicineId;}
    public int getRecordId() {return recordId;}
}
