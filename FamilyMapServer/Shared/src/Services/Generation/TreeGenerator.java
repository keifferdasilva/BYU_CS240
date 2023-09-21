package Services.Generation;

import java.io.*;
import java.sql.Connection;
import java.util.UUID;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import DataAccess.PersonDAO;
import DataAccess.UserDAO;
import JSON.Decoder;
import Model.*;

public class TreeGenerator {

    private final String username;
    private final Connection conn;


    private final PersonDAO personDAO;

    private final EventDAO eventDAO;

    private Locations locations;

    private Fnames femaleNames;

    private Mnames maleNames;

    private Snames surNames;

    private int eventsAdded;

    private int peopleAdded;

    public TreeGenerator(String username, Connection conn){
        this.username = username;
        this.conn = conn;
        scanJsons();
        personDAO = new PersonDAO(conn);
        eventDAO = new EventDAO(conn);
        peopleAdded = 0;
        eventsAdded = 0;
    }

    private void scanJsons(){
        File locationJson = new File("json/locations.json");
        try(FileReader fileReader = new FileReader(locationJson)){
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            locations = (Locations) Decoder.deserialize(bufferedReader, Locations.class);
        }
        catch(IOException ex){
            ex.printStackTrace();
            System.out.println("Could not open the file");
        }

        File mnamesJson = new File("json/mnames.json");
        try(FileReader fileReader = new FileReader(mnamesJson)){
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            maleNames = (Mnames) Decoder.deserialize(bufferedReader, Mnames.class);
        }
        catch(IOException ex){
            ex.printStackTrace();
            System.out.println("Could not open the file");
        }

        File fnamesJson = new File("json/fnames.json");
        try(FileReader fileReader = new FileReader(fnamesJson)){
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            femaleNames = (Fnames) Decoder.deserialize(bufferedReader, Fnames.class);
        }
        catch(IOException ex){
            ex.printStackTrace();
            System.out.println("Could not open the file");
        }

        File snamesJson = new File("json/snames.json");
        try(FileReader fileReader = new FileReader(snamesJson)){
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            surNames = (Snames) Decoder.deserialize(bufferedReader, Snames.class);
        }
        catch(IOException ex){
            ex.printStackTrace();
            System.out.println("Could not open the file");
        }


    }

    public int getEventsAdded() {
        return eventsAdded;
    }

    public int getPeopleAdded() {
        return peopleAdded;
    }

    public Person generatePerson(String gender, int numGenerations, int birth, String userPersonID) throws DataAccessException{

        Person person = new Person();

        Person mother = null;
        Person father = null;

        if(numGenerations > 0){
            mother = generatePerson("f", numGenerations - 1, birth - 30, userPersonID);
            father = generatePerson("m", numGenerations - 1, birth - 30, userPersonID);
            //add marriage events
            Location marriageLocation = locations.getLocations().get(random(locations.getLocations().size()));
            String motherEventID = UUID.randomUUID().toString();
            String fatherEventID = UUID.randomUUID().toString();
            int marriageYear = birth - 5;
            Event fatherMarriage = new Event(fatherEventID, username, father.getPersonID(), marriageLocation.getLatitude(), marriageLocation.getLongitude(),
                    marriageLocation.getCountry(), marriageLocation.getCity(), "marriage", marriageYear);
            Event motherMarriage = new Event(motherEventID, username, mother.getPersonID(), marriageLocation.getLatitude(), marriageLocation.getLongitude(),
                    marriageLocation.getCountry(), marriageLocation.getCity(), "marriage", marriageYear);
            eventDAO.insert(fatherMarriage);
            eventDAO.insert(motherMarriage);
            eventsAdded += 2;

            //set spouse ids
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());
            personDAO.updatePerson(mother);
            personDAO.updatePerson(father);
            //set father and mother
            person.setFatherID(father.getPersonID());
            person.setMotherID(mother.getPersonID());

        }


        int deathYear = birth + 60;
        String personID = UUID.randomUUID().toString();

        //add death event
        String deathEventID = UUID.randomUUID().toString();
        Location deathLocation = locations.getLocations().get(random(locations.getLocations().size()));
        Event deathEvent = new Event(deathEventID, username, personID, deathLocation.getLatitude(), deathLocation.getLongitude(),
                deathLocation.getCountry(), deathLocation.getCity(), "death", deathYear);
        eventDAO.insert(deathEvent);

        //add birth event
        String birthEventID = UUID.randomUUID().toString();
        Location birthLocation = locations.getLocations().get(random(locations.getLocations().size()));
        Event birthEvent = new Event(birthEventID, username, personID, birthLocation.getLatitude(), birthLocation.getLongitude(),
                birthLocation.getCountry(), birthLocation.getCity(), "birth", birth);
        eventDAO.insert(birthEvent);

        eventsAdded += 2;
        //First and last names are randomly generated based on gender
        String firstName = null;
        String lastName = null;
        if(gender.equals("m")){
            firstName = maleNames.getData().get(random(maleNames.getData().size()));
            lastName = surNames.getData().get(random(surNames.getData().size()));
        }
        else if(gender.equals("f")){
            firstName = femaleNames.getData().get(random(femaleNames.getData().size()));
            lastName = surNames.getData().get(random(surNames.getData().size()));
        }
        else{
            firstName = maleNames.getData().get(random(maleNames.getData().size()));
            lastName = surNames.getData().get(random(surNames.getData().size()));
        }

        //set gender, first name, last name, and person id
        if(person.getPersonID() != userPersonID) {
            person.setGender(gender);
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setPersonID(personID);
            person.setAssociatedUsername(username);
        }


        personDAO.addPerson(person);
        peopleAdded++;
        return person;
    }


    private int random(int limit){
        return Math.abs(UUID.randomUUID().hashCode() % limit);
    }
}
