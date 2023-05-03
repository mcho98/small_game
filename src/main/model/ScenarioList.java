package model;

import java.util.ArrayList;

public class ScenarioList {

    //Represents a list of Scenarios
    private final ArrayList<Scenario> scenarios;

    // Creates a new ScenarioList
    public ScenarioList() {
        scenarios = new ArrayList<>();
    }

    // EFFECTS: returns size of the scenario list
    public int size() {
        return scenarios.size();
    }

    // EFFECTS: returns scenario at position
    public Scenario get(int position) {
        return scenarios.get(position);
    }

    // MODIFIES: this, EventLog
    // EFFECTS: adds a scenario and adds event to log
    public void add(Scenario scenario) {
        scenarios.add(scenario);
        Event e = new Event("Scenario " + scenario.getName() + " added to Scenario List.");
        EventLog.getInstance().logEvent(e);
    }

    // MODIFIES: this, EventLog
    // EFFECTS: removes a scenario and adds event to log
    public void remove(int position) {
        Scenario scenario = scenarios.get(position);
        Event e = new Event("Scenario " + scenario.getName() + " removed from Scenario List.");
        EventLog.getInstance().logEvent(e);
        scenarios.remove(position);
    }

    // EFFECTS: returns list of scenarios as ArrayList
    public ArrayList<Scenario> asArrayList() {
        return scenarios;
    }


}
