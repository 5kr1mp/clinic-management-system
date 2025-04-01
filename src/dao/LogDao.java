package dao;

import model.*;

import java.time.*;
import java.util.*;

public class LogDao {

    /**
     * Placeholder database
     */
    private ArrayList<Log> logs = new ArrayList<Log>(List.of(

        new Log(
            1,
            new PatientDao().get(20240143),
            LocalDateTime.now(),
            "reason 1"
        ),
        new Log(
            2,
            new PatientDao().get(20240143),
            LocalDateTime.of(2025,2,27,0,0,0),
            "reason 2"
        ),
        new Log(
            3,
            new PatientDao().get(202400045),
            LocalDateTime.of(2025,2,25,0,0,0),
            "reason 3"
        )

    ));
    

    /**
     * Inserts a {@code Log} object in {@link #logs}
     * 
     * @param log object to be inserted in the arraylist
     */
    public void add(Log log){
        logs.add(log);
    }

    /**
     * Returns the whole list of logs
     * 
     * @return {@link #logs}
     */
    public ArrayList<Log> getAll(){
        return logs;
    }

    /**
     * Returns the {@code Log} object with the id in {@link #logs}
     * 
     * @param id of the {@code Log} object
     * @return {@code Log} object with the id.
     */
    public Log get(int id){

        for (Log log : logs) {
            if (log.getId() == id){
                return log;
            }
        }

        return null;

    }

    /**
     * Deletes the {@code Log} object in {@link #logs} with the id
     * 
     * @param id of the {@code Log} object to remove
     */
    public void delete(int id){
        for (int i = 0; i < logs.size(); i++){
            if (logs.get(i).getId() == id){
                logs.remove(i);
            }
        }
    }

}
