package persistence;

import ui.Game;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public void read(Game game) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        parseGame(game, jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game data from JSON object and modified game
    private void parseGame(Game game, JSONObject jsonObject) {
        int clearings = jsonObject.getInt("clearings");
        game.setClearings(clearings);
        addCharacter(game, jsonObject);
        addInventory(game,jsonObject);
    }

    // MODIFIES: game
    // EFFECTS: parses potions and items from JSON object and adds them to game.inventory
    private void addInventory(Game game, JSONObject jsonObject) {
        int potions = jsonObject.getInt("potions");
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        game.setInventoryFromJson(potions, jsonArray);
    }

    // MODIFIES: game
    // EFFECTS: parses character data from JSON object and adds it to game.character
    private void addCharacter(Game game, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int exp = jsonObject.getInt("exp");
        int level = jsonObject.getInt("level");
        int health = jsonObject.getInt("health");
        int maxHealth = jsonObject.getInt("maxHealth");
        int vit = jsonObject.getInt("VIT");
        int agi = jsonObject.getInt("AGI");
        int str = jsonObject.getInt("STR");
        int mag = jsonObject.getInt("MAG");
        int in = jsonObject.getInt("INT");
        int luc = jsonObject.getInt("LUC");
        game.setCharacterFromJson(name, exp, level, health, maxHealth);
        game.setCharacterStatsFromJson(vit, agi, str, mag, in, luc);
    }
}

