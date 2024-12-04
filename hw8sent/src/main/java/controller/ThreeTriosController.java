package controller;

public interface ThreeTriosController {

  void onPlayerTurn(String playerName);

  void onGameOver(String winnerName, int winningScore);

  void onInvalidMove(String errorMessage);

  void onModelUpdated();
}