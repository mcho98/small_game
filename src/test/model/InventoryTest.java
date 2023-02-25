package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test class for Inventory
public class InventoryTest {
    Character A = new Character("Arthur");
    Item I = new Item("TestItem", "A small twig with 'TestItem' written on it");
    Item J = new Item("", "");
    Inventory Inv;

    @BeforeEach
    void runBefore() {
        I.addEffects("INT", 7);
        I.addEffects("STR", -3);
        Inv = new Inventory();
    }

    @Test
    void newInventoryTest() {
        assertEquals(Inv.getPotions(), 0);
        Inv.setPotions(40);
        assertEquals(Inv.getPotions(), 40);
    }

    @Test
    void potionsTest() {
        assertEquals(Inv.getPotions(), 0);
        Inv.addPotions(1);
        assertEquals(Inv.getPotions(), 1);

        Inv.usePotion(A);
        assertEquals(Inv.getPotions(), 1);

        A.setHealth(1);
        Inv.usePotion(A);
        assertEquals(Inv.getPotions(), 0);
        assertEquals(A.getHealth(), 2);

        Inv.usePotion(A);
        assertEquals(Inv.getPotions(), 0);
        assertEquals(A.getHealth(), 2);
    }

    @Test
    void itemTest() {
        Inv.addItems(I);
        Inv.addItems(J);

        Inv.useItem(A, 0);
        Inv.discardItem(0);
        assertTrue(Inv.getItems().isEmpty());
        assertEquals(A.checkStat("INT"), 12);
        assertEquals(A.checkStat("STR"), 2);
    }
}
