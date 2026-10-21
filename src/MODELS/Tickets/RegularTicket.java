package Tickets;

import Attendees.Attendee;

public class RegularTicket extends Ticket {
    public RegularTicket(Attendee attendee, double basePrice) {
        super(attendee, basePrice);
    }

    public double calculatePrice() {
        return getBasePrice(); // Regular price
    }


}
