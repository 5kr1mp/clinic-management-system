import com.usep.clinic.management.system.model.Medicine;
import com.usep.clinic.management.system.model.IssuedMedicine;
import com.usep.clinic.management.system.model.PatientRecord;
import com.usep.clinic.management.system.service.DuplicateEntityException;
import com.usep.clinic.management.system.service.MedicineService;
import com.usep.clinic.management.system.service.PatientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RecordAddDialog extends JDialog implements ActionListener {
    private RoundedTextField recordIdField, patientIdField, dateField, descriptionField, diagnosisField;
    private JComboBox<Medicine> medicineComboBox;
    private RoundedTextField quantityField;
    private RoundedButton addButton, cancelButton, backButton;

    public RecordAddDialog() {
        super((Frame) null, "ADD PATIENT RECORD", true);
        setSize(415, 450);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel header = new JLabel("  ADD PATIENT RECORD");
        header.setOpaque(true);
        header.setBackground(new Color(143, 186, 229));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBounds(0, 0, 415, 40);
        add(header);

        JLabel recordIdLabel = new JLabel("RECORD ID:");
        recordIdLabel.setBounds(20, 50, 120, 30);
        recordIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(recordIdLabel);

        recordIdField = new RoundedTextField(20);
        recordIdField.setBounds(205, 50, 180, 30);
        add(recordIdField);

        JLabel patientIdLabel = new JLabel("PATIENT ID:");
        patientIdLabel.setBounds(20, 90, 120, 30);
        patientIdLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(patientIdLabel);

        patientIdField = new RoundedTextField(20);
        patientIdField.setBounds(205, 90, 180, 30);
        add(patientIdField);

        JLabel dateLabel = new JLabel("DATE (YYYY-MM-DD):");
        dateLabel.setBounds(20, 130, 200, 30);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(dateLabel);

        dateField = new RoundedTextField(20);
        dateField.setBounds(205, 130, 180, 30);
        add(dateField);

        JLabel descriptionLabel = new JLabel("DESCRIPTION:");
        descriptionLabel.setBounds(20, 170, 120, 30);
        descriptionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(descriptionLabel);

        descriptionField = new RoundedTextField(20);
        descriptionField.setBounds(205, 170, 180, 30);
        add(descriptionField);

        JLabel diagnosisLabel = new JLabel("DIAGNOSIS:");
        diagnosisLabel.setBounds(20, 210, 120, 30);
        diagnosisLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(diagnosisLabel);

        diagnosisField = new RoundedTextField(20);
        diagnosisField.setBounds(205, 210, 180, 30);
        add(diagnosisField);

        JLabel medicineLabel = new JLabel("MEDICINE:");
        medicineLabel.setBounds(20, 250, 120, 30);
        medicineLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(medicineLabel);

        medicineComboBox = new JComboBox<>();
        medicineComboBox.setBounds(205, 250, 180, 30);
        add(medicineComboBox);


        for (Medicine med : MedicineService.getInstance().getMedicines()) {
            medicineComboBox.addItem(med);
        }

        JLabel quantityLabel = new JLabel("QUANTITY:");
        quantityLabel.setBounds(20, 290, 120, 30);
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(quantityLabel);

        quantityField = new RoundedTextField(20);
        quantityField.setBounds(205, 290, 180, 30);
        add(quantityField);

        addButton = new RoundedButton("ADD");
        addButton.setBackground(new Color(143, 186, 229));
        addButton.setBounds(50, 340, 90, 30);
        add(addButton);
        addButton.addActionListener(this);

        cancelButton = new RoundedButton("CANCEL");
        cancelButton.setBackground(new Color(143, 186, 229));
        cancelButton.setBounds(150, 340, 100, 30);
        add(cancelButton);
        cancelButton.addActionListener(this);

        backButton = new RoundedButton("BACK");
        backButton.setBackground(new Color(143, 186, 229));
        backButton.setBounds(260, 340, 90, 30);
        add(backButton);
        backButton.addActionListener(this);

        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == addButton) {
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

            Medicine selectedMedicine = (Medicine) medicineComboBox.getSelectedItem();
            String quantityText = quantityField.getText().trim();

            boolean hasMedicineInfo = selectedMedicine != null && !quantityText.isEmpty();

            int recordId = Integer.parseInt(recordIdText);
            int patientId = Integer.parseInt(patientIdText);
            int quantity = 0;

            if (hasMedicineInfo && !quantityText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric quantity.", "INPUT ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (hasMedicineInfo) {
                quantity = Integer.parseInt(quantityText);
            }

            LocalDate date;
            try {
                date = LocalDate.parse(dateText);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid date in YYYY-MM-DD format.", "INPUT ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int choice = JOptionPane.showConfirmDialog(this, "Do you want to save this record?", "CONFIRM SAVE", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                try {
                    PatientRecord record = new PatientRecord(recordId, patientId, date.atStartOfDay(), description, diagnosis);
                    PatientService.getInstance().add(record);

                    JOptionPane.showMessageDialog(this, "Record saved.", "", JOptionPane.INFORMATION_MESSAGE);
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

        } else if (source == cancelButton) {
            recordIdField.setText("");
            patientIdField.setText("");
            dateField.setText("");
            descriptionField.setText("");
            diagnosisField.setText("");
            quantityField.setText("");
            medicineComboBox.setSelectedIndex(0);
        } else if (source == backButton) {
            dispose();
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

            FontMetrics fm = g2.getFontMetrics();
            int stringWidth = fm.stringWidth(getText());
            int stringHeight = fm.getAscent();
            g2.setColor(getForeground());
            g2.drawString(getText(), (getWidth() - stringWidth) / 2, (getHeight() + stringHeight) / 2 - 2);

            g2.dispose();
        }
    }
}