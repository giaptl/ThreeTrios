package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the initial game model created for ThreeTrios.
 */
public class GameModel implements ThreeTriosModel {

  private Grid grid;
  private Player currentPlayer;
  private final Map<Player, List<Card>> playerHands;
  private Player pRed;
  private Player pBlue;
  private boolean isGameOver;

  /**
   * Creates a new game model.
   */
  public GameModel() {
    this.playerHands = new HashMap<>();
    this.isGameOver = false;
  }

  @Override
  public Player getRedPlayer() {
    return pRed;
  }

  @Override
  public Player getBluePlayer() {
    return pBlue;
  }

  @Override
  public void startGameWithConfig(Grid grid, List<Card> cards, boolean shuffle) {
    this.grid = grid;
    if (shuffle) {
      Collections.shuffle(cards);
    }

    int numCardCells = grid.getNumCardCells();
    if (numCardCells % 2 == 0) {
      throw new IllegalArgumentException("Number of card cells must be odd.");
    }

    int totalCardsNeeded = numCardCells + 1;
    if (cards.size() < totalCardsNeeded) {
      throw new IllegalArgumentException("Not enough cards to start game.");
    }

    int cardsPerPlayer = (numCardCells + 1) / 2;
    List<Card> redHand = new ArrayList<>(cards.subList(0, cardsPerPlayer));
    List<Card> blueHand = new ArrayList<>(cards.subList(cardsPerPlayer + 1, (cardsPerPlayer * 2)));

    pRed = new Player("Red", redHand);
    pBlue = new Player("Blue", blueHand);

    playerHands.put(pRed, redHand);
    playerHands.put(pBlue, blueHand);

    currentPlayer = pRed;
    isGameOver = false;
  }


  @Override
  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public Grid getGrid() {
    return grid.copyOfGrid();
  }

  @Override
  public List<Card> getPlayerHand(Player player) {
    return playerHands.get(player);
  }

  @Override
  public boolean isGameOver() {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        Cell cell = grid.getCell(row, col);
        if (!cell.isHole() && cell.isEmpty()) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public Player getWinner() {

    int redCardCount = pRed.getHand().size();
    int blueCardCount = pBlue.getHand().size();

    int[] counts = countOccupiedCells(redCardCount, blueCardCount);
    redCardCount = counts[0];
    blueCardCount = counts[1];

    if (redCardCount > blueCardCount) {
      // Red Player wins
      return pRed;
    } else if (blueCardCount > redCardCount) {
      // Blue player wins
      return pBlue;
    } else {
      // Tie
      return null;
    }
  }

  // Helper method that counts the number of occupied cells for each player.
  private int[] countOccupiedCells(int redCardCount, int blueCardCount) {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        Cell cell = grid.getCell(row, col);
        if (cell.isOccupied()) {
          Player owner = cell.getOwner();
          if (owner.equals(pRed)) {
            redCardCount++;
          } else if (owner.equals(pBlue)) {
            blueCardCount++;
          }
        }
      }
    }
    return new int[]{redCardCount, blueCardCount};
  }

  @Override
  public void playCard(Player player, Card card, int row, int col) {
    playCardConditions(player, row, col, card);

    CardCell cardCell = new CardCell(card, player);
    grid.setCell(row, col, cardCell);
    player.getHand().remove(card);

    // Switch the current player
    currentPlayer = currentPlayer.equals(pRed) ? pBlue : pRed;

    // Check if the game is over
    isGameOver = isGameOver();

  }

  // Helper method to check the conditions for playing a card.
  private void playCardConditions(Player player, int row, int col, Card cardToPlay) {
    if (!player.equals(currentPlayer)) {
      throw new IllegalArgumentException("It is not " + player.getName() + "'s turn.");
    }

    if (!player.getHand().contains(cardToPlay)) {
      throw new IllegalArgumentException("Card is not in player's hand.");
    }

    Cell cell = grid.getCell(row, col);
    if (!cell.isEmpty()) {
      throw new IllegalArgumentException("Cell is not empty.");
    }

    if (row < 0 || row >= grid.getRows() || col < 0 || col >= grid.getColumns()) {
      throw new IllegalArgumentException("Invalid row or column.");
    }

    if (isGameOver) {
      throw new IllegalArgumentException("Game is over.");
    }

  }

  @Override
  public void startBattlePhase(int row, int col) {

    Cell cell = grid.getCell(row, col);
    if (!(cell instanceof CardCell)) {
      throw new IllegalArgumentException("No card at the specified cell or cell is a hole.");
    }
    CardCell cardCell = (CardCell) cell;
    Card card = cardCell.getCard();
    Player owner = cardCell.getOwner();

    // Battle logic (example: compare attack values with adjacent cards)
    for (Direction direction : Direction.values()) {
      int newRow = row + direction.getRowOffset();
      int newCol = col + direction.getColOffset();
      cardAttackDirections(direction, newRow, newCol, owner, card);
    }
  }

  // Helper method which extracts the logic behind cards attacking and changing ownership.
  private void cardAttackDirections(Direction direction, int newRow,
                                    int newCol, Player owner, Card card) {

    if (newRow >= 0 && newRow < grid.getRows() && newCol >= 0 && newCol < grid.getColumns()) {
      Cell adjacentCell = grid.getCell(newRow, newCol);

      // Ensure the adjacent cell is a CardCell and not null
      if (adjacentCell instanceof CardCell) {
        CardCell adjacentCardCell = (CardCell) adjacentCell;
        Card adjacentCard = adjacentCardCell.getCard();
        Player adjacentOwner = adjacentCardCell.getOwner();

        if (adjacentCard != null && !owner.equals(adjacentOwner)) {
          int attackValue = card.getAttackValue(direction);
          int defenseValue = adjacentCard.getAttackValue(direction.getOpposite());

          if (attackValue > defenseValue) {
            adjacentCardCell.setOwner(owner);
          }
        }
      }
    }
  }


  @Override
  public int getNumCardsAbleToFlip(Card card, int row, int col) {
  // Save the current state of the cell
    Cell originalCell = grid.getCell(row, col);

    // Temporarily place the card on the grid
    CardCell tempCardCell = new CardCell(card, currentPlayer);
    grid.setCell(row, col, tempCardCell);

    // Simulate the battle phase and count the number of cards flipped
    int cardsFlipped = 0;
    for (Direction direction : Direction.values()) {
      int newRow = row + direction.getRowOffset();
      int newCol = col + direction.getColOffset();
      cardsFlipped += simulateCardAttack(direction, newRow, newCol, currentPlayer, card);
    }

    // Revert the grid to its original state
    grid.setCell(row, col, originalCell);

    return cardsFlipped;
  }

  // Helper method to simulate a card attack and return the number of cards flipped
  private int simulateCardAttack(Direction direction, int newRow, int newCol, Player owner, Card card) {
    int cardsFlipped = 0;

    if (newRow >= 0 && newRow < grid.getRows() && newCol >= 0 && newCol < grid.getColumns()) {
      Cell adjacentCell = grid.getCell(newRow, newCol);

      if (adjacentCell instanceof CardCell) {
        CardCell adjacentCardCell = (CardCell) adjacentCell;
        Card adjacentCard = adjacentCardCell.getCard();
        Player adjacentOwner = adjacentCardCell.getOwner();

        if (adjacentCard != null && !owner.equals(adjacentOwner)) {
          int attackValue = card.getAttackValue(direction);
          int defenseValue = adjacentCard.getAttackValue(direction.getOpposite());

          if (attackValue > defenseValue) {
            cardsFlipped++;
          }
        }
      }
    }
    return cardsFlipped;
  }


  @Override
  public int getPlayerScore(Player player) {
    int redCardCount = pRed.getHand().size();
    int blueCardCount = pBlue.getHand().size();

    int[] counts = countOccupiedCells(redCardCount, blueCardCount);
    redCardCount = counts[0];
    blueCardCount = counts[1];

    if (player == pRed) {
      return redCardCount;
    } else if (player == pBlue) {
      return blueCardCount;
    }
    return -1;
  }
}