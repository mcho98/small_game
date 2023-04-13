package ui;

import model.Character;
import model.Inventory;
import model.Item;
import model.Scenario;
import model.ScenarioList;
import persistence.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

// Represents the game application
public class Game {

    private static final String GAME_LOCATION = "./data/game.json";
    public static final String WELCOME = "Welcome to my rogue-like text-based RPG";
    public static final String MENU = "1 : create a character, 2 : change scenarios, L: load from save, Q : quit";
    public static final String START_MESSAGE = "You awake in a dark cabin with the uneasy feeling that you've"
            + " been here before. Compelled by some unknown conviction, you exit into the pitch-black woods.";
    public static final String FIND_MESSAGE = "Stumbling in the dark, you come across: ";
    public static final String REST_MESSAGE_1 = "You take a moment to gather your thoughts.";
    public static final String REST_MESSAGE_2 = "I : Check inventory. A: press on. S: Save. Q, Quit";
    public static final String REST_MESSAGE_3 = "After a quick respite, you press on.";
    public static final String CLEARINGS_MESSAGE = "You have, for better or worse, made it through ";
    public static final String GAME_OVER_MESSAGE = "You succumb to your wounds.";
    public static final String INVALID = "Invalid input";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Character character;
    private Inventory inventory;
    private ArrayList<Item> itemList;
    private ScenarioList scenarioList;
    private ArrayList<String> statNames;
    private Random random;
    private Scanner scanner;
    private int clearings;

    // initializes fields prior to game start
    // EFFECTS: generates items and scenarios and places into item and scenario lists. generates list of stats
    public Game() {
        jsonWriter = new JsonWriter(GAME_LOCATION);
        jsonReader = new JsonReader(GAME_LOCATION);
        itemList = new ArrayList<Item>();
        scenarioList = new ScenarioList();
        statNames = new ArrayList<String>();
        statNamesInit();
        scanner = new Scanner(System.in);
        random = new Random();
        inventory = new Inventory();
        character = new Character("");
        initItems1();
        initItems2();
        initItems3();
        initScenarios1();
        initScenarios2();
        initScenarios3();

    }

    // returns character name
    public String getName() {
        return character.getName();
    }

    // returns character name
    public int getLevel() {
        return character.getLevel();
    }

    // returns character name
    public int getHealth() {
        return character.getHealth();
    }

    // returns potions in inventory
    public int getPotions() {
        return inventory.getPotions();
    }

    // returns character stats
    public int getStat(String stat) {
        return character.checkStat(stat);
    }

    // returns name of item in position
    public ArrayList<Item> getItems() {
        return inventory.getItems();
    }

    // returns clearings
    public int getClearings() {
        return clearings;
    }

    // sets clearings
    public void setClearings(int clearings) {
        this.clearings = clearings;
    }

    // returns list of scenario names
    public ArrayList<String> getScenarioNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Scenario x : this.getScenarios()) {
            names.add(x.getName());
        }
        return names;
    }

    // adds stat names to the list of stats
    // MODIFIES: this
    // EFFECTS: adds stat names to the list of stats
    private void statNamesInit() {
        statNames.add("VIT");
        statNames.add("AGI");
        statNames.add("STR");
        statNames.add("MAG");
        statNames.add("INT");
        statNames.add("LUC");
    }

    // creates a character
    // REQUIRES: input string is not empty
    // MODIFIES: this
    // EFFECTS: creates a character with input as name and starts game, or returns to main menu
    public void createCharacter(String s) {
        character = new Character(s);
        clearings = 0;
    }

    // find and return an item
    // REQUIRES: non-empty itemList
    public Item findItem() {
        int upperBound = itemList.size();
        return itemList.get(random.nextInt(upperBound));
    }

    // MODIFIES: this
    // EFFECTS: adds item to inventory
    public void addItem(Item i) {
        inventory.addItems(i);
    }

    // MODIFIES: this
    // EFFECTS: 50% chance to add potion
    public void findPotion() {
        int rand = random.nextInt(2);
        if (rand == 0) {
            inventory.addPotions(1);
        }
    }

    // returns size of inventory
    public int getItemListSize() {
        return inventory.getItems().size();
    }


    // uses potions
    // MODIFIES: this
    // EFFECTS: reduce inventory depending on potion and item use
    public void usePotion() {
        inventory.usePotion(character);
    }

    // allows use or discarding of items from inventory
    // REQUIRES: non-empty inventory item list, value is less than size of inventory item list
    // MODIFIES: this
    // EFFECTS: desired item will be used or discarded, or exit with "b".
    public void use(int value) {
        Item item = inventory.getItems().get(value);
        inventory.useItem(character, value);
    }

    // discard item from inventory
    // REQUIRES: non-empty inventory item list, value is less than size of inventory item list
    // MODIFIES: this
    // EFFECTS: desired item will be discarded
    public void discard(int value) {
        Item item = inventory.getItems().get(value);
        inventory.discardItem(value);
        character.addExp(50);
    }

    // finds a random scenario and resolves it
    // REQUIRES: non-empty scenarioList
    // MODIFIES: this
    // EFFECTS: finds a random scenario and resolves it, adds 1 to clearings
    public void findScenario() {
        int upperBound = scenarioList.size();
        Scenario scenario = scenarioList.get(random.nextInt(upperBound));
        if (scenario.resolveScenario(character, clearings)) {
            addItem(findItem());
            addItem(findItem());
            findPotion();
        } else {
            addItem(findItem());
            findPotion();
        }
        clearings++;
    }

    // REQUIRES: non-empty list of scenarios
    // EFFECTS: returns list of scenarios
    public ArrayList<Scenario> getScenarios() {
        return scenarioList;
    }

    // REQUIRES: non-empty scenarios list
    // MODIFIES: this
    // EFFECTS: removes a chosen scenario from scenario list
    public void removeScenario(int location) {
        if (location >= 0 && location < scenarioList.size()) {
            scenarioList.remove(location);
        }
    }

    // allows users to make and add a scenario
    // REQUIRES: values be at least 0
    // MODIFIES: this, newScenario
    // EFFECTS: creates and adds newScenario to scenarioList
    public void makeScenario(String name, int a, int b, int c, int d, int e, int f) {
        Scenario newScenario = new Scenario(name);
        if (a > 0) {
            newScenario.addCondition("VIT", a);
        }
        if (b > 0) {
            newScenario.addCondition("AGI", b);
        }
        if (c > 0) {
            newScenario.addCondition("STR", c);
        }
        if (d > 0) {
            newScenario.addCondition("MAG", d);
        }
        if (e > 0) {
            newScenario.addCondition("INT", e);
        }
        if (f > 0) {
            newScenario.addCondition("LUC", f);
        }
        scenarioList.add(newScenario);
    }


    // displays a string and returns the input string
    private String makeScenarioInput(String string) {
        System.out.println(string);
        return scanner.nextLine();
    }

    // generates first batch of items
    // MODIFIES: this, i1, i2, i3, i4, i5
    // EFFECTS: generates 5 items and adds them to itemList
    private void initItems1() {
        Item i1 = new Item("Very Large Hammer", "Everything begins to look like a nail");
        i1.addEffects("STR", 5);
        i1.addEffects("MAG", -5);
        Item i2 = new Item("New Magic Wand", "Sometimes you gotta close a door to open a window");
        i2.addEffects("MAG", 5);
        i2.addEffects("VIT", -1);
        Item i3 = new Item("Glasses", "Really versatile");
        i3.addEffects("INT", 5);
        i3.addEffects("STR", -2);
        i3.addEffects("AGI", -2);
        Item i4 = new Item("Blindfold", "It's already so dark it doesn't make a difference");
        i4.addEffects("LUC", 5);
        i4.addEffects("INT", -5);
        Item i5 = new Item("Ninja Headband", "ninja headband");
        i5.addEffects("AGI", 5);
        i5.addEffects("LUC", -5);
        itemList.add(i1);
        itemList.add(i2);
        itemList.add(i3);
        itemList.add(i4);
        itemList.add(i5);
    }

    // generates second batch of items
    // MODIFIES: this, i1, i2, i3, i4, i5
    // EFFECTS: generates 5 items and adds them to itemList
    private void initItems2() {
        Item i1 = new Item("Hearty Soup", "Who leaves soup around?");
        i1.addEffects("VIT", 2);
        i1.addEffects("AGI", -2);
        Item i2 = new Item("Moldy Cheese", "Please don't eat it");
        i2.addEffects("AGI", -1);
        i2.addEffects("STR", -1);
        i2.addEffects("MAG", -1);
        i2.addEffects("INT", -1);
        i2.addEffects("LUC", -1);
        Item i3 = new Item("Unlit Lantern", "No oil though...");
        i3.addEffects("MAG", 2);
        i3.addEffects("INT", 2);
        Item i4 = new Item("Non-Fungible Toad", "He's one of a kind!");
        i4.addEffects("LUC", 2);
        i4.addEffects("AGI", 2);
        Item i5 = new Item("Enchanted Brass Knuckles", "'I cast fist!'");
        i5.addEffects("STR", 2);
        i5.addEffects("MAG", 2);
        itemList.add(i1);
        itemList.add(i2);
        itemList.add(i3);
        itemList.add(i4);
        itemList.add(i5);
    }

    // generates third batch of items
    // MODIFIES: this, i1
    // EFFECTS: generates 1 item and adds it to itemList
    private void initItems3() {
        Item i1 = new Item("Effigy of a Forgotten God", "Its unnatural form makes it hard to look away");
        i1.addEffects("AGI", 5);
        i1.addEffects("STR", 5);
        i1.addEffects("MAG", 5);
        i1.addEffects("INT", 5);
        i1.addEffects("LUC", 5);
        itemList.add(i1);
    }

    // generates first batch of scenarios
    // MODIFIES: this, s1, s2, s3
    // EFFECTS: generates 3 scenarios and adds them to scenarioList
    private void initScenarios1() {
        Scenario s1 = new Scenario("Forest Clearing");
        s1.addCondition("LUC", 6);
        Scenario s2 = new Scenario("A Strange Lever");
        s2.addCondition("INT", 6);
        Scenario s3 = new Scenario("Will-o-Wisp");
        s3.addCondition("MAG", 7);
        scenarioList.add(s1);
        scenarioList.add(s2);
        scenarioList.add(s3);
    }

    // generates seconds batch of scenarios
    // MODIFIES: this, s1, s2, s3
    // EFFECTS: generates 3 scenarios and adds them to scenarioList
    private void initScenarios2() {
        Scenario s1 = new Scenario("River");
        s1.addCondition("STR", 8);
        Scenario s2 = new Scenario("Eyes");
        s2.addCondition("AGI", 7);
        Scenario s3 = new Scenario("Old Man");
        s3.addCondition("VIT", 8);
        scenarioList.add(s1);
        scenarioList.add(s2);
        scenarioList.add(s3);
    }

    // generates third batch of scenarios
    // MODIFIES: this, s1
    // EFFECTS: generates 1 scenario and adds it to scenarioList
    private void initScenarios3() {
        Scenario s1 = new Scenario("Demon");
        s1.addCondition("STR", 10);
        s1.addCondition("MAG", 10);
        s1.addCondition("INT", 8);
        s1.addCondition("AGI", 10);
        s1.addCondition("LUC", 5);
        scenarioList.add(s1);
    }

    // EFFECTS: saves game to file
    public void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(this);
            jsonWriter.close();
            System.out.println("Saved " + character.getName() + " to " + GAME_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + GAME_LOCATION);
        }
    }

    // EFFECTS: creates JSON
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        charToJson(json);
        inventoryToJson(json);
        json.put("clearings", clearings);
        return json;
    }

    // EFFECTS: adds character data to JSON
    private void charToJson(JSONObject json) {
        json.put("name", character.getName());
        json.put("exp", character.getExp());
        json.put("level", character.getLevel());
        json.put("health", character.getHealth());
        json.put("maxHealth", character.getMaxHealth());
        json.put("VIT", character.checkStat("VIT"));
        json.put("AGI", character.checkStat("AGI"));
        json.put("STR", character.checkStat("STR"));
        json.put("MAG", character.checkStat("MAG"));
        json.put("INT", character.checkStat("INT"));
        json.put("LUC", character.checkStat("LUC"));
    }

    // EFFECTS: adds inventory data to JSON
    private void inventoryToJson(JSONObject json) {
        json.put("potions", inventory.getPotions());
        json.put("items", itemsToJson());
    }

    // EFFECTS: adds inventory items data to JSON
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Item i : inventory.getItems()) {
            jsonArray.put(i.getName());
        }
        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    void loadGame() {
        try {
            jsonReader.read(this);
            System.out.println("Loaded " + character.getName() + " from " + GAME_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + GAME_LOCATION);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets character values from JSON
    public void setCharacterFromJson(String name, int exp, int level, int health, int maxHealth) {
        character.setName(name);
        character.setExp(exp);
        character.setLevel(level);
        character.setHealth(health);
        character.setMaxHealth(maxHealth);
    }

    // MODIFIES: this
    // EFFECTS: sets character stat values from JSON
    public void setCharacterStatsFromJson(int v, int a, int s, int m, int i, int l) {
        character.setStat("VIT", v);
        character.setStat("AGI", a);
        character.setStat("STR", s);
        character.setStat("MAG", m);
        character.setStat("INT", i);
        character.setStat("LUC", l);

    }

    // MODIFIES: this
    // EFFECTS: sets inventory from JSON
    public void setInventoryFromJson(int potions, JSONArray items) {
        inventory.setPotions(potions);
        try {
            for (Object i : items) {
                for (Item item : itemList) {
                    if (Objects.equals(i, item.getName())) {
                        inventory.addItems(item);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot read JSON");
        }
    }

}
