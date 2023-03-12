package model;

import java.util.Hashtable;

// Represents a scenario that the character must overcome or lose health points.
public class Scenario {

    private final String name;
    private final String intro;
    private final String passMessage;
    private final String failMessage;
    private final String endMessage;
    private final Hashtable<String, Integer> conditions;

    // EFFECTS: initializes scenario with name, intro, and pass-fail-end messages
    public Scenario(String name, String intro, String pass, String fail, String end) {
        this.name = name;
        this.intro = intro;
        passMessage = pass;
        failMessage = fail;
        endMessage = end;

        conditions = new Hashtable<>();

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
    // REQUIRES: character and scenario share stat keyset
    public boolean passFail(Character character, int clearing) {
        boolean verdict = true;
        for (String key : conditions.keySet()) {
            if ((conditions.get(key) + (clearing / 3)) > character.checkStat(key)) {
                verdict = false;
            }
        }
        return verdict;
    }

    // determine outcome of scenario
    // REQUIRES: character and scenario share stat keyset
    // MODIFIES: character
    // EFFECTS: character loses health based on passFail()
    public String resolveScenario(Character character, int clearings) {
        if (passFail(character, clearings)) {
            character.addExp(25);
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
