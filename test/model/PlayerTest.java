package model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlayerTest {

  @Test
  public void testPlayerCreation() {
    List<Card> hand = Arrays.asList(
            new Card("Card1", 1, 2, 3, 4),
            new Card("Card2", 5, 6, 7, 8)
    );
    Player player = new Player("TestPlayer", hand);
    assertNotNull(player);
    assertEquals("TestPlayer", player.getName());
    assertEquals(hand, player.getHand());
  }

  @Test
  public void testGetName() {
    Player player = new Player("TestPlayer", List.of());
    assertEquals("TestPlayer", player.getName());
  }

  @Test
  public void testGetHand() {
    List<Card> hand = Arrays.asList(
            new Card("Card1", 1, 2, 3, 4),
            new Card("Card2", 5, 6, 7, 8)
    );
    Player player = new Player("TestPlayer", hand);
    assertEquals(hand, player.getHand());
  }
}
