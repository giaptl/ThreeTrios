# ThreeTrios

ThreeTrios is a strategic card game designed for players who enjoy decision-making and tactical gameplay. The goal of the game is to form trios of matching cards and strategically outsmart your opponents.

## Game Overview

- **Game Type**: Card Game
- **Players**: 2
- **Objective**: Own the most card by the end of the game
- **Game Features**:  
  - Multiple rounds of gameplay  
  - Various card types and abilities  
  - Strategic decision-making for selecting and discarding cards

## Installation

To run the game locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/ThreeTrios.git

2. Navigate to the project directory:
   ```bash
   cd ThreeTrios

3. Install dependencies (if applicable):
   ```bash
   npm install  # Or another command depending on your environment

4. Run the game:
   ```bash
   npm start  # Or your equivalent command

## How to Play

The Three Trios game is a strategic card-based board game played on a 3x3 grid. Players take turns placing cards from their 
hand onto empty grid cells. Each card has four values (North, South, East, West) that determine its strength in battles with 
adjacent cards. When a card is placed, it can flip opponent's cards if the attacking value is higher than the defending value 
of adjacent cards. The game supports various battle rules: Normal (higher value wins), Reverse (lower value wins), FallenAce 
(Ace loses to 1), Same (flips cards with matching values in opposing directions), and Plus (flips cards when sums match in 
opposing directions). Players can choose between human and AI opponents with different strategies (Corner, FlipMaximizer, 
LeastLikelyFlipped). The game ends when all cells are filled, and the player with the most cards on the board wins.

## Features

### Game Modes
- Human vs Human
- Human vs AI
- AI vs AI

### AI Strategy Options
- **Corner Strategy**: AI prioritizes corner positions
- **FlipMaximizer Strategy**: AI maximizes card flips
- **LeastLikelyFlipped Strategy**: AI minimizes chance of cards being flipped

### Battle Rule Variants
- **Normal**: Higher value wins battles
- **Reverse**: Lower value wins battles
- **FallenAce**: Ace (value 10) loses to value 1
- **Same**: Flips cards with matching values in opposing directions
- **Plus**: Flips cards when sums match in opposing directions

### View Options
- Classic View: Our original implementation
- Provider View: Alternative view for Player 2 (--provider-view)

## Contribution

If you’d like to contribute to the project:

1. Fork the repository.
2. Create a new branch for your feature.
3. Commit your changes.
4. Push to the branch and create a pull request.

## Collaborators

Abhishiek Karthik

Gia Patel
