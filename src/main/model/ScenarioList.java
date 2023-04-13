package model;

import java.util.ArrayList;

public class ScenarioList extends ArrayList<Scenario> {

    //Represents a list of Scenarios

    // Creates a new ScenarioList
    public ScenarioList() {
        super();
    }

    // MODIFIES: this, EventLog
    // EFFECTS: adds a scenario and adds event to log
    @Override
    public boolean add(Scenario scenario) {
        Event e = new Event("Scenario " + scenario.getName() + " added to Scenario List.");
        EventLog.getInstance().logEvent(e);
        return super.add(scenario);
    }

    // MODIFIES: this, EventLog
    // EFFECTS: removes a scenario and adds event to log
    @Override
    public Scenario remove(int position) {
        Scenario scenario = super.get(position);
        Event e = new Event("Scenario " + scenario.getName() + " removed from Scenario List.");
        EventLog.getInstance().logEvent(e);
        return super.remove(position);
    }
}
