package persistence;

import ui.Game;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

// Test class for JsonWriter
public class JsonWriterTest {
    Game game;

    @BeforeEach
    void runeBefore(){
        game = new Game();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGame() {
        JsonReader reader = new JsonReader("./data/gameEmptyTest.json");
        try {
            reader.read(game);
            JsonWriter writer = new JsonWriter("./data/writerGameEmptyTest.json");
            writer.open();
            writer.write(game);
            writer.close();

            Game test = new Game();

            JsonReader reader2 = new JsonReader("./data/writerGameEmptyTest.json");
            reader2.read(test);
            assertEquals("asdf", game.getName());
            assertEquals(11, game.getStat("MAG"));
            assertEquals(0, game.getPotions());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGame() {
        JsonReader reader = new JsonReader("./data/gameGeneralTest.json");
        try {
            reader.read(game);
            JsonWriter writer = new JsonWriter("./data/writerGameGeneralTest.json");
            writer.open();
            writer.write(game);
            writer.close();

            Game test = new Game();

            JsonReader reader2 = new JsonReader("./data/writerGameEmptyTest.json");
            reader2.read(test);
            assertEquals("asdf", game.getName());
            assertEquals(11, game.getStat("MAG"));
            assertEquals(3, game.getPotions());

            ArrayList<Item> items = game.getItems();
            assertEquals(4, items.size());
            assertEquals("Glasses", items.get(0).getName());
            assertEquals("Enchanted Brass Knuckles", items.get(3).getName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
