package model;

import java.util.ArrayList;

// Represents the inventory in-game
public class Inventory {

    private int potions;
    private final ArrayList<Item> items;

    // EFFECTS: initializes empty inventory
    public Inventory() {
        items = new ArrayList<>();
        potions = 0;
    }

    public int getPotions() {
        return potions;
    }

    public void setPotions(int potions) {
        this.potions = potions;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    // add potions
    // MODIFIES: this
    // EFFECTS: adds value to potions
    public void addPotions(int value) {
        this.potions += value;
    }

    // use a potion
    // MODIFIES: this, character
    //EFFECTS: uses a potion
    public void usePotion(Character character) {
        if ((character.getHealth() != character.getMaxHealth()) && (getPotions() > 0)) {
            addPotions(-1);
            int currentHealth = character.getHealth();
            currentHealth += 1;
            character.setHealth(currentHealth);
        }
    }

    // adds item to items
    // MODIFIES: this, EventLog
    // EFFECTS: adds item to items, adds event to log
    public void addItems(Item item) {
        items.add(item);
        itemEventLogging(item, " added to inventory.");
    }

    // use and remove an item from the inventory
    // REQUIRES: valid character and position
    // MODIFIES: this, character, EventLog
    // EFFECTS: use and removes the item at position from the inventory list, adds event to log
    public void useItem(Character character, int position) {
        Item item = items.get(position);
        item.applyEffects(character);
        itemEventLogging(item, " used by " + character.getName() + ".");
        items.remove(position);
    }

    // discard item from inventory
    // REQUIRES: valid position
    // MODIFIES: this, EventLog
    // EFFECTS: removes the item at position from the inventory list, adds event to log
    public void discardItem(int position) {
        Item item = items.get(position);
        itemEventLogging(item, " discarded from inventory.");
        items.remove(position);
    }

    // MODIFIES: EventLog
    // EFFECTS: adds a new item-based event to event log
    private void itemEventLogging(Item item, String task) {
        Event e = new Event(item.getName() + task);
        EventLog.getInstance().logEvent(e);
    }
}
