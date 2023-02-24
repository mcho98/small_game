package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScenarioTest {
    Character A;
    Scenario S = new Scenario();

    @BeforeEach
    void runBefore() {
        A = new Character("Arthur");
        S.setName("Test");
        S.setIntro("You enter a testing room");
        S.setPassMessage("You complete the test with ease");
        S.setFailMessage("You fail the test");
        S.setEndMessage("You despair at your failed test. You lose the will to live");
    }

    @Test
    void newScenarioTest() {
        assertEquals(S.getName(), "Test");
        assertEquals(S.getIntro(), "You enter a testing room");
        assertEquals(S.getPassMessage(), "You complete the test with ease");
        assertEquals(S.getFailMessage(), "You fail the test");
        assertEquals(S.getEndMessage(), "You despair at your failed test. You lose the will to live");
    }

    @Test
    void conditionTest() {
        S.addCondition("VIT", 5);
        assertTrue(S.passFail(A));

        S.addCondition("MAG", 5);
        assertTrue(S.passFail(A));

        S.addCondition("AGI", 10);
        S.removeCondition("VIT");
        assertFalse(S.passFail(A));

    }

    @Test
    void resolveTest() {
        S.addCondition("VIT", 5);
        assertEquals(S.resolveScenario(A), S.getPassMessage());

        S.addCondition("LUC", 20);
        assertEquals(S.resolveScenario(A), S.getFailMessage());
        assertEquals(A.getHealth(), 4);

        A.setHealth(1);
        assertEquals(S.resolveScenario(A), S.getEndMessage());
        assertEquals(A.getHealth(), 0);
    }
}
