package com.prog.service;

import com.prog.dao.LogDao;
import com.prog.model.Log;
import java.time.LocalDate;
import java.util.ArrayList;

public class LogService {
    

    private static LogService instance;
    private LogDao dao;

    public static LogService getInstance(){
        if (instance == null){
            instance = new LogService(new LogDao());
        }

        return instance;
    }

    private LogService(LogDao dao) {
        this.dao = dao;
    }

    public void add(Log log) throws Exception {
        if (log == null) {
            throw new Exception("Please provide a valid log entry.");
        }
        
        if (dao.get(log.getId()) != null) {
            throw new Exception("A log with ID " + log.getId() + " already exists.");
        }
        
        dao.add(log);
    }

    public int generateId() {
        ArrayList<Log> logs = dao.getAll();
        int maxId = 0;
    
        for (Log log : logs) {
            if (log.getId() > maxId) {
                maxId = log.getId();
            }
        }
    
        return maxId + 1;
    

    }

    public ArrayList<Log> getLogsByPatientId(int id){
        ArrayList<Log> filteredLogs = new ArrayList<>();
        for (Log log : dao.getAll()) {
            if (log.getPatientId() == id) { 
                filteredLogs.add(log);
            }
        }
        return filteredLogs;
    }
    
    public Log getLog(int id) throws Exception {
        Log log = dao.get(id);
        if (log == null) {
            throw new Exception("No log found with ID " + id + ".");
        }
        return log;
    }

    public ArrayList<Log> getLogs() throws Exception {
        ArrayList<Log> logs = dao.getAll();
        return logs;
    }

    public ArrayList<Log> getLogsByName(String name) throws Exception {
        if (name.isEmpty()) {
            throw new Exception("Please provide a valid name.");
        }
        
        ArrayList<Log> logsByName = new ArrayList<>();
        for (Log log : dao.getAll()) {
            if (log.getName().equalsIgnoreCase(name)) { 
                logsByName.add(log);
            }
        }
        
        return logsByName;
    }

    public ArrayList<Log> getLogsByDate(LocalDate date) throws Exception {
        if (date == null) {
            throw new Exception("Please provide a valid date.");
        }
        
        ArrayList<Log> filteredLogs = new ArrayList<>();
        for (Log log : dao.getAll()) {
            if (log.getDate().toLocalDate().isEqual(date)) {
                filteredLogs.add(log);
            }
        }
        
        return filteredLogs;
    }

    public ArrayList<Log> getLogsThisWeek() throws Exception {
        ArrayList<Log> filteredLogs = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
        
        for (Log log : dao.getAll()) {
            LocalDate logDate = log.getDate().toLocalDate();
            if (!logDate.isBefore(startOfWeek) && !logDate.isAfter(today)) {
                filteredLogs.add(log);
            }
        }
        
        return filteredLogs;
    }

    public void delete(int id) throws Exception {
        Log log = dao.get(id);
        if (log == null) {
            throw new Exception("Cannot delete. No log found with ID " + id + ".");
        }
        
        dao.delete(id);
    }
}
