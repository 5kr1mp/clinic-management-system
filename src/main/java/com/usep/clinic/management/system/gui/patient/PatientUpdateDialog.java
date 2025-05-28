package com.usep.clinic.management.system.gui.patient;

import com.usep.clinic.management.system.gui.model.PatientTableModel;
import com.usep.clinic.management.system.model.Patient;
import com.usep.clinic.management.system.model.enums.Category;
import com.usep.clinic.management.system.service.PatientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatientUpdateDialog extends JDialog implements ActionListener {

    private RoundedTextField PatientIdField, PatientFirstNameField, PatientLastNameField,
            PatientMiddleInitialField, PatientContactNumberField;
    private JComboBox<String> CategoryBox, DesignationBox;
    private RoundedButton UpdateButton, CancelButton, BackButton;

    private PatientTableModel tableModel;
    private Patient patient;

    public PatientUpdateDialog(PatientTableModel tableModel, Patient patient) {
        super((Frame) null, "UPDATE PATIENT", true);
        this.tableModel = tableModel;
        this.patient = patient;

        setSize(415, 400);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel header = new JLabel("  UPDATE PATIENT");
        header.setOpaque(true);
        header.setBackground(new Color(143, 186, 229));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setBounds(0, 0, 415, 40);
        add(header);

        addLabel("PATIENT ID :", 20, 50);
        PatientIdField = new RoundedTextField(20);
        PatientIdField.setBounds(150, 50, 190, 35);
        PatientIdField.setEnabled(false);
        add(PatientIdField);

        addLabel("FIRST NAME :", 20, 90);
        PatientFirstNameField = new RoundedTextField(20);
        PatientFirstNameField.setBounds(150, 90, 190, 35);
        add(PatientFirstNameField);

        addLabel("LAST NAME :", 20, 130);
        PatientLastNameField = new RoundedTextField(20);
        PatientLastNameField.setBounds(150, 130, 190, 35);
        add(PatientLastNameField);

        addLabel("MIDDLE INITIAL :", 20, 170);
        PatientMiddleInitialField = new RoundedTextField(20);
        PatientMiddleInitialField.setBounds(150, 170, 190, 35);
        add(PatientMiddleInitialField);

        addLabel("CONTACT NO. :", 20, 210);
        PatientContactNumberField = new RoundedTextField(20);
        PatientContactNumberField.setBounds(150, 210, 190, 35);
        add(PatientContactNumberField);

        DesignationBox = new JComboBox<>(new String[]{"BSIT", "BSED", "BSNED", "BEED", "BECED", "BSABE", "BTVTED"});
        DesignationBox.setBounds(80, 255, 85, 25);
        add(DesignationBox);

        CategoryBox = new JComboBox<>(new String[]{"STUDENT", "FACULTY"});
        CategoryBox.setBounds(200, 255, 85, 25);
        add(CategoryBox);

        UpdateButton = new RoundedButton("UPDATE");
        UpdateButton.setBackground(new Color(143, 186, 229));
        UpdateButton.setBounds(50, 300, 100, 30);
        add(UpdateButton);
        UpdateButton.addActionListener(this);

        CancelButton = new RoundedButton("CANCEL");
        CancelButton.setBackground(new Color(143, 186, 229));
        CancelButton.setBounds(160, 300, 100, 30);
        add(CancelButton);
        CancelButton.addActionListener(this);

        BackButton = new RoundedButton("BACK");
        BackButton.setBackground(new Color(143, 186, 229));
        BackButton.setBounds(270, 300, 80, 30);
        add(BackButton);
        BackButton.addActionListener(this);

        // patient data
        PatientIdField.setText(String.valueOf(patient.getId()));
        PatientFirstNameField.setText(patient.getFirstname());
        PatientLastNameField.setText(patient.getLastname());
        PatientMiddleInitialField.setText(patient.getMiddlename());
        PatientContactNumberField.setText(patient.getContact());
        CategoryBox.setSelectedItem(patient.getCategory().name());
        DesignationBox.setSelectedItem(patient.getDesignation());

        setVisible(true);
        setResizable(false);
    }

    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBounds(x, y, 130, 35);
        add(label);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == UpdateButton) {
            String firstName = PatientFirstNameField.getText().trim();
            String lastName = PatientLastNameField.getText().trim();
            String contact = PatientContactNumberField.getText().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "First name, last name, and contact number are required.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int choice = JOptionPane.showConfirmDialog(this,
                    "Save changes to this patient?",
                    "Confirm Update",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                try {

                    if (patient == null) {
                        JOptionPane.showMessageDialog(this,
                                "No patient selected for update.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    patient.setFirstname(firstName);
                    patient.setLastname(lastName);
                    patient.setMiddlename(PatientMiddleInitialField.getText().trim());
                    patient.setContact(contact);
                    patient.setCategory(Category.valueOf((String) CategoryBox.getSelectedItem()));
                    patient.setDesignation((String) DesignationBox.getSelectedItem());

                    PatientService.getInstance().update(patient);

                    JOptionPane.showMessageDialog(this, "Patient updated successfully.");
                    dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error updating patient: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }



    } else if (source == CancelButton) {
            PatientIdField.setText(String.valueOf(patient.getId()));
            PatientFirstNameField.setText(patient.getFirstname());
            PatientLastNameField.setText(patient.getLastname());
            PatientMiddleInitialField.setText(patient.getMiddlename());
            PatientContactNumberField.setText(patient.getContact());
            CategoryBox.setSelectedItem(patient.getCategory().name());
            DesignationBox.setSelectedItem(patient.getDesignation());
        } else if (source == BackButton) {
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
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
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
