package service;

import java.util.ArrayList;

import dao.IssuedMedicineDao;
import model.IssuedMedicine;


public class IssuedMedicineService {
    
    IssuedMedicineDao dao;

    public IssuedMedicineService(IssuedMedicineDao dao){
        this.dao = dao;
    }

    // methods

    public int generateId() {
        ArrayList<IssuedMedicine> issuedMedicines = dao.getAll();
        int maxId = 0;
    
        for (IssuedMedicine issuedMed : issuedMedicines) {
            if (issuedMed.getId() > maxId) {
                maxId = issuedMed.getId();
            }
        }
    
        return maxId + 1;
    }

    public void add(IssuedMedicine issuedMed) throws Exception{

        if (dao.get(issuedMed.getId()) != null) throw new Exception("Record already exists");

        add(issuedMed);
    }

    public ArrayList<IssuedMedicine> getAll(){
        return dao.getAll();
    }

    public ArrayList<IssuedMedicine> getIssuedMedicinesByRecordId(int id){

        ArrayList<IssuedMedicine> issuedMeds = new ArrayList<>();
        
        for (IssuedMedicine issuedMed : issuedMeds) {
            if (issuedMed.getRecordId() == id){
                issuedMeds.get(id);
            }
        }

        return issuedMeds;

    }

    public ArrayList<IssuedMedicine> getIssuedMedicinesByMedicineId(int id){

        ArrayList<IssuedMedicine> issuedMeds = new ArrayList<>();
        
        for (IssuedMedicine issuedMed : issuedMeds) {
            if (issuedMed.getMedicineId() == id){
                issuedMeds.get(id);
            }
        }

        return issuedMeds;

    }

    public IssuedMedicine get(int id) throws Exception{
        IssuedMedicine issuedMed = dao.get(id);
        
        if (issuedMed == null) throw new Exception("Record does not exist");

        return issuedMed;
    }
}
