package model;


import java.util.ArrayList;
import java.util.Hashtable;

public class Character {

    public static final int EXP_LIMIT = 100;
    public static final int LEVEL_CAP = 20;


    private String name;
    private int exp;
    private int level;
    private int health;
    private int maxHealth;
    private Hashtable<String, Integer> stats;
    private ArrayList<String> statNames;

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

    public Hashtable<String, Integer> getStats() {
        return stats;
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


    // returns corresponding stat value
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
    // MODIFIES: this
    // EFFECTS: level is increased by value if possible
    public String addLevelCheck(int value) {
        if ((level + value) <= LEVEL_CAP) {
            return addLevel(value);
        } else if (level == LEVEL_CAP) {
            return "You cannot level up anymore.";
        } else {
            value = LEVEL_CAP - level;
            return addLevel(value);
        }
    }

    // Level is added to
    // MODIFIES: this
    // EFFECTS: level is increased by value
    public String addLevel(int value) {
        for (String statName : statNames) {
            this.changeStat(statName, value);
        }
        level += value;
        determineMaxHealth();
        if (value == 1) {
            return "You have leveled up" + Integer.toString(value) + "time.";
        } else {
            return "You have leveled up" + Integer.toString(value) + "times.";
        }
    }

    // Desired stat is changed by value
    // MODIFIES: this
    // EFFECTS: Desired stat is changed by value
    public void changeStat(String statName, int value) {
        int old = stats.get(statName);
        old += value;
        stats.replace(statName, old);
    }

}

