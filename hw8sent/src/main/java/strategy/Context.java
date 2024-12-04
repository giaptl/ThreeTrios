package strategy;

/**
 * The Context class allows for the use of a specific strategy at runtime.
 * It can dynamically switch between strategies as needed.
 */
public class Context {
  private Strategy strategy;

  /**
   * Sets the current strategy to be used.
   * @param strategy the strategy to set
   */
  public void setStrategy(Strategy strategy) {
    this.strategy = strategy;
  }

  /**
   * Executes the current strategy.
   */
  public void executeStrategy() {
    if (strategy != null) {
      strategy.executeStrategy();
    } else {
      System.out.println("No strategy set!");
    }
  }
}
