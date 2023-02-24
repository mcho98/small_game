package model;

import java.util.Enumeration;
import java.util.Hashtable;

public class Item {

    private String name;
    private String description;
    private Hashtable<String, Integer> effects;

    public Item() {
        name = "";
        description = "";
        effects = new Hashtable<String, Integer>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Hashtable<String, Integer> getEffects() {
        return effects;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    // add effect to effects
    // MODIFIES: this
    // EFFECTS: add item effect to effects
    public void addEffects(String stat, int effect) {
        effects.put(stat, effect);
    }

    // apply effect
    // MODIFIES: character
    // EFFECTS: apply effects to character
    public void applyEffects(Character character) {
        for (String stat : effects.keySet()) {
            character.changeStat(stat, effects.get(stat));
        }

    }

}
