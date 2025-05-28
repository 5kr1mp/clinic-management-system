package com.usep.clinic.management.system.gui.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.usep.clinic.management.system.model.MedicineBatch;
import com.usep.clinic.management.system.util.DateTimeFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Skrimp
 */
public class MedicineBatchTableModel extends AbstractTableModel {

    private ArrayList<MedicineBatch> batches = new ArrayList<>();
    private Object[] columnNames = {
        "ID","Stock","Quantity Ordered", "Expiry Date", "Date Stocked"
    };
    
    @Override
    public int getRowCount() {
        return batches.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        return switch(columnIndex){
            case 0 -> batches.get(rowIndex).getId();
            case 1 -> batches.get(rowIndex).getStock();
            case 2 -> batches.get(rowIndex).getQuantity();
            case 3 -> DateTimeFormat.formatDate(batches.get(rowIndex).getExpiryDate());
            case 4 -> DateTimeFormat.formatDate(batches.get(rowIndex).getStockedDate());
            default -> throw new ArrayIndexOutOfBoundsException(columnIndex);
        };
    }

    @Override
    public String getColumnName(int column) {
        return (String) columnNames[column]; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
    public void addAll(ArrayList<MedicineBatch> batches){
        this.batches.addAll(batches);
        fireTableDataChanged();
    }
    
    public void add(MedicineBatch batch){
        this.batches.add(batch);
        fireTableDataChanged();
    }
    
    public MedicineBatch getRow(int index){
        return this.batches.get(index);
    }
    
    public void clear(){
        this.batches.clear();
    }
    
    public void replaceAll(ArrayList<MedicineBatch> batches){
        clear();
        addAll(batches);
    }
    
}
