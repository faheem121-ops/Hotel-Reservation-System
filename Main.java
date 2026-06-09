import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Main extends JFrame {

    JLabel lblUser, lblPass;
    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin;

    public Main() {
        setTitle("Hotel Reservation System - Login");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        lblUser = new JLabel("Username:");
        lblUser.setBounds(50, 50, 100, 30);
        add(lblUser);

        txtUser = new JTextField();
        txtUser.setBounds(150, 50, 150, 30);
        add(txtUser);

        lblPass = new JLabel("Password:");
        lblPass.setBounds(50, 100, 100, 30);
        add(lblPass);

        txtPass = new JPasswordField();
        txtPass.setBounds(150, 100, 150, 30);
        add(txtPass);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 160, 100, 30);
        add(btnLogin);

        btnLogin.addActionListener(e -> {
            String username = txtUser.getText();
            String password = new String(txtPass.getPassword());

            if(username.equals("admin") && password.equals("1234")) {
                JOptionPane.showMessageDialog(null, "Welcome " + username + "!");
                dispose();
                new HotelDashboard();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}

// Room Class
class Room {
    int roomNumber;
    String type; // Single, Double, Suite
    double price;
    boolean isAvailable;
    String customerName;
    String checkInDate;
    String checkOutDate;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isAvailable = true;
        this.customerName = "";
        this.checkInDate = "";
        this.checkOutDate = "";
    }

    public String toString() {
        return "Room " + roomNumber + " - " + type + " - $" + price + "/night" + (isAvailable ? " (Available)" : " (Booked by " + customerName + ")");
    }
}

// Customer Class
class Customer {
    int id;
    String name;
    String phone;
    String email;
    String address;

    public Customer(int id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String toString() {
        return id + " - " + name + " (" + phone + ")";
    }
}

// Booking Class (only ONE definition, includes toString)
class Booking {
    int bookingId;
    Room room;
    Customer customer;
    int nights;
    double totalAmount;
    String bookingDate;

    public Booking(int bookingId, Room room, Customer customer, int nights, double totalAmount) {
        this.bookingId = bookingId;
        this.room = room;
        this.customer = customer;
        this.nights = nights;
        this.totalAmount = totalAmount;
        this.bookingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    @Override
    public String toString() {
        return "Booking #" + bookingId + " - " + customer.name + " - Room " + room.roomNumber + " - $" + totalAmount;
    }
}

// Hotel Dashboard
class HotelDashboard extends JFrame {
    JButton btnBookRoom, btnViewRooms, btnCustomers, btnPayments, btnLogout;
    JLabel welcomeLabel;
    
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Customer> customers = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static int customerIdCounter = 1;

    public HotelDashboard() {
        initializeRooms();

        setTitle("Hotel Reservation System - Dashboard");
        setSize(700, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        welcomeLabel = new JLabel("Welcome to Hotel Reservation System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setBounds(200, 20, 350, 30);
        add(welcomeLabel);

        btnBookRoom = new JButton("📅 Book Room");
        btnBookRoom.setBounds(250, 80, 200, 50);
        btnBookRoom.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnBookRoom);

        btnViewRooms = new JButton("👁️ View Available Rooms");
        btnViewRooms.setBounds(250, 150, 200, 50);
        btnViewRooms.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnViewRooms);

        btnCustomers = new JButton("👥 Customer Management");
        btnCustomers.setBounds(250, 220, 200, 50);
        btnCustomers.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnCustomers);

        btnPayments = new JButton("💰 Payment");
        btnPayments.setBounds(250, 290, 200, 50);
        btnPayments.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnPayments);

        btnLogout = new JButton("🚪 Logout");
        btnLogout.setBounds(250, 360, 200, 50);
        btnLogout.setFont(new Font("Arial", Font.BOLD, 14));
        add(btnLogout);

        btnBookRoom.addActionListener(e -> new BookRoomDialog(this));
        btnViewRooms.addActionListener(e -> showAvailableRooms());
        btnCustomers.addActionListener(e -> new CustomerManagementDialog(this));
        btnPayments.addActionListener(e -> new PaymentDialog(this));
        btnLogout.addActionListener(e -> {
            dispose();
            new Main();
        });

        setVisible(true);
    }

    private void initializeRooms() {
        rooms.add(new Room(101, "Single", 100));
        rooms.add(new Room(102, "Single", 100));
        rooms.add(new Room(103, "Double", 150));
        rooms.add(new Room(104, "Double", 150));
        rooms.add(new Room(105, "Suite", 250));
        rooms.add(new Room(106, "Suite", 250));
        rooms.add(new Room(107, "Single", 100));
        rooms.add(new Room(108, "Double", 150));
    }

    private void showAvailableRooms() {
        JFrame frame = new JFrame("Available Rooms");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        String[] columns = {"Room No", "Type", "Price/Night", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        for (Room room : rooms) {
            Object[] row = {
                room.roomNumber,
                room.type,
                "$" + room.price,
                room.isAvailable ? "Available" : "Booked by " + room.customerName
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.setVisible(true);
    }
}

// Book Room Dialog
class BookRoomDialog extends JDialog {
    JComboBox<String> roomCombo;
    JComboBox<Customer> customerCombo;
    JTextField nightsField;
    JButton bookButton, addCustomerButton;
    HotelDashboard dashboard;

    public BookRoomDialog(HotelDashboard dashboard) {
        this.dashboard = dashboard;
        setTitle("Book a Room");
        setSize(450, 350);
        setLayout(null);
        setModal(true);
        setLocationRelativeTo(null);

        JLabel roomLabel = new JLabel("Select Room:");
        roomLabel.setBounds(50, 30, 100, 30);
        add(roomLabel);

        roomCombo = new JComboBox<>();
        for (Room room : HotelDashboard.rooms) {
            if (room.isAvailable) {
                roomCombo.addItem(room.toString());
            }
        }
        roomCombo.setBounds(180, 30, 200, 30);
        add(roomCombo);

        JLabel customerLabel = new JLabel("Select Customer:");
        customerLabel.setBounds(50, 80, 100, 30);
        add(customerLabel);

        customerCombo = new JComboBox<>();
        updateCustomerCombo();
        customerCombo.setBounds(180, 80, 200, 30);
        add(customerCombo);

        addCustomerButton = new JButton("+ New Customer");
        addCustomerButton.setBounds(390, 80, 130, 30);
        add(addCustomerButton);

        JLabel nightsLabel = new JLabel("Number of Nights:");
        nightsLabel.setBounds(50, 130, 130, 30);
        add(nightsLabel);

        nightsField = new JTextField();
        nightsField.setBounds(180, 130, 100, 30);
        add(nightsField);

        bookButton = new JButton("Confirm Booking");
        bookButton.setBounds(150, 200, 150, 40);
        bookButton.setFont(new Font("Arial", Font.BOLD, 14));
        add(bookButton);

        addCustomerButton.addActionListener(e -> {
            new AddCustomerDialog(dashboard, this);
            updateCustomerCombo();
        });

        bookButton.addActionListener(e -> confirmBooking());

        setVisible(true);
    }

    private void updateCustomerCombo() {
        customerCombo.removeAllItems();
        for (Customer c : HotelDashboard.customers) {
            customerCombo.addItem(c);
        }
        if (HotelDashboard.customers.isEmpty()) {
            customerCombo.addItem(new Customer(0, "No customers - Add one", "", "", ""));
        }
    }

    private void confirmBooking() {
        int selectedIndex = roomCombo.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "No available rooms!");
            return;
        }
        
        // Find the selected room object
        Room selectedRoom = null;
        int count = 0;
        for (Room room : HotelDashboard.rooms) {
            if (room.isAvailable) {
                if (count == selectedIndex) {
                    selectedRoom = room;
                    break;
                }
                count++;
            }
        }
        
        Customer selectedCustomer = (Customer) customerCombo.getSelectedItem();
        String nightsText = nightsField.getText();

        if (selectedCustomer.id == 0) {
            JOptionPane.showMessageDialog(this, "Please add a customer first!");
            return;
        }

        if (nightsText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter number of nights!");
            return;
        }

        try {
            int nights = Integer.parseInt(nightsText);
            double total = selectedRoom.price * nights;

            int confirm = JOptionPane.showConfirmDialog(this,
                "Booking Details:\n" +
                "Room: " + selectedRoom.roomNumber + " (" + selectedRoom.type + ")\n" +
                "Customer: " + selectedCustomer.name + "\n" +
                "Nights: " + nights + "\n" +
                "Total Amount: $" + total + "\n\n" +
                "Confirm booking?",
                "Confirm Booking",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                selectedRoom.isAvailable = false;
                selectedRoom.customerName = selectedCustomer.name;
                
                Booking booking = new Booking(
                    HotelDashboard.bookings.size() + 1,
                    selectedRoom,
                    selectedCustomer,
                    nights,
                    total
                );
                HotelDashboard.bookings.add(booking);
                
                JOptionPane.showMessageDialog(this, 
                    "Booking Successful!\n" +
                    "Booking ID: " + booking.bookingId + "\n" +
                    "Total Amount: $" + total);
                dispose();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of nights!");
        }
    }
}

// Add Customer Dialog
class AddCustomerDialog extends JDialog {
    JTextField nameField, phoneField, emailField, addressField;
    JButton saveButton;
    HotelDashboard dashboard;

    public AddCustomerDialog(HotelDashboard dashboard, JDialog parent) {
        this.dashboard = dashboard;
        setTitle("Add New Customer");
        setSize(400, 350);
        setLayout(null);
        setModal(true);
        setLocationRelativeTo(parent);

        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(50, 30, 100, 30);
        add(nameLabel);
        
        nameField = new JTextField();
        nameField.setBounds(160, 30, 180, 30);
        add(nameField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 80, 100, 30);
        add(phoneLabel);
        
        phoneField = new JTextField();
        phoneField.setBounds(160, 80, 180, 30);
        add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 130, 100, 30);
        add(emailLabel);
        
        emailField = new JTextField();
        emailField.setBounds(160, 130, 180, 30);
        add(emailField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 180, 100, 30);
        add(addressLabel);
        
        addressField = new JTextField();
        addressField.setBounds(160, 180, 180, 30);
        add(addressField);

        saveButton = new JButton("Save Customer");
        saveButton.setBounds(120, 240, 150, 40);
        add(saveButton);

        saveButton.addActionListener(e -> saveCustomer());

        setVisible(true);
    }

    private void saveCustomer() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter customer name!");
            return;
        }

        Customer customer = new Customer(
            HotelDashboard.customerIdCounter++,
            name,
            phone,
            email,
            address
        );
        HotelDashboard.customers.add(customer);
        
        JOptionPane.showMessageDialog(this, "Customer added successfully!\nCustomer ID: " + customer.id);
        dispose();
    }
}

// Customer Management Dialog
class CustomerManagementDialog extends JDialog {
    JTable customerTable;
    DefaultTableModel tableModel;
    JButton deleteButton;

    public CustomerManagementDialog(HotelDashboard dashboard) {
        setTitle("Customer Management");
        setSize(700, 500);
        setModal(true);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = {"ID", "Name", "Phone", "Email", "Address"};
        tableModel = new DefaultTableModel(columns, 0);
        
        refreshTable();

        customerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        deleteButton = new JButton("Delete Selected Customer");
        deleteButton.addActionListener(e -> deleteCustomer());
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Customer c : HotelDashboard.customers) {
            Object[] row = {c.id, c.name, c.phone, c.email, c.address};
            tableModel.addRow(row);
        }
    }

    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow >= 0) {
            int customerId = (int) tableModel.getValueAt(selectedRow, 0);
            HotelDashboard.customers.removeIf(c -> c.id == customerId);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Customer deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete!");
        }
    }
}

// Payment Dialog
class PaymentDialog extends JDialog {
    JComboBox<Booking> bookingCombo;
    JLabel totalLabel, paymentStatusLabel;
    JButton payButton;
    HotelDashboard dashboard;

    public PaymentDialog(HotelDashboard dashboard) {
        this.dashboard = dashboard;
        setTitle("Process Payment");
        setSize(450, 300);
        setLayout(null);
        setModal(true);
        setLocationRelativeTo(null);

        JLabel bookingLabel = new JLabel("Select Booking:");
        bookingLabel.setBounds(50, 30, 120, 30);
        add(bookingLabel);

        bookingCombo = new JComboBox<>();
        for (Booking b : HotelDashboard.bookings) {
            bookingCombo.addItem(b);
        }
        bookingCombo.setBounds(180, 30, 200, 30);
        add(bookingCombo);

        JLabel totalTitle = new JLabel("Total Amount:");
        totalTitle.setBounds(50, 80, 120, 30);
        add(totalTitle);

        totalLabel = new JLabel("$0");
        totalLabel.setBounds(180, 80, 150, 30);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(totalLabel);

        paymentStatusLabel = new JLabel("");
        paymentStatusLabel.setBounds(50, 120, 300, 30);
        add(paymentStatusLabel);

        payButton = new JButton("Process Payment");
        payButton.setBounds(130, 180, 180, 40);
        payButton.setFont(new Font("Arial", Font.BOLD, 14));
        add(payButton);

        bookingCombo.addActionListener(e -> updateTotal());
        payButton.addActionListener(e -> processPayment());

        updateTotal();
        setVisible(true);
    }

    private void updateTotal() {
        Booking selected = (Booking) bookingCombo.getSelectedItem();
        if (selected != null) {
            totalLabel.setText("$" + selected.totalAmount);
        }
    }

    private void processPayment() {
        Booking selected = (Booking) bookingCombo.getSelectedItem();
        if (selected != null) {
            paymentStatusLabel.setText("✓ Payment of $" + selected.totalAmount + " processed successfully!");
            paymentStatusLabel.setForeground(Color.GREEN);
            JOptionPane.showMessageDialog(this, 
                "Payment Successful!\n" +
                "Booking ID: " + selected.bookingId + "\n" +
                "Amount Paid: $" + selected.totalAmount + "\n" +
                "Thank you for choosing our hotel!");
        }
    }
}