package strategy;

import java.util.List;

import model.Grid;
import model.ICard;
import player.IPlayer;
import finalProviderCode.model.ReadOnlyThreeTriosModel;

/**
 * Strategy that selects the move which minimizes the maximum gain the opponent can achieve in
 * their next move. This approach is known as the minimax strategy.
 * The minimax strategy calculates the "best"
 * move an opponent can make by simulating the opponent's moves
 * using various strategies and choosing the move that leaves the opponent with the least advantage.
 */
public class MinimaxStrategy implements Strategy {

  private static final int MAX_DEPTH = 3; // Adjust this value to control the depth of the search

  @Override
  public Move selectMove(IPlayer player, ReadOnlyThreeTriosModel model) {
    List<ICard> hand = model.getPlayerHand(player);
    Grid grid = model.getGrid();

    Move bestMove = null;
    int bestScore = Integer.MIN_VALUE;

    for (ICard card : hand) {
      for (int row = 0; row < grid.getRows(); row++) {
        for (int col = 0; col < grid.getColumns(); col++) {
          if (grid.getCell(row, col).isEmpty()) {
            int score = minimax(card, row, col, player, model, MAX_DEPTH, true);
            if (score > bestScore || (score == bestScore && isUpperLeft(row, col, bestMove))) {
              bestMove = new Move(card, row, col);
              bestScore = score;
            }
          }
        }
      }
    }

    return bestMove != null ? bestMove : Move.findFallbackMove(hand, grid, model, player);
  }

  private int minimax(ICard card, int row, int col, IPlayer player,
                      ReadOnlyThreeTriosModel model, int depth, boolean isMaximizingPlayer) {
    if (depth == 0 || model.isGameOver()) {
      return evaluateBoard(player, model);
    }

    IPlayer opponent = model.getOpponent(player);

    if (isMaximizingPlayer) {
      int maxEval = Integer.MIN_VALUE;
      for (ICard opponentCard : model.getPlayerHand(opponent)) {
        for (int r = 0; r < model.getGrid().getRows(); r++) {
          for (int c = 0; c < model.getGrid().getColumns(); c++) {
            if (model.getGrid().getCell(r, c).isEmpty()) {
              int flips = model.getNumCardsAbleToFlip(opponent, opponentCard, r, c);
              int eval = minimax(
                      opponentCard, r, c, opponent, model, depth - 1, false)
                      + flips;
              maxEval = Math.max(maxEval, eval);
            }
          }
        }
      }
      return maxEval;
    } else {
      int minEval = Integer.MAX_VALUE;
      for (ICard playerCard : model.getPlayerHand(player)) {
        for (int r = 0; r < model.getGrid().getRows(); r++) {
          for (int c = 0; c < model.getGrid().getColumns(); c++) {
            if (model.getGrid().getCell(r, c).isEmpty()) {
              int flips = model.getNumCardsAbleToFlip(player, playerCard, r, c);
              int eval = minimax(
                      playerCard, r, c, player, model, depth - 1, true) - flips;
              minEval = Math.min(minEval, eval);
            }
          }
        }
      }
      return minEval;
    }
  }

  private int evaluateBoard(IPlayer player, ReadOnlyThreeTriosModel model) {
    return model.getPlayerScore(player) - model.getPlayerScore(model.getOpponent(player));
  }

  private boolean isUpperLeft(int row, int col, Move currentBest) {
    if (currentBest == null) {
      return true;
    }
    return row < currentBest.getRow()
            || (row == currentBest.getRow() && col < currentBest.getCol());
  }

}
