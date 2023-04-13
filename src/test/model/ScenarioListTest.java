package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


// Test class of Scenario List
public class ScenarioListTest {

    Scenario A;
    Scenario B;
    Scenario C;
    ScenarioList scenarioList;

    @BeforeEach
    void runBefore() {
        A = new Scenario("Test");
        B = new Scenario("Testing");
        C = new Scenario("Testing Testing");
        scenarioList = new ScenarioList();
    }

    @Test
    void basicTest() {
        assertEquals(0, scenarioList.size());
        scenarioList.add(A);
        scenarioList.add(B);
        assertEquals(2, scenarioList.size());
        assertEquals(A, scenarioList.get(0));
        scenarioList.remove(0);
        assertEquals(B, scenarioList.get(0));
    }
}

