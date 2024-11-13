package controller;


/**
 * Test class for the controller. Yet to be implemented due to time constraints.
 */
public class InitialControllerTestClass {
//  private Controller controller;
//  private ThreeTriosModel model;
//  private IGameView view;
//  private Player testPlayer;
//  private Card testCard;
//  private List<Card> testHand;

//  MOST OF THESE TESTS TO BE IMPLEMENTED

//  @Before
//  public void setUp() {
//    model = new GameModel();
//    view = new GameView(model);
//    controller = new Controller(model, view);
//
//    testPlayer = new Player("TestPlayer");
//    testCard = new Card("TestCard");
//
//    testHand = new ArrayList<>();
//    testHand.add(testCard);
//
//    model.setPlayerHand(testPlayer, testHand);
//  }
//
//  @Test
//  public void testHandleCardClick_SelectCard() {
//    controller.handleCardClick(testPlayer, 0);
//    assertEquals(testPlayer, view.getSelectedPlayer());
//    assertEquals(testCard, view.getSelectedCard());
//  }
//
//  @Test
//  public void testHandleCardClick_DeselectSameCard() {
//    controller.handleCardClick(testPlayer, 0);
//    controller.handleCardClick(testPlayer, 0);
//    assertNull(view.getSelectedPlayer());
//    assertNull(view.getSelectedCard());
//  }
//
//  @Test
//  public void testHandleCardClick_SelectDifferentCard() {
//    Card secondCard = new Card("SecondCard");
//    testHand.add(secondCard);
//
//    controller.handleCardClick(testPlayer, 0);
//    controller.handleCardClick(testPlayer, 1);
//
//    assertEquals(testPlayer, view.getSelectedPlayer());
//    assertEquals(secondCard, view.getSelectedCard());
//  }
//
//  @Test
//  public void testHandleGridClick_WithNoSelection() {
//    controller.handleGridClick(0, 0);
//    assertFalse(model.isPlayCardCalled());
//  }
//
//  @Test
//  public void testHandleGridClick_ValidMove() {
//    controller.handleCardClick(testPlayer, 0);
//    controller.handleGridClick(0, 0);
//
//    assertTrue(model.isPlayCardCalled());
//    assertEquals(0, model.getRow());
//    assertEquals(0, model.getCol());
//    assertEquals(testPlayer, model.getPlayer());
//    assertEquals(testCard, model.getCard());
//  }
//
//  @Test
//  public void testHandleGridClick_InvalidMove() {
//    controller.handleCardClick(testPlayer, 0);
//    model.setInvalidMove(true);
//
//    controller.handleGridClick(0, 0);
//
//    assertEquals("Invalid move: Invalid move", view.showError();
//  }
//
//  @Test
//  public void testHandleGridClick_ClearsSelectionAfterValidMove() {
//    controller.handleCardClick(testPlayer, 0);
//    controller.handleGridClick(0, 0);
//    controller.handleGridClick(1, 1);
//
//    assertEquals(1, model.getPlayCardCallCount());
//  }
}