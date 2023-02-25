package model;

import java.util.Hashtable;

//Represents an in-game item
public class Item {

    private String name;
    private String description;
    private final Hashtable<String, Integer> effects;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
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
    // REQUIRES: valid character that shares stats
    // MODIFIES: character
    // EFFECTS: apply effects to character
    public void applyEffects(Character character) {
        for (String stat : effects.keySet()) {
            character.changeStat(stat, effects.get(stat));
        }
        character.determineMaxHealth();
    }
}
