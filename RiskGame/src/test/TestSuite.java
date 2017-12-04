package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.cards.DiceTest;
import test.cards.HandTest;
import test.map.MapModelTest;
import test.player.*;
import test.tournament.TournamentTest;

/**
 * This class will run all the junit test cases for the Risk Game.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TournamentTest.class,
        MapModelTest.class,
        PlayerTest.class,
        DiceTest.class,
        HandTest.class,
        AggressiveBotTest.class,
        BenevolentBotTest.class,
        RandomBotTest.class,
        CheaterBotTest.class
        })
public class TestSuite {
    //nothing
}
