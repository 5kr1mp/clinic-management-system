package com.usep.clinic.management.system.gui.patient;

import com.usep.clinic.management.system.gui.model.PatientTableModel;
import com.usep.clinic.management.system.model.PatientRecord;
import com.usep.clinic.management.system.service.DuplicateEntityException;
import com.usep.clinic.management.system.service.PatientService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.usep.clinic.management.system.model.Patient;
import com.usep.clinic.management.system.model.enums.Category;
import java.util.ArrayList;

public class PatientAddDialog extends JDialog implements ActionListener {
    private RoundedTextField PatientIdField, PatientFirstNameField, PatientLastNameField, PatientMiddleInitialField, PatientContactNumberField;
    private JComboBox<String> CategoryBox;
    private JComboBox<String> DesignationBox;
    private RoundedButton AddPatientButton, CancelPatientButton, BackButton;

    private PatientTableModel tableModel;
    private Patient patientToUpdate;

    public PatientAddDialog(PatientTableModel tableModel) {
        this(tableModel, null);
    }

    public PatientAddDialog(PatientTableModel tableModel, Patient patientToUpdate) {
        super((Frame)null, patientToUpdate == null ? "ADD PATIENTS" : "UPDATE PATIENTS", true);
        this.tableModel = tableModel;
        this.patientToUpdate = patientToUpdate;
        setSize(415, 400);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel AddPatientheader = new JLabel(patientToUpdate == null ? "  ADD PATIENT" : "  UPDATE PATIENT");
        AddPatientheader.setOpaque(true);
        AddPatientheader.setBackground(new Color(143, 186, 229));
        AddPatientheader.setForeground(Color.WHITE);
        AddPatientheader.setFont(new Font("Arial", Font.BOLD, 16));
        AddPatientheader.setBounds(0, 0, 415, 40);
        AddPatientheader.setBorder(null);
        add(AddPatientheader);

        JLabel PatientIDLabel = new JLabel("PATIENT ID :");
        PatientIDLabel.setBounds(20, 50, 120, 35);
        PatientIDLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(PatientIDLabel);

        PatientIdField = new RoundedTextField(20);
        PatientIdField.setBounds(150, 50, 190, 35);
        add(PatientIdField);

        JLabel PatientFirstNameLabel = new JLabel("FIRST NAME :");
        PatientFirstNameLabel.setBounds(20, 90, 120, 35);
        PatientFirstNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(PatientFirstNameLabel);

        PatientFirstNameField = new RoundedTextField(20);
        PatientFirstNameField.setBounds(150, 90, 190, 35);
        add(PatientFirstNameField);

        JLabel PatientLastNameLabel = new JLabel("LAST NAME :");
        PatientLastNameLabel.setBounds(20, 130, 120, 35);
        PatientLastNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(PatientLastNameLabel);

        PatientLastNameField = new RoundedTextField(20);
        PatientLastNameField.setBounds(150, 130, 190, 35);
        add(PatientLastNameField);

        JLabel PatientMiddleInitialLabel = new JLabel("MIDDLE INITIAL :");
        PatientMiddleInitialLabel.setBounds(20, 170, 130, 35);
        PatientMiddleInitialLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(PatientMiddleInitialLabel);

        PatientMiddleInitialField = new RoundedTextField(20);
        PatientMiddleInitialField.setBounds(150, 170, 190, 35);
        add(PatientMiddleInitialField);

        JLabel PatientContactLabel = new JLabel("CONTACT NO. :");
        PatientContactLabel.setBounds(20, 210, 120, 35);
        PatientContactLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(PatientContactLabel);

        PatientContactNumberField = new RoundedTextField(20);
        PatientContactNumberField.setBounds(150, 210, 190, 35);
        add(PatientContactNumberField);

        DesignationBox = new JComboBox<>(new String[]{"BSIT", "BSED", "BSNED", "BEED", "BECED", "BSABE", "BTVTED"});
        DesignationBox.setBounds(80, 255, 85, 25);
        DesignationBox.setBackground(Color.WHITE);
        add(DesignationBox);

        CategoryBox = new JComboBox<>(new String[]{"STUDENT", "FACULTY"});
        CategoryBox.setBounds(200, 255, 85, 25);
        CategoryBox.setBackground(Color.WHITE);
        add(CategoryBox);

        AddPatientButton = new RoundedButton(patientToUpdate == null ? "ADD" : "UPDATE");
        AddPatientButton.setBackground(new Color(143, 186, 229));
        AddPatientButton.setBounds(50, 300, 80, 30);
        add(AddPatientButton);
        AddPatientButton.addActionListener(this);

        CancelPatientButton = new RoundedButton("CANCEL");
        CancelPatientButton.setBackground(new Color(143, 186, 229));
        CancelPatientButton.setBounds(140, 300, 100, 30);
        add(CancelPatientButton);
        CancelPatientButton.addActionListener(this);

        BackButton = new RoundedButton("BACK");
        BackButton.setBackground(new Color(143, 186, 229));
        BackButton.setBounds(250, 300, 80, 30);
        add(BackButton);
        BackButton.addActionListener(this);

        if (patientToUpdate != null) {
            PatientIdField.setText(String.valueOf(patientToUpdate.getId()));
            PatientFirstNameField.setText(patientToUpdate.getFirstname());
            PatientLastNameField.setText(patientToUpdate.getLastname());
            PatientMiddleInitialField.setText(patientToUpdate.getMiddlename());
            PatientContactNumberField.setText(patientToUpdate.getContact());
            CategoryBox.setSelectedItem(patientToUpdate.getCategory().name());
            DesignationBox.setSelectedItem(patientToUpdate.getDesignation());
        }

        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == AddPatientButton) {
            String IdText = PatientIdField.getText().trim();
            String FirstName = PatientFirstNameField.getText().trim();
            String LastName = PatientLastNameField.getText().trim();
            String Contact = PatientContactNumberField.getText().trim();

            if (IdText.isEmpty() || FirstName.isEmpty() || LastName.isEmpty() || Contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields (ID, First Name, Last Name, Contact).", "INPUT ERROR 404", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!IdText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Patient ID must be numeric.", "INPUT ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int Id = Integer.parseInt(IdText);

            int choice = JOptionPane.showConfirmDialog(this, patientToUpdate == null ? "Do you want to save this patient?" : "Do you want to update this patient?", patientToUpdate == null ? "CONFIRM SAVE" : "CONFIRM UPDATE", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choice == JOptionPane.YES_OPTION) {
                try {
                    String MiddleInitial = PatientMiddleInitialField.getText().trim();
                    String category = (String) CategoryBox.getSelectedItem();
                    String Designation = (String) DesignationBox.getSelectedItem();

                    Patient patient = new Patient(
                            Id,
                            LastName,
                            FirstName,
                            MiddleInitial,
                            Designation,
                            Category.valueOf(category),
                            Contact,
                            new ArrayList<PatientRecord>()
                    );

                    PatientService patientService = PatientService.getInstance();

                    if (patientToUpdate == null) {
                        patientService.add(patient);
                        tableModel.add(patient);
                        JOptionPane.showMessageDialog(this, "Patient updated!", "", JOptionPane.INFORMATION_MESSAGE);
                    }

                    dispose();

                } catch (DuplicateEntityException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "INPUT ERROR 404", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred: " + ex.getMessage(), "ERROR 404", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, patientToUpdate == null ? "Patient was not saved." : "Patient update canceled.", "", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (source == CancelPatientButton) {
            PatientIdField.setText("");
            PatientFirstNameField.setText("");
            PatientLastNameField.setText("");
            PatientMiddleInitialField.setText("");
            PatientContactNumberField.setText("");
            CategoryBox.setSelectedIndex(0);
            DesignationBox.setSelectedIndex(0);
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
