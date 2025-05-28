package com.usep.clinic.management.system.gui.patient;

import com.usep.clinic.management.system.gui.model.IssuedMedicineTableModel;
import com.usep.clinic.management.system.gui.model.PatientRecordTableModel;
import com.usep.clinic.management.system.gui.model.PatientTableModel;
import com.usep.clinic.management.system.model.Medicine;
import com.usep.clinic.management.system.model.IssuedMedicine;
import com.usep.clinic.management.system.model.Patient;
import com.usep.clinic.management.system.model.PatientRecord;
import com.usep.clinic.management.system.service.DuplicateEntityException;
import com.usep.clinic.management.system.service.MedicineService;
import com.usep.clinic.management.system.service.PatientService;

import javax.swing.*;
import javax.swing.Box.Filler;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class RecordAddDialog extends JDialog implements ActionListener {

    private RoundedTextField descriptionField, diagnosisField, quantityField, medicineField;
    private JTable issuedMedTable;
    private IssuedMedicineTableModel tableModel;
    private RoundedButton confirmButton, cancelButton;
    private JButton addBtn, removeBtn, editBtn;

    private PatientRecordTableModel recordModel;
    private Patient patientToUpdate;

    public RecordAddDialog(PatientRecordTableModel recordModel) {
        this(recordModel, null);
    }

    public RecordAddDialog(PatientRecordTableModel recordModel, Patient patientToUpdate) {
        super((Frame) null, "ADD PATIENT RECORD", true);
        this.recordModel = recordModel;
        this.patientToUpdate = patientToUpdate;

        setSize(450, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JLabel header = new JLabel("ADD PATIENT RECORD");
        header.setOpaque(true);
        header.setBackground(new Color(143, 186, 229));
        header.setBorder(BorderFactory.createLineBorder(header.getBackground(),4));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel topCenterPanel = new JPanel();
        topCenterPanel.setLayout(new BoxLayout(topCenterPanel, BoxLayout.Y_AXIS));

            topCenterPanel.add(Box.createVerticalStrut(5));

            JPanel descriptionPanel = new JPanel(new BorderLayout(0,2));
            descriptionPanel.setMaximumSize(new Dimension(410,50));

                JLabel descLbl= new JLabel("Description:" );
                descLbl.setFont(new Font("Arial", Font.BOLD, 14));
                descLbl.setHorizontalAlignment(JLabel.LEFT);
                descriptionPanel.add(descLbl, BorderLayout.NORTH );

                descriptionField = new RoundedTextField();
                descriptionField.setPreferredSize(new Dimension(200,25));
                descriptionPanel.add(descriptionField, BorderLayout.CENTER);
            topCenterPanel.add(descriptionPanel);

            JPanel diagnosisPanel = new JPanel(new BorderLayout(0,2));
            diagnosisPanel.setMaximumSize(new Dimension(410,50));

                JLabel diagnosisLbl= new JLabel("Diagnosis:" );
                diagnosisLbl.setFont(new Font("Arial", Font.BOLD, 14));
                diagnosisLbl.setHorizontalAlignment(JLabel.LEFT);
                diagnosisPanel.add(diagnosisLbl, BorderLayout.NORTH );

                diagnosisField = new RoundedTextField();
                diagnosisField.setPreferredSize(new Dimension(200,25));
                diagnosisPanel.add(diagnosisField, BorderLayout.CENTER);
            topCenterPanel.add(diagnosisPanel);
            
            topCenterPanel.add(Box.createVerticalStrut(10));

                                JSeparator jSeparator = new JSeparator(JSeparator.HORIZONTAL);
                                jSeparator.setPreferredSize(new Dimension(310, 10));
                                topCenterPanel.add(jSeparator);

            JPanel medicinePanel = new JPanel(new BorderLayout(0,2));
            medicinePanel.setMaximumSize(new Dimension(410,50));

                JLabel medicineLbl= new JLabel("Medicine Name:" );
                medicineLbl.setFont(new Font("Arial", Font.BOLD, 14));
                medicineLbl.setHorizontalAlignment(JLabel.LEFT);
                medicinePanel.add(medicineLbl, BorderLayout.NORTH );

                medicineField = new RoundedTextField();
                medicineField.setPreferredSize(new Dimension(200,25));
                medicinePanel.add(medicineField, BorderLayout.CENTER);
            topCenterPanel.add(medicinePanel);

            JPanel qtyPanel = new JPanel(new BorderLayout(0,2));
            qtyPanel.setMaximumSize(new Dimension(410,50));
                JLabel qtyLbl= new JLabel("Quantity:" );
                qtyLbl.setFont(new Font("Arial", Font.BOLD, 14));
                qtyLbl.setHorizontalAlignment(JLabel.LEFT);
                qtyPanel.add(qtyLbl, BorderLayout.NORTH );

                quantityField = new RoundedTextField();
                quantityField.setPreferredSize(new Dimension(200,25));
                qtyPanel.add(quantityField, BorderLayout.CENTER);
            topCenterPanel.add(qtyPanel);

        centerPanel.add(topCenterPanel, BorderLayout.NORTH);

        tableModel = new IssuedMedicineTableModel();
        issuedMedTable = new JTable(tableModel);
        issuedMedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(issuedMedTable);
        tableScrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20,20,20,20),
            tableScrollPane.getBorder()
        ));
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        JPanel issuedMedControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

            addBtn = new JButton("Add");
            addBtn.setPreferredSize(new Dimension(90,25));
            issuedMedControlPanel.add(addBtn);
            
            editBtn = new JButton("Edit");
            editBtn.setPreferredSize(new Dimension(90,25));
            editBtn.setEnabled(false);
            issuedMedControlPanel.add(editBtn);
            
            removeBtn = new JButton("Remove");
            removeBtn.setPreferredSize(new Dimension(90,25));
            removeBtn.setEnabled(false);
            issuedMedControlPanel.add(removeBtn);
        centerPanel.add(issuedMedControlPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
            confirmButton = new RoundedButton("Confirm");
            confirmButton.setPreferredSize(new Dimension(120,35));
            confirmButton.setBackground(new Color(143,186,229));
            controlPanel.add(confirmButton);

            cancelButton = new RoundedButton("Cancel");
            cancelButton.setPreferredSize(new Dimension(120,35));
            cancelButton.setBackground(new Color(143,186,229));
            controlPanel.add(cancelButton);
        
        add(controlPanel, BorderLayout.SOUTH);

        addListeners();

        setResizable(false);
        setVisible(true);
    }

    private void addListeners(){
        issuedMedTable.getSelectionModel().addListSelectionListener(e->{
            if (issuedMedTable.getSelectedRowCount() == 0){
                editBtn.setEnabled(false);
                removeBtn.setEnabled(false);
                return;
            }
            editBtn.setEnabled(true);
            removeBtn.setEnabled(true);

        });
        addBtn.addActionListener(this);
        editBtn.addActionListener(this);
        removeBtn.addActionListener(this);
        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    private int showConfirmDialog(String message){
        return JOptionPane.showConfirmDialog(null,
        message, "Confirm", JOptionPane.YES_NO_OPTION);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == addBtn){
            MedicineService medService = MedicineService.getInstance();
            PatientService patientService = PatientService.getInstance();
            String nameInp = medicineField.getText().trim();
            String quantityInp = quantityField.getText().trim();
            IssuedMedicine pendingMedicine;
            Medicine med;

            if ((med = medService.getMedicineByName(nameInp)) == null){
                showError( nameInp + " medicine does not exist.");
                return;
            }

            if (!quantityInp.matches("\\d+")){
                showError("Please enter a valid quantity");
                return;
            }

            pendingMedicine = new IssuedMedicine(
                medService.generateIssuedMedicineId(),
                patientService.generateRecordId(),
                med.getId(),
                Integer.valueOf(quantityInp)
            );

            tableModel.add(pendingMedicine);
            
        }

        if (source == editBtn){

            String inp = JOptionPane.showInputDialog(null,"Enter new quantity: ",0);

            if (!inp.matches("\\d+")){
                showError("Please enter a valid quantity");
                return;
            }

            int selectedRowIndex = issuedMedTable.getSelectedRow();
            IssuedMedicine pendingMedicine = tableModel.getRow(selectedRowIndex); 
            pendingMedicine.setAmount(Integer.valueOf(inp));
            tableModel.fireTableDataChanged();
        }

        if (source == removeBtn){
            if (showConfirmDialog("Confirm removal?") == JOptionPane.YES_OPTION){
                tableModel.remove(issuedMedTable.getSelectedRow());
            }
        }

        if (source == confirmButton) {
            String description = descriptionField.getText().trim();
            String diagnosis = diagnosisField.getText().trim();
            ArrayList<IssuedMedicine> issuedMedicines = tableModel.getIssueMeds();

            int choice = JOptionPane.showConfirmDialog(this, "Do you want to save this record?", "CONFIRM SAVE", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                try {

                    PatientRecord record = new PatientRecord(
                        PatientService.getInstance().generateRecordId(),
                        patientToUpdate.getId(),
                        LocalDateTime.now(),
                        description,
                        diagnosis
                    );

                    PatientService.getInstance().add(record);

                    for (IssuedMedicine issuedMedicine : issuedMedicines) {
                        MedicineService.getInstance().issueMedicine(issuedMedicine.getMedicineId(), choice);
                    }

                    JOptionPane.showMessageDialog(this, "Record saved.", "", JOptionPane.INFORMATION_MESSAGE);
                    recordModel.replaceAll(
                        PatientService.getInstance().getRecordsByPatientId(patientToUpdate.getId())
                    );
                    dispose();
                } catch (DuplicateEntityException ex) {
                    showError(ex.getMessage());
                } catch (Exception ex) {
                    showError("An error occurred: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Record was not saved.", "", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (source == cancelButton) {
            if (
                showConfirmDialog("Confirm cancel?") == JOptionPane.YES_OPTION
            ){
                dispose();
            }
        }

    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "INPUT ERROR", JOptionPane.ERROR_MESSAGE);
    }


    static class RoundedTextField extends JTextField {

        public RoundedTextField(){
            super();
        }

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
