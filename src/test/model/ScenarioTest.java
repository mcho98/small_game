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
        S = new Scenario("Test");
    }

    @Test
    void newScenarioTest() {
        assertEquals(S.getName(), "Test");
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
        assertEquals(A.getHealth(), 5);

        S.addCondition("LUC", 20);
        assertFalse(S.resolveScenario(A, 0));
        assertEquals(A.getHealth(), 4);

        A.setHealth(1);
        assertFalse(S.resolveScenario(A, 0));
        assertEquals(A.getHealth(), 0);
    }

    @Test
    void resolveTestHigh() {
        S.addCondition("VIT", 5);
        assertTrue(S.resolveScenario(A, 0));
        assertEquals(A.getHealth(), 5);
        assertFalse(S.resolveScenario(A, 20));
        assertEquals(A.getHealth(), 4);
    }
}
