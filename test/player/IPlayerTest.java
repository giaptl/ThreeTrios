package player;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.Card;
import model.ICard;
import strategy.CornerStrategy;
import strategy.Strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * Test class for the IPlayer interface.
 */
public class IPlayerTest {
  private IPlayer humanPlayer;
  private IPlayer machinePlayer;
  private List<Card> initialHand;
  private Strategy mockStrategy;

  @Before
  public void setUp() {
    initialHand = new ArrayList<>();
    initialHand.add(new Card("Test Card 1", 1, 1, 1, 1));
    initialHand.add(new Card("Test Card 2", 2, 2, 2, 2));
    mockStrategy = new CornerStrategy();
    humanPlayer = new HumanPlayer("HumanPlayer", new ArrayList<>(initialHand));
    machinePlayer = new MachinePlayer("MachinePlayer", new ArrayList<>(initialHand),
           mockStrategy);
  }

  @Test
  public void testAddRemoveCard() {
    Card newCard = new Card("New Card", 3, 3, 3, 3);
    humanPlayer.addCard(newCard);
    machinePlayer.addCard(newCard);
    assertTrue(humanPlayer.getHand().contains(newCard));
    assertTrue(machinePlayer.getHand().contains(newCard));

    humanPlayer.removeCard(newCard);
    machinePlayer.removeCard(newCard);
    assertFalse(humanPlayer.getHand().contains(newCard));
    assertFalse(machinePlayer.getHand().contains(newCard));
  }

  @Test
  public void testGetName() {
    assertEquals("HumanPlayer", humanPlayer.getName().substring(0, 11));
    assertEquals("MachinePlayer", machinePlayer.getName().substring(0, 13));
  }

  @Test
  public void testGetHand() {
    assertEquals(initialHand, humanPlayer.getHand());
    assertEquals(initialHand, machinePlayer.getHand());
  }

  @Test
  public void testIsComputer() {
    assertFalse(humanPlayer.isComputer());
    assertTrue(machinePlayer.isComputer());
  }

  @Test
  public void testGetStrategy() {
    assertNull(humanPlayer.getStrategy());
    assertEquals(mockStrategy, machinePlayer.getStrategy());
  }

  @Test
  public void testSetHand() {
    List<ICard> newHand = new ArrayList<>();
    newHand.add(new Card("New Card", 3, 3, 3, 3));
    humanPlayer.setHand(newHand);
    machinePlayer.setHand(newHand);
    assertEquals(newHand, humanPlayer.getHand());
    assertEquals(newHand, machinePlayer.getHand());
  }
}
