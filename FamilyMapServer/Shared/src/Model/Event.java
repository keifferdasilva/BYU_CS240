package Model;

import java.util.Objects;

/**
 * An event that happened in a Person's life.
 */
public class Event {
    /**
     * The unique id of the event.
     */
    private String eventID;

    /**
     * The username of the user associated with the event.
     */
    private String associatedUsername;

    /**
     * The id of the person the event belongs to.
     */
    private String personID;

    /**
     * The latitude of the event's location.
     */
    private float latitude;

    /**
     * The longitude of the event's location.
     */
    private float longitude;

    /**
     * The country that the event took place in.
     */
    private String country;

    /**
     * The city that the event took place in.
     */
    private String city;

    /**
     * The type of event
     */
    private String eventType;

    /**
     * The year the event took place.
     */
    private int year;

    /**
     * Create an event using the given parameters.
     * @param eventID The id
     * @param associatedUsername The user
     * @param personID The person
     * @param latitude The latitude
     * @param longitude The longitude
     * @param country The country
     * @param city The city
     * @param eventType The type of event
     * @param year The year of the event
     */
    public Event(String eventID, String associatedUsername, String personID, float latitude, float longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
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

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventID, event.eventID) && Objects.equals(associatedUsername, event.associatedUsername) && Objects.equals(personID, event.personID) && Objects.equals(latitude, event.latitude) && Objects.equals(longitude, event.longitude) && Objects.equals(country, event.country) && Objects.equals(city, event.city) && Objects.equals(eventType, event.eventType) && Objects.equals(year, event.year);
    }
}
