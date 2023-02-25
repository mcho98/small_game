package ui;

import model.Character;
import model.Inventory;
import model.Item;
import model.Scenario;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

// Represents the game application
public class Game {

    public static final String WELCOME = "Welcome to my rogue-like text-based RPG";
    public static final String MENU = "1 : create a character, 2 : change scenarios, Q : quit";
    public static final String START_MESSAGE = "You awake in a dark cabin with the uneasy feeling that you've"
            + " been here before. Compelled by some unknown conviction, you exit into the pitch-black woods.";
    public static final String FIND_MESSAGE = "Stumbling in the dark, you come across: ";
    public static final String REST_MESSAGE_1 = "You take a moment to gather your thoughts.";
    public static final String REST_MESSAGE_2 = "check inventory: I, press on: A, quit: B";
    public static final String REST_MESSAGE_3 = "After a quick respite, you press on.";
    public static final String CLEARINGS_MESSAGE = "You have, for better or worse, made it through ";
    public static final String GAME_OVER_MESSAGE = "You succumb to your wounds.";
    public static final String INVALID = "Invalid input";


    private Character character;
    private Inventory inventory;
    private ArrayList<Item> itemList;
    private ArrayList<Scenario> scenarioList;
    private ArrayList<String> statNames;
    private Random random;
    private Scanner scanner;
    private int clearings;

    public Game() {
        init();
        start();
    }

    // initialize fields prior to game start
    // MODIFIES: this
    // EFFECTS: generates items and scenarios and places into item and scenario lists. generates list of stats
    private void init() {
        itemList = new ArrayList<Item>();
        scenarioList = new ArrayList<Scenario>();
        statNames = new ArrayList<String>();
        statNamesInit();
        scanner = new Scanner(System.in);
        initItems1();
        initItems2();
        initItems3();
        initScenarios1();
        initScenarios2();
        initScenarios3();
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

    // main menu
    // MODIFIES: this
    // EFFECTS: can either view and change scenarios, create a character and play the game, or quit
    private void start() {
        System.out.println(WELCOME);
        System.out.println("Enter: ");
        System.out.println(MENU);
        String input = scanner.nextLine();
        if (input.equals("q") || input.equals("Q")) {
            System.out.println("Goodbye");
            System.exit(0);
        } else if (input.equals("1")) {
            createCharacter();
        } else if (input.equals("2")) {
            viewScenarios();
        } else {
            System.out.println(INVALID);
            start();
        }
    }

    // creates a character
    // REQUIRES: input string is not empty
    // MODIFIES: this
    // EFFECTS: creates a character with input as name and starts game, or returns to main menu
    private void createCharacter() {
        System.out.println("Enter a name:");
        String input = scanner.nextLine();
        if (input.equals("")) {
            System.out.println(INVALID);
            createCharacter();
        } else if (input.equals("b") || input.equals("B")) {
            start();
        } else {
            character = new Character(input);
            playGame();
        }
    }

    // core game loop
    // REQUIRES: character health > 0
    // MODIFIES: this
    // EFFECTS: sets up a new game and loops core gameplay until character is out of health. Then returns to main menu
    private void playGame() {
        System.out.println(START_MESSAGE);
        random = new Random();
        clearings = 0;
        inventory = new Inventory();
        while (character.getHealth() != 0) {
            findItem();
            rest();
            findScenario();
            clearings(clearings);
        }
        System.out.println(GAME_OVER_MESSAGE);
        finalOverview(clearings);
        start();
    }

    // gives final stats for a finished game
    // REQUIRES: character health = 0
    private void finalOverview(int clearings) {
        String name = character.getName();
        String lvl = Integer.toString(character.getLevel());
        String clears = Integer.toString(clearings);
        System.out.println(name + " ended their journey at level " + lvl + " after clearing " + clears + " trials.");
        System.out.println(name + "'s stats were...");
        statsOverview();
    }

    // gives final stats for character from character stats
    // REQUIRES: character health = 0
    private void statsOverview() {
        for (String stats : character.getStatNames()) {
            String value = Integer.toString(character.checkStat(stats));
            System.out.println(stats + ": " + value);
        }
    }

    // find an item and possibly a potion
    // REQUIRES: non-empty itemList
    // MODIFIES: this
    // EFFECTS: adds random item from itemList to inventory, adds potion at 50% chance
    private void findItem() {
        int upperBound = itemList.size();
        Item found = itemList.get(random.nextInt(upperBound));
        inventory.addItems(found);
        System.out.println(FIND_MESSAGE + found.getName() + " - " + found.getDescription());
        int rand = random.nextInt(2);
        if (rand == 0) {
            inventory.addPotions(1);
            System.out.println(FIND_MESSAGE + "a potion.");
        }
    }

    // allow access to inventory or to return to main menu
    // MODIFIES: this
    // EFFECTS: allow access to inventory, which can change character and inventory fields, or to return to main menu
    private void rest() {
        System.out.println(REST_MESSAGE_1);
        overview();
        System.out.println(REST_MESSAGE_2);
        String input = scanner.nextLine();
        if (input.equals("B") || input.equals("b")) {
            start();
        } else if (input.equals("A") || input.equals("a")) {
            System.out.println(REST_MESSAGE_3);
        } else if (input.equals("I") || input.equals("i")) {
            checkInventory();
        } else {
            System.out.println(INVALID);
            rest();
        }
    }

    // gives overview of game stats so far
    // REQUIRES: character health > 0
    private void overview() {
        String name = character.getName();
        String level = Integer.toString(character.getLevel());
        String health = Integer.toString(character.getHealth());
        String maxHealth = Integer.toString(character.getMaxHealth());
        System.out.println(name + " is level " + level + ".");
        System.out.println(name + " has " + health + " out of " + maxHealth + " health.");
        System.out.println("Current stats:");
        statsOverview();
    }

    // allow use of potions and items
    // MODIFIES: this
    // EFFECTS: reduce inventory depending on potion and item use
    private void checkInventory() {
        System.out.println("You have " + character.getHealth() +  " health points.");
        boolean potions = potionCheck();
        boolean items = itemCheck();
        if (potions || items) {
            System.out.println("use potions: P, see item: enter number, Press on : A");
            String input = scanner.nextLine();
            if (input.equals("A") || input.equals("a")) {
                return;
            }
            if (input.equals("P") || input.equals("p")) {
                inventory.usePotion(character);
                checkInventory();
            } else {
                checkInventoryTry(input);
            }
        }
    }

    // checks to see if input is in inventory list
    // MODIFIES: this
    // EFFECTS: checks to see if input is in inventory list. If so, item can be used or discarded
    private void checkInventoryTry(String input) {
        try {
            int value = Integer.parseInt(input);
            if (value < inventory.getItems().size() && value > -1) {
                useOrDiscard(value);
            } else {
                System.out.println(INVALID);
            }
        } catch (Exception e) {
            System.out.println(INVALID);
        } finally {
            checkInventory();
        }
    }

    // check number of potions and return true if > 0
    private boolean potionCheck() {
        if (inventory.getPotions() == 0 && inventory.getItems().isEmpty()) {
            System.out.println("Your inventory is empty.");
            return false;
        } else if (inventory.getPotions() == 1) {
            System.out.println("You have 1 potion.");
            return true;
        } else {
            System.out.println("You have " + inventory.getPotions() + " potions.");
            return true;
        }
    }

    // check number of items and return true if > 0
    private boolean itemCheck() {
        if (!inventory.getItems().isEmpty()) {
            System.out.println("Your current items are");
            currentItems();
            return true;
        } else {
            System.out.println("You have no items.");
            return false;
        }
    }

    // print out all items in inventory
    // REQUIRES: non-empty inventory item list
    private void currentItems() {
        for (int x = 0; x < inventory.getItems().size(); x++) {
            String stringX = Integer.toString(x);
            Item itemX = inventory.getItems().get(x);
            System.out.println(System.getProperty("line.separator") + stringX + ": " + itemX.getName());
        }
    }

    // allows use or discarding of items from inventory
    // REQUIRES: non-empty inventory item list, value is less than size of inventory item list
    // MODIFIES: this
    // EFFECTS: desired item will be used or discarded, or exit with "b".
    private void useOrDiscard(int value) {
        Item item = inventory.getItems().get(value);
        System.out.println("You have chosen:" + item.getName() + " - " + item.getDescription());
        System.out.println("stats: ");
        for (String key : item.getEffects().keySet()) {
            String statVal = Integer.toString(item.getEffects().get(key));
            System.out.println(key + ": " + statVal);
        }
        System.out.println("Use for stats: 1, Discard for EXP: 2, Back: b");
        String input = scanner.nextLine();
        if (input.equals("1")) {
            inventory.useItem(character, value);
        } else if (input.equals("2")) {
            inventory.discardItem(value);
            character.addExp(50);
        } else if (input.equals("b") || input.equals("B")) {
            return;
        } else {
            System.out.println(INVALID);
            useOrDiscard(value);
        }
    }

    // finds a random scenario and resolves it
    // REQUIRES: non-empty scenarioList
    // MODIFIES: this
    // EFFECTS: finds a random scenario and resolves it, adds 1 to clearings
    private void findScenario() {
        int upperBound = scenarioList.size();
        Scenario scenario = scenarioList.get(random.nextInt(upperBound));
        System.out.println(scenario.getIntro());
        System.out.println(scenario.resolveScenario(character, clearings));
        clearings++;
    }

    // gives number of clearings
    private void clearings(int clearings) {
        System.out.println(CLEARINGS_MESSAGE + clearings + " trials");
    }

    // shows list of scenarios and displays scenario menu
    // MODIFIES: this
    // EFFECTS: can add to or remove from scenario list, or return to main menu
    private void viewScenarios() {
        System.out.println("Scenarios:");
        for (int x = 0; x < scenarioList.size(); x++) {
            String scenario = scenarioList.get(x).getName();
            System.out.println(x + ": " + scenario);
        }
        System.out.println("Add scenario: A, remove scenario: number of scenario, back: B");
        String input = scanner.nextLine();
        if (input.equals("B") || input.equals("b")) {
            start();
        } else if (input.equals("A") || input.equals("a")) {
            makeScenario();
        } else {
            try {
                int position = Integer.parseInt(input);
                if (position < scenarioList.size() && position > -1) {
                    scenarioList.remove(position);
                }
            } catch (Exception e) {
                System.out.println(INVALID);
            } finally {
                viewScenarios();
            }
        }
    }

    // allows users to make and add a scenario
    // REQUIRES: values be at least 0
    // MODIFIES: this, newScenario
    // EFFECTS: creates and adds newScenario to scenarioList
    private void makeScenario() {
        String name = makeScenarioInput("Choose name:");
        String intro = makeScenarioInput("Add an intro:");
        String pass = makeScenarioInput("Add a success message:");
        String fail = makeScenarioInput("Add a failure message:");
        String end = makeScenarioInput("Add a critical failure message:");
        Scenario newScenario = new Scenario(name, intro, pass, fail, end);
        System.out.println("Pass conditions?:");
        makeScenarioStats(newScenario);
        scenarioList.add(newScenario);
        viewScenarios();
    }

    // allow users to create requirements to pass scenario and add to scenario list
    // MODIFIES: this, newScenario
    // EFFECTS: requirements are added to newScenario and newScenario is added to scenarioList
    private void makeScenarioStats(Scenario newScenario) {
        for (String stats : statNames) {
            System.out.println(stats);
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value > -1) {
                    newScenario.addCondition(stats, value);
                } else {
                    System.out.println(INVALID);
                }
            } catch (Exception e) {
                System.out.println(INVALID);
                viewScenarios();
            }
        }
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
        Scenario s1 = new Scenario("Forest Clearing", "You enter a forest clearing and spot a bear.",
                "You sneak by without alerting the bear.",
                "You alert the bear and narrowly avoid being mauled.",
                "You alert the bear and end up its next meal.");
        s1.addCondition("LUC", 6);
        Scenario s2 = new Scenario("A Strange Lever", "A strange lever pokes out a gnarled tree.",
                "You think better of pulling strange levers.",
                "Curiosity gets the better of you and you pull the lever, only to get a nasty shock.",
                "Curiosity gets the better of you and you pull the lever. You are electrocuted.");
        s2.addCondition("INT", 6);
        Scenario s3 = new Scenario("Will-o-Wisp", "A soft glow illuminates the forest."
                + " A strange glowing orb chases after you.", "You blast it out of the sky."
                + " You are shrouded in darkness once more", "The orb gains on you and leaves minor burns.",
                "The orb gains on you and leaves sever burns.");
        s3.addCondition("MAG", 7);
        scenarioList.add(s1);
        scenarioList.add(s2);
        scenarioList.add(s3);
    }

    // generates seconds batch of scenarios
    // MODIFIES: this, s1, s2, s3
    // EFFECTS: generates 3 scenarios and adds them to scenarioList
    private void initScenarios2() {
        Scenario s1 = new Scenario("River", "A river blocks your advance. Time to swim across.",
                "You are a fantastic swimmer.",
                "The swift current drags you along until you finally make it across.",
                "The swift current drags you under.");
        s1.addCondition("STR", 8);
        Scenario s2 = new Scenario("Eyes", "Strange eyes peer out from the forest.",
                "You sprint deeper into the forest, leaving your pursuers behind.",
                "You try to sprint deeper into the forest, but are raked by gnashing teeth and claws.",
                "You try to sprint deeper into the forest, but are mauled by gnashing teeth and claws.");
        s2.addCondition("AGI", 7);
        Scenario s3 = new Scenario("Old Man", "You come across an old man. He wants to arm wrestle.",
                "You oblige. He's stronger than you thought but you emerge the victor.",
                "You oblige. He's stronger than you thought. You're pretty sure you tore a rotator cuff.",
                "You oblige. He's stronger than you thought and tears your arm clean off. What.");
        s3.addCondition("VIT", 8);
        scenarioList.add(s1);
        scenarioList.add(s2);
        scenarioList.add(s3);
    }

    // generates third batch of scenarios
    // MODIFIES: this, s1
    // EFFECTS: generates 1 scenario and adds it to scenarioList
    private void initScenarios3() {
        Scenario s1 = new Scenario("Demon", "A strange figure blocks your path."
                + " Its silhouette is hard to make out but something about seems off. Unnatural."
                + " You slowly back away from the creature, not wanting to turn your back to it."
                + " As you begin to feel as though you've made a fair bit of distance, it bolts towards you.",
                "You manage to dodge its unorthodox attacks and even succeed at getting some hits in."
                + " The creature dashes into the woods. You lose sight of it, but have the feeling it won't be back"
                + " For now.",
                "You are buffeted as it inflicts blows, gashes, and burns at a whirlwind pace."
                        + "You lose consciousness and awake under a moonlit sky. The forest is quiet. Too quiet.",
                "You are buffeted as it inflicts blows, gashes, and burns at a whirlwind pace."
                        + "You bleed out under a moonlit sky.");
        s1.addCondition("STR", 10);
        s1.addCondition("MAG", 10);
        s1.addCondition("INT", 8);
        s1.addCondition("AGI", 10);
        s1.addCondition("LUC", 5);
        scenarioList.add(s1);
    }
}
