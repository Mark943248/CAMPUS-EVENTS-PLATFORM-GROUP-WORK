package Events;

import Exceptions.EventCapacityExceededException;
import Interfaces.Reportable;
import Interfaces.Scanned;
import Tickets.Ticket;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Event implements Scanned, Reportable {
    // Basic events details e.g  name, venue etc
    private String id;
    private String eventTitle;
    private String venue;
    private LocalDateTime eventDate;
    private int capacity;
    private double baseTicketPrice;
    private List<Ticket> issuedTickets;

    // Event constructor
    public Event(String id, String eventTitle, String venue, LocalDateTime eventDate, int capacity, double baseTicketPrice) {
        this.id = id;
        this.eventTitle = eventTitle;
        this.venue = venue;
        this.eventDate = eventDate;
        this.capacity = capacity;
        this.baseTicketPrice = baseTicketPrice;
        this.issuedTickets = new ArrayList<>();
    }

    public abstract String getCategory(); // returns events category

    public Ticket issuedTicket(Ticket ticket) throws EventCapacityExceededException {
        if (issuedTickets.size() >= capacity) {
            throw new EventCapacityExceededException(
                    "Capacity Full! Cannot issue ticket for '" + eventTitle + "'. Max capacity (" + capacity + ") reached."
            );
        }
        issuedTickets.add(ticket);
        return ticket;
    }

    @Override
    public boolean isScanned(String ticketCode) {
        Ticket ticket = issuedTickets.stream().filter(t -> t.getTicketCode().equalsIgnoreCase(ticketCode)).findFirst().orElse(null);
        if (ticket == null) {
            System.out.println("Ticket not found");
        }

        if (ticket.isScanned()) {
            System.out.print("This ticket has been scanned");
        }

        ticket.setScanned(true);
        System.out.println("Gate Check-in Successful for: " + ticket.getAttendee().getName());
        return true;
    }

    @Override
    public void generateReport(String filePath) {
        long attended = issuedTickets.stream().filter(Ticket::isScanned).count();
        long noShows = issuedTickets.size() - attended;
        double totalRevenue = issuedTickets.stream().mapToDouble(Ticket::calculatePrice).sum();

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("==========================================");
            writer.println("          CAMPUS EVENT SUMMARY            ");
            writer.println("==========================================");
            writer.println("Event Title   : " + eventTitle);
            writer.println("Category      : " + getCategory());
            writer.println("Venue         : " + venue);
            writer.println("Date & Time   : " + eventDate);
            writer.println("Capacity      : " + capacity);
            writer.println("------------------------------------------");
            writer.println("Tickets Sold  : " + issuedTickets.size());
            writer.println("Checked-In    : " + attended);
            writer.println("No-Shows      : " + noShows);
            writer.println(String.format("Total Revenue : $%.2f", totalRevenue));
            writer.println("==========================================");

            System.out.println("Post-event report generated at: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing report file: " + e.getMessage());
        }
    }

    // Getters to retrieve event details safely
    public String getId() { return id; }
    public String getTitle() { return eventTitle; }
    public String getVenue() { return venue; }
    public LocalDateTime getDateTime() { return eventDate; }
    public int getCapacity() { return capacity; }
    public double getBaseTicketPrice() { return baseTicketPrice; }
    public List<Ticket> getIssuedTickets() { return issuedTickets; }
}
