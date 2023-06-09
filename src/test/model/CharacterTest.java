package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;


// Test class for Character
class CharacterTest {
    Character A;

    @BeforeEach
    void runBefore() {
        A = new Character("Arthur");
    }

    // create new Character test
    @Test
    void newCharTest() {
        assertEquals(A.getName(), "Arthur");
        assertEquals(A.getExp(), 0);
        assertEquals(A.getLevel(), 1);
        assertEquals(A.getMaxHealth(), 5);
        assertEquals(A.getHealth(), 5);
        assertEquals(A.checkStat("MAG"), 5);

        A.setName("Amy");
        assertEquals(A.getName(), "Amy");

        ArrayList<String> x = A.getStatNames();
        assertEquals(x, A.getStatNames());
    }

    // create new Character test
    @Test
    void maxHealthTest() {
        A.setMaxHealth(40);
        A.setHealth(1);
        A.determineMaxHealth();
        assertEquals(A.getMaxHealth(), 5);
        assertEquals(A.getHealth(), 1);

        A.setHealth(5);
        A.setMaxHealth(40);
        A.determineMaxHealth();
        assertEquals(A.getMaxHealth(), A.getHealth());
        assertEquals(A.getHealth(), 5);
    }

    @Test
    void expTest() {
        A.setExp(50);
        assertEquals(A.getExp(), 50);
        assertEquals(A.getLevel(), 1);

        A.addExp(49);
        assertEquals(A.getLevel(), 1);

        A.addExp(1);
        assertEquals(A.getExp(), 0);
        assertEquals(A.getLevel(), 2);

        A.addExp(400);
        assertEquals(A.getExp(), 0);
        assertEquals(A.getLevel(), 6);
        assertEquals(A.checkStat("VIT"), 10);
    }

    @Test
    void maxLevelTest() {
        A.setLevel(19);
        A.addExp(99);
        assertEquals(A.getExp(), 99);
        assertEquals(A.getLevel(), 19);

        A.addExp(1);
        assertEquals(A.getLevel(), 20);

        A.addExp(1);
        assertEquals(A.getExp(), 1);
        assertEquals(A.getLevel(), 20);

        A.addExp(400);
        assertEquals(A.getExp(), 1);
        assertEquals(A.getLevel(), 20);
    }

    @Test
    void maxLevelTestOverflow() {
        A.setLevel(19);
        A.addExp(400);
        assertEquals(A.getExp(), 0);
        assertEquals(A.getLevel(), 20);
    }

    @Test
    void changeStatTest() {
        A.changeStat("STR", 10);
        assertEquals(A.checkStat("STR"), 15);

        A.changeStat("STR", -15);
        assertEquals(A.checkStat("STR"), 0);

        A.changeStat("MAG", -10);
        assertEquals(A.checkStat("MAG"), 0);

        A.setStat("VIT", 10);
        assertEquals(A.checkStat("VIT"), 10);
    }
}

