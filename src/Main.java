import Attendees.Attendee;
import Events.Concert;
import Events.Event;
import Events.SportsMatch;
import Events.WorkShop;
import Exceptions.DuplicateScannedTicketException;
import Exceptions.EventCapacityExceededException;
import Tickets.RegularTicket;
import Tickets.StudentTicket;
import Tickets.Ticket;
import Tickets.VipTickets;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventGui extends JFrame {
    public class EventAppGUI extends JFrame {
        // Store multiple events created by the user
        private List<Event> eventList;

        // UI Components for Create Event
        private JTextField eventIdField, titleField, venueField, capacityField, priceField, extraDetailField;
        private JComboBox<String> eventTypeBox;

        // UI Components for Buy Tickets
        private JComboBox<Event> buyEventSelector;
        private JTextField nameField, emailField, idField;
        private JComboBox<String> attendeeTypeBox, ticketTypeBox;

        // UI Components for Gate Check-In
        private JComboBox<Event> checkInEventSelector;
        private JTextField checkInField;

        // UI Components for Reports
        private JComboBox<Event> reportEventSelector;
        private JTextArea reportLogArea;

        public EventAppGUI() {
            eventList = new ArrayList<>();

            // Add a default sample event so the app isn't empty on launch
            Event defaultEvent = new Concert(
                    "EVT-001", "Spring Campus Fest", "Main Auditorium",
                    LocalDateTime.of(2026, 5, 20, 18, 0), 3, 50.0, "The University Band"
            );
            eventList.add(defaultEvent);

            setTitle("Campus Event & Ticketing Platform - Admin & Gate");
            setSize(750, 550);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            // Create Tabbed Pane with 4 Tabs
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Create Event", createEventPanel());
            tabbedPane.addTab("Buy Tickets", createBuyTicketPanel());
            tabbedPane.addTab("Gate Check-In", createCheckInPanel());
            tabbedPane.addTab("Reports", createReportPanel());

            add(tabbedPane);
        }

        // --- TAB 1: CREATE EVENT PANEL ---
        private JPanel createEventPanel() {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(6, 6, 6, 6);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel header = new JLabel("<html><h3>Create a New Campus Event</h3>Fill in the details to publish a new event:</html>");
            gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
            panel.add(header, gbc);

            // Event Type
            gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
            panel.add(new JLabel("Event Category Type:"), gbc);
            eventTypeBox = new JComboBox<>(new String[]{"Concert", "Workshop", "Sports Match"});
            gbc.gridx = 1;
            panel.add(eventTypeBox, gbc);

            // Event ID
            gbc.gridy = 2; gbc.gridx = 0;
            panel.add(new JLabel("Event ID (e.g. EVT-002):"), gbc);
            eventIdField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(eventIdField, gbc);

            // Title
            gbc.gridy = 3; gbc.gridx = 0;
            panel.add(new JLabel("Event Title:"), gbc);
            titleField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(titleField, gbc);

            // Venue
            gbc.gridy = 4; gbc.gridx = 0;
            panel.add(new JLabel("Venue:"), gbc);
            venueField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(venueField, gbc);

            // Capacity
            gbc.gridy = 5; gbc.gridx = 0;
            panel.add(new JLabel("Maximum Capacity:"), gbc);
            capacityField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(capacityField, gbc);

            // Base Price
            gbc.gridy = 6; gbc.gridx = 0;
            panel.add(new JLabel("Base Ticket Price ($):"), gbc);
            priceField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(priceField, gbc);

            // Extra Detail (Headliner / Topic / Opposing Team)
            gbc.gridy = 7; gbc.gridx = 0;
            panel.add(new JLabel("Extra Detail (Artist/Topic/Team):"), gbc);
            extraDetailField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(extraDetailField, gbc);

            // Submit Button
            JButton createButton = new JButton("Publish Event");
            createButton.setBackground(new Color(39, 174, 96));
            createButton.setForeground(Color.BLACK);
            gbc.gridy = 8; gbc.gridx = 0; gbc.gridwidth = 2;
            panel.add(createButton, gbc);

            createButton.addActionListener(e -> handleEventCreation());

            return panel;
        }

        // --- TAB 2: BUY TICKETS PANEL ---
        private JPanel createBuyTicketPanel() {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Select Event:"), gbc);

            buyEventSelector = new JComboBox<>();
            updateEventSelectors(); // Populate dropdown
            gbc.gridx = 1;
            panel.add(buyEventSelector, gbc);

            gbc.gridy = 1; gbc.gridx = 0;
            panel.add(new JLabel("Attendee Name:"), gbc);
            nameField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(nameField, gbc);

            gbc.gridy = 2; gbc.gridx = 0;
            panel.add(new JLabel("Student/Staff ID:"), gbc);
            idField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(idField, gbc);

            gbc.gridy = 3; gbc.gridx = 0;
            panel.add(new JLabel("Email Address:"), gbc);
            emailField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(emailField, gbc);

            gbc.gridy = 4; gbc.gridx = 0;
            panel.add(new JLabel("Attendee Category:"), gbc);
            attendeeTypeBox = new JComboBox<>(new String[]{"STUDENT", "STAFF", "GUEST"});
            gbc.gridx = 1;
            panel.add(attendeeTypeBox, gbc);

            gbc.gridy = 5; gbc.gridx = 0;
            panel.add(new JLabel("Ticket Tier:"), gbc);
            ticketTypeBox = new JComboBox<>(new String[]{"Regular (Standard)", "VIP (50% Markup)", "Student (30% Discount)"});
            gbc.gridx = 1;
            panel.add(ticketTypeBox, gbc);

            JButton buyButton = new JButton("Issue Ticket");
            buyButton.setBackground(new Color(41, 128, 185));
            buyButton.setForeground(Color.BLACK);
            gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
            panel.add(buyButton, gbc);

            buyButton.addActionListener(e -> handleTicketPurchase());

            return panel;
        }

        // --- TAB 3: GATE CHECK-IN PANEL ---
        private JPanel createCheckInPanel() {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            gbc.gridx = 0; gbc.gridy = 0;
            panel.add(new JLabel("Select Event:"), gbc);
            checkInEventSelector = new JComboBox<>();
            gbc.gridx = 1;
            panel.add(checkInEventSelector, gbc);

            gbc.gridy = 1; gbc.gridx = 0;
            panel.add(new JLabel("Ticket Code:"), gbc);
            checkInField = new JTextField(15);
            gbc.gridx = 1;
            panel.add(checkInField, gbc);

            JButton scanButton = new JButton("Verify & Check-In");
            scanButton.setBackground(new Color(211, 84, 0));
            scanButton.setForeground(Color.BLACK);
            gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2;
            panel.add(scanButton, gbc);

            scanButton.addActionListener(e -> handleGateCheckIn());

            return panel;
        }

        // --- TAB 4: REPORTS PANEL ---
        private JPanel createReportPanel() {
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel topSubPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topSubPanel.add(new JLabel("Select Event for Report:"));
            reportEventSelector = new JComboBox<>();
            topSubPanel.add(reportEventSelector);
            panel.add(topSubPanel, BorderLayout.NORTH);

            reportLogArea = new JTextArea();
            reportLogArea.setEditable(false);
            reportLogArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            panel.add(new JScrollPane(reportLogArea), BorderLayout.CENTER);

            JButton generateBtn = new JButton("Generate & Export Text Report");
            generateBtn.setBackground(new Color(142, 68, 173));
            generateBtn.setForeground(Color.BLACK);
            panel.add(generateBtn, BorderLayout.SOUTH);

            generateBtn.addActionListener(e -> {
                Event selectedEvent = (Event) reportEventSelector.getSelectedItem();
                if (selectedEvent == null) {
                    JOptionPane.showMessageDialog(this, "No event selected!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String path = selectedEvent.getTitle().replaceAll("\\s+", "_") + "_report.txt";
                selectedEvent.generateReport(path);

                long sold = selectedEvent.getIssuedTickets().size();
                long attended = selectedEvent.getIssuedTickets().stream().filter(Ticket::isScanned).count();
                double revenue = selectedEvent.getIssuedTickets().stream().mapToDouble(Ticket::calculatePrice).sum();

                reportLogArea.setText(String.format(
                        "=== REPORT GENERATED ===\nFile saved as: %s\nEvent: %s\nCategory: %s\nTickets Sold: %d / %d\nChecked-In: %d\nTotal Revenue: $%.2f\n",
                        path, selectedEvent.getTitle(), selectedEvent.getCategory(), sold, selectedEvent.getCapacity(), attended, revenue
                ));
            });

            return panel;
        }

        // --- HELPER METHODS ---
        private void updateEventSelectors() {
            // Only update dropdowns if they have already been initialized
            if (buyEventSelector != null) {
                buyEventSelector.removeAllItems();
            }
            if (checkInEventSelector != null) {
                checkInEventSelector.removeAllItems();
            }
            if (reportEventSelector != null) {
                reportEventSelector.removeAllItems();
            }

            // Populate them if they exist
            for (Event ev : eventList) {
                if (buyEventSelector != null) buyEventSelector.addItem(ev);
                if (checkInEventSelector != null) checkInEventSelector.addItem(ev);
                if (reportEventSelector != null) reportEventSelector.addItem(ev);
            }
        }

        private void handleEventCreation() {
            try {
                String type = (String) eventTypeBox.getSelectedItem();
                String id = eventIdField.getText().trim();
                String title = titleField.getText().trim();
                String venue = venueField.getText().trim();
                int capacity = Integer.parseInt(capacityField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());
                String detail = extraDetailField.getText().trim();

                if (id.isEmpty() || title.isEmpty() || venue.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all text fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Event newEvent;
                LocalDateTime now = LocalDateTime.now().plusDays(7); // Default future date

                if ("Concert".equals(type)) {
                    newEvent = new Concert(id, title, venue, now, capacity, price, detail);
                } else if ("Workshop".equals(type)) {
                    newEvent = new WorkShop(id, title, venue, now, capacity, price, "Instructor", detail);
                } else {
                    newEvent = new SportsMatch(id, title, venue, now, capacity, price, "Home Team", detail, "Football");
                }

                eventList.add(newEvent);
                updateEventSelectors();

                JOptionPane.showMessageDialog(this, "Event '" + title + "' published successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Clear inputs
                eventIdField.setText("");
                titleField.setText("");
                venueField.setText("");
                capacityField.setText("");
                priceField.setText("");
                extraDetailField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Capacity must be an integer and Price must be a valid number!", "Format Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void handleTicketPurchase() {
            Event selectedEvent = (Event) buyEventSelector.getSelectedItem();
            if (selectedEvent == null) {
                JOptionPane.showMessageDialog(this, "Please select an event first!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String name = nameField.getText().trim();
            String id = idField.getText().trim();
            String email = emailField.getText().trim();

            if (name.isEmpty() || id.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all attendee fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Attendee.Type type = Attendee.Type.valueOf((String) attendeeTypeBox.getSelectedItem());
            Attendee attendee = new Attendee(id, name, email, type);

            String ticketChoice = (String) ticketTypeBox.getSelectedItem();
            Ticket newTicket;

            if (ticketChoice.startsWith("VIP")) {
                newTicket = new VipTickets(attendee, selectedEvent.getBaseTicketPrice());
            } else if (ticketChoice.startsWith("Student")) {
                newTicket = new StudentTicket(attendee, selectedEvent.getBaseTicketPrice());
            } else {
                newTicket = new RegularTicket(attendee, selectedEvent.getBaseTicketPrice());
            }

            try {
                selectedEvent.issuedTicket(newTicket);
                JOptionPane.showMessageDialog(this,
                        "Ticket Issued Successfully!\nCode: " + newTicket.getTicketCode() + "\nPrice: $" + newTicket.calculatePrice(),
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                nameField.setText("");
                idField.setText("");
                emailField.setText("");

            } catch (EventCapacityExceededException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Capacity Exceeded!", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void handleGateCheckIn() {
            Event selectedEvent = (Event) checkInEventSelector.getSelectedItem();
            if (selectedEvent == null) {
                JOptionPane.showMessageDialog(this, "Please select an event!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String code = checkInField.getText().trim().toUpperCase();
            if (code.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a ticket code.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

                boolean success = selectedEvent.isScanned(code);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Gate Access Granted!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                    checkInField.setText("");
                }
        }
    }
    void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new EventAppGUI().setVisible(true);
        });
    }
}
