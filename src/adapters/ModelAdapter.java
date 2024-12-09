package adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import finalProviderCode.model.CardProvider;
import finalProviderCode.model.CellInterfaceProvider;
import finalProviderCode.model.PlayerProvider;
import finalProviderCode.model.ThreeTriosModelProvider;
import model.Grid;
import model.ICard;
import model.ModelStatusListener;
import model.ThreeTriosModel;
import player.IPlayer;

public class ModelAdapter implements ThreeTriosModelProvider {
  private final ThreeTriosModel adaptee;
  private final Map<PlayerProvider, IPlayer> playerMap;
  private final List<ModelStatusListener> listeners = new ArrayList<>();


  public ModelAdapter(ThreeTriosModel adaptee, IPlayer redPlayer, IPlayer bluePlayer) {
    this.adaptee = adaptee;
    this.playerMap = new HashMap<>();
    PlayerProvider redPlayerProvider = new PlayerAdapter(redPlayer);
    PlayerProvider bluePlayerProvider = new PlayerAdapter(bluePlayer);
    this.playerMap.put(redPlayerProvider, redPlayer);
    this.playerMap.put(bluePlayerProvider, bluePlayer);
  }

  @Override
  public int getGridRows() {
    return adaptee.getGrid().getRows();
  }

  public void addModelStatusListener(ModelStatusListener listener) {
    listeners.add(listener);
  }

  private void notifyModelUpdated() {
    for (ModelStatusListener listener : listeners) {
      listener.onPlayerTurn(adaptee.getCurrentPlayer());
    }
  }

  @Override
  public List<CardProvider> getPlayerHand(PlayerProvider player) {
    IPlayer mappedPlayer = playerMap.get(player);
    return mappedPlayer.getHand().stream()
            .map(card -> (CardProvider) card) // Replace with actual conversion logic
            .collect(Collectors.toList());
  }

  @Override
  public PlayerProvider getRedPlayer() {
    return new PlayerAdapter(adaptee.getRedPlayer());
  }

  @Override
  public PlayerProvider getBluePlayer() {
    return new PlayerAdapter(adaptee.getBluePlayer());
  }

  @Override
  public int getPotentialFlips(PlayerProvider player, CardProvider card, int row, int col) {
    IPlayer mappedPlayer = playerMap.get(player);
    return adaptee.getNumCardsAbleToFlip(mappedPlayer, (ICard) card, row, col);
  }

  @Override
  public int getPotentialOpponentFlips(PlayerProvider player, CardProvider card, int row, int col) {
    IPlayer mappedPlayer = playerMap.get(player);
    IPlayer opponent = mappedPlayer.equals(adaptee.getRedPlayer()) ? adaptee.getBluePlayer() : adaptee.getRedPlayer();
    return adaptee.getNumCardsAbleToFlip(opponent, (ICard) card, row, col);
  }

  @Override
  public int getGridCols() {
    return adaptee.getGrid().getColumns();
  }

  @Override
  public boolean isCellOccupied(int row, int col) {
    return !adaptee.getGrid().getCell(row, col).isEmpty();
  }

  @Override
  public String getCellOwner(int row, int col) {
    if (adaptee.getGrid().getCell(row, col) == null ||
            adaptee.getGrid().getCell(row, col).getOwner() == null) {
      return "";
    }
    return adaptee.getGrid().getCell(row, col).getOwner().getName();
  }

  @Override
  public String getCardValueAt(int row, int col) {
    ICard card = adaptee.getGrid().getCell(row, col).getCard();
    return card != null ? card.toString() : "";
  }

  @Override
  public PlayerProvider getCurrentPlayer() {
    return new PlayerAdapter(adaptee.getCurrentPlayer());
  }

  @Override
  public boolean isFull() {
    for (int row = 0; row < adaptee.getGrid().getRows(); row++) {
      for (int col = 0; col < adaptee.getGrid().getColumns(); col++) {
        if (adaptee.getGrid().getCell(row, col).isEmpty()) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public int countOwnedCards(PlayerProvider player) {
    IPlayer mappedPlayer = playerMap.get(player);
    return mappedPlayer.getHand().size();
  }

  @Override
  public void render() {
    getGridView();
  }

  @Override
  public boolean isGameOver() {
    return adaptee.isGameOver();
  }

  @Override
  public PlayerProvider getWinner() {
    return new PlayerAdapter(adaptee.getWinner());
  }

  @Override
  public String getGridView() {
    return adaptee.getGrid().toString();
  }

  @Override
  public void startGame(List<CellInterfaceProvider> deck, CellInterfaceProvider[][] board, boolean shuffle) {
    Grid grid = convertToGrid(board);
    List<ICard> cards = convertToCards(deck);
    adaptee.startGameWithConfig(grid, cards, shuffle, adaptee.getRedPlayer(), adaptee.getBluePlayer());
  }

  private Grid convertToGrid(CellInterfaceProvider[][] board) {
    // Implement the conversion logic from CellInterfaceProvider[][] to Grid
    return new Grid(board.length, board[0].length);
  }

  private List<ICard> convertToCards(List<CellInterfaceProvider> deck) {
    return deck.stream()
            .map(cell -> (ICard) cell) // Replace with actual conversion logic
            .collect(Collectors.toList());
  }

  @Override
  public boolean placeCard(PlayerProvider player, CardProvider card, int row, int col) {
    try {
      adaptee.playCard(playerMap.get(player), ((CardAdapter) card).getAdaptee(), row, col);
      notifyModelUpdated(); // Notify listeners after placing the card
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  @Override
  public boolean isLegalMove(PlayerProvider player, CardProvider card, int row, int col) {
    try {
      adaptee.playCardConditions(playerMap.get(player), row, col, (ICard) card);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  @Override
  public CardProvider getNextCardForPlayer(PlayerProvider currentPlayer) {
    IPlayer mappedPlayer = playerMap.get(currentPlayer);
    if (mappedPlayer == null) {
      throw new IllegalArgumentException("Player not found in playerMap: " + currentPlayer);
    }
    return mappedPlayer.getHand().isEmpty() ? null : new CardAdapter(mappedPlayer.getHand().get(0));
  }
}