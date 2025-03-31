package service;

import java.util.ArrayList;
import model.*;
import dao.*;

/**
 * Changes:
 * <ul>
 *  <li>Changed getExpiredMedicines() to {@link #getExpiredMedicineBatches()}</li>
 *  <li>Change algo of {@link #updateMedicine(Medicine)}</li>
 *  <li>Removed medicineBatch instance variable</li>
 *  <li>Added 3 methods:</li>
 *  <li><ul>
 *          <li>{@link #getMedicine(int)}</li>
 *          <li>{@link #getBatch(int)}</li>
 *          <li>{@link #decreaseStock(int, int)}</li>
 *  </ul></li>
 * </ul>
 * Notes:
 * <p>paki tiwas sa {@link #decreaseStock(int, int)} hehe </p>
 */
public class MedicineService {

    private MedicineDao medicineDao;
    private MedicineBatchDao batchDao;

    public MedicineService(MedicineDao dao, MedicineBatchDao batchDao){
        this.medicineDao = dao;
        this.batchDao = batchDao;
    }

    public void addMedicine(Medicine medicine) throws Exception {
        
        for (Medicine med : medicineDao.getAll()){
            if (med.getId() == medicine.getId()){
                throw new IllegalArgumentException("Duplicate medicine.");
            }
        }

        medicineDao.add(medicine);
    }

    public ArrayList<Medicine> getMedicines(){
        return medicineDao.getAll();
    }

    public ArrayList<MedicineBatch> getExpiredMedicineBatches(){
        ArrayList<MedicineBatch> expiredMedicineBatches = new ArrayList<>();
        
        // for (Medicine medicine : medicineDao.getAll()){
        //     if (medicineBatch.getExpiryDate().isBefore(LocalDate.now())){
        //         expiredMedicines.add(medicine);
        //     }
        // }

        // iterate through batches
        for (MedicineBatch batch : batchDao.getAll()){
            // if batch is expired
            if (batch.isExpired()){
                expiredMedicineBatches.add(batch);
            }
        }

        return expiredMedicineBatches;
    }

    public ArrayList<MedicineBatch> getNonExpiredMedicineBatches(){
        ArrayList<MedicineBatch> nonExpiredMedicineBatches = new ArrayList<>();

        // iterate through batches
        for (MedicineBatch batch : batchDao.getAll()){
            // if batch is expired
            if (!batch.isExpired()){
                nonExpiredMedicineBatches.add(batch);
            }
        }

        return nonExpiredMedicineBatches;
    }

    public ArrayList<MedicineBatch> getMedicineBatches(){
        return batchDao.getAll();
    }

    public void updateMedicine(Medicine medicine) throws Exception {

        // Mu error ni nacode if ang first na medicine sa database kay dili mumatch sa id
        // Dapat i-iterate through the whole database muna bago mag throw ug exception
        //
        // for (Medicine med : medicineDao.getAll()){
        //     if (med.getId() != medicine.getId()){
        //         throw new IllegalArgumentException("Medicine ID not found.");
        //     }
        // }

        // Retrieves medicine from the database. If none (null) is retrieved,
        // mag throw ug exception
        if ( medicineDao.get(medicine.getId()) == null) throw new Exception("Medicine not found");

        medicineDao.update(medicine);
    
    }

    public void addBatch(MedicineBatch batch) throws Exception {

        for (MedicineBatch add : batchDao.getAll()){
            if (add.getId() == batch.getId()){
                throw new IllegalArgumentException("Duplicate batch.");
            }
        }

        batchDao.add(batch);
    }
    
    public void updateBatch(MedicineBatch batch) throws Exception{

        if (batchDao.get(batch.getId()) == null) throw new Exception("Batch not found");

        batchDao.update(batch);

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

    /**
     * Subtracts a MedicineBatch object's stock by an amount.
     * 
     * @param batchId id of batch of which to decrease stock.
     * @param amount amount to subtract
     */
    public void decreaseStock(int batchId, int amount) throws Exception{
        MedicineBatch batch = batchDao.get(batchId);

        // Add code diri and exception handling
        if(batch == null){
            throw new Exception("Batch ID not found");
        }
        if(amount < 0){
            throw new Exception("It must not be negative");
        }
        if(amount > batch.getStock()){
            throw new Exception("not bigger daw sa current stock");
        }
        batch.decreaseStock(amount);

        batchDao.update(batch);
    }

    public MedicineBatch getBatch(int id) throws Exception{
        MedicineBatch batch = batchDao.get(id);

        // if not found
        if (batch == null) throw new Exception("Batch not found");

        return batch;
    }

    public Medicine getMedicine(int id) throws Exception{
        Medicine medicine = medicineDao.get(id);

        // if not found
        if (medicine == null) throw new Exception("Medicine not found");

        return medicine;
    }

    public int generateMedicineId() {
        ArrayList<Medicine> medicines = medicineDao.getAll();
        int maxId = 0;

        for (Medicine medicine: medicines) {
            if (medicine.getId() > maxId) {
                maxId = medicine.getId();
            }
        }

        return maxId + 1;
    }

    public int generateBatchId() {
        ArrayList<MedicineBatch> batches = batchDao.getAll();
        int maxId = 0;

        for (MedicineBatch batch : batches) {
            if (batch.getId() > maxId) {
                maxId = batch.getId();
            }
        }

        return maxId + 1;
    }








    // public void deleteMedicine(int id) throws Exception {

    //     for (Medicine med : medicineDao.getAll()){
    //         if (medicineBatch.getId() != med.getId()){
    //             throw new IllegalArgumentException("Medicine ID not found.");
    //         }
    //     }

    //     if (medicineDao.get(id) == null){
    //         throw new Exception("Medicine not found");
    //     }

    //     medicineDao.delete(id);
    //     System.out.println("Medicine deleted successfully!");

    // }
}
