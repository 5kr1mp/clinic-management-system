package com.usep.clinic.management.system.gui.patient;

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

    public RecordsPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(850, 680));

        JLabel header = new JLabel("  ADD PATIENT");
        header.setOpaque(true);
        header.setBackground(new Color(143, 186, 229));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBounds(0, 0, 850, 40);
        add(header);

        String[] labels = {"PATIENT ID:", "NAME:", "CONTACT NO.:", "DESIGNATION:", "CATEGORY:"};
        RoundedTextField[] fields = new RoundedTextField[5];
        int y = 55;

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setBounds(30, y, 100, 25);
            add(label);

            RoundedTextField field = new RoundedTextField(20);
            field.setBounds(150, y, 200, 30);
            field.setEditable(false);
            fields[i] = field;
            add(field);

            y += 40;
        }

        patientIdField = fields[0];
        patientNameField = fields[1];
        patientContactField = fields[2];
        patientDesignationField = fields[3];
        patientCategoryField = fields[4];

        String[] recordColumns = {"RecordID", "PatientID", "Date", "Description", "Diagnosis"};
        recordModel = new DefaultTableModel(recordColumns, 0);
        recordTable = new JTable(recordModel);
        JScrollPane scrollPane = new JScrollPane(recordTable);
        scrollPane.setBounds(20, 270, 800, 280);
        add(scrollPane);

        addButton = new RoundedButton("ADD RECORD");
        viewButton = new RoundedButton("VIEW");
        updateButton = new RoundedButton("UPDATE");

        RoundedButton[] buttons = {addButton, viewButton, updateButton};
        int x = 200;
        for (RoundedButton button : buttons) {
            button.setBounds(x, 570, 130, 30);
            button.setBackground(new Color(143, 186, 229));
            button.addActionListener(this);
            add(button);
            x += 140;
        }

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
           // no
        } else if (source == addButton) {
            // new RecordAddDialog();
        } else if (source == updateButton) {

        }
    }

    public RoundedTextField getPatientIdField() { return patientIdField; }
    public RoundedTextField getPatientNameField() { return patientNameField; }
    public RoundedTextField getPatientContactField() { return patientContactField; }
    public RoundedTextField getPatientDesignationField() { return patientDesignationField; }
    public RoundedTextField getPatientCategoryField() { return patientCategoryField; }
    public JTable getRecordTable() { return recordTable; }

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
