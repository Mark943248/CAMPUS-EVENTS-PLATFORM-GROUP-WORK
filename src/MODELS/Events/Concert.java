 package Events;

public class Concert extends Event{

    private String headArtist;

    public Concert(String eventID, String eventTitle, int capacity, String venue, double baseTicketPrice, String headArtist) {
        super(eventID, eventTitle, capacity, venue, baseTicketPrice);
        this.headArtist = headArtist;
    }

    public String getCategory(){
        return "Concert " + "Head Artist: " + headArtist;
    }



}
package MODELS.Events;

public class Concert {
}
