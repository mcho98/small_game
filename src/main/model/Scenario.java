package model;

import java.util.Enumeration;
import java.util.Hashtable;

public class Scenario {

    private String name;
    private String intro;
    private String passMessage;
    private String failMessage;
    private String endMessage;
    private Hashtable<String, Integer> conditions;

    public Scenario() {
        name = "";
        intro = "";

        passMessage = "";
        failMessage = "";
        endMessage = "";

        conditions = new Hashtable<>();

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setPassMessage(String passMessage) {
        this.passMessage = passMessage;
    }

    public void setFailMessage(String failMessage) {
        this.failMessage = failMessage;
    }

    public void setEndMessage(String endMessage) {
        this.endMessage = endMessage;
    }

    public String getName() {
        return name;
    }

    public String getIntro() {
        return intro;
    }

    public String getPassMessage() {
        return passMessage;
    }

    public String getFailMessage() {
        return failMessage;
    }

    public String getEndMessage() {
        return endMessage;
    }

    // adds a condition
    // MODIFIES: this
    // EFFECTS: adds a condition to conditions
    public void addCondition(String stat, int value) {
        conditions.put(stat, value);
    }

    // removes a condition
    // MODIFIES: this
    // EFFECTS: removes a condition to conditions
    public void removeCondition(String stat) {
        conditions.remove(stat);
    }

    // check if character passes or fails the scenario
    public boolean passFail(Character character) {
        boolean verdict = true;
        for (String key : conditions.keySet()) {
            if (conditions.get(key) > character.checkStat(key)) {
                verdict = false;
            }
        }
        return verdict;
    }

    // determine outcome of scenario
    public String resolveScenario(Character character) {
        if (passFail(character)) {
            return passMessage;
        } else if (character.getHealth() == 1) {
            character.setHealth(0);
            return endMessage;
        } else {
            int currentHealth = character.getHealth();
            character.setHealth(currentHealth - 1);
            return failMessage;
        }
    }



}
