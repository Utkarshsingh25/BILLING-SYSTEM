# 🧾 Billing System – Java Swing GUI Application

A desktop-based Billing System built using Java Swing that allows users to manage items, calculate totals with tax and discounts, and handle exchange/refund operations.

## 🚀 Features

* Add, edit, and remove items from the billing table.
* Calculate and display total, tax, discount, and final bill.
* Exchange an item with another or process a refund.
* Non-editable table to avoid accidental changes.
* Interactive GUI for a smooth user experience.

## 🖥️ GUI Technologies

* Java Swing
* JTable, JTextField, JButton, JOptionPane, and JScrollPane

## 📦 How to Run

### Requirements:

* Java JDK 8 or higher
* Any IDE (e.g., IntelliJ, Eclipse, NetBeans) or command line

### Steps:

1. Save the file as `BillingSystem.java`.
2. Compile and run

Or simply run from your IDE.

## 🧮 Bill Calculation Logic

* **Total** = Σ(Item Price × Quantity)
* **Tax** = `(Tax Rate)%` of Total
* **Discount** = `(Discount Rate)%` on (Total + Tax)
* **Final Bill** = `(Total + Tax - Discount)`

## 🔁 Exchange/Refund Logic

* **Exchange**: Prompts user to update the item with a new name, price, and quantity.
* **Refund**: Removes item and shows refund value = `price × quantity`.

## 🛠️ To-Do / Enhancements

* Add saving/loading bills from file
* Generate printable invoice
* Add product search and filter
* Add validation messages for inputs

