package model;

import java.util.ArrayList;

// Represents the inventory in-game
public class Inventory {

    private int potions;
    private final ArrayList<Item> items;

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
    // MODIFIES: this
    // EFFECTS: adds item to items
    public void addItems(Item item) {
        items.add(item);
    }

    // use and remove an item from the inventory
    // REQUIRES: valid character and position
    // MODIFIES: this, character
    // EFFECTS: use and removes the item at position from the inventory list
    public void useItem(Character character, int position) {
        Item item = items.get(position);
        item.applyEffects(character);
        discardItem(position);
    }

    // discard item from inventory
    // REQUIRES: valid position
    // MODIFIES: this
    // EFFECTS: removes the item at position from the inventory list
    public void discardItem(int position) {
        items.remove(position);
    }
}
