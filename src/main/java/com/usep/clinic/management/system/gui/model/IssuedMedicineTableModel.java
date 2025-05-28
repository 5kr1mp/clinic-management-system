package com.usep.clinic.management.system.gui.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.usep.clinic.management.system.model.*;
import com.usep.clinic.management.system.service.MedicineService;

public class IssuedMedicineTableModel extends AbstractTableModel{
    private ArrayList<IssuedMedicine> issuedMeds = new ArrayList<>();
    private Object[] columnNames = {
        "Medicine","Amount"
    };
    
    @Override
    public int getRowCount() {
        return issuedMeds.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        MedicineService service = MedicineService.getInstance();
        String name = "";
        try {
            name = service.getMedicine(issuedMeds.get(rowIndex).getMedicineId()).getName();
        } catch (Exception ex){
            
        }
        
        return switch(columnIndex){
            case 0 -> name;
            case 1 -> issuedMeds.get(rowIndex).getAmount();
            default -> throw new ArrayIndexOutOfBoundsException(columnIndex);
        };
    }

    @Override
    public String getColumnName(int column) {
        return (String) columnNames[column]; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    public void addAll(ArrayList<IssuedMedicine> issuedMeds){
        this.issuedMeds.addAll(issuedMeds);
        fireTableDataChanged();
    }
    
    public void add(IssuedMedicine issuedMeds){
        this.issuedMeds.add(issuedMeds);
        fireTableDataChanged();
    }
    
    public IssuedMedicine getRow(int index){
        return this.issuedMeds.get(index);
    }

    public ArrayList<IssuedMedicine> getIssueMeds() {
        return issuedMeds;
    }
    
    public void clear(){
        this.issuedMeds.clear();
        fireTableDataChanged();
    }
    
    public void replaceAll(ArrayList<IssuedMedicine> issuedMeds){
        clear();
        addAll(issuedMeds);
    }

    public void remove(int index){
        issuedMeds.remove(index);
        fireTableDataChanged();
    }
}
