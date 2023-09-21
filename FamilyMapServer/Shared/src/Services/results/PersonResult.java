package Services.results;

import Model.Person;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Result representing one person
 */
public class PersonResult extends Result{
    /**
     * User this person belongs to
     */
    private String associatedUsername;

    /**
     * Person's ID
     */
    private String personID;

    /**
     * Person's first name
     */
    private String firstName;

    /**
     * Person's last name
     */
    private String lastName;

    /**
     * Person's gender
     */
    private String gender;

    private String fatherID;

    private String motherID;

    private String spouseID;



    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
