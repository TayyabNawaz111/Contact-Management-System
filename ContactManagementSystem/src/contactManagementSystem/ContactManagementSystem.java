package contactManagementSystem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ContactManagementSystem {

    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<Contact> contacts;

    public static void main(String[] args) {
        new ContactManagementSystem();
    }

    public ContactManagementSystem() {
        contacts = new ArrayList<>();
        frame = new JFrame("Contact Management System");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton removeButton = new JButton("Remove");
        JButton searchButton = new JButton("Search");
        JButton viewAllButton = new JButton("View All");

        topPanel.add(addButton);
        topPanel.add(editButton);
        topPanel.add(removeButton);
        topPanel.add(searchButton);
        topPanel.add(viewAllButton);
        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"Name", "Phone", "Email", "Address"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> addContact());
        editButton.addActionListener(e -> editContact());
        removeButton.addActionListener(e -> removeContact());
        searchButton.addActionListener(e -> searchContact());
        viewAllButton.addActionListener(e -> viewAllContacts());

        frame.setVisible(true);
    }

    private void addContact() {
        JTextField nameField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField addressField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        int result = JOptionPane.showConfirmDialog(frame, panel, "Add Contact", JOptionPane.OK_CANCEL_OPTION);
        if (nameField.getText().isEmpty()|| phoneField.getText().isEmpty()) {
        	JOptionPane.showMessageDialog(frame, "Name and Phone are required fields!", "Error", JOptionPane.ERROR_MESSAGE);
        	return;
        }
        if (result == JOptionPane.OK_OPTION) {
            contacts.add(new Contact(nameField.getText(), phoneField.getText(), emailField.getText(), addressField.getText()));
            viewAllContacts();
        }
    }

    private void editContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Select a contact to edit.");
            return;
        }

        Contact contact = contacts.get(selectedRow);
        JTextField nameField = new JTextField(contact.name);
        JTextField phoneField = new JTextField(contact.phone);
        JTextField emailField = new JTextField(contact.email);
        JTextField addressField = new JTextField(contact.address);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Edit Contact", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            contact.name = nameField.getText();
            contact.phone = phoneField.getText();
            contact.email = emailField.getText();
            contact.address = addressField.getText();
            viewAllContacts();
        }
    }

    private void removeContact() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Select a contact to remove.");
            return;
        }

        contacts.remove(selectedRow);
        viewAllContacts();
    }

    private void searchContact() {
        String search = JOptionPane.showInputDialog(frame, "Enter name to search:");
        if (search != null && !search.isEmpty()) {
            model.setRowCount(0);
            for (Contact c : contacts) {
                if (c.name.equalsIgnoreCase(search)) {
                    model.addRow(new Object[]{c.name, c.phone, c.email, c.address});
                }
            }
        }
    }

    private void viewAllContacts() {
        model.setRowCount(0);
        for (Contact c : contacts) {
            model.addRow(new Object[]{c.name, c.phone, c.email, c.address});
        }
    }

    static class Contact {
        String name, phone, email, address;

        Contact(String name, String phone, String email, String address) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.address = address;
        }
    }
}
