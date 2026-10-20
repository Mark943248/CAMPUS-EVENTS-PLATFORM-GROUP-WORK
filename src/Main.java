import Attendees.Attendee;
import Events.Concert;
import Events.Event;
import Exceptions.DuplicateScannedTicketException;
import Exceptions.EventCapacityExceededException;
import Tickets.RegularTicket;
import Tickets.StudentTicket;
import Tickets.Ticket;
import Tickets.VipTickets;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class EventGui extends JFrame {
    private Event sampleEvent;

    // UI components
    private JTextField nameField, emailField, idField, checkInField;
    private JComboBox<String> attendeeTypeBox, ticketTypeBox;
    private JTextArea logArea;

    public EventGui() {
        sampleEvent = new Concert(1125553, "Spring Campus Fest", "Main Auditorium",
                LocalDateTime.of(2026, 5, 20, 18, 0), 3, 50.0, "The University Band");


        setTitle("MUT Events Platform");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center's the window

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Buy Tickets", createBuyTicketPanel());
        tabbedPane.addTab("Gate Check-In", createCheckInPanel());
        tabbedPane.addTab("Reports & Status", createReportPanel());

        add(tabbedPane);
    }

    private JPanel createBuyTicketPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header info showing active event details
        JLabel eventLabel = new JLabel("<html><b>Active Event:</b> " + sampleEvent.getTitle() +
                " | <b>Capacity:</b> " + sampleEvent.getCapacity() + " | <b>Base Price:</b> KSH" + sampleEvent.getBaseTicketPrice() + "</html>");
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(eventLabel, gbc);

        // Name Field
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Attendee Name:"), gbc);
        nameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // ID Field
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("Student/Staff ID:"), gbc);
        idField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        // Email Field
        gbc.gridy = 3; gbc.gridx = 0;
        panel.add(new JLabel("Email Address:"), gbc);
        emailField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        // Attendee Type Dropdown
        gbc.gridy = 4; gbc.gridx = 0;
        panel.add(new JLabel("Attendee Category:"), gbc);
        attendeeTypeBox = new JComboBox<>(new String[]{"STUDENT", "STAFF", "GUEST"});
        gbc.gridx = 1;
        panel.add(attendeeTypeBox, gbc);

        // Ticket Tier Dropdown
        gbc.gridy = 5; gbc.gridx = 0;
        panel.add(new JLabel("Ticket Tier:"), gbc);
        ticketTypeBox = new JComboBox<>(new String[]{"Regular (Standard)", "VIP (50% Markup)", "Student (30% Discount)"});
        gbc.gridx = 1;
        panel.add(ticketTypeBox, gbc);

        // Issue Ticket Button
        JButton buyButton = new JButton("Issue Ticket");
        buyButton.setBackground(new Color(13, 251, 113));
        buyButton.setForeground(new Color(24, 27, 26));
        gbc.gridy = 6; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(buyButton, gbc);

        // Connect button click to ticket logic
        buyButton.addActionListener(e -> handleTicketPurchase());

        return panel;
    }

    // --- TAB 3: REPORTS PANEL ---
    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton generateBtn = new JButton("Generate & Export Post-Event Report File");
        generateBtn.setBackground(new Color(166, 13, 227));
        generateBtn.setForeground(Color.BLACK);
        panel.add(generateBtn, BorderLayout.SOUTH);

        generateBtn.addActionListener(e -> {
            String path = "campus_event_report.txt";
            sampleEvent.generateReport(path);
            JOptionPane.showMessageDialog(this, "Report successfully exported to " + path, "Success", JOptionPane.INFORMATION_MESSAGE);

            // Display stats in text area
            long sold = sampleEvent.getIssuedTickets().size();
            long attended = sampleEvent.getIssuedTickets().stream().filter(Ticket::isScanned).count();
            double revenue = sampleEvent.getIssuedTickets().stream().mapToDouble(Ticket::calculatePrice).sum();

            logArea.setText(String.format(
                    "=== EVENT STATUS SUMMARY ===\nEvent: %s\nTickets Issued: %d / %s\nChecked-In: %d\nTotal Revenue: $%.2f\n",
                    sampleEvent.getTitle(), sold, sampleEvent.getCapacity(), attended, revenue
            ));
        });

        return panel;
    }

    private JPanel createCheckInPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel instructionLabel = new JLabel("<html><h3>Gate Security Scanner</h3>Enter or scan the unique 8-character ticket code below:</html>");
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(instructionLabel, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Ticket Code:"), gbc);

        checkInField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(checkInField, gbc);

        JButton scanButton = new JButton("Verify & Check-In");
        scanButton.setBackground(new Color(52, 152, 219));
        scanButton.setForeground(Color.WHITE);
        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2;
        panel.add(scanButton, gbc);

        // Connect button to check-in logic
        scanButton.addActionListener(e -> handleGateCheckIn());

        return panel;
    }

    private void handleGateCheckIn() {
        String code = checkInField.getText().trim().toUpperCase();

        if (code.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a ticket code.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean success = sampleEvent.isScanned(code);
        if (success) {
            JOptionPane.showMessageDialog(this, "Gate Access Granted! Checked in successfully.", "Welcome", JOptionPane.INFORMATION_MESSAGE);
            checkInField.setText("");
        }
    }

    /**
     * Handles the business logic when the 'Issue Ticket' button is clicked
     */
    private void handleTicketPurchase() {
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String email = emailField.getText().trim();

        // Simple validation check
        if (name.isEmpty() || id.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all attendee fields!", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Create the Attendee object
        Attendee.Type type = Attendee.Type.valueOf((String) attendeeTypeBox.getSelectedItem());
        Attendee attendee = new Attendee(id, name, email, type);

        // Determine ticket type based on dropdown selection
        String ticketChoice = (String) ticketTypeBox.getSelectedItem();
        Ticket newTicket;

        if (ticketChoice.startsWith("VIP")) {
            newTicket = new VipTickets(attendee, sampleEvent.getBaseTicketPrice());
        } else if (ticketChoice.startsWith("Student")) {
            newTicket = new StudentTicket(attendee, sampleEvent.getBaseTicketPrice());
        } else {
            newTicket = new RegularTicket(attendee, sampleEvent.getBaseTicketPrice());
        }

        // Try issuing the ticket (enforcing your custom capacity limit of 3!)
        try {
            sampleEvent.issuedTicket(newTicket);

            // Show success popup with the generated unique code
            JOptionPane.showMessageDialog(this,
                    "Ticket Issued Successfully!\n\n" +
                            "Code: " + newTicket.getTicketCode() + "\n" +
                            "Attendee: " + attendee.getName() + "\n" +
                            "Price: KSH" + newTicket.calculatePrice(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear input fields for the next attendee
            nameField.setText("");
            idField.setText("");
            emailField.setText("");

        } catch (EventCapacityExceededException ex) {
            // Popup error message when capacity (3) is exceeded
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Capacity Exceeded!", JOptionPane.ERROR_MESSAGE);
        }
    }

    void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new EventGui().setVisible(true);
        });
    }


}
