package com.usep.clinic.management.system.gui;

import com.usep.clinic.management.system.model.IssuedMedicine;
import com.usep.clinic.management.system.model.PatientRecord;
import com.usep.clinic.management.system.service.MedicineService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RecordUpdateDialog extends JDialog implements ActionListener {
    private RoundedTextField RecordIdField, NameField, ContactNumberField, DescriptionField, DiagnosisField;
    private JComboBox<String> MedicineBox;
    private JComboBox<Integer> QuantityBox;
    private RoundedButton UpdateRecordButton, CancelButton, BackButton;

    private PatientRecord record;

    public RecordUpdateDialog(JFrame parent, PatientRecord record) {
        super(parent, "UPDATE RECORD", true);
        this.record = record;
        setSize(500, 480);
        setLayout(null);
        setLocationRelativeTo(parent);

        JLabel header = new JLabel("  UPDATE RECORD");
        header.setOpaque(true);
        header.setBackground(new Color(143, 186, 229));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBounds(0, 0, 500, 40);
        add(header);

        JLabel recordIdLabel = new JLabel("RECORD ID:");
        recordIdLabel.setBounds(30, 60, 140, 30);
        add(recordIdLabel);

        RecordIdField = new RoundedTextField(10);
        RecordIdField.setBounds(180, 60, 280, 30);
        RecordIdField.setText(String.valueOf(record.getId()));
        RecordIdField.setEditable(false);
        add(RecordIdField);

        JLabel nameLabel = new JLabel("NAME:");
        nameLabel.setBounds(30, 100, 140, 30);
        add(nameLabel);

        NameField = new RoundedTextField(20);
        NameField.setBounds(180, 100, 280, 30);
        add(NameField);

        JLabel contactLabel = new JLabel("CONTACT NUMBER:");
        contactLabel.setBounds(30, 140, 140, 30);
        add(contactLabel);

        ContactNumberField = new RoundedTextField(20);
        ContactNumberField.setBounds(180, 140, 280, 30);
        add(ContactNumberField);

        JLabel descLabel = new JLabel("DESCRIPTION:");
        descLabel.setBounds(30, 180, 140, 30);
        add(descLabel);

        DescriptionField = new RoundedTextField(20);
        DescriptionField.setBounds(180, 180, 280, 30);
        DescriptionField.setText(record.getDesc());
        add(DescriptionField);

        JLabel diagnosisLabel = new JLabel("DIAGNOSIS:");
        diagnosisLabel.setBounds(30, 220, 140, 30);
        add(diagnosisLabel);

        DiagnosisField = new RoundedTextField(20);
        DiagnosisField.setBounds(180, 220, 280, 30);
        DiagnosisField.setText(record.getDiagnosis());
        add(DiagnosisField);

        JLabel medicineLabel = new JLabel("MEDICINE:");
        medicineLabel.setBounds(30, 260, 140, 30);
        add(medicineLabel);

        MedicineBox = new JComboBox<>();
        for (IssuedMedicine med : MedicineService.getInstance().getIssuedMedicines()) {
            MedicineBox.addItem(med.getMedicineName());
        }
        MedicineBox.setBounds(180, 260, 280, 30);
        add(MedicineBox);

        JLabel quantityLabel = new JLabel("QUANTITY:");
        quantityLabel.setBounds(30, 300, 140, 30);
        add(quantityLabel);

        QuantityBox = new JComboBox<>();
        for (int i = 1; i <= 10; i++) QuantityBox.addItem(i);
        QuantityBox.setBounds(180, 300, 280, 30);
        add(QuantityBox);

        UpdateRecordButton = new RoundedButton("UPDATE");
        UpdateRecordButton.setBounds(70, 360, 120, 40);
        UpdateRecordButton.setBackground(new Color(143, 186, 229));
        UpdateRecordButton.addActionListener(this);
        add(UpdateRecordButton);

        CancelButton = new RoundedButton("CANCEL");
        CancelButton.setBounds(200, 360, 120, 40);
        CancelButton.setBackground(new Color(143, 186, 229));
        CancelButton.addActionListener(this);
        add(CancelButton);

        BackButton = new RoundedButton("BACK");
        BackButton.setBounds(330, 360, 120, 40);
        BackButton.setBackground(new Color(143, 186, 229));
        BackButton.addActionListener(this);
        add(BackButton);

        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == UpdateRecordButton) {
            String name = NameField.getText().trim();
            String contact = ContactNumberField.getText().trim();
            String desc = DescriptionField.getText().trim();
            String diagnosis = DiagnosisField.getText().trim();

            if (name.isEmpty() || contact.isEmpty() || desc.isEmpty() || diagnosis.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields (Name, Contact, Description, Diagnosis).", "INPUT ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String medicineName = (String) MedicineBox.getSelectedItem();
            int quantity = (int) QuantityBox.getSelectedItem();

            ArrayList<IssuedMedicine> updatedList = new ArrayList<>(record.getMedicineIssued());


            record.setDiagnosis(diagnosis);
            record.setMedicinesIssued(updatedList);
            record.setDateTime(LocalDateTime.now());

            JOptionPane.showMessageDialog(this, "Record updated successfully!", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } else if (src == CancelButton) {
            NameField.setText("");
            ContactNumberField.setText("");
            DescriptionField.setText("");
            DiagnosisField.setText("");
            MedicineBox.setSelectedIndex(0);
            QuantityBox.setSelectedIndex(0);

        } else if (src == BackButton) {
            dispose();
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
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.setColor(getForeground());
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 2;
            g2.drawString(getText(), x, y);
            g2.dispose();
        }
    }

    static class RoundedTextField extends JTextField {
        public RoundedTextField(int size) {
            super(size);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            setFont(new Font("Arial", Font.PLAIN, 14));
            setForeground(Color.BLACK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(hasFocus() ? new Color(143, 186, 229) : Color.GRAY);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            g2.dispose();
        }
    }
}
