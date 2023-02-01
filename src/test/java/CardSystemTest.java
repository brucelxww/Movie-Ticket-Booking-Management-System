import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.io.*;

public class CardSystemTest {
    private CardSystem cardSystem;

    @BeforeEach
    public void init() {
        cardSystem = new CardSystem();
        cardSystem.load_gifts_card();
        cardSystem.load_credits_cards();
        cardSystem.load_old_customer();
    }

    @Test
    public void checkingIfGiftCardExists() {
        assertTrue(cardSystem.check_gift_card_exists("0000000000000000GC"));
        assertFalse(cardSystem.check_gift_card_exists("0000001230000000GC"));
    }

    @Test
    public void insertGiftCardTest() {
        assertTrue(cardSystem.updateOrInsertGiftCard("0000000000000023GC", 20));
        assertFalse(cardSystem.updateOrInsertGiftCard("0000000000000000GC", 20));
    }

    @Test
    public void gettingGiftCardAmount() {
        assertEquals(cardSystem.get_card_amount("0000000000000000GC"), 100);
    }

    @Test
    public void checkingIfGiftCardIsRedeemable() {
        assertTrue(cardSystem.check_gift_card("0000000000000000GC"));
        cardSystem.redeemCard("0000000000000000GC");
        assertFalse(cardSystem.check_gift_card("0000000000000000GC"));
    }

    @Test
    public void test_checkGiftCard_exist() {
        assertTrue(cardSystem.check_gift_card("8810323965721415GC"));
        assertFalse(cardSystem.check_gift_card("0000001230000000GC"));
    }

    @Test
    public void test_check_credit_card() {
        assertTrue(cardSystem.check_credit_card("Charles", "40691"));
        assertFalse(cardSystem.check_credit_card("Charles", "40692"));
    }

    @Test
    public void test_cardExits() {
        assertTrue(cardSystem.cardExits("Charles"));
        assertFalse(cardSystem.cardExits(""));
        assertFalse(cardSystem.cardExits("aaa"));
        assertFalse(cardSystem.cardExits("Charle"));
    }
}
