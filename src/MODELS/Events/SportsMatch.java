package Events;

public class SportMatch extends Event{
    private String Coach;
    private String Hometeam;
    private String Awayteam;

    public SportMatch(String eventID, String eventTitle, int capacity, String venue, double baseTicketPrice,String Coach,String Hometeam,String Awayteam){
        super(eventID, eventTitle, capacity, venue, baseTicketPrice);
        this.Coach=Coach;
        this.Hometeam=Hometeam;
        this.Awayteam=Awayteam;

    }
    public String getCategory(){
        return("SportMatch "+"Coach: "+Coach +"Hometeam: "+Hometeam +"Awayteam:"+ Awayteam);
    }
}package MODELS.Events;

public class SportsMatch {
}
