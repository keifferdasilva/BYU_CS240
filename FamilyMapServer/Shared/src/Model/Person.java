package Model;

import java.util.Objects;

/**
 * A person either living or dead that is part of the familyMap.
 */
public class Person {
    /**
     * The id associated with the person.
     */
    private String personID;

    /**
     * The name of the user to which this person belongs.
     */
    private String associatedUsername;

    /**
     * The first name of the person.
     */
    private String firstName;

    /**
     * The last name of the person.
     */
    private String lastName;

    /**
     * The gender of the person in one character, either 'm' or 'f'.
     */
    private String gender;

    /**
     * The ID of the person's father if they exist.
     */
    private String fatherID;

    /**
     * The ID of the person's mother if they exist.
     */
    private String motherID;

    /**
     * The ID of the person's spouse if they exist.
     */
    private String spouseID;

    /**
     * Create a person with no relatives or some relatives.
     * @param personID The id
     * @param associatedUsername The user the person belongs to.
     * @param firstName The first name
     * @param lastName The last name
     * @param gender The gender
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Create a person with all relatives
     * @param personID The ID of the person
     * @param associatedUsername The name of the user that the person belongs to
     * @param firstName Person's first name
     * @param lastName Person's last name
     * @param gender Person's gender
     * @param fatherID ID of person's father
     * @param motherID ID of person's mother
     * @param spouseID ID of person's spouse
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public Person() {}

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personID, person.personID) && Objects.equals(associatedUsername, person.associatedUsername) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(gender, person.gender) && Objects.equals(fatherID, person.fatherID) && Objects.equals(motherID, person.motherID) && Objects.equals(spouseID, person.spouseID);
    }
}
