package Services.results;

import Model.Event;

import java.util.ArrayList;

public class EventsResult extends Result{
    private ArrayList<Event> data;

    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }
}
