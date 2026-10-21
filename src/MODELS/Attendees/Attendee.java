package MODELS.Attendees;

public class Attendee {

    public enum TYPE {
        STAFF, STUDENT, GUEST
    }
    private String name;
    private String email;
    private String id;
    private TYPE attendeType;



    public Attendee(String name,String email,String id,String studentOrStaff, TYPE attendeType){
        this.name=name;
        this.email=email;
        this.id=id;
        this.attendeType=attendeType;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public TYPE getAttendeType() { return attendeType; }
}
