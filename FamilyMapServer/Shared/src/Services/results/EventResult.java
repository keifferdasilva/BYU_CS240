package Services.results;

/**
 * The data of the event if successful or explanation of failure
 */
public class EventResult extends Result {
    /**
     * The user the event belongs to
     */
    private String associatedUsername;
    /**
     * The event ID
     */
    private String eventID;
    /**
     * The ID of the person the event belongs to
     */
    private String personID;
    /**
     * The latitude where the event took place
     */
    private Float latitude;
    /**
     * The longitude where the event took place
     */
    private Float longitude;
    /**
     * The country where the event took place
     */
    private String country;
    /**
     * The city where the event took place
     */
    private String city;
    /**
     * The type of event
     */
    private String eventType;
    /**
     * The year the event took place
     */
    private Integer year;



    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
