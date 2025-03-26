package service;

import java.time.LocalDate;
import java.util.ArrayList;
import model.*;
import dao.*;

public class MedicineService {

    private MedicineDao dao;
    private MedicineBatchDao batchDao;

    public MedicineService(MedicineDao dao){
        this.dao = dao;
    }

    public void addMedicine(Medicine medicine) throws Exception {
        
        for (Medicine med : dao.getAll()){
            if (med.getId() == medicine.getId()){
                throw new IllegalArgumentException("Duplicate medicine");
            }
        }

        dao.add(medicine);
    }

    public ArrayList<Medicine> getMedicines(){
        return dao.getAll();
    }

    public ArrayList<Medicine> getExpiredMedicines(){
        ArrayList<Medicine> expiredMedicines = new ArrayList<>();
        
        for (Medicine medicine : dao.getAll()){
            if (medicine.getExpiryDate().isBefore(LocalDate.now())){
                expiredMedicine.add(medicine);
            }
        }

        return expiredMedicines;
    }

    public Medicine(int id){
        // loop if naaay same or not sumth like dat ambot sean ikaw na bahala
        return id;
    }

    // hehe

    public void updateMedicine(Medicine medicine) throws Exception {
        for (Medicine med : dao.getAll()){
            if (med.getId() != medicine.getId()){
                throw new IllegalArgumentException("Medicine ID not found.");
            
            }
        }

        dao.update(medicine);
    
    }

    public void deleteMedicine(int id) throws Exception {
        for (Medicine med : dao.getAll()){
            if (med.getId() != medicine.getId()){
                throw new IllegalArgumentException("Medicine ID not found.");
            }
        }

        dao.delete(id);
        System.out.println("Medicine deleted successfully!");

    }

    public void restock(Medicine medicine, int amount) throws Exception{

        if (dao.get(medicine.getId()) == null) throw new Exception("Medicine not found");

        if (amount < 1) throw new IllegalArgumentException("amount cannot be negative");

        medicine.restock(amount);

    }

    public void decreaseStock(int id, int amount) throws Exception{

        Medicine medicine = dao.get(id);

        if (medicine == null) throw new Exception("Medicine not found");
        if (amount < 1) throw new IllegalArgumentException("Amount should be 1 and above");
        if (medicine.getStock() < amount) throw new IllegalArgumentException("Insufficent stock");

        for (Medicine med : dao.getAll()){
            if (med.getId() != medicine.getId()){
                throw new IllegalArgumentException("Medicine ID not found.");
            }
        }

        medicine.get(index).decreaseStock -= amount;

    }
}
