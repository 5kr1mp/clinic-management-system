/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.usep.clinic.management.system.gui.inventory;

import com.usep.clinic.management.system.AppContext;
import com.usep.clinic.management.system.gui.NavigationManager;
import com.usep.clinic.management.system.gui.model.MedicineTableModel;
import com.usep.clinic.management.system.model.Medicine;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author my pc
 */
public class MedicinePanel extends javax.swing.JPanel implements ActionListener{
    private MedicineTableModel medicineModel;
    private JTable medicineTable;
    private InventoryPanel invPanel;
    NavigationManager navigation;

    /**
     * Creates new form MedicinePanel
     */
    public MedicinePanel() {
        medicineModel =  new MedicineTableModel();
        initComponents();
        invPanel = new InventoryPanel();
        navigation = NavigationManager.getInstance();
        navigation.registerPanel(invPanel, "Inventory");
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        
        if(source == addMedicineButton){
            
        } else if (source == viewButton){
            int selectedRowIndex = medicineTable.getSelectedRow();
            if(selectedRowIndex >= 0){
                Medicine medicine = medicineModel.getRow(selectedRowIndex);
                invPanel.setMedicine(medicine);
            }

            navigation.show("Inventory");
        }
    }
    
    public void addMedicineDialog() {
        // Create a modal dialog
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Add New Medicine", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));

        // Form fields
        JTextField nameField = new JTextField();
        JTextField manufacturerField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField priceField = new JTextField();

        formPanel.add(new JLabel("Medicine Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Manufacturer:"));
        formPanel.add(manufacturerField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(descriptionField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                // Validate inputs
                if (nameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Medicine name is required");
                    return;
                }

                // Create new medicine
                // Medicine newMedicine = new Medicine();
                // newMedicine.setName(nameField.getText().trim());
                // newMedicine.setManufacturer(manufacturerField.getText().trim());
                // newMedicine.setDescription(descriptionField.getText().trim());

                // try {
                //     newMedicine.setPrice(Double.parseDouble(priceField.getText().trim()));
                // } catch (NumberFormatException ex) {
                //     JOptionPane.showMessageDialog(dialog, "Invalid price format");
                //     return;
                // }

                // Save to database
                // Medicine savedMedicine = AppContext.getMedicineService().createMedicine(newMedicine);

                // Add to table
                // medicineTable.add(savedMedicine);

                // Close dialog
                dialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error saving medicine: " + ex.getMessage());
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add components to dialog
        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
}
    
    
    public void searchValidation(){
        String searchMedicine = searchField.getText().trim();
        
        if (searchMedicine.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid medicine name.");
            return;
        }
        
        if(AppContext.getMedicineService().getMedicinesByName(searchMedicine).size() == 0){
            JOptionPane.showMessageDialog(this, "Medicine not found. Please try again.");
            clearSearchField();
            return;
        }
        
        ArrayList<Medicine> foundMedicines = AppContext.getMedicineService().getMedicinesByName(searchMedicine);
        
        if (foundMedicines.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Medicine not found. Please try again.");
            clearSearchField();
            return;
        } else {
            medicineModel.replaceAll(foundMedicines);

        }
        
    }
    
    public void clearSearchField(){
        searchField.setText("");
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        addMedicineButton = new javax.swing.JButton();
        viewButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Inventory");

        searchButton.setText("🔍");
        searchButton.setPreferredSize(new java.awt.Dimension(24, 24));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(449, Short.MAX_VALUE)
                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jTable1.setModel(medicineModel);
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        addMedicineButton.setText("Add Medicine");
        addMedicineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMedicineButtonActionPerformed(evt);
            }
        });

        viewButton.setText("View");
        viewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(535, Short.MAX_VALUE)
                .addComponent(addMedicineButton)
                .addGap(18, 18, 18)
                .addComponent(viewButton)
                .addGap(65, 65, 65))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addMedicineButton)
                    .addComponent(viewButton))
                .addGap(31, 31, 31))
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void viewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewButtonActionPerformed
        ArrayList<Medicine> allMedicines = AppContext.getMedicineService().getMedicines();
        medicineModel.replaceAll(allMedicines);
    }//GEN-LAST:event_viewButtonActionPerformed

    private void addMedicineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMedicineButtonActionPerformed
        addMedicineDialog();
    }//GEN-LAST:event_addMedicineButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addMedicineButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JButton viewButton;
    // End of variables declaration//GEN-END:variables
}
