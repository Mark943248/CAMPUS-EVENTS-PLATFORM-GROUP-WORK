package Tickets;

import Attendees.Attendee;

import java.util.UUID;

public abstract class Ticket {
    private String ticketId;
    private Attendee attendee;
    private double basePrice;
    private boolean isScanned;

    // Constructor
    public Ticket(Attendee attendee, double basePrice) {
        // Automatically generate a short, unique 8-character ticket code
        this.ticketId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.attendee = attendee;
        this.basePrice = basePrice;
        this.isScanned = false; // Starts as not checked-in
    }

    // Abstract method: Every ticket type must define its own pricing rule
    public abstract double calculatePrice();

    // Getters and Setters
    public String getTicketCode() {
        return ticketId;
    }

    public Attendee getAttendee() {
        return attendee;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean isScanned) {
        this.isScanned = isScanned;
    }
}
