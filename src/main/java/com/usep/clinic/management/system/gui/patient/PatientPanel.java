package com.usep.clinic.management.system.gui.patient;

import com.usep.clinic.management.system.gui.NavigationManager;
import com.usep.clinic.management.system.gui.model.PatientTableModel;
import com.usep.clinic.management.system.model.Patient;
import com.usep.clinic.management.system.model.enums.Role;
import com.usep.clinic.management.system.service.AuthService;
import com.usep.clinic.management.system.service.EntityNotFoundException;
import com.usep.clinic.management.system.service.PatientService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientPanel extends JPanel implements ActionListener {

    private PatientTableModel patientModel;
    private JTable patientTable;

    private RecordsPanel recordsPanel;
    NavigationManager navigationManager;

    private RoundedTextField searchPatients;
    private JButton patientAddButton, patientViewButton, patientUpdateButton, patientSearchButton, patientDeleteButton;

    public PatientPanel() {
        super();
        setSize(850, 565);
        setLayout(new BorderLayout());

        recordsPanel = new RecordsPanel();

        navigationManager = NavigationManager.getInstance();
        navigationManager.registerPanel(recordsPanel, "Records");

        JPanel header = new JPanel(new BorderLayout(10,0));
        header.setBorder(BorderFactory.createLineBorder(new Color(242, 242, 242), 4));
        JLabel patientHeader = new JLabel("PATIENTS");
        patientHeader.setForeground(Color.WHITE);
        patientHeader.setBorder(BorderFactory.createLineBorder(new Color(143, 186, 229), 5));
        patientHeader.setBackground(new Color(143, 186, 229));
        patientHeader.setOpaque(true);
        patientHeader.setFont(new Font("Arial", Font.BOLD, 16));
        header.add(patientHeader, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPatients = new RoundedTextField(15);
        searchPatients.setFont(new Font("Arial", Font.PLAIN, 12));
        searchPatients.setPreferredSize(new Dimension(25,25));
        searchPanel.add(searchPatients, BorderLayout.CENTER);

        patientSearchButton = new JButton("🔍");
        patientSearchButton.setFocusable(false);
        patientSearchButton.setPreferredSize(new Dimension(30,30));
        patientSearchButton.setBackground(new Color(143, 186, 229));
        patientSearchButton.setHorizontalAlignment(SwingConstants.CENTER);
        patientSearchButton.setVerticalAlignment(SwingConstants.CENTER);
        patientSearchButton.setMargin(new Insets(0, 0, 0, 0));
        patientSearchButton.setFocusPainted(false);
        patientSearchButton.setContentAreaFilled(false);
        patientSearchButton.setBorderPainted(false);
        searchPanel.add(patientSearchButton, BorderLayout.EAST);
        patientSearchButton.addActionListener(this);
        header.add(searchPanel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        patientModel = new PatientTableModel();
        patientTable = new JTable(patientModel);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        patientTable.getSelectionModel().addListSelectionListener(e -> {
            if (patientTable.getSelectedRow() == -1) {
                patientViewButton.setEnabled(false);
                patientDeleteButton.setEnabled(false);
                patientUpdateButton.setEnabled(false);

            } else {
                patientDeleteButton.setEnabled(true);
                patientViewButton.setEnabled(true);
                patientUpdateButton.setEnabled(true);
            }
        });

        JScrollPane patientTableScrollPane = new JScrollPane(patientTable);
        patientTableScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(242, 242, 242), 4),
                patientTableScrollPane.getBorder()
        ));
        centerPanel.add(patientTableScrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        patientAddButton = new RoundedButton("ADD PATIENT");
        patientAddButton.setBackground(new Color(143, 186, 229));
        patientAddButton.setPreferredSize(new Dimension(120, 30));
        controlPanel.add(patientAddButton);
        patientAddButton.addActionListener(this);

        patientViewButton = new RoundedButton("VIEW");
        patientViewButton.setBackground(new Color(143, 186, 229));
        patientViewButton.setPreferredSize(new Dimension(120, 30));
        patientViewButton.setEnabled(false);
        controlPanel.add(patientViewButton);
        patientViewButton.addActionListener(this);

        patientUpdateButton = new RoundedButton("UPDATE");
        patientUpdateButton.setBackground(new Color(143, 186, 229));
        patientUpdateButton.setPreferredSize(new Dimension(120, 30));
        patientUpdateButton.setEnabled(false);
        controlPanel.add(patientUpdateButton);
        patientUpdateButton.addActionListener(this);

        patientDeleteButton = new RoundedButton("DELETE");
        patientDeleteButton.setBackground(new Color(143, 186, 229));
        patientDeleteButton.setPreferredSize(new Dimension(120, 30));
        patientDeleteButton.setEnabled(false);
        controlPanel.add(patientDeleteButton);
        patientDeleteButton.addActionListener(this);

        add(controlPanel, BorderLayout.SOUTH);

        if (AuthService.getInstance().getLoggedInUserRole() == Role.ADMIN){
            patientDeleteButton.setVisible(true);
        }
        else {
            patientDeleteButton.setVisible(false);
        }

        loadAllPatients();
    }

    private void loadAllPatients() {
        PatientService patientService = PatientService.getInstance();
        ArrayList<Patient> patients = patientService.getPatients();
        patientModel.replaceAll(patients);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == patientAddButton) {
            new PatientAddDialog(patientModel);

        } else if (source == patientUpdateButton) {
            int selectedRow = patientTable.getSelectedRow();
            if (selectedRow >= 0) {
                Patient patientToUpdate = patientModel.getRow(selectedRow);
                new PatientUpdateDialog(patientModel, patientToUpdate);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a patient to update.", "No Patient Selected", JOptionPane.WARNING_MESSAGE);
            }

        } else if (source == patientViewButton) {
            int selectedRowIndex = patientTable.getSelectedRow();
            if (selectedRowIndex >= 0) {
                Patient patient = patientModel.getRow(selectedRowIndex);
                recordsPanel.setPatient(patient);
                navigationManager.show("Records");
            }

        } else if (source == patientSearchButton) {
            String searchingPatient = searchPatients.getText().trim().toLowerCase();
            PatientService patientService = PatientService.getInstance();

            ArrayList<Patient> patients = patientService.getPatients();
            patientModel.clear();

            if (patients.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No patients found.", "EMPTY DATABASE", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ArrayList<Patient> matchedPatients = new ArrayList<>();
            for (Patient patient : patients) {
                if (searchingPatient.isEmpty() ||
                        patient.getName().toLowerCase().contains(searchingPatient) ||
                        String.valueOf(patient.getId()).contains(searchingPatient) ||
                        patient.getContact().toLowerCase().contains(searchingPatient)) {
                    matchedPatients.add(patient);
                }
            }

            if (matchedPatients.isEmpty()) {
                for (Patient patient : patients) {
                    patientModel.add(patient);
                }
                JOptionPane.showMessageDialog(this, "No existing patient found.", "NOT FOUND", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Patient patient : matchedPatients) {
                    patientModel.add(patient);
                }
            }
        } else if (source == patientDeleteButton){
            Patient patient = patientModel.getRow(
                patientTable.getSelectedRow()
            );

            if (confirmDelete(patient.getFullName()) == JOptionPane.YES_OPTION){
                try {
                    PatientService.getInstance().delete(patient.getId());
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(this, "Failed to delete patient", "Failed to delete", JOptionPane.ERROR_MESSAGE);
                }

                loadAllPatients();
            }
        }
    }

    private int confirmDelete(String name){
        return JOptionPane.showConfirmDialog(
            null,
            "Confirm deletion of " + name + " and all their records.",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
    }



static class RoundedButton extends JButton {
    public RoundedButton(String label) {
        super(label);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setFont(new Font("Arial", Font.BOLD, 14));
        setMargin(new Insets(0, 0, 0, 0));
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (isEnabled()) {
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setColor(getForeground());
        } else {
            g2.setColor(new Color(180, 180, 180));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setColor(new Color(100, 100, 100));
        }

        FontMetrics fm = g2.getFontMetrics();
        int stringWidth = fm.stringWidth(getText());
        int stringHeight = fm.getAscent();
        g2.drawString(getText(), (getWidth() - stringWidth) / 2, (getHeight() + stringHeight) / 2 - 2);

        g2.dispose();
    }
}

    static class RoundedTextField extends JTextField {
        public RoundedTextField(int size) {
            super(size);
            setOpaque(false);
            setBorder(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            g2.dispose();
        }
    }
}
