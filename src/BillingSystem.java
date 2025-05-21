import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BillingSystem extends JFrame {
    private JTextField itemNameField;
    private JTextField itemPriceField;
    private JTextField itemQuantityField;
    private JTextField taxRateField;
    private JTextField discountRateField;
    private JButton addButton, editButton, removeButton, generateBillButton, exchangeRefundButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private int editRow = -1;

    public BillingSystem() {
        setTitle("Billing System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Item Name
        JPanel itemNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemNamePanel.add(new JLabel("Item Name:"));
        itemNameField = new JTextField(20);
        itemNamePanel.add(itemNameField);
        inputPanel.add(itemNamePanel);

        // Item Price
        JPanel itemPricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemPricePanel.add(new JLabel("Item Price (Rs):"));
        itemPriceField = new JTextField(20);
        itemPricePanel.add(itemPriceField);
        inputPanel.add(itemPricePanel);

        // Item Quantity
        JPanel itemQuantityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        itemQuantityPanel.add(new JLabel("Item Quantity:"));
        itemQuantityField = new JTextField(20);
        itemQuantityPanel.add(itemQuantityField);
        inputPanel.add(itemQuantityPanel);

        // Tax Rate
        JPanel taxRatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        taxRatePanel.add(new JLabel("Tax Rate (%):"));
        taxRateField = new JTextField(10);
        taxRatePanel.add(taxRateField);
        inputPanel.add(taxRatePanel);

        // Discount Rate
        JPanel discountRatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        discountRatePanel.add(new JLabel("Discount Rate (%):"));
        discountRateField = new JTextField(10);
        discountRatePanel.add(discountRateField);
        inputPanel.add(discountRatePanel);

        // Add Button
        addButton = new JButton("Add Item");
        inputPanel.add(addButton);

        // Edit, Remove, and Exchange/Refund Buttons
        JPanel editRemovePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        editButton = new JButton("Edit Item");
        removeButton = new JButton("Remove Item");
        exchangeRefundButton = new JButton("Exchange/Refund");
        editRemovePanel.add(editButton);
        editRemovePanel.add(removeButton);
        editRemovePanel.add(exchangeRefundButton);
        inputPanel.add(editRemovePanel);

        // Table to display items
        String[] columnNames = {"Item Name", "Item Price (Rs)", "Item Quantity"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing by default
            }
        };
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Generate Bill Button
        generateBillButton = new JButton("Generate Bill");

        // Main Layout
        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(generateBillButton, BorderLayout.SOUTH);

        // Button Listeners
        addButton.addActionListener(new AddButtonListener());
        editButton.addActionListener(new EditButtonListener());
        removeButton.addActionListener(new RemoveButtonListener());
        exchangeRefundButton.addActionListener(new ExchangeRefundButtonListener());
        generateBillButton.addActionListener(new GenerateBillButtonListener());
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String itemName = itemNameField.getText();
            double itemPrice = Double.parseDouble(itemPriceField.getText());
            int itemQuantity = Integer.parseInt(itemQuantityField.getText());

            if (editRow == -1) {
                Object[] row = {itemName, "Rs " + itemPrice, itemQuantity};
                tableModel.addRow(row);
            } else {
                tableModel.setValueAt(itemName, editRow, 0);
                tableModel.setValueAt("Rs " + itemPrice, editRow, 1);
                tableModel.setValueAt(itemQuantity, editRow, 2);
                editRow = -1;
                addButton.setText("Add Item");
            }

            // Clear fields
            itemNameField.setText("");
            itemPriceField.setText("");
            itemQuantityField.setText("");
        }
    }

    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                editRow = selectedRow;
                itemNameField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                itemPriceField.setText(tableModel.getValueAt(selectedRow, 1).toString().replace("Rs ", "")); // Remove "Rs" prefix
                itemQuantityField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                addButton.setText("Update Item"); // Change button text to "Update Item"
                table.editCellAt(selectedRow, 0); // Enable editing for the selected row
            }
        }
    }

    private class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
            }
        }
    }

    private class ExchangeRefundButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String[] options = {"Exchange", "Refund"};
                int choice = JOptionPane.showOptionDialog(null, "Choose action:", "Exchange or Refund", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                if (choice == 0) { // Exchange
                    String itemName = tableModel.getValueAt(selectedRow, 0).toString();
                    double itemPrice = Double.parseDouble(tableModel.getValueAt(selectedRow, 1).toString().replace("Rs ", "")); // Remove "Rs" prefix
                    int itemQuantity = Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString());

                    JPanel exchangePanel = new JPanel(new GridLayout(0, 2));
                    exchangePanel.add(new JLabel("New Item Name:"));
                    JTextField newItemNameField = new JTextField(20);
                    exchangePanel.add(newItemNameField);
                    exchangePanel.add(new JLabel("New Item Price (Rs):"));
                    JTextField newItemPriceField = new JTextField(20);
                    exchangePanel.add(newItemPriceField);
                    exchangePanel.add(new JLabel("New Item Quantity:"));
                    JTextField newItemQuantityField = new JTextField(20);
                    exchangePanel.add(newItemQuantityField);

                    int result = JOptionPane.showConfirmDialog(null, exchangePanel, "Exchange Item", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (result == JOptionPane.OK_OPTION) {
                        String newItemName = newItemNameField.getText();
                        double newItemPrice = Double.parseDouble(newItemPriceField.getText());
                        int newItemQuantity = Integer.parseInt(newItemQuantityField.getText());

                        // Update table with new item details
                        tableModel.setValueAt(newItemName, selectedRow, 0);
                        tableModel.setValueAt("Rs " + newItemPrice, selectedRow, 1);
                        tableModel.setValueAt(newItemQuantity, selectedRow, 2);
                    }

                } else if (choice == 1) { // Refund
                    double refundedAmount = Double.parseDouble(tableModel.getValueAt(selectedRow, 1).toString().replace("Rs ", "")) * Integer.parseInt(tableModel.getValueAt(selectedRow, 2).toString());
                    JOptionPane.showMessageDialog(null, "Refund processed for Rs " + refundedAmount, "Refund Processed", JOptionPane.INFORMATION_MESSAGE);
                    tableModel.removeRow(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select an item to perform Exchange or Refund.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class GenerateBillButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double taxPercentage = Double.parseDouble(taxRateField.getText());
            double discountPercentage = Double.parseDouble(discountRateField.getText());

            DefaultTableModel billTableModel = new DefaultTableModel(new String[]{"Item Name", "Item Price (Rs)", "Item Quantity"}, 0);
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String itemName = tableModel.getValueAt(i, 0).toString();
                String itemPrice = tableModel.getValueAt(i, 1).toString().replace("Rs ", ""); // Remove "Rs" prefix
                String itemQuantity = tableModel.getValueAt(i, 2).toString();
                billTableModel.addRow(new Object[]{itemName, itemPrice, itemQuantity});
            }

            JTable billTable = new JTable(billTableModel);
            JScrollPane billScrollPane = new JScrollPane(billTable);

            double total = calculateTotal();
            double totalWithTax = total + (total * taxPercentage / 100);
            double finalTotal = totalWithTax - (totalWithTax * discountPercentage / 100);

            JTextArea billSummaryArea = new JTextArea();
            billSummaryArea.append(String.format("Total: Rs %.2f\n", total));
            billSummaryArea.append(String.format("Tax (%.2f%%): Rs %.2f\n", taxPercentage, total * taxPercentage / 100));
            billSummaryArea.append(String.format("Total with Tax: Rs %.2f\n", totalWithTax));
            billSummaryArea.append(String.format("Discount (%.2f%%): Rs %.2f\n", discountPercentage, totalWithTax * discountPercentage / 100));
            billSummaryArea.append(String.format("Final Total: Rs %.2f\n", finalTotal));

            JPanel billPanel = new JPanel(new BorderLayout());
            billPanel.add(billScrollPane, BorderLayout.CENTER);
            billPanel.add(billSummaryArea, BorderLayout.SOUTH);

            JFrame billFrame = new JFrame("Bill Summary");
            billFrame.setSize(600, 400);
            billFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            billFrame.add(billPanel);
            billFrame.setVisible(true);
        }

        private double calculateTotal() {
            double total = 0;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                double itemPrice = Double.parseDouble(tableModel.getValueAt(i, 1).toString().replace("Rs ", ""));
                int itemQuantity = Integer.parseInt(tableModel.getValueAt(i, 2).toString());
                total += itemPrice * itemQuantity;
            }
            return total;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BillingSystem billingSystem = new BillingSystem();
            billingSystem.setVisible(true);
        });
    }
}
