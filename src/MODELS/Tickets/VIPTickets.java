package Tickets;

import Attendees.Attendee;

public class VipTickets extends Ticket {
    public VipTickets(Attendee attendee, double basePrice) {
        super(attendee, basePrice);
    }

    @Override
    public double calculatePrice() {
        return getBasePrice() * 1.50; // 50% extra for VIP perks
    }
}
