package dao;

import java.time.*;
import java.util.*;
import model.*;

/**
 * Changes:
 * <ul>
 *  <li>changed batch.getBatchId() to batch.getId() </li>
 * </ul>
 */

public class MedicineBatchDao {

    private ArrayList<MedicineBatch> batches = new ArrayList<>(List.of(
        new MedicineBatch(
            1,
            1,
            30,
            30,
            LocalDate.of(2025,5,26),
            LocalDate.now()
        ),
        new MedicineBatch(
            2,
            1,
            20,
            30,
            LocalDate.of(2025,5,25),
            LocalDate.now()
        ),
        new MedicineBatch(
            3,
            1,
            10,
            30,
            LocalDate.of(2025,3,20),
            LocalDate.of(2025,3,10)
        ),
        new MedicineBatch(
            4,
            2,
            10,
            10,
            LocalDate.of(2025,5,30),
            LocalDate.of(2025,2,1)
        )
    ));

    /**
     * Adds a medicineBatch object into {@link #batches}
     * 
     * @param batch to be added to {@link #batches}
     */
    public void add(MedicineBatch batch){
        batches.add(batch);
    }

    /**
     * Returns the list of medicine batches
     * 
     * @return {@link #batches}
     */
    public ArrayList<MedicineBatch> getAll(){
        return batches;
    }

    /**
     * Retrieves the {@code MedicineBatch} object by id
     * 
     * @param id of the {@code MedicineBatch} to be retrieved 
     *           from {@link #batches}
     * @return {@code MedicineBatch} object with the id
     */
    public MedicineBatch get(int id){
        for (MedicineBatch batch : batches) {
            if (batch.getId() == id){
                return batch;
            }
        }
        return null;
    }

    /**
     * Updates the {@code MedicineBatch} object from {@link #batches}
     * with the same {@code id}
     * 
     * @param batch the updated medicine batch object. Its id will be checked
     *                 to find which element in the arraylist it is
     *                 supposed to replace.
     *                 
     */
    public void update(MedicineBatch batch){
        for (int i = 0; i < batches.size(); i++){
            if (batches.get(i).getId() == batch.getId()){
                batches.set(i, batch);
            }
        }
    }

    /**
     * Removes a medicine batch object from the arraylist {@link #batches}
     * 
     * @param id of the {@code MedicineBatch} object to be removed.
     */
    public void delete(int id){
        for (int i = 0; i < batches.size(); i++){
            if (batches.get(i).getId() == id){
                batches.remove(i);
            }
        }
    }
}
