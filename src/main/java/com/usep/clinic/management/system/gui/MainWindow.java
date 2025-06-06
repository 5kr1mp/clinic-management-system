/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.usep.clinic.management.system.gui;

import com.formdev.flatlaf.FlatLightLaf;
import com.usep.clinic.management.system.AppContext;
import com.usep.clinic.management.system.gui.inventory.MedicinePanel;
import com.usep.clinic.management.system.gui.logbook.LogBookPanel;
import com.usep.clinic.management.system.gui.patient.PatientPanel;
import com.usep.clinic.management.system.gui.patient.RecordsPanel;
import com.usep.clinic.management.system.gui.reports.ReportsPanel;
import com.usep.clinic.management.system.model.User;
import com.usep.clinic.management.system.model.enums.Role;

/**
 *
 * @author Skrimp
 */
public class MainWindow extends javax.swing.JFrame {

    NavigationManager navigationManager;

    PatientPanel patientPanel;
    ReportsPanel reportsPanel;
    LogBookPanel logBookPanel;
    MedicinePanel medicinePanel;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        
        // setup navigation manager
        navigationManager = NavigationManager.getInstance();
        mainContent.setLayout(navigationManager);
        navigationManager.setContainer(mainContent);

        patientPanel = new PatientPanel();
        reportsPanel = new ReportsPanel();
        medicinePanel = new MedicinePanel();
        logBookPanel = new LogBookPanel();
        
        // register panels
        navigationManager.registerPanel(logBookPanel, "Logbook");
        navigationManager.registerPanel(medicinePanel, "Medicines");
        navigationManager.registerPanel(patientPanel, "Patients");
        navigationManager.registerPanel(reportsPanel, "Reports");
                
        authorizeUser();
        setVisible(true);
    }

    private void authorizeUser(){
        User user = AppContext.getCurrentUser();
        
        switch(user.getRole()){
            
            case Role.NURSE -> { 
                patientRecordsBtn.setVisible(true);
                inventoryBtn.setVisible(true);
                logbookBtn.setVisible(true);
                reportsBtn.setVisible(false);
                navigationManager.show("Patients");
            }
            case Role.DOCTOR -> {
                patientRecordsBtn.setVisible(true);
                inventoryBtn.setVisible(true);
                logbookBtn.setVisible(false);
                reportsBtn.setVisible(false);
                navigationManager.show("Patients");
            }
            case Role.PATIENT -> {
                patientRecordsBtn.setVisible(false);
                inventoryBtn.setVisible(false);
                logbookBtn.setVisible(true);
                reportsBtn.setVisible(false);
                navigationManager.show("Logbook");
            }
            case Role.ADMIN -> {
                patientRecordsBtn.setVisible(true);
                inventoryBtn.setVisible(true);
                logbookBtn.setVisible(true);
                reportsBtn.setVisible(true);
                navigationManager.show("Reports");
            }
        }
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        sidebarControl = new javax.swing.JButton();
        title = new javax.swing.JLabel();
        rightPanel = new javax.swing.JPanel();
        logoutBtn = new javax.swing.JButton();
        sidebar = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 8));
        patientRecordsBtn = new javax.swing.JButton();
        inventoryBtn = new javax.swing.JButton();
        logbookBtn = new javax.swing.JButton();
        reportsBtn = new javax.swing.JButton();
        mainContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(AppContext.APP_NAME);
        setLocation(new java.awt.Point(0, 0));
        setMinimumSize(new java.awt.Dimension(1012, 720));
        setPreferredSize(new java.awt.Dimension(1012, 720));
        setSize(new java.awt.Dimension(1012, 720));
        getContentPane().setLayout(new java.awt.BorderLayout(2, 0));

        header.setBackground(new java.awt.Color(143, 186, 229));
        header.setMinimumSize(new java.awt.Dimension(237, 50));
        header.setPreferredSize(new java.awt.Dimension(612, 50));
        header.setRequestFocusEnabled(false);
        header.setLayout(new java.awt.BorderLayout());

        leftPanel.setBackground(new java.awt.Color(143, 186, 229));
        leftPanel.setOpaque(false);
        leftPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 5));

        sidebarControl.setBackground(new java.awt.Color(143, 186, 229));
        sidebarControl.setIcon(AppContext.BAR_ICON);
        sidebarControl.setBorderPainted(false);
        sidebarControl.setFocusPainted(false);
        sidebarControl.setPreferredSize(new java.awt.Dimension(40, 40));
        sidebarControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sidebarControlActionPerformed(evt);
            }
        });
        leftPanel.add(sidebarControl);

        title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setText("Clinic Management System");
        leftPanel.add(title);

        header.add(leftPanel, java.awt.BorderLayout.CENTER);

        rightPanel.setBackground(new java.awt.Color(143, 186, 229));
        rightPanel.setMinimumSize(new java.awt.Dimension(116, 50));
        rightPanel.setOpaque(false);
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 20, 5);
        flowLayout1.setAlignOnBaseline(true);
        rightPanel.setLayout(flowLayout1);

        logoutBtn.setBackground(new java.awt.Color(143, 186, 229));
        logoutBtn.setIcon(AppContext.LOGOUT_ICON);
        logoutBtn.setAlignmentY(0.0F);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setPreferredSize(new java.awt.Dimension(40, 40));
        logoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutBtnActionPerformed(evt);
            }
        });
        rightPanel.add(logoutBtn);

        header.add(rightPanel, java.awt.BorderLayout.EAST);

        getContentPane().add(header, java.awt.BorderLayout.NORTH);

        sidebar.setBackground(new java.awt.Color(255, 255, 255));
        sidebar.setMaximumSize(new java.awt.Dimension(200, 81));
        sidebar.setMinimumSize(new java.awt.Dimension(200, 81));
        sidebar.setPreferredSize(new java.awt.Dimension(200, 394));
        sidebar.setVerifyInputWhenFocusTarget(false);
        sidebar.setLayout(new javax.swing.BoxLayout(sidebar, javax.swing.BoxLayout.Y_AXIS));
        sidebar.add(filler1);

        patientRecordsBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        patientRecordsBtn.setText("Patient Records");
        patientRecordsBtn.setAlignmentX(0.5F);
        patientRecordsBtn.setBorderPainted(false);
        patientRecordsBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        patientRecordsBtn.setMaximumSize(new java.awt.Dimension(500, 50));
        patientRecordsBtn.setPreferredSize(new java.awt.Dimension(79, 50));
        patientRecordsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientRecordsBtnActionPerformed(evt);
            }
        });
        sidebar.add(patientRecordsBtn);

        inventoryBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        inventoryBtn.setText("Inventory");
        inventoryBtn.setAlignmentX(0.5F);
        inventoryBtn.setBorderPainted(false);
        inventoryBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        inventoryBtn.setMaximumSize(new java.awt.Dimension(500, 50));
        inventoryBtn.setPreferredSize(new java.awt.Dimension(79, 50));
        inventoryBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inventoryBtnActionPerformed(evt);
            }
        });
        sidebar.add(inventoryBtn);

        logbookBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        logbookBtn.setText("Logbook");
        logbookBtn.setAlignmentX(0.5F);
        logbookBtn.setBorderPainted(false);
        logbookBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        logbookBtn.setMaximumSize(new java.awt.Dimension(500, 50));
        logbookBtn.setPreferredSize(new java.awt.Dimension(79, 50));
        logbookBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logbookBtnActionPerformed(evt);
            }
        });
        sidebar.add(logbookBtn);

        reportsBtn.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        reportsBtn.setText("Reports");
        reportsBtn.setAlignmentX(0.5F);
        reportsBtn.setBorderPainted(false);
        reportsBtn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        reportsBtn.setMaximumSize(new java.awt.Dimension(500, 50));
        reportsBtn.setPreferredSize(new java.awt.Dimension(79, 50));
        reportsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportsBtnActionPerformed(evt);
            }
        });
        sidebar.add(reportsBtn);

        getContentPane().add(sidebar, java.awt.BorderLayout.WEST);

        mainContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        mainContent.setLayout(new java.awt.CardLayout());
        getContentPane().add(mainContent, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sidebarControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sidebarControlActionPerformed
        // TODO add your handling code here:
        sidebar.setVisible(!sidebar.isVisible());
    }//GEN-LAST:event_sidebarControlActionPerformed

    private void logoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutBtnActionPerformed
        // TODO add your handling code here:
        dispose();
        new LoginWindow();
    }//GEN-LAST:event_logoutBtnActionPerformed

    private void patientRecordsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientRecordsBtnActionPerformed
        // TODO add your handling code here:
        navigationManager.show("Patients");
    }//GEN-LAST:event_patientRecordsBtnActionPerformed

    private void reportsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportsBtnActionPerformed
        // TODO add your handling code here:
        navigationManager.show("Reports");
    }//GEN-LAST:event_reportsBtnActionPerformed

    private void inventoryBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inventoryBtnActionPerformed
        // TODO add your handling code here:
        navigationManager.show("Medicines");
    }//GEN-LAST:event_inventoryBtnActionPerformed

    private void logbookBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logbookBtnActionPerformed
        navigationManager.show("Logbook");
    }//GEN-LAST:event_logbookBtnActionPerformed

    //
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JPanel header;
    private javax.swing.JButton inventoryBtn;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton logbookBtn;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JPanel mainContent;
    private javax.swing.JButton patientRecordsBtn;
    private javax.swing.JButton reportsBtn;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JPanel sidebar;
    private javax.swing.JButton sidebarControl;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
