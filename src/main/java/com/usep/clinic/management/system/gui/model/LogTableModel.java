package com.usep.clinic.management.system.gui.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.usep.clinic.management.system.model.Log;
import com.usep.clinic.management.system.util.DateTimeFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Skrimp
 */
public class LogTableModel extends AbstractTableModel {
    
    private ArrayList<Log> logs = new ArrayList<>();
    private Object[] columnNames = {
        "ID","Name","Designation","Purpose","Contact","Date"
    };
    
    @Override
    public int getRowCount() {
        return logs.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        return switch(columnIndex){
            case 0 -> logs.get(rowIndex).getPatientId();
            case 1 -> logs.get(rowIndex).getName();
            case 2 -> logs.get(rowIndex).getDesignation();
            case 3 -> logs.get(rowIndex).getReason();
            case 4 -> logs.get(rowIndex).getContact();
            case 5 -> DateTimeFormat.formatDateTime(logs.get(rowIndex).getDateTime());
            default -> throw new ArrayIndexOutOfBoundsException(columnIndex);
        };
    }

    @Override
    public String getColumnName(int column) {
        return (String) columnNames[column]; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public void addAll(ArrayList<Log> logs){
        this.logs.addAll(logs);
        fireTableDataChanged();
    }
    
    public void add(Log log){
        this.logs.add(log);
        fireTableDataChanged();
    }
    
    public Log getRow(int index){
        return this.logs.get(index);
    }
    
    public void clear(){
        this.logs.clear();
    }
    
    public void replaceAll(ArrayList<Log> logs){
        clear();
        addAll(logs);
    }
}
