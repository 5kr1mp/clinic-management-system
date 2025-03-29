package dao;

import java.util.*;
import model.IssuedMedicine;

public class IssuedMedicineDao {

    private ArrayList<IssuedMedicine> issuedMedicines = new ArrayList<>(List.of(
        new IssuedMedicine(
            1,
            1, // record ni patient id = 1
            1, // medicine id = 1 is paracetamol, check MedicineDao
            3
        ),
        new IssuedMedicine(
            2,
            2, // record ni patient id = 1
            1, // paracetamol
            2
        ),
        new IssuedMedicine(
            3,
            3, // record ni patient id = 2
            1, // paracetamol
            2
        ),
        new IssuedMedicine(
            4,
            3, // record ni patient id = 2
            2, // ibuprofen
            2
        )
    )); 

    /**
     * Adds a IssuedMedicine object into {@link #issuedMedicines}
     * 
     * @param issuedMedicine to be added to {@link #issuedMedicines}
     */
    public void add(IssuedMedicine issuedMedicine){
        issuedMedicines.add(issuedMedicine);
    }

    /**
     * Returns the list of issued medicines
     * 
     * @return {@link #issuedMedicines}
     */
    public ArrayList<IssuedMedicine> getAll(){
        return issuedMedicines;
    }

    /**
     * Retrieves the {@code IssuedMedicine} object by id
     * 
     * @param id of the {@code IssuedMedicine} to be retrieved 
     *           from {@link #issuedMedicines}
     * @return {@code IssuedMedicine} object with the id
     */
    public IssuedMedicine get(int id){
        for (IssuedMedicine medicine : issuedMedicines) {
            if (medicine.getId() == id){
                return medicine;
            }
        }
        return null;
    }

    /**
     * Updates the {@code IssuedMedicine} object from {@link #issuedMedicines}
     * with the same {@code id}
     * 
     * @param medicine the updated medicine. Its id will be checked
     *                 to find which element in the arraylist it is
     *                 supposed to replace.
     *                 
     */
    public void update(IssuedMedicine medicine){
        for (int i = 0; i < issuedMedicines.size(); i++){
            if (issuedMedicines.get(i).getId() == medicine.getId()){
                issuedMedicines.set(i, medicine);
            }
        }
    }

    /**
     * Removes a medicine object from the arraylist {@link #issuedMedicines}
     * 
     * @param id of the {@code IssuedMedicine} object to be removed.
     */
    public void delete(int id){
        for (int i = 0; i < issuedMedicines.size(); i++){
            if (issuedMedicines.get(i).getId() == id){
                issuedMedicines.remove(i);
            }
        }
    }
}
