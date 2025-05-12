package com.prog.model;

import java.time.LocalDate;

/**
 * Changes:
 * <ul>
 *  <li>added {@link #isExpired()} method</li>
 *  <li>added {@link #decreaseStock(int)} method</li> 
 * </ul>
 */

public class MedicineBatch {
    
    private int batchId;
    private int medicineId;
    private int stock; // stock remaining
    private int quantity; // quantity ordered
    private LocalDate expiryDate;
    private LocalDate stockedDate;

    // constructor
    public MedicineBatch(
        int batchId,
        int medicineId,
        int stock,
        int quantity,
        LocalDate expiryDate,
        LocalDate stockedDate
    ){
        this.batchId = batchId;
        this.medicineId = medicineId;
        this.stock = stock;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.stockedDate = stockedDate;
    }

    // getters & setters
    public int getId() {return batchId;}
    public LocalDate getExpiryDate() {return expiryDate;}
    public int getMedicineId() {return medicineId;}
    public int getQuantity() {return quantity;}
    public int getStock() {return stock;}
    public LocalDate getStockedDate() {return stockedDate;}

    // methods
    public boolean isExpired(){
        return LocalDate.now().isAfter(expiryDate);
    }

    public void decreaseStock(int amount){
        stock -= amount;
    }
}
