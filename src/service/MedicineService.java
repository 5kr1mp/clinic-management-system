package service;

import java.time.LocalDate;
import java.util.ArrayList;
import model.*;
import dao.*;

public class MedicineService {

    private MedicineDao dao;
    private MedicineBatchDao batchDao;
    private MedicineBatch medicineBatch;

    public MedicineService(MedicineDao dao){
        this.dao = dao;
    }

    public void addMedicine(Medicine medicine) throws Exception {
        
        for (Medicine med : dao.getAll()){
            if (med.getId() == medicine.getId()){
                throw new IllegalArgumentException("Duplicate medicine.");
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
            if (medicineBatch.getExpiryDate().isBefore(LocalDate.now())){
                expiredMedicines.add(medicine);
            }
        }

        return expiredMedicines;
    }

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
            if (medicineBatch.getBatchId() != med.getId()){
                throw new IllegalArgumentException("Medicine ID not found.");
            }
        }

        dao.delete(id);
        System.out.println("Medicine deleted successfully!");

    }

    public int getTotalStock(int medicineId){
        int totalStock = 0;

        for (MedicineBatch batch : batchDao.getAll()){
            if (batch.getMedicineId() == medicineId && !batch.isExpired()){
                totalStock += batch.getStock();
            }
        }

        return totalStock;
    }
}
