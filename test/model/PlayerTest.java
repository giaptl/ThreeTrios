package model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for the Player class in model.
 */
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

  @Test
  public void testEqualsEqualObjects() {
    List<Card> hand = List.of(new Card("MysticEagle", 5, 10, 4, 2));
    Player player1 = new Player("Player1", hand);
    Player player2 = new Player("Player1", hand);
    assertEquals(player1, player2);
  }

  @Test
  public void testEqualsDifferentObjects() {
    List<Card> hand1 = List.of(new Card("MysticEagle", 5, 10, 4, 2));
    List<Card> hand2 = List.of(new Card("DragonWarrior", 5, 10, 4, 2));
    Player player1 = new Player("Player1", hand1);
    Player player2 = new Player("Player2", hand2);
    assertNotEquals(player1, player2);
  }

  @Test
  public void testEqualsNull() {
    Player player = new Player("Player1", List.of(new Card("MysticEagle", 5, 10, 4, 2)));
    assertNotEquals(player, null);
  }

  @Test
  public void testHashCodeEqualObjects() {
    List<Card> hand = List.of(new Card("MysticEagle", 5, 10, 4, 2));
    Player player1 = new Player("Player1", hand);
    Player player2 = new Player("Player1", hand);
    assertEquals(player1.hashCode(), player2.hashCode());
  }

  @Test
  public void testHashCodeDifferentObjects() {
    List<Card> hand1 = List.of(new Card("MysticEagle", 5, 10, 4, 2));
    List<Card> hand2 = List.of(new Card("DragonWarrior", 5, 10, 4, 2));
    Player player1 = new Player("Player1", hand1);
    Player player2 = new Player("Player2", hand2);
    assertNotEquals(player1.hashCode(), player2.hashCode());
  }
}
