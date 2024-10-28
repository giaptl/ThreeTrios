package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import configuration.ConfigurationReader;

/**
 * Represents the game model.
 */
public class GameModel implements IGameModel {

  private Grid grid;
  private Player currentPlayer;
  private Map<Player, List<Card>> playerHands;
  private Player pRed;
  private Player pBlue;
  boolean isGameOver;
  private List<Card> deck;

  /**
   * Creates a new game model.
   */
  public GameModel() {
    this.playerHands = new HashMap<>();
    this.isGameOver = false;
  }

  public Player getRedPlayer() {
    return pRed;
  }

  public Player getBluePlayer() {
    return pBlue;
  }

  @Override
  public void startGameDefault(boolean shuffle, int row, int col) {
//    deck = new ArrayList<>();
//
//    this.grid = new Grid(row, col);
//
//    int numCardCells = grid.getNumCardCells();
//    if (numCardCells % 2 == 0) {
//      throw new IllegalArgumentException("Number of card cells must be odd.");
//    }
//
//    int totalCardsNeeded = numCardCells + 1;
//
//    if (deck.size() < totalCardsNeeded) {
//      throw new IllegalArgumentException("Not enough cards to start game.");
//    }
//
//    if (shuffle) {
//      Collections.shuffle(deck);
//    }
//
//    int cardsPerPlayer = (numCardCells + 1) / 2;
//    List<Card> redHand = new ArrayList<>(deck.subList(0, cardsPerPlayer));
//    List<Card> blueHand = new ArrayList<>(deck.subList(cardsPerPlayer + 1, (cardsPerPlayer * 2)));
//
//    pRed = new Player("Red", redHand);
//    pBlue = new Player("Blue", blueHand);
//
//    playerHands.put(pRed, redHand);
//    playerHands.put(pBlue, blueHand);
//    currentPlayer = pRed;
//
//
//    isGameOver = false;
  }


  @Override
  public void startGameWithConfig(Grid grid, List<Card> cards, boolean shuffle) {
    this.grid = grid;
    this.deck = cards;
    if (shuffle) {
      Collections.shuffle(deck);
    }

    int numCardCells = grid.getNumCardCells();
    if (numCardCells % 2 == 0) {
      throw new IllegalArgumentException("Number of card cells must be odd.");
    }

    int totalCardsNeeded = numCardCells + 1;
    if (deck.size() < totalCardsNeeded) {
      throw new IllegalArgumentException("Not enough cards to start game.");
    }


    int cardsPerPlayer = (numCardCells + 1) / 2;
    List<Card> redHand = new ArrayList<>(deck.subList(0, cardsPerPlayer));
    List<Card> blueHand = new ArrayList<>(deck.subList(cardsPerPlayer + 1, (cardsPerPlayer * 2)));

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
    return grid;
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
    if (!player.equals(currentPlayer)) {
      throw new IllegalArgumentException("It is not " + player.getName() + "'s turn.");
    }

    if (isGameOver) {
      throw new IllegalArgumentException("Game is over.");
    }

    if (row < 0 || row >= grid.getRows() || col < 0 || col >= grid.getColumns()) {
      throw new IllegalArgumentException("Invalid row or column.");
    }

    Cell cell = grid.getCell(row, col);
    if (!cell.isEmpty()) {
      throw new IllegalArgumentException("Cell is not empty.");
    }

    CardCell cardCell = new CardCell(card, player);
    grid.setCell(row, col, cardCell);
    player.getHand().remove(card);

    // Switch the current player
    currentPlayer = currentPlayer.equals(pRed) ? pBlue : pRed;

    // Check if the game is over
    isGameOver = isGameOver();

  }

  @Override
  public void startBattlePhase(int row, int col) {

    Cell cell = grid.getCell(row, col);
    if (cell.isEmpty()) {
      throw new IllegalArgumentException("Cell is empty.");
    }

    CardCell cardCell = (CardCell) cell;
    Card card = cardCell.getCard();
    Player owner = cardCell.getOwner();

    // Battle logic (example: compare attack values with adjacent cards)
    for (Direction direction : Direction.values()) {
      int newRow = row + direction.getRowOffset();
      int newCol = col + direction.getColOffset();

      if (newRow >= 0 && newRow < grid.getRows() && newCol >= 0 && newCol < grid.getColumns()) {
        Cell adjacentCell = grid.getCell(newRow, newCol);

        // Checks if the adjacent cell is a card cell and not a hole
        // REMEMEBER ME TOMORROW THAT HOLS EXTENDS A CARDCELL
        if (!(adjacentCell instanceof Hole)) {
          // Error is happening right here
          CardCell adjacentCardCell = (CardCell) adjacentCell;
          Card adjacentCard = adjacentCardCell.getCard();
          Player adjacentOwner = adjacentCardCell.getOwner();

          // Checks if card is not owned by the same player, if it isn't then compare attack
          if (!owner.equals(adjacentOwner)) {
            int attackValue = card.getAttackValue(direction);
            // Or this can be your problem (assuming it is in getOpposite)
            int defenseValue = adjacentCard.getAttackValue(direction.getOpposite());
            if (attackValue > defenseValue) {
              adjacentCardCell.setOwner(owner);
            }
          }
        }
      }
    }
  }
}