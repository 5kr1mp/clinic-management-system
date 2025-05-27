package com.usep.clinic.management.system.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecordPatientView extends JDialog {

    public RecordPatientView(Frame owner) {
        super(owner, "Patient Record Details", true); // modal dialog
        setSize(430, 500);
        setLayout(null);
        setLocationRelativeTo(owner);
        getContentPane().setBackground(new Color(245, 248, 255));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel dateLabel = new JLabel("DATE:");
        dateLabel.setBounds(30, 20, 100, 25);
        add(dateLabel);

        JLabel dateValue = new JLabel("time");
        dateValue.setBounds(30, 40, 200, 25);
        add(dateValue);

        JLabel descLabel = new JLabel("DESCRIPTION:");
        descLabel.setBounds(30, 60, 150, 25);
        add(descLabel);

        JTextArea descArea = new JTextArea();
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBounds(30, 90, 350, 60);
        add(descScroll);

        JLabel diagLabel = new JLabel("DIAGNOSIS:");
        diagLabel.setBounds(30, 160, 150, 25);
        add(diagLabel);

        JTextArea diagArea = new JTextArea();
        diagArea.setLineWrap(true);
        diagArea.setWrapStyleWord(true);
        JScrollPane diagScroll = new JScrollPane(diagArea);
        diagScroll.setBounds(30, 190, 350, 60);
        add(diagScroll);

        String[] columns = {"Medicine", "Quantity"};
        Object[][] data = {
                {"Paracetamol", 2},
                {"Ibuprofen", 1}
        };
        JTable table = new JTable(new DefaultTableModel(data, columns)) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBounds(30, 270, 350, 100);
        add(tableScroll);

        RoundedButton backBtn = new RoundedButton("BACK");
        backBtn.setBackground(new Color(149, 186, 229));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBounds(150, 390, 100, 30);
        add(backBtn);

        backBtn.addActionListener(e -> dispose());

        setVisible(true);
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
