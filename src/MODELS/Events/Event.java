package MODELS.Events;

public class Event {package Events;

public abstract class Event {
    private String eventID;
    private String eventTitle;
    private int capacity;
    private String venue;
    private double baseTicketPrice;

    public Event(String eventID, String eventTitle, int capacity, String venue, double baseTicketPrice) {
        this.eventID =eventID;
        this.eventTitle=eventTitle;
        this.capacity=capacity;
        this.venue=venue;
        this.baseTicketPrice=baseTicketPrice;
    }



    public abstract String getCategory();

    public String getEventID() { return eventID; }
    public String getEventTitle(){ return eventTitle;}
    public int getCapacity(){ return capacity;}
    public String getVenue(){ return venue;}
    public double getBaseTicketPrice(){ return baseTicketPrice;}

}

}
