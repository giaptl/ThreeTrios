package player;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import controller.PlayerActionListener;
import model.Card;
import model.ThreeTriosModel;
import strategy.CornerStrategy;
import strategy.Strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

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
  public void testTakeTurn() {
//    ThreeTriosModel mockModel = mock(ThreeTriosModel.class);
//    humanPlayer.takeTurn(mockModel);
//    machinePlayer.takeTurn(mockModel);
//    // Verify that the machine player's strategy was called
//    verify(mockStrategy).selectMove(eq(machinePlayer), eq(mockModel));
  }

  @Test
  public void testAddRemovePlayerActionListener() {
    //PlayerActionListener mockListener = mock(PlayerActionListener.class);
//    humanPlayer.addPlayerActionListener(mockListener);
//    humanPlayer.removePlayerActionListener(mockListener);
//    machinePlayer.addPlayerActionListener(mockListener);
//    machinePlayer.removePlayerActionListener(mockListener);
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
    List<Card> newHand = new ArrayList<>();
    newHand.add(new Card("New Card", 3, 3, 3, 3));
    humanPlayer.setHand(newHand);
    machinePlayer.setHand(newHand);
    assertEquals(newHand, humanPlayer.getHand());
    assertEquals(newHand, machinePlayer.getHand());
  }
}
