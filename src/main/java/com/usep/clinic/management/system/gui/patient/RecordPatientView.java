package com.usep.clinic.management.system.gui.patient;

import com.usep.clinic.management.system.model.IssuedMedicine;
import com.usep.clinic.management.system.model.PatientRecord;
import com.usep.clinic.management.system.service.MedicineService;
import com.usep.clinic.management.system.util.DateTimeFormat;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class RecordPatientView extends JDialog {

    private JLabel date;
    private JTextArea descArea;
    private JTextArea diagArea;
    private JTable medicineTable;
    private PatientRecord record;

    public RecordPatientView(Frame owner, PatientRecord record) {
        super(owner, "Patient Record", true);
        this.record = record;

        setSize(850, 565);
        setLayout(new BorderLayout());
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(245, 248, 255));

        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createLineBorder(new Color(242, 242, 242), 4));
        JLabel titleLabel = new JLabel("PATIENT RECORD");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground(new Color(143, 186, 229));
        titleLabel.setOpaque(true);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(titleLabel, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        contentPanel.setOpaque(false);

        JLabel dateLabel = new JLabel("DATE");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 12));
        date = new JLabel(DateTimeFormat.formatDateTime(record.getDateTime()));
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setOpaque(false);
        datePanel.add(dateLabel);
        datePanel.add(date);
        contentPanel.add(datePanel);

        JLabel descLabel = new JLabel("DESCRIPTION");
        descLabel.setFont(new Font("Arial", Font.BOLD, 12));
        descArea = new JTextArea(3, 40);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setText(record.getDesc());
        JScrollPane descScroll = new JScrollPane(descArea);
        contentPanel.add(descLabel);
        contentPanel.add(descScroll);

        JLabel diagLabel = new JLabel("DIAGNOSIS");
        diagLabel.setFont(new Font("Arial", Font.BOLD, 12));
        diagArea = new JTextArea(3, 40);
        diagArea.setText(record.getDiagnosis());
        diagArea.setLineWrap(true);
        diagArea.setWrapStyleWord(true);
        diagArea.setEditable(false);
        JScrollPane diagScroll = new JScrollPane(diagArea);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(diagLabel);
        contentPanel.add(diagScroll);

        JLabel medLabel = new JLabel("ISSUED MEDICINES");
        medLabel.setFont(new Font("Arial", Font.BOLD, 12));
        medicineTable = new JTable(new IssuedMedicineTableModel());
        medicineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScroll = new JScrollPane(medicineTable);
        tableScroll.setPreferredSize(new Dimension(400, 150));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(medLabel);
        contentPanel.add(tableScroll);

        add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        JButton backButton = new RoundedButton("BACK");
        backButton.setBackground(new Color(149, 186, 229));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(120, 30));
        backButton.addActionListener(e -> dispose());
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setIssuedMedicines(MedicineService.getInstance().getIssuedMedicinesByRecordId(record.getId()));
        setVisible(true);
    }

    public void setDate(String dateText) {
        date.setText(dateText);
    }


    public void setIssuedMedicines(List<IssuedMedicine> medicines) {
        IssuedMedicineTableModel model = (IssuedMedicineTableModel) medicineTable.getModel();
        model.replaceAll(medicines);
    }

    private static class IssuedMedicineTableModel extends AbstractTableModel {

        private final String[] columnNames = {"Medicine", "Quantity"};
        private java.util.List<IssuedMedicine> medicines = new java.util.ArrayList<>();

        @Override
        public int getRowCount() {
            return medicines.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            IssuedMedicine im = medicines.get(rowIndex);

            MedicineService service = MedicineService.getInstance();
            String name = "";
            try {
                name = service.getMedicine(medicines.get(rowIndex).getMedicineId()).getName();
            } catch (Exception ex){
                
            }

            return switch (columnIndex) {
                case 0 -> name;
                case 1 -> im.getAmount();
                default -> throw new IndexOutOfBoundsException("Invalid column index");
            };
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        public void addAll(List<IssuedMedicine> medicines) {
            this.medicines.addAll(medicines);
            fireTableDataChanged();
        }

        public void clear() {
            this.medicines.clear();
            fireTableDataChanged();
        }

        public void replaceAll(List<IssuedMedicine> medicines) {
            clear();
            addAll(medicines);
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
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
}
