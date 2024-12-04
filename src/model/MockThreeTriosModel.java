package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import player.HumanPlayer;
import player.IPlayer;
import player.MachinePlayer;
import strategy.Strategy;

import static model.CardValues.A;

/**
 * Represents the initial game model created for ThreeTrios.
 * This class manages the state and logic of the game, including the grid, players, and game rules.
 */
public class MockThreeTriosModel implements ThreeTriosModel {
  private Grid grid;
  private IPlayer currentPlayer;
  private IPlayer redPlayer;
  private IPlayer bluePlayer;
  private boolean isGameOver;
  private final List<ModelStatusListener> modelStatusListeners = new ArrayList<>();

  /**
   * Constructs a new MockGameModel with a 3x3 grid and two human players with cards.
   */
  public MockThreeTriosModel() {
    this.grid = new Grid(3, 3);

    this.redPlayer = new HumanPlayer("Red", new ArrayList<>());
    redPlayer.addCard(new Card("RedCard1", 1, 1, 1, 1));
    redPlayer.addCard(new Card("RedCard2", 3, 3, 3, 3));
    redPlayer.addCard(new Card("RedCard3", 4, 4, 4, 4));
    redPlayer.addCard(new Card("RedCard4", 6, 6, 6, 6));
    redPlayer.addCard(new Card("RedCard5", A.getValue(), A.getValue(), 1, 1));

    this.bluePlayer = new HumanPlayer("Blue", new ArrayList<>());
    bluePlayer.addCard(new Card("BlueCard1", 2, 2, 2, 2));
    bluePlayer.addCard(new Card("BlueCard2", 5, 5, 5, 5));
    bluePlayer.addCard(new Card("BlueCard3", 7, 7, 7, 7));
    bluePlayer.addCard(new Card("BlueCard4", 9, 9, 9, 9));

    this.currentPlayer = redPlayer;
    this.isGameOver = false;

  }

  /**
   * Constructs a new MockGameModel with a 3x3 grid, 1 human player, and 1 machine player with
   * cards.
   * @param isComputerGame whether the game has only computer players or not
   * @param strategy1 the strategy for the first player
   * @param strategy2 the strategy for the second player
   */
  public MockThreeTriosModel(boolean isComputerGame, Strategy strategy1, Strategy strategy2) {
    if (isComputerGame) {
      this.grid = new Grid(3, 3);

      this.redPlayer = new MachinePlayer("Red", new ArrayList<>(), strategy1);
      redPlayer.addCard(new Card("RedCard1", 1, 1, 1, 1));
      redPlayer.addCard(new Card("RedCard2", 3, 3, 3, 3));
      redPlayer.addCard(new Card("RedCard3", 4, 4, 4, 4));
      redPlayer.addCard(new Card("RedCard4", 6, 6, 6, 6));
      redPlayer.addCard(new Card("RedCard5", A.getValue(), A.getValue(), 1, 1));

      this.bluePlayer = new MachinePlayer("Blue",
              new ArrayList<>(), strategy2);
      bluePlayer.addCard(new Card("BlueCard1", 2, 2, 2, 2));
      bluePlayer.addCard(new Card("BlueCard2", 5, 5, 5, 5));
      bluePlayer.addCard(new Card("BlueCard3", 7, 7, 7, 7));
      bluePlayer.addCard(new Card("BlueCard4", 9, 9, 9, 9));

      this.currentPlayer = redPlayer;
      this.isGameOver = false;
    } else {
      System.out.println("This is a computer only game."
              + " Pass in nothing if u want a human vs human.");
    }
  }

  /**
   * Constructs a new MockGameModel with a 3x3 grid, 1 human player, and 1 machine player with
   * cards.
   * @param isComputerGame whether the game contains any computer players
   * @param strategy1 the strategy for the computer player
   */
  public MockThreeTriosModel(boolean isComputerGame, Strategy strategy1) {
    if (isComputerGame) {
      this.grid = new Grid(3, 3);

      Strategy strat1 = strategy1;

      this.redPlayer = new HumanPlayer("Red", new ArrayList<>());
      redPlayer.addCard(new Card("RedCard1", 1, 1, 1, 1));
      redPlayer.addCard(new Card("RedCard2", 3, 3, 3, 3));
      redPlayer.addCard(new Card("RedCard3", 4, 4, 4, 4));
      redPlayer.addCard(new Card("RedCard4", 6, 6, 6, 6));
      redPlayer.addCard(new Card("RedCard5", A.getValue(), A.getValue(), 1, 1));

      this.bluePlayer = new MachinePlayer("Blue",
              new ArrayList<>(), strat1);
      bluePlayer.addCard(new Card("BlueCard1", 2, 2, 2, 2));
      bluePlayer.addCard(new Card("BlueCard2", 5, 5, 5, 5));
      bluePlayer.addCard(new Card("BlueCard3", 7, 7, 7, 7));
      bluePlayer.addCard(new Card("BlueCard4", 9, 9, 9, 9));

      this.currentPlayer = redPlayer;
      this.isGameOver = false;
    } else {
      System.out.println("This is a computer only game."
              + " Pass in nothing if u want a human vs human.");
    }
  }


  /**
   * Sets the cell at the specified row and column as a hole.
   * @param row the row of the cell
   * @param col the column of the cell
   */
  public void setCellAsHole(int row, int col) {
    grid.setCell(row, col, new Hole());
  }

  /**
   * Not used really but needed for implementation in testing.
   */
  public void startGameWithConfig(Grid grid) {
    this.grid = grid;
    isGameOver = false;
  }

  @Override
  public void startGameWithConfig(Grid grid, List<ICard> cards, boolean shuffle,
                                  IPlayer player1, IPlayer player2) {
    // will not be used since we already assigned players cards in constructor
    isGameOver = false;
  }

  @Override
  public void playCard(IPlayer player, ICard card, int row, int col) {
    if (!player.equals(currentPlayer)) {
      throw new IllegalArgumentException("It is not " + player.getName() + "'s turn.");
    }
    if (!player.getHand().contains(card)) {
      throw new IllegalArgumentException("Card is not in player's hand.");
    }
    if (grid.getCell(row, col).isHole()) {
      throw new IllegalArgumentException("Card cannot be placed on a hole.");
    }
    if (!grid.getCell(row, col).isEmpty()) {
      throw new IllegalArgumentException("Cell is not empty.");
    }
    grid.setCell(row, col, new CardCell(card, player));
    player.removeCard(card);
    startBattlePhase(row, col);
    currentPlayer = currentPlayer.equals(redPlayer) ? bluePlayer : redPlayer;
    isGameOver = isGameOver();
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
  public Grid getGrid() {
    return this.grid;
  }

  @Override
  public List<ICard> getPlayerHand(IPlayer player) {
    return player.getHand();
  }

  @Override
  public IPlayer getCurrentPlayer() {
    return this.currentPlayer;
  }

  @Override
  public IPlayer getOpponent(IPlayer player) {
    return player.equals(redPlayer) ? bluePlayer : redPlayer;
  }

  @Override
  public int getNumCardsAbleToFlip(IPlayer player, ICard card, int row, int col) {
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
  public boolean isGameOver() {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        Cell cell =  grid.getCell(row, col);
        if (!cell.isHole() && cell.isEmpty()) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public IPlayer getWinner() {

    int redCardCount = redPlayer.getHand().size();
    int blueCardCount = bluePlayer.getHand().size();

    int[] counts = countOccupiedCells(redCardCount, blueCardCount);
    redCardCount = counts[0];
    blueCardCount = counts[1];

    if (redCardCount > blueCardCount) {
      // Red Player wins
      return redPlayer;
    } else if (blueCardCount > redCardCount) {
      // Blue player wins
      return bluePlayer;
    } else {
      // Tie
      return null;
    }
  }

  /**
   * Helper method that counts the number of occupied cells for each player.
   */
  private int[] countOccupiedCells(int redCardCount, int blueCardCount) {
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        CardCell cell = (CardCell) grid.getCell(row, col);
        if (cell.isOccupied()) {
          IPlayer owner = cell.getOwner();
          if (owner.equals(redPlayer)) {
            redCardCount++;
          } else if (owner.equals(bluePlayer)) {
            blueCardCount++;
          }
        }
      }
    }
    return new int[]{redCardCount, blueCardCount};
  }

  private int countOccupiedCells(IPlayer player) {
    int count = 0;
    for (int row = 0; row < grid.getRows(); row++) {
      for (int col = 0; col < grid.getColumns(); col++) {
        if (!grid.getCell(row, col).isHole()) {
          CardCell cell = (CardCell) grid.getCell(row, col);
          if (cell.isOccupied()) {
            IPlayer owner = cell.getOwner();
            if (owner.equals(player)) {
              count++;
            }
          }
        }
      }
    }
    return count;
  }

  @Override
  public IPlayer getRedPlayer() {
    return this.redPlayer;
  }

  @Override
  public IPlayer getBluePlayer() {
    return this.bluePlayer;
  }

  @Override
  public int getPlayerScore(IPlayer player) {
    return getPlayerHand(player).size() + countOccupiedCells(player);
  }

  /**
   * Additional methods for testing. Used to setGameOver.
   */
  public void setGameOver(boolean isOver) {
    this.isGameOver = isOver;
  }

  /**
   * Used to set the current player to whoever is passed in.
   */
  public void setCurrentPlayer(IPlayer player) {
    this.currentPlayer = player;
  }

  /**
   * Helper method which abstracts out much of the battle phase
   * code from both the simulation and the actual battle phase.
   */
  protected int processBattlePhase(IPlayer player, int startRow, int startCol) {
    int cardsFlipped = 0;
    Queue<int[]> toProcess = new LinkedList<>();
    Set<String> visited = new HashSet<>();
    toProcess.offer(new int[]{startRow, startCol});

    while (!toProcess.isEmpty()) {
      int[] current = toProcess.poll();
      int row = current[0];
      int col = current[1];
      String cellKey = row + "," + col;

      if (visited.contains(cellKey)) {
        continue;
      }
      visited.add(cellKey);

      CardCell currentCell = (CardCell) grid.getCell(row, col);
      if (currentCell == null || currentCell.getCard() == null) {
        continue;
      }

      cardsFlipped += processAdjacentCells(
              player, currentCell.getCard(), row, col, visited, toProcess);
    }
    return cardsFlipped;
  }

  /**
   * Checks to see which cells are being flipped and also flipping cards
   * that have already been flipped.
   */
  protected int processAdjacentCells(IPlayer player, ICard currentCard, int row, int col,
                                     Set<String> visited, Queue<int[]> toProcess) {
    int flipped = 0;
    for (Direction direction : Direction.values()) {
      int newRow = row + direction.getRowOffset();
      int newCol = col + direction.getColOffset();
      String cellKey = newRow + "," + newCol;

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
   * Helper method that abstracted out redundant code to check if a CELL is valid.
   */
  protected boolean isValidCell(int row, int col) {
    return row >= 0 && row < grid.getRows() && col >= 0 && col < grid.getColumns();
  }

  /**
   * This method allows us to flip cards in the 4 directions and keep track of how many cards
   * are flipped from each turn.
   */
  protected int cardAttackDirections(Direction direction, int newRow, int newCol,
                                     IPlayer owner, ICard card) {
    int cardsFlipped = 0;
    if (newRow >= 0 && newRow < grid.getRows() && newCol >= 0 && newCol < grid.getColumns()) {
      Cell adjacentCell = grid.getCell(newRow, newCol);

      if (!(adjacentCell.isHole())) {
        CardCell adjacentCardCell = (CardCell) adjacentCell;
        ICard adjacentCard = adjacentCardCell.getCard();
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

  /**
   * Checks if a card is played at a specific row and column.
   * @param card the card to check
   * @param row the row
   * @param col the column
   * @return true if the card given is played at the specified row and column, false otherwise
   */
  public boolean isCardPlayed(ICard card, int row, int col) {
    Cell cell = grid.getCell(row, col);
    if (cell instanceof CardCell) {
      CardCell cardCell = (CardCell) cell;
      return card.equals(cardCell.getCard());
    }
    return false;
  }
}