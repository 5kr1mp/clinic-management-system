/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.usep.clinic.management.system.gui.model;

import com.usep.clinic.management.system.model.Medicine;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Skrimp
 */
public class MedicineTableModel extends AbstractTableModel {
    
    private ArrayList<Medicine> medicines = new ArrayList<>();
    private Object[] columnNames = {
        "ID","Name","Manufacturer"
    };
    
    @Override
    public int getRowCount() {
        return medicines.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        return switch (columnIndex){
            case 0 -> medicines.get(rowIndex).getId();
            case 1 -> medicines.get(rowIndex).getName();
            case 2 -> medicines.get(rowIndex).getManufacturer();
            default -> throw new ArrayIndexOutOfBoundsException(columnIndex);
        };
        
    }
    
    @Override
    public String getColumnName(int column) {
        return (String) columnNames[column];
    }
    
    public Medicine getRow(int index){
        return this.medicines.get(index);
    }
    
    public void addAll(ArrayList<Medicine> medicines){
        this.medicines.addAll(medicines);
        fireTableDataChanged();
    }
    
    public void add(Medicine medicine){
        this.medicines.add(medicine);
        fireTableDataChanged();
    }
    
    public void clear(){
        this.medicines.clear();
        fireTableDataChanged();
    }
    
    public void replaceAll(ArrayList<Medicine> medicines){
        clear();
        addAll(medicines);
    }
    
}
