/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usep.clinic.management.system.gui.model;

import com.usep.clinic.management.system.model.Patient;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Skrimp
 */
public class PatientTableModel extends AbstractTableModel {
    
    private ArrayList<Patient> patients = new ArrayList<>();
    private Object[] columnNames = new Object[] {
        "ID", "Last Name","First Name", "M.I.", "Designation","Contact"
    };
    
    @Override
    public int getRowCount() {
        return patients.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex){
            case 0 -> patients.get(rowIndex).getId();
            case 1 -> patients.get(rowIndex).getLastname();
            case 2 -> patients.get(rowIndex).getFirstname();
            case 3 -> patients.get(rowIndex).getMiddlename().charAt(0);
            case 4 -> patients.get(rowIndex).getDesignation();
            case 5 -> patients.get(rowIndex).getContact();
            default -> throw new ArrayIndexOutOfBoundsException(columnIndex);
        };
        
    }

    @Override
    public String getColumnName(int column) {
        return (String) columnNames[column];
    }
    
    public void add(Patient patient){
        patients.add(patient);
        fireTableRowsInserted(getRowCount() - 1 , getRowCount() - 1);
    }
    
    public void addAll(ArrayList<Patient> patients){
        this.patients.addAll(patients);
        fireTableDataChanged();
    }
    
    public Patient getRow(int index){
        return patients.get(index);
    }
    
    public void clear(){
        patients.clear();
        fireTableDataChanged();
    }
    
    public void replaceAll(ArrayList<Patient> patients){
        this.patients.clear();
        this.patients.addAll(patients);
        fireTableDataChanged();
    }
    
}
