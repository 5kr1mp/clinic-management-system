package model;

import java.time.LocalDate;

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
    public int getBatchId() {
        return batchId;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public int getQuantity() {
        return quantity;
    }
    public int getStock() {
        return stock;
    }
    public LocalDate getStockedDate() {
        return stockedDate;
    }

    public boolean isExpired(){
        return LocalDate.now().isAfter(expiryDate);
    }
}
