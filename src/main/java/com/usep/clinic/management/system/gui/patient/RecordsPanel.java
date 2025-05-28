package com.usep.clinic.management.system.gui.patient;

import com.usep.clinic.management.system.model.Patient;
import com.usep.clinic.management.system.model.PatientRecord;
import com.usep.clinic.management.system.service.PatientService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RecordsPanel extends JPanel implements ActionListener {

    private RoundedTextField patientIdField, patientNameField, patientContactField, patientDesignationField, patientCategoryField;
    private JTable recordTable;
    private DefaultTableModel recordModel;

    private RoundedButton addButton, viewButton, updateButton;

    private Patient patient;

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public RecordsPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(850, 565));

        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setBorder(BorderFactory.createLineBorder(new Color(242, 242, 242), 4));

        JLabel header = new JLabel("PATIENT RECORDS");
        header.setOpaque(true);
        header.setBackground(new Color(143, 186, 229));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBorder(BorderFactory.createLineBorder(new Color(143, 186, 229), 5));
        headerPanel.add(header, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(null);
        formPanel.setPreferredSize(new Dimension(850, 200));
        formPanel.setBackground(Color.WHITE);

        String[] labels = {"PATIENT ID:", "NAME:", "CONTACT NO.:", "DESIGNATION:", "CATEGORY:"};
        RoundedTextField[] fields = new RoundedTextField[5];
        int y = 25;

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setBounds(30, y, 100, 25);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            formPanel.add(label);

            RoundedTextField field = new RoundedTextField(20);
            field.setBounds(155, y, 200, 28);
            field.setEditable(false);
            fields[i] = field;
            formPanel.add(field);

            y += 30;
        }

        patientIdField = fields[0];
        patientNameField = fields[1];
        patientContactField = fields[2];
        patientDesignationField = fields[3];
        patientCategoryField = fields[4];

        // sa Table
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createLineBorder(new Color(242, 242, 242), 4));

        String[] recordColumns = {"RecordID", "PatientID", "Date", "Description", "Diagnosis"};
        recordModel = new DefaultTableModel(recordColumns, 0);
        recordTable = new JTable(recordModel);
        recordTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(recordTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel formAndTablePanel = new JPanel();
        formAndTablePanel.setLayout(new BorderLayout());
        formAndTablePanel.add(formPanel, BorderLayout.NORTH);

        JPanel tablePanelWrapper = new JPanel(new BorderLayout());
        tablePanelWrapper.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Space before table
        tablePanelWrapper.add(centerPanel, BorderLayout.CENTER);

        formAndTablePanel.add(tablePanelWrapper, BorderLayout.CENTER);
        add(formAndTablePanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        addButton = new RoundedButton("ADD RECORD");
        viewButton = new RoundedButton("VIEW");
        updateButton = new RoundedButton("UPDATE");

        RoundedButton[] buttons = {addButton, viewButton, updateButton};
        for (RoundedButton button : buttons) {
            button.setPreferredSize(new Dimension(120, 30));
            button.setBackground(new Color(143, 186, 229));
            button.addActionListener(this);
            controlPanel.add(button);
        }

        add(controlPanel, BorderLayout.SOUTH);

        loadRecords();
    }

    private void loadRecords() {
        PatientService service = PatientService.getInstance();
        ArrayList<PatientRecord> records = service.getRecords();

        recordModel.setRowCount(0);

        for (PatientRecord record : records) {
            Object[] row = new Object[]{
                    record.getId(),
                    record.getPatientId(),
                    record.getDateTime().toLocalDate(),
                    record.getDesc(),
                    record.getDiagnosis()
            };
            recordModel.addRow(row);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == viewButton) {
           // new RecordPatientView();
        } else if (source == addButton) {
           // new RecordAddDialog();
        } else if (source == updateButton) {
           // new RecordUpdateDialog();
        }
    }


    public void populatePatientDetails(Patient patient) {
        this.patient = patient;

        if (patient != null) {
            patientIdField.setText(" " + String.valueOf(patient.getId()));
            patientNameField.setText(" " + patient.getFirstname() + " " + patient.getMiddlename()  + ". " + patient.getLastname());
            patientContactField.setText(" " + patient.getContact());
            patientDesignationField.setText(" " + patient.getDesignation());
            patientCategoryField.setText(" " +String.valueOf(patient.getCategory()));

            patientIdField.setEditable(false);
            patientNameField.setEditable(false);
            patientContactField.setEditable(false);
            patientDesignationField.setEditable(false);
            patientCategoryField.setEditable(false);
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