//package controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import model.Card;
//import model.Player;
//import model.MockThreeTriosModel;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import view.IGameView;
//
//public class InitialControllerTestClass {
//
//  private MockThreeTriosModel model;
//  private IGameView view;
//  private Controller controller;
//  private Player player;
//  private Card card;
//
//  @BeforeEach
//  public void setUp() {
//    model = new MockThreeTriosModel();
//    view = new IGameView() {
//      @Override
//      public void updateCardSelection(Player player, Card card) {
//        // Mock implementation
//      }
//
//      @Override
//      public void updateGridCell(int row, int col, Card card) {
//        // Mock implementation
//      }
//
//      @Override
//      public void removeCardFromHandPanel(Player player, Card card) {
//        // Mock implementation
//      }
//
//      @Override
//      public void refreshView() {
//        // Mock implementation
//      }
//
//      @Override
//      public void showError(String message) {
//        // Mock implementation
//      }
//    };
//    controller = new Controller(model, view);
//    player = model.getRedPlayer();
//    card = player.getHand().get(0);
//  }
//
//  @Test
//  public void testHandleCardClick_SelectCard() {
//    // Act
//    controller.handleCardClick(player, 0);
//
//    // Assert
//    assertEquals(card, controller.getSelectedCard());
//    assertEquals(player, controller.getSelectedPlayer());
//  }
//
//  @Test
//  public void testHandleCardClick_DeselectCard() {
//    // Arrange
//    controller.handleCardClick(player, 0); // Select the card first
//
//    // Act
//    controller.handleCardClick(player, 0); // Deselect the card
//
//    // Assert
//    assertNull(controller.getSelectedCard());
//    assertNull(controller.getSelectedPlayer());
//  }
//
//  @Test
//  public void testHandleGridClick_ValidMove() {
//    // Arrange
//    controller.handleCardClick(player, 0); // Select the card first
//
//    // Act
//    controller.handleGridClick(1, 1);
//
//    // Assert
//    assertNull(controller.getSelectedCard());
//    assertNull(controller.getSelectedPlayer());
//    assertEquals(card, model.getGrid().getCell(1, 1).getCard());
//  }
//
//  @Test
//  public void testHandleGridClick_InvalidMove() {
//    // Arrange
//    controller.handleCardClick(player, 0); // Select the card first
//    model.setCell(1, 1, new CardCell(card, player)); // Set cell to be non-empty
//
//    // Act
//    controller.handleGridClick(1, 1);
//
//    // Assert
//    assertNotNull(controller.getSelectedCard());
//    assertNotNull(controller.getSelectedPlayer());
//  }
//}