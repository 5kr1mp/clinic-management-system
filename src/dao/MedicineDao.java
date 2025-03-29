package dao;

import java.util.*;
import model.Medicine;

public class MedicineDao {
    
    /**
     * Placeholder database
     */
    private ArrayList<Medicine> medicines = new ArrayList<Medicine>(List.of(
        new Medicine(
            1,
            "Paracetamol",
            "Unilabs"
        ),
        new Medicine(
            2,
            "Ibuprofen",
            "Unilabs"
        )
    ));

    /**
     * Adds a medicine object into {@link #medicines}
     * 
     * @param medicine to be added to {@link #medicines}
     */
    public void add(Medicine medicine){
        medicines.add(medicine);
    }

    /**
     * Returns the list of medicines
     * 
     * @return {@link #medicines}
     */
    public ArrayList<Medicine> getAll(){
        return medicines;
    }

    /**
     * Retrieves the {@code Medicine} object by id
     * 
     * @param id of the {@code Medicine} to be retrieved 
     *           from {@link #medicines}
     * @return {@code Medicine} object with the id
     */
    public Medicine get(int id){
        for (Medicine medicine : medicines) {
            if (medicine.getId() == id){
                return medicine;
            }
        }
        return null;
    }

    /**
     * Updates the {@code Medicine} object from {@link #medicines}
     * with the same {@code id}
     * 
     * @param medicine the updated medicine. Its id will be checked
     *                 to find which element in the arraylist it is
     *                 supposed to replace.
     *                 
     */
    public void update(Medicine medicine){
        for (int i = 0; i < medicines.size(); i++){
            if (medicines.get(i).getId() == medicine.getId()){
                medicines.set(i, medicine);
            }
        }
    }

    /**
     * Removes a medicine object from the arraylist {@link #medicines}
     * 
     * @param id of the {@code Medicine} object to be removed.
     */
    public void delete(int id){
        for (int i = 0; i < medicines.size(); i++){
            if (medicines.get(i).getId() == id){
                medicines.remove(i);
            }
        }
    }
}
