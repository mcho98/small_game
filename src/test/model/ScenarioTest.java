package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test class for Scenario
public class ScenarioTest {
    Character A;
    Scenario S;

    @BeforeEach
    void runBefore() {
        A = new Character("Arthur");
        S = new Scenario("Test", "You enter a testing room", "You complete the test with ease",
                "You fail the test", "You despair at your failed test. You lose the will to live");
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
    void conditionTestLow() {
        S.addCondition("VIT", 5);
        assertTrue(S.passFail(A, 0));

        S.addCondition("MAG", 5);
        assertTrue(S.passFail(A, 0));

        S.addCondition("AGI", 10);
        S.removeCondition("VIT");
        assertFalse(S.passFail(A, 0));

    }

    @Test
    void conditionTestHigh() {
        S.addCondition("VIT", 5);
        assertTrue(S.passFail(A, 0));
        assertFalse(S.passFail(A, 20));
    }

    @Test
    void resolveTestLow() {
        S.addCondition("VIT", 5);
        assertEquals(S.resolveScenario(A, 0), S.getPassMessage());

        S.addCondition("LUC", 20);
        assertEquals(S.resolveScenario(A, 0), S.getFailMessage());
        assertEquals(A.getHealth(), 4);

        A.setHealth(1);
        assertEquals(S.resolveScenario(A, 0), S.getEndMessage());
        assertEquals(A.getHealth(), 0);
    }

    @Test
    void resolveTestHigh() {
        S.addCondition("VIT", 5);
        assertEquals(S.resolveScenario(A, 0), S.getPassMessage());
        assertEquals(S.resolveScenario(A, 20), S.getFailMessage());
    }
}
