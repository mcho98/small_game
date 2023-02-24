package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    Character A = new Character("Arthur");
    Item I;


    @BeforeEach
    void runBefore() {
        I = new Item();
        I.setName("TestItem");
        I.setDescription("A small twig with 'TestItem' written on it");
    }

    @Test
    void newItemTest() {
        assertEquals(I.getName(), "TestItem");
        assertEquals(I.getDescription(), "A small twig with 'TestItem' written on it");
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
