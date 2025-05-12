package com.prog.service;

import java.util.*;
import com.prog.model.*;
import com.prog.dao.*;

public class MedicineService {
    private static MedicineService instance;

    private MedicineDao medicineDao;
    private MedicineBatchDao batchDao;
    private IssuedMedicineDao issuedMedicineDao;

    public static MedicineService getInstance(){
        if (instance == null){
            instance = new MedicineService(new MedicineDao(), new MedicineBatchDao(), new IssuedMedicineDao());
        }

        return instance;
    }

    private MedicineService(MedicineDao dao, MedicineBatchDao batchDao, IssuedMedicineDao issuedMedicineDao){
        this.medicineDao = dao;
        this.batchDao = batchDao;
        this.issuedMedicineDao = issuedMedicineDao;
    }

    // methods
    public int generateIssuedMedicineId() {
        ArrayList<IssuedMedicine> issuedMedicines = issuedMedicineDao.getAll();
        int maxId = 0;
    
        for (IssuedMedicine issuedMed : issuedMedicines) {
            if (issuedMed.getId() > maxId) {
                maxId = issuedMed.getId();
            }
        }
    
        return maxId + 1;
    }

    public int generateMedicineId() {
        int maxId = 0;
        for (Medicine med : medicineDao.getAll()){
            if (med.getId() > maxId){
                maxId = med.getId();
            }
        }

        return maxId + 1;
    }

    public int generateBatchId() {
        int maxId = 0;
        for (MedicineBatch batch : batchDao.getAll()){
            if (batch.getId() > maxId){
                maxId = batch.getId();
            }
        }

        return maxId + 1;
    }

    public void issueMedicine(int medicineId, int amount) throws Exception{

        int remainingAmountNeeded = amount;

        for (MedicineBatch batch : getMedicineBatchesByMedicineId(medicineId,false)) {

            // if batch stock is less than amount, huruton ang stock tapos continue sa next non expired batch
            if (batch.getStock() < remainingAmountNeeded){

                int remainingStock = batch.getStock();
                remainingAmountNeeded -= remainingStock;
                decreaseStock(batch.getId(), remainingStock);
                continue;
            }

            // batch stock is greater than amount, diri nalang mukuha
            if (batch.getStock() >= remainingAmountNeeded){
                decreaseStock(batch.getId(), remainingAmountNeeded);
                remainingAmountNeeded = 0;
                break;
            }

            // if way stock 
            if(batch.getStock() <= 0){
                continue;
            }  
        }

        if (remainingAmountNeeded > 0){
            throw new InsufficientStockException();
        }

    }
    // CREATE

    public void addMedicine(Medicine medicine) throws Exception {
        
        for (Medicine med : medicineDao.getAll()){
            if (med.getId() == medicine.getId()){
                throw new DuplicateEntityException("Duplicate medicine.");
            }
        }

        medicineDao.add(medicine);
    }
    
    public void addBatch(MedicineBatch batch) throws Exception {

        for (MedicineBatch add : batchDao.getAll()){
            if (add.getId() == batch.getId()){
                throw new DuplicateEntityException("Duplicate batch.");
            }
        }

        batchDao.add(batch);
    }

    public void add(IssuedMedicine issuedMed) throws Exception{

        if (issuedMedicineDao.get(issuedMed.getId()) != null) throw new DuplicateEntityException("Record already exists");

        issuedMedicineDao.add(issuedMed);
    }

    // RETRIEVE
    public ArrayList<Medicine> getMedicines(){
        return medicineDao.getAll();
    }

    public ArrayList<MedicineBatch> getExpiredMedicineBatches(){
        ArrayList<MedicineBatch> expiredMedicineBatches = new ArrayList<>();

        // iterate through batches
        for (MedicineBatch batch : batchDao.getAll()){
            // if batch is expired
            if (batch.isExpired()){
                expiredMedicineBatches.add(batch);
            }
        }

        return expiredMedicineBatches;
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

    public Medicine getMedicineByName(String name){

        for (Medicine med : medicineDao.getAll()) {
            
            if (med.getName().equalsIgnoreCase(name.trim())){
                return med;
            }

        }

        return null;

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

        // sort from oldest to earliest
        nonExpiredMedicineBatches.sort((a,b) -> a.getExpiryDate().compareTo(b.getExpiryDate()));
        
        return nonExpiredMedicineBatches;
    }

    public ArrayList<IssuedMedicine> getIssuedMedicines(){
        return issuedMedicineDao.getAll();
    }

    public ArrayList<IssuedMedicine> getIssuedMedicinesByRecordId(int id){

        ArrayList<IssuedMedicine> issuedMeds = new ArrayList<>();
        
        for (IssuedMedicine issuedMed : issuedMedicineDao.getAll()) {
            if (issuedMed.getRecordId() == id){
                issuedMeds.add(issuedMed);
            }
        }

        return issuedMeds;

    }

    public ArrayList<IssuedMedicine> getIssuedMedicinesByMedicineId(int id){

        ArrayList<IssuedMedicine> issuedMeds = new ArrayList<>();
        
        for (IssuedMedicine issuedMed : issuedMedicineDao.getAll()) {
            if (issuedMed.getMedicineId() == id){
                issuedMeds.add(issuedMed);
            }
        }

        return issuedMeds;

    }

    public IssuedMedicine get(int id) throws Exception{
        IssuedMedicine issuedMed = issuedMedicineDao.get(id);
        
        if (issuedMed == null) throw new Exception("Record does not exist");

        return issuedMed;
    }

    public ArrayList<MedicineBatch> getMedicineBatchesByMedicineId(int medId, boolean includeExpired){
        ArrayList<MedicineBatch> batches = new ArrayList<>();

        // iterate through batches
        for (MedicineBatch batch : batchDao.getAll()){
            boolean condition = includeExpired ? 
                                    batch.getMedicineId() == medId && batch.getStock() > 0 :
                                    batch.getMedicineId() == medId && !batch.isExpired() && batch.getStock() > 0;
            
            if (condition){
                batches.add(batch);
            }
        }

        // sort from oldest to earliest
        batches.sort((a,b) -> a.getExpiryDate().compareTo(b.getExpiryDate()));

        return batches;
    }

    public ArrayList<MedicineBatch> getMedicineBatches(){
        return batchDao.getAll();
    }

    // UPDATE

    /**
     * Subtracts a MedicineBatch object's stock by an amount.
     * 
     * @param batchId id of batch of which to decrease stock.
     * @param amount amount to subtract
     */
    public void decreaseStock(int batchId, int amount) throws EntityNotFoundException, 
                                                              InvalidAmountException, 
                                                              InsufficientStockException{
        MedicineBatch batch = batchDao.get(batchId);

        if(batch == null) throw new EntityNotFoundException(String.format("Batch with id %d not found.",batchId));
        if(amount < 0) throw new InvalidAmountException();
        if(amount > batch.getStock()) throw new InsufficientStockException();

        batch.decreaseStock(amount);

        batchDao.update(batch);
    }

    public void updateBatch(MedicineBatch batch) throws Exception{

        if (batchDao.get(batch.getId()) == null) throw new Exception("Batch not found");

        batchDao.update(batch);

    }

    public void updateMedicine(Medicine medicine) throws Exception {

        if ( medicineDao.get(medicine.getId()) == null) throw new Exception("Medicine not found");

        medicineDao.update(medicine);
    
    }

}
