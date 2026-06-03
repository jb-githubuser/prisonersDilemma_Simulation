# Prisoner's Dilemma Simulation

A game-theory simulation written in Java that pits various classical and learning-based strategies against each other in iterated Prisoner's Dilemma matches. Results are exported as CSV files for analysis and visualization in Python.

## Strategies

| Player | Description |
|---|---|
| `ReinforcementLearningPlayer` | Q-learning agent that adapts based on opponent moves |
| `PatternLearningPlayer` | Tracks opponent move history to predict next action |
| `BayesianInferencePlayer` | Updates beliefs about opponent cooperativeness each round |
| `UCBPlayer` | Upper Confidence Bound bandit; balances exploration and exploitation |
| `JointPQLearningPlayer` | Joint-action Q-learning that accounts for both players' rewards |
| `titForTat` | Cooperates first, then mirrors the opponent's last move |
| `revtitForTat` | Defects first, then mirrors the opposite of the opponent's last move |
| `grudgePlayer` | Cooperates until betrayed, then defects forever |
| `patternPlayer` | Alternates cooperation/defection on a fixed pattern |
| `alwaysCooperate` | Cooperates every round |
| `alwaysDefect` | Defects every round |
| `alternateEven` | Alternates moves starting with cooperate |
| `random` | Randomly cooperates or defects each round |

## Project Structure

```
prisonersDilemma_Simulation/
├── Game.java          # Main simulation runner and CSV exporter
└── players/           # All player strategy classes (players package)
    ├── Player.java    # Abstract base class
    └── *.java         # Individual strategy implementations
```

## Building & Running

From the project root:

```bash
javac Game.java players/*.java
java Game
```

To export results to a CSV, uncomment the `generateResultsCSV` call in `main` and specify an output file name.

## Payoff Matrix

|  | Cooperate | Defect |
|---|---|---|
| **Cooperate** | +3 / +3 | +0 / +5 |
| **Defect** | +5 / +0 | +1 / +1 |

## Contact

Jayden Bai — undergraduate at Emory University (Statistics + CS, Neuroethics minor)  
Email: jayden.bai@emory.edu  
LinkedIn: [jayden-bai](https://www.linkedin.com/in/jayden-bai/)
