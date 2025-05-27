package com.usep.clinic.management.system.gui;

import com.usep.clinic.management.system.model.Patient;
import com.usep.clinic.management.system.service.EntityNotFoundException;
import com.usep.clinic.management.system.service.PatientService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PatientWindow extends JPanel implements ActionListener {

    private DefaultTableModel patientModel;
    private JTable patientTable;
    private RoundedTextField searchPatients;
    private JButton patientAddButton, patientViewButton, patientUpdateButton, patientSearchButton;

    public PatientWindow() {
        super();
        setSize(850, 565);
        setLayout(null);

        JLabel patientHeader = new JLabel("   PATIENTS");
        patientHeader.setForeground(Color.WHITE);
        patientHeader.setBackground(new Color(143, 186, 229));
        patientHeader.setOpaque(true);
        patientHeader.setFont(new Font("Arial", Font.BOLD, 16));
        patientHeader.setBounds(20, 15, 570, 30);
        add(patientHeader);

        searchPatients = new RoundedTextField(15);
        searchPatients.setBounds(595, 16, 160, 30);
        searchPatients.setFont(new Font("Arial", Font.PLAIN, 12));
        add(searchPatients);

        patientSearchButton = new JButton("🔍");
        patientSearchButton.setBounds(760, 15, 35, 25);
        patientSearchButton.setFocusable(false);
        patientSearchButton.setBackground(new Color(143, 186, 229));
        patientSearchButton.setHorizontalAlignment(SwingConstants.CENTER);
        patientSearchButton.setVerticalAlignment(SwingConstants.CENTER);
        patientSearchButton.setMargin(new Insets(0, 0, 0, 0));
        patientSearchButton.setFocusPainted(false);
        patientSearchButton.setContentAreaFilled(false);
        patientSearchButton.setBorderPainted(false);
        add(patientSearchButton);
        patientSearchButton.addActionListener(this);

        String[] patientTableColumns = {"PatientID", "Name", "Designation", "Category", "Contact"};
        patientModel = new DefaultTableModel(patientTableColumns, 0);
        patientTable = new JTable(patientModel);
        JScrollPane patientTableScrollPane = new JScrollPane(patientTable);
        patientTableScrollPane.setBounds(20, 50, 800, 400);
        add(patientTableScrollPane);

        loadAllPatients();

        patientAddButton = new RoundedButton("ADD PATIENT");
        patientAddButton.setBackground(new Color(143, 186, 229));
        patientAddButton.setBounds(199, 470, 135, 30);
        add(patientAddButton);
        patientAddButton.addActionListener(this);

        patientViewButton = new RoundedButton("VIEW");
        patientViewButton.setBackground(new Color(143, 186, 229));
        patientViewButton.setBounds(340, 470, 100, 30);
        add(patientViewButton);
        patientViewButton.addActionListener(this);

        patientUpdateButton = new RoundedButton("UPDATE");
        patientUpdateButton.setBackground(new Color(143, 186, 229));
        patientUpdateButton.setBounds(450, 470, 100, 30);
        add(patientUpdateButton);
        patientUpdateButton.addActionListener(this);
    }

    private void loadAllPatients() {
        PatientService patientService = PatientService.getInstance();
        ArrayList<Patient> patients = patientService.getPatients();
        patientModel.setRowCount(0);

        for (Patient patient : patients) {
            Object[] row = new Object[]{
                    patient.getId(),
                    patient.getName(),
                    patient.getDesignation(),
                    patient.getCategory(),
                    patient.getContact()
            };
            patientModel.addRow(row);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == patientAddButton) {
            // new PatientAddDialog();
        } else if (source == patientUpdateButton) {
           // new PatientUpdateDialog(); // boist d matawag
        } else if (source == patientViewButton) {
            // la pa
        } else if (source == patientSearchButton) {
            String searchingPatient = searchPatients.getText().trim().toLowerCase();
            PatientService patientService = PatientService.getInstance();
            ArrayList<Patient> patients = patientService.getPatientsByName(searchingPatient);

            patientModel.setRowCount(0);

            try {
                for (Patient patient : patients) {
                    if (patient.getName().toLowerCase().contains(searchingPatient) ||
                            String.valueOf(patient.getId()).contains(searchingPatient) ||
                            patient.getContact().toLowerCase().contains(searchingPatient)) {

                        patientModel.addRow(new Object[]{
                                patient.getId(),
                                patient.getName(),
                                patient.getDesignation(),
                                patient.getCategory(),
                                patient.getContact()
                        });
                    }
                }

                if (patientModel.getRowCount() == 0) {
                    throw new EntityNotFoundException("No existing patient found.");
                }

            } catch (EntityNotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "PATIENT NOT FOUND 404", JOptionPane.ERROR_MESSAGE);
            }
        }
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
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

            FontMetrics fm = g2.getFontMetrics();
            int stringWidth = fm.stringWidth(getText());
            int stringHeight = fm.getAscent();
            g2.setColor(getForeground());
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
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
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
