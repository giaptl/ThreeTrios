package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import player.IPlayer;

/**
 * Represents the initial game model created for ThreeTrios.
 * This class manages the state and logic of the game, including the grid, players, and game rules.
 * It provides methods to start the game, play cards, and determine the game's status and winner.
 * Model declares ownership of cards and hands them out to players.
 */
public class GameModel implements ThreeTriosModel {

  private Grid grid;
  private IPlayer currentPlayer;
  private IPlayer pRed;
  private IPlayer pBlue;
  private boolean isGameOver;
  private final List<ModelStatusListener> modelStatusListeners = new ArrayList<>();


  /**
   * Creates a new game model. Doesn't need to be passed in anything as this is basically a
   * door to the "actual" instantiation of the model which happens in startGameWithConfig.
   */
  public GameModel() {
  }

  @Override
  public IPlayer getRedPlayer() {
    return pRed;
  }

  @Override
  public IPlayer getBluePlayer() {
    return pBlue;
  }

  @Override
  public void startGameWithConfig(Grid grid, List<Card> cards, boolean shuffle,
                                  IPlayer player1, IPlayer player2) {
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

    System.out.println("RedHand: " + redHand);
    System.out.println("BlueHand: " + blueHand);
    player1.setHand(redHand);
    player2.setHand(blueHand);
    pRed = player1;
    pBlue = player2;

    for (ModelStatusListener listener : modelStatusListeners) {
      listener.onPlayerTurn(currentPlayer);
    }

    currentPlayer = pRed;
    isGameOver = false;
  }


  @Override
  public IPlayer getCurrentPlayer() {
    return this.currentPlayer;
  }

  @Override
  public IPlayer getOpponent(IPlayer player) {
    if (player.equals(pRed)) {
      return pBlue;
    }
    return pRed;
  }

  @Override
  public Grid getGrid() {
    return grid.copyOfGrid();
  }

  @Override
  public List<Card> getPlayerHand(IPlayer player) {
    return player.getHand();
  }

  /**
   * Helper method that counts the number of occupied cells for each player.
   */
  private int[] countOccupiedCells() {
    int redCardCount = 0;
    int blueCardCount = 0;
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        if (!grid.getCell(row, col).isHole()) {
          CardCell cell = (CardCell) grid.getCell(row, col);
          if (cell.isOccupied()) {
            IPlayer owner = cell.getOwner();
            if (owner.equals(pRed)) {
              redCardCount++;
            } else if (owner.equals(pBlue)) {
              blueCardCount++;
            }
          }
        }
      }
    }
    return new int[]{redCardCount + getPlayerHand(pRed).size(), blueCardCount +getPlayerHand(pBlue).size()};
  }

  @Override
  public void playCard(IPlayer player, Card card, int row, int col) {
    playCardConditions(player, row, col, card);

    CardCell cardCell = new CardCell(card, player);
    grid.setCell(row, col, cardCell);
    player.removeCard(card);
    startBattlePhase(row, col);

    // Switch the current player
    currentPlayer = currentPlayer.equals(pRed) ? pBlue : pRed;
    if (!modelStatusListeners.isEmpty()) {
      modelStatusListeners.get(0).onPlayerTurn(currentPlayer);
    }

    // Notify all listeners about the card play
    for (ModelStatusListener listener : modelStatusListeners) {
      listener.onCardPlayed(player, card, row, col);
      listener.onPlayerTurn(currentPlayer);
    }

    // isGameOver = isGameOver();

//    if (isGameOver()) {
//      for (ModelStatusListener listener : modelStatusListeners) {
//        listener.gameOver(getWinner());
//      }
//    }
  }

  /**
   * Helper method to check the conditions for playing a card.
   */
  private void playCardConditions(IPlayer player, int row, int col, Card cardToPlay) {
    Cell cell = grid.getCell(row, col);
    if (!player.equals(currentPlayer)) {
      throw new IllegalArgumentException("It is not " + player.getName() + "'s turn.");
    }

    if (!player.getHand().contains(cardToPlay)) {
      throw new IllegalArgumentException("Card is not in player's hand.");
    }

    if (!(isValidCell(row, col))) {
      throw new IllegalArgumentException("Invalid row or column.");
    }

    if (cell.isHole()) {
      throw new IllegalArgumentException("Card cannot be placed on a hole.");
    }

    if (!cell.isEmpty()) {
      System.out.println("Cell at (" + row + "," + col + ") isEmpty: " + cell.isEmpty());
      throw new IllegalArgumentException("Cell is not empty.");
    }

    if (isGameOver) {
      throw new IllegalArgumentException("Game is over.");
    }
  }

  @Override
  public void startBattlePhase(int row, int col) {
    Cell cell = grid.getCell(row, col);
    if (cell.isHole()) {
      throw new IllegalArgumentException("No card at the specified cell or cell is a hole.");
    }
    CardCell cardCell = (CardCell) cell;
    IPlayer owner = cardCell.getOwner();

    processBattlePhase(owner, row, col);
  }

  @Override
  public void addModelStatusListener(ModelStatusListener listener) {
    modelStatusListeners.add(listener);
  }

  @Override
  public void removeModelStatusListener(ModelStatusListener listener) {
    modelStatusListeners.remove(listener);
  }

  @Override
  public int getNumCardsAbleToFlip(IPlayer player, Card card, int row, int col) {
    // Save the current state of the grid
    Grid originalGrid = grid.copyOfGrid();

    // Temporarily place the card on the grid
    CardCell tempCardCell = new CardCell(card, player);
    grid.setCell(row, col, tempCardCell);

    // Simulate the battle phase and count the number of cards flipped
    int cardsFlipped = simulateEntireBattlePhase(player, row, col);

    // Revert the grid to its original state
    grid.setGrid(originalGrid);
    return cardsFlipped;
  }

  /**
   * Simulation of entire battle phase. Abstracted into its own method for code clarity.
   */
  private int simulateEntireBattlePhase(IPlayer player, int startRow, int startCol) {
    return processBattlePhase(player, startRow, startCol);
  }

  @Override
  public int getPlayerScore(IPlayer player) {
    int[] counts = countOccupiedCells();
    int redCardCount = counts[0];
    int blueCardCount = counts[1];

    if (player == pRed) {
      return redCardCount;
    } else if (player == pBlue) {
      return blueCardCount;
    }
    return -1;
  }

  /**
   * Helper method which abstracts out much of the battle phase
   * code from both the simulation and the actual battle phase.
   */
  protected int processBattlePhase(IPlayer player, int startRow, int startCol) {
    int cardsFlipped = 0;
    Queue<int[]> toProcess = new LinkedList<>();
    toProcess.offer(new int[]{startRow, startCol});

    while (!toProcess.isEmpty()) {
      int[] current = toProcess.poll();
      int row = current[0];
      int col = current[1];

      CardCell currentCell = (CardCell) grid.getCell(row, col);
      if (currentCell == null || currentCell.getCard() == null) {
        continue;
      }
      cardsFlipped += processAdjacentCells(player, currentCell.getCard(), row, col, toProcess);
    }
    return cardsFlipped;
  }

  /**
   * Checks to see which cells are being flipped and also flipping cards
   * that have already been flipped.
   */
  protected int processAdjacentCells(IPlayer player, Card currentCard, int row, int col,
                                     Queue<int[]> toProcess) {
    int flipped = 0;
    for (Direction direction : Direction.values()) {
      int newRow = row + direction.getRowOffset();
      int newCol = col + direction.getColOffset();

      if (isValidCell(newRow, newCol)) {
        int flippedInDirection = cardAttackDirections(
                direction, newRow, newCol, player, currentCard);
        flipped += flippedInDirection;
        if (flippedInDirection > 0) {
          toProcess.offer(new int[]{newRow, newCol});
        }
      }
    }
    return flipped;
  }

  /**
   * Helper method that abstracted out redundant code to check if a CELL is valid. A cell is
   * considered valid if it is within the grid boundaries and is not a hole.
   */
  protected boolean isValidCell(int row, int col) {
    return row >= 0 && row < grid.getRows() && col >= 0 && col < grid.getColumns();
  }

  /**
   * This method allows us to flip cards in the 4 directions and keep track of how many cards
   * are flipped from each turn.
   */
  protected int cardAttackDirections(Direction direction, int newRow, int newCol,
                                     IPlayer owner, Card card) {
    int cardsFlipped = 0;
    if (newRow >= 0 && newRow < grid.getRows() && newCol >= 0 && newCol < grid.getColumns()) {
      Cell adjacentCell = grid.getCell(newRow, newCol);

      if (!(adjacentCell.isHole())) {
        CardCell adjacentCardCell = (CardCell) adjacentCell;
        Card adjacentCard = adjacentCardCell.getCard();
        IPlayer adjacentOwner = adjacentCardCell.getOwner();

        if (adjacentCard != null && !owner.equals(adjacentOwner)) {
          int attackValue = parseAttackValue(card.getAttackValue(direction));
          int defenseValue = parseAttackValue(adjacentCard.getAttackValue(direction.getOpposite()));

          if (attackValue > defenseValue) {
            adjacentCardCell.setOwner(owner);
            cardsFlipped++;
          }
        }
      }
    }
    return cardsFlipped;
  }

  /**
   * Finds the attack value. Method helps us deal with the A representing the value 10 as an int.
   */
  protected int parseAttackValue(String value) {
    return "A".equals(value) ? 10 : Integer.parseInt(value);
  }

  @Override
  public boolean isGameOver() {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        Cell cell = grid.getCell(row, col);
        if (!cell.isHole() && cell.isEmpty()) {
          isGameOver = false;
          return false;
        }
      }
    }
    isGameOver = true;
    return true;
  }

  @Override
  public IPlayer getWinner() {
    int[] counts = countOccupiedCells();
    int redCardCount = counts[0] + getPlayerHand(pRed).size();
    int blueCardCount = counts[1] + getPlayerHand(pBlue).size();

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
}