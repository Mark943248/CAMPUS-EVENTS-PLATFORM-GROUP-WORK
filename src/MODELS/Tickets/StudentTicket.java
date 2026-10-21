package Tickets;

import Attendees.Attendee;

public class StudentTicket extends Ticket {
    public StudentTicket(Attendee attendee, double basePrice) {
        super(attendee, basePrice);
    }

    @Override
    public double calculatePrice() {
        return getBasePrice() * 0.70; // 30% discount
    }
}
