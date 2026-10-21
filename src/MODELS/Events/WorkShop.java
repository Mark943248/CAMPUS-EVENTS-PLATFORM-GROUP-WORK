package Events;

public class Workshop extends Event{
    private String Instructor;
    private String Topic;

    public Workshop(String eventID, String eventTitle, int capacity, String venue, double baseTicketPrice, String Instructor,String Topic) {
        super(eventID, eventTitle, capacity, venue, baseTicketPrice);
        this.Instructor = Instructor;
        this.Topic = Topic;
    }

    public String getCategory() {
        return "Workshop "+"Instructor: "+Instructor+"Topic: "+Topic;
    }
}
package MODELS.Events;

public class WorkShop {
}
