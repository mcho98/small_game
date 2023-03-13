package persistence;

import ui.Game;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

// Test class for JsonReader
public class JsonReaderTest {
    Game game;

    @BeforeEach
    void runeBefore(){
        game = new Game();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read(game);
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyInventory() {
        JsonReader reader = new JsonReader("./data/gameEmptyTest.json");
        try {
            reader.read(game);
            assertEquals("asdf", game.getName());
            assertEquals(11, game.getStat("MAG"));
            assertEquals(0, game.getPotions());
            assertEquals(3, game.getClearings());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/gameGeneralTest.json");
        try {
            reader.read(game);
            assertEquals("asdf", game.getName());
            assertEquals(11, game.getStat("MAG"));
            assertEquals(3, game.getPotions());
            assertEquals(3, game.getClearings());

            ArrayList<Item> items = game.getItems();
            assertEquals(4, items.size());
            assertEquals("Glasses", items.get(0).getName());
            assertEquals("Enchanted Brass Knuckles", items.get(3).getName());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
