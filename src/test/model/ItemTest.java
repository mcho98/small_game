package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Hashtable;

// Test class for Item
public class ItemTest {
    Character A = new Character("Arthur");
    Item I;

    @BeforeEach
    void runBefore() {
        I = new Item("TestItem", "A small twig with 'TestItem' written on it");
    }

    @Test
    void newItemTest() {
        assertEquals(I.getName(), "TestItem");
        assertEquals(I.getDescription(), "A small twig with 'TestItem' written on it");

        I.setName("A");
        I.setDescription("B");
        assertEquals(I.getName(), "A");
        assertEquals(I.getDescription(), "B");

        Hashtable<String, Integer> x = I.getEffects();
        assertEquals(I.getEffects(), x);
    }

    @Test
    void applyEffectsTest() {
        I.addEffects("INT", 7);
        I.addEffects("STR", -3);
        I.applyEffects(A);

        assertEquals(A.checkStat("INT"), 12);
        assertEquals(A.checkStat("STR"), 2);

    }

}
