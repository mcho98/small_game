package model;

import java.util.ArrayList;
import java.util.Hashtable;


// Represents a character with associated stats/lvl/hp
public class Character {

    public static final int EXP_LIMIT = 100;
    public static final int LEVEL_CAP = 20;

    private String name;
    private int exp;
    private int level;
    private int health;
    private int maxHealth;
    private final Hashtable<String, Integer> stats;
    private final ArrayList<String> statNames;

    // EFFECTS: initializes a character with default stats and "name" name
    public Character(String name) {
        this.name = name;
        exp = 0;
        level = 1;
        health = 5;
        maxHealth = 5;

        stats = new Hashtable<>();

        stats.put("VIT", 5);
        stats.put("AGI", 5);
        stats.put("STR", 5);
        stats.put("MAG", 5);
        stats.put("INT", 5);
        stats.put("LUC", 5);

        statNames = new ArrayList<>();

        statNames.add("VIT");
        statNames.add("AGI");
        statNames.add("STR");
        statNames.add("MAG");
        statNames.add("INT");
        statNames.add("LUC");

    }

    public String getName() {
        return name;
    }

    public int getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public ArrayList<String> getStatNames() {
        return statNames;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    // REQUIRES: stat exists in character
    // MODIFIES: this
    // EFFECTS: stat is set to value
    public void setStat(String stat, int value) {
        stats.replace(stat, value);
    }

    // returns corresponding stat value
    // REQUIRES: stat exists in character
    public int checkStat(String stat) {
        return stats.get(stat);
    }

    // Max health is determined
    // MODIFIES: this
    // EFFECTS: max health is determined by VIT value, health is also changed if same as max health.
    public void determineMaxHealth() {
        if (health == maxHealth) {
            maxHealth = stats.get("VIT");
            health = maxHealth;
        } else {
            maxHealth = stats.get("VIT");
        }
    }

    // EXP is added
    // REQUIRES: positive integer value
    // MODIFIES: this
    // EFFECTS: exp is added to and level increases if EXP limit is reached
    public void addExp(int value) {
        exp += value;
        if (exp >= EXP_LIMIT) {
            addLevelCheck(exp / EXP_LIMIT);
            exp = exp % EXP_LIMIT;
        }
    }

    // Level is added to if possible
    // REQUIRES: positive integer value
    // MODIFIES: this
    // EFFECTS: level is increased by value if possible
    private void addLevelCheck(int value) {
        if ((level + value) <= LEVEL_CAP) {
            addLevel(value);
        } else if (level == LEVEL_CAP) {
            return;
        } else {
            value = LEVEL_CAP - level;
            addLevel(value);
        }
    }

    // Level is added to character
    // REQUIRES: positive integer value
    // MODIFIES: this
    // EFFECTS: level is increased by value
    private void addLevel(int value) {
        for (String statName : statNames) {
            this.changeStat(statName, value);
        }
        level += value;
        determineMaxHealth();
    }

    // Desired stat is changed by value
    // REQUIRES: valid stat name for character
    // MODIFIES: this
    // EFFECTS: Desired stat is changed by value
    public void changeStat(String statName, int value) {
        int old = stats.get(statName);
        old += value;
        if (old > -1) {
            stats.replace(statName, old);
        } else {
            stats.replace(statName, 0);
        }
    }
}

