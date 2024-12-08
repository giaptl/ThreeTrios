
Three Trios Game Project

Overview

The Three Trios Game Project is a Java-based application that simulates a strategic card game with a graphical user interface (GUI). The project encompasses the full model, view, and controller implementation to ensure a comprehensive and engaging game experience. It includes functionality for card placement, game state management, and player interactions.
Features

Model Implementation: Core game logic for card placement, game rules, and card-flipping mechanics.
GUI: Interactive game interface for users, displaying the game grid, player hands, and status updates.
Player Interaction: Ensures turn-based gameplay with validation for moves and updates for game states.
Testing: Extensive unit and integration tests to verify the accuracy and functionality of the game logic and GUI.
Project Structure

src/
└── main/
├── java/
│   ├── controller/
│   │   ├── ModelStatusListener.java
│   │   ├── PlayerAction.java
│   │   ├── PlayerActionImpl.java
│   │   ├── StartGameController.java
│   │   ├── ThreeTriosController.java
│   │   └── ThreeTriosControllerImpl.java
│   ├── model/
│   │   ├── cards/
│   │   │   ├── Card.java
│   │   │   ├── CardLoader.java
│   │   │   └── TripleTriadCard.java
│   │   ├── grid/
│   │   │   ├── Cell.java
│   │   │   ├── EmptyCell.java
│   │   │   ├── FilledCell.java
│   │   │   ├── Grid.java
│   │   │   └── GridLoader.java
│   │   ├── managers/
│   │   │   ├── BattleManager.java
│   │   │   ├── GameManager.java
│   │   │   └── TurnManager.java
│   │   └── utils/
│   │       ├── AttackResult.java
│   │       ├── Color.java
│   │       ├── Direction.java
│   │       ├── Hand.java
│   │       ├── ReadOnlyThreeTriosModel.java
│   │       ├── ThreeTriosModel.java
│   │       └── ThreeTriosModelImpl.java
│   ├── org.example/
│   │   └── Main.java
│   ├── players/
│   │   ├── Player.java
│   │   └── PlayerImpl.java
│   └── strategy/
│       ├── BalancedStrategy.java
│       ├── Context.java
│       ├── DefensiveStrategy.java
│       ├── Move.java
│       ├── OffensiveStrategy.java
│       └── Strategy.java
└── view/
├── CardRenderer.java
├── GameGUI.java
├── SidePanel.java
└── TextView.java

resources/
├── cards/
│   ├── cards_anyboard.txt
│   ├── cards_example.txt
│   └── cards_small.txt
└── grids/
├── grid_example.txt
└── grid_no_holes.txt

test/
├── AdditionalTests.java
├── AllTests.java
├── CardLoaderTest.java
├── CardTest.java
├── ComprehensiveTest.java
├── GameGUITest.java
├── GameManagerTest.java
├── GridTest.java
├── HandTest.java
├── PlayerTest.java
├── RuleValidationTests.java
├── ThreeTriosModelExtendedTest.java
├── ThreeTriosModelTest.java
└── TurnManagerTest.java

Assignment 7 updates
Controller Implementation
* The ThreeTriosControllerImpl class has been updated to:
    * Implement ModelStatusListener: The controller now listens to the model for events like turn changes, invalid moves, and game state updates.
    * Enforce Turn-Based Actions: Players can only interact with their own cards during their turn.
    * Integrate with Player Actions: The controller communicates with the view to handle player interactions, such as selecting a card or placing it on the grid.
Model-Status Listeners
* The ModelStatusListener interface is introduced to notify controllers about changes in the model's state. It includes methods for:
    * onPlayerTurn(String playerName): Notifies the controller when it is a player's turn.
    * onGameOver(String winnerName, int winningScore): Signals the end of the game with the winner's name and score.
    * onInvalidMove(String errorMessage): Notifies the controller of invalid move attempts.
    * onModelUpdated(): Notifies the controller of updates to the model, such as grid changes or score updates.
* The model (ThreeTriosModelImpl) has been updated to:
    * Maintain a list of ModelStatusListener instances.
    * Notify listeners about relevant events (e.g., turn changes, state updates).
Player Action Listener
* The controller now enforces player actions through dedicated methods:
    * Players can select cards and place them only when it's their turn.
    * Controllers validate moves before communicating them to the model.
    * Invalid interactions are handled gracefully, with error messages displayed in the GUI.
Main Method
* The main method now:
    * Instantiates and registers ThreeTriosControllerImpl instances for both players.
    * Connects each controller to the model and its respective view (GameGUI).
    * Ensures that the model notifies all registered controllers of state changes.
    * Launches the GUI for both players, with independent views for each.
How to Run
Launch our attached .jar file to run the game.



