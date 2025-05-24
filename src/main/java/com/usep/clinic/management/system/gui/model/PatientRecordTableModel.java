/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usep.clinic.management.system.gui.model;

import com.usep.clinic.management.system.model.PatientRecord;
import com.usep.clinic.management.system.util.DateTimeFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Skrimp
 */
public class PatientRecordTableModel extends AbstractTableModel {
    
    private ArrayList<PatientRecord> records = new ArrayList<>();
    private Object[] columnNames = new Object[]{
        "Description", "Diagnosis", "Date and Time"
    };
    
    @Override
    public int getRowCount() {
        return records.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        return switch(columnIndex){
            case 0 -> records.get(rowIndex).getDesc();
            case 1 -> records.get(rowIndex).getDiagnosis();
            case 2 -> DateTimeFormat.formatDateTime(records.get(rowIndex).getDateTime());
            default -> throw new ArrayIndexOutOfBoundsException(columnIndex);
        };
        
    }
    
    @Override
    public String getColumnName(int column) {
        return (String) columnNames[column];
    }
    
    public PatientRecord getRow(int index){
        return records.get(index);
    }
    
    public void add(PatientRecord record){
        records.add(record);
        fireTableDataChanged();
    }
    
    public void addAll(ArrayList<PatientRecord> records){
        this.records.addAll(records);
        fireTableDataChanged();
    }
     
    public void clear(){
        records.clear();
        fireTableDataChanged();
    }
    
    public void replaceAll(ArrayList<PatientRecord> records){
        this.records.clear();
        this.records.addAll(records);
        fireTableDataChanged();
    }
    
}
