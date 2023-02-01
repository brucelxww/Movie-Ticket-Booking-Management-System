import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

public class ErrorCheckTest {
    private InputErrorChecks errorChecks = new InputErrorChecks();
    private CardSystem cardSystem = new CardSystem();

    @Test
    public void validOptionCheckerTest() {
        assertFalse(errorChecks.validOptionChecker("sad",1,2));
        assertFalse(errorChecks.validOptionChecker("-2",1,2));
        assertFalse(errorChecks.validOptionChecker("3",1,2));
        assertTrue(errorChecks.validOptionChecker("2",1,2));
    }

    @Test
    public void timeCheckerTest() {
        assertFalse(errorChecks.timeChecker(""));
        assertFalse(errorChecks.timeChecker("sfzsdf"));
        assertTrue(errorChecks.timeChecker("12:30"));
    }

    @Test
    public void dateCheckerTest() {
        assertFalse(errorChecks.dateChecker(""));
        assertFalse(errorChecks.dateChecker("asdasd"));
        assertFalse(errorChecks.dateChecker("22/24/2010"));
        assertTrue(errorChecks.dateChecker("22/12/2021"));
        assertFalse(errorChecks.dateChecker("22/06/2021"));
    }

    @Test
    public void giftCardCheckTest() {
        cardSystem.load_gifts_card();
        assertFalse(errorChecks.giftCardCheck("", cardSystem));
        assertFalse(errorChecks.giftCardCheck("asdasd", cardSystem));
        assertFalse(errorChecks.giftCardCheck("0000000000000000", cardSystem));
        assertFalse(errorChecks.giftCardCheck("000000000000000", cardSystem));
        assertFalse(errorChecks.giftCardCheck("-9191928382838777", cardSystem));
        assertFalse(errorChecks.giftCardCheck("0000000000000000000", cardSystem));
        assertFalse(errorChecks.giftCardCheck("123456789012345a", cardSystem));
        assertFalse(errorChecks.giftCardCheck("-123456789012345", cardSystem));
        assertTrue(errorChecks.giftCardCheck("2838482838483828", cardSystem));
    }

    @Test
    public void numberCheckerTest() {
        assertFalse(errorChecks.numberChecker("ajhsdkuas"));
        assertFalse(errorChecks.numberChecker("-123"));
        assertTrue(errorChecks.numberChecker("123"));
    }

    @Test
    public void classificationCheckerTest() {
        assertTrue(errorChecks.classificationChecker("G"));
        assertTrue(errorChecks.classificationChecker("PG"));
        assertTrue(errorChecks.classificationChecker("M"));
        assertTrue(errorChecks.classificationChecker("MA15+"));
        assertTrue(errorChecks.classificationChecker("R18+"));
        assertFalse(errorChecks.classificationChecker(""));
        assertFalse(errorChecks.classificationChecker("aaa"));
        assertFalse(errorChecks.classificationChecker("0"));
        assertFalse(errorChecks.classificationChecker("100"));
    }

    @Test
    public void nonEmptyCheckerTest() {
        assertFalse(errorChecks.nonEmpty(""));
        assertFalse(errorChecks.nonEmpty(" "));
        assertFalse(errorChecks.nonEmpty("     "));
        assertTrue(errorChecks.nonEmpty("abc"));
    }

    @Test
    public void confirmCheckerTest() {
        assertTrue(errorChecks.confirmChecker("yes"));
        assertTrue(errorChecks.confirmChecker(" yes "));
        assertTrue(errorChecks.confirmChecker("YES"));
        assertTrue(errorChecks.confirmChecker("cancel"));
        assertTrue(errorChecks.confirmChecker(" cancel "));
        assertTrue(errorChecks.confirmChecker("CANCEL"));

        assertFalse(errorChecks.confirmChecker(""));
        assertFalse(errorChecks.confirmChecker("aaaaa"));
        assertFalse(errorChecks.confirmChecker("0"));
    }
}
