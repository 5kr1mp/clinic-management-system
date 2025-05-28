package com.usep.clinic.management.system.gui.patient;

import com.usep.clinic.management.system.model.PatientRecord;
import com.usep.clinic.management.system.service.DuplicateEntityException;
import com.usep.clinic.management.system.service.PatientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class RecordAddDialog extends JDialog implements ActionListener {
    private RoundedTextField recordIdField, patientIdField, dateField, descriptionField, diagnosisField;
    private RoundedButton addRecordButton, cancelRecordButton, backButton;
    private JComboBox<String> medicineComboBox, quantityComboBox;

    public RecordAddDialog(JFrame parent) {
        super(parent, "ADD PATIENT RECORD", true);
        setSize(450, 420);
        setLayout(null);
        setLocationRelativeTo(parent);

        JLabel header = new JLabel("  ADD PATIENT RECORD");
        header.setOpaque(true);
        header.setBackground(new Color(143, 186, 229));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBounds(0, 0, 450, 40);
        add(header);

        JLabel recordIdLabel = new JLabel("RECORD ID:");
        recordIdLabel.setBounds(20, 50, 120, 30);
        recordIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(recordIdLabel);

        recordIdField = new RoundedTextField(20);
        recordIdField.setBounds(150, 50, 250, 30);
        add(recordIdField);

        JLabel patientIdLabel = new JLabel("PATIENT ID:");
        patientIdLabel.setBounds(20, 90, 120, 30);
        patientIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(patientIdLabel);

        patientIdField = new RoundedTextField(20);
        patientIdField.setBounds(150, 90, 250, 30);
        add(patientIdField);

        JLabel dateLabel = new JLabel("DATE (YYYY-MM-DD):");
        dateLabel.setBounds(20, 130, 140, 30);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(dateLabel);

        dateField = new RoundedTextField(20);
        dateField.setBounds(170, 130, 230, 30);
        add(dateField);

        JLabel descriptionLabel = new JLabel("DESCRIPTION:");
        descriptionLabel.setBounds(20, 170, 120, 30);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(descriptionLabel);

        descriptionField = new RoundedTextField(20);
        descriptionField.setBounds(150, 170, 250, 30);
        add(descriptionField);

        JLabel diagnosisLabel = new JLabel("DIAGNOSIS:");
        diagnosisLabel.setBounds(20, 210, 120, 30);
        diagnosisLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(diagnosisLabel);

        diagnosisField = new RoundedTextField(20);
        diagnosisField.setBounds(150, 210, 250, 30);
        add(diagnosisField);

        JLabel medicineLabel = new JLabel("MEDICINE:");
        medicineLabel.setBounds(20, 250, 120, 30);
        medicineLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(medicineLabel);

        medicineComboBox = new JComboBox<>();
        List<String> medicines = Arrays.asList("Paracetamol", "Ibuprofen", "Amoxicillin", "Cough Syrup");
        for (String med : medicines) {
            medicineComboBox.addItem(med);
        }
        medicineComboBox.setBounds(150, 250, 250, 30);
        add(medicineComboBox);

        JLabel quantityLabel = new JLabel("QUANTITY:");
        quantityLabel.setBounds(20, 290, 120, 30);
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(quantityLabel);

        quantityComboBox = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            quantityComboBox.addItem(String.valueOf(i));
        }
        quantityComboBox.setBounds(150, 290, 250, 30);
        add(quantityComboBox);

        addRecordButton = new RoundedButton("ADD");
        addRecordButton.setBackground(new Color(143, 186, 229));
        addRecordButton.setBounds(80, 340, 90, 30);
        add(addRecordButton);
        addRecordButton.addActionListener(this);

        cancelRecordButton = new RoundedButton("CANCEL");
        cancelRecordButton.setBackground(new Color(143, 186, 229));
        cancelRecordButton.setBounds(180, 340, 90, 30);
        add(cancelRecordButton);
        cancelRecordButton.addActionListener(this);

        backButton = new RoundedButton("BACK");
        backButton.setBackground(new Color(143, 186, 229));
        backButton.setBounds(280, 340, 90, 30);
        add(backButton);
        backButton.addActionListener(this);

        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == addRecordButton) {
            String recordIdText = recordIdField.getText().trim();
            String patientIdText = patientIdField.getText().trim();
            String dateText = dateField.getText().trim();
            String description = descriptionField.getText().trim();
            String diagnosis = diagnosisField.getText().trim();

            if (recordIdText.isEmpty() || patientIdText.isEmpty() || dateText.isEmpty() || description.isEmpty() || diagnosis.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "INPUT ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!recordIdText.matches("\\d+") || !patientIdText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Record ID and Patient ID must be numeric.", "INPUT ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int recordId = Integer.parseInt(recordIdText);
            int patientId = Integer.parseInt(patientIdText);

            LocalDate date;
            try {
                date = LocalDate.parse(dateText);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid date in YYYY-MM-DD format.", "INPUT ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int choice = JOptionPane.showConfirmDialog(this, "Do you want to save this record?", "CONFIRM SAVE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choice == JOptionPane.YES_OPTION) {
                try {
                    PatientRecord record = new PatientRecord(recordId, patientId, date.atStartOfDay(), description, diagnosis);

                    PatientService.getInstance().add(record);

                    JOptionPane.showMessageDialog(this, "Record saved successfully!", "", JOptionPane.INFORMATION_MESSAGE);
                    dispose();

                } catch (DuplicateEntityException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "DUPLICATE RECORD ERROR", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Record was not saved.", "", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if (source == cancelRecordButton) {
            recordIdField.setText("");
            patientIdField.setText("");
            dateField.setText("");
            descriptionField.setText("");
            diagnosisField.setText("");
            quantityComboBox.setSelectedIndex(0);
            medicineComboBox.setSelectedIndex(0);
        } else if (source == backButton) {
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
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            setFont(new Font("Arial", Font.PLAIN, 14));
            setForeground(Color.BLACK);
            setCaretColor(Color.BLACK);
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
