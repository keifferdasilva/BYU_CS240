package Services.results;

import Model.Person;

import java.util.ArrayList;

public class PeopleResult extends Result{
    private ArrayList<Person> data;

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }
}
