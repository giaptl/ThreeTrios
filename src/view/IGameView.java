package view;

import model.Card;
import model.Player;

public interface IGameView {
//  void setSelectedCard(Card card, Player player);
//  Card getSelectedCard();
//  Player getSelectedPlayer();

  void highlightSelectedCard(int cardIndex, Player player);

  void updateGridCell(int row, int col, Card card);
  void removeCardFromHandPanel(Player player, Card card);
  void refreshView();
  void showError(String message);

  void updateCardSelection(Player player, Card card);
}