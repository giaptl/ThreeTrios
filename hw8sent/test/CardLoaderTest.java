import model.cards.Card;
import model.cards.CardLoader;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the CardLoader class to verify correct loading of card files.
 */
public class CardLoaderTest {

  /**
   * Tests loading a valid card file to ensure it loads correctly and is not empty.
   */
  @Test
  public void testLoadValidCardFile() {
    List<Card> cards = CardLoader.loadCards("src/main/resources/cards/cards_example.txt");
    assertNotNull(cards);
    assertFalse(cards.isEmpty());
  }

  /**
   * Tests loading an empty card file to verify it returns an empty list.
   */
  @Test
  public void testLoadEmptyCardFile() {
    List<Card> cards = CardLoader.loadCards("src/main/resources/cards/empty_file.txt");
    assertTrue(cards.isEmpty());
  }
}
