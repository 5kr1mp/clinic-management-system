package service;

import dao.LogDao;
import model.Log;
import java.time.LocalDate;
import java.util.ArrayList;

public class LogService {
    
    private LogDao dao;

    public LogService(LogDao dao) {
        this.dao = dao;
    }

    public void add(Log log) throws Exception {
        
        if (log == null) {
            throw new IllegalArgumentException("Please provide a valid log entry.");
        }
        
        if (dao.get(log.getId()) != null) {
            throw new Exception("A log with ID " + log.getId() + " already exists.");
        }
        
        dao.add(log);
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

        if (logs.isEmpty()) {
            throw new Exception("There are no logs available at the moment.");
        }
        
        return logs;
    }

    public ArrayList<Log> getLogsByName(String name) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Please provide a valid name.");
        }
        
        ArrayList<Log> filteredLogs = new ArrayList<>();
        for (Log log : dao.getAll()) {
            if (log.getName().equalsIgnoreCase(name)) {
                filteredLogs.add(log);
            }
        }
        
        if (filteredLogs.isEmpty()) {
            throw new Exception("No logs found for the name: " + name);
        }
        return filteredLogs;
    }

    public ArrayList<Log> getLogs(LocalDate date) throws Exception {
        if (date == null) {
            throw new IllegalArgumentException("Please provide a valid date.");
        }
        
        ArrayList<Log> filteredLogs = new ArrayList<>();
        for (Log log : dao.getAll()) {
            if (log.getDate().toLocalDate().equals(date)) {
                filteredLogs.add(log);
            }
        }
        
        if (filteredLogs.isEmpty()) {
            throw new Exception("No logs found for the date: " + date);
        }
        return filteredLogs;
    }

    public ArrayList<Log> getLogsByDate(LocalDate date) throws Exception {
        if (date == null) {
            throw new IllegalArgumentException("Please provide a valid date.");
        }
        
        ArrayList<Log> filteredLogs = new ArrayList<>();
        for (Log log : dao.getAll()) {
            if (log.getDate().toLocalDate().isEqual(date)) {
                filteredLogs.add(log);
            }
        }
        
        if (filteredLogs.isEmpty()) {
            throw new Exception("No logs found for the date: " + date);
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
        
        if (filteredLogs.isEmpty()) {
            throw new Exception("No logs recorded this week.");
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
}
