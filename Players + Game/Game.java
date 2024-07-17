import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {

    //if both collaborate each get +3
    //if one collaborates and one defects, the defector gets +5 and the collaborator gets +0
    //if both defects, both get +1

    //this tracks the match result to be later collected into a csv file
    public static class MatchResult {
        private int playerAScore;
        private int playerBScore;
        private int cumulativeScore;
        private int CCs;
        private int DDs;

        public MatchResult(int playerAScore, int playerBScore, int cumulativeScore, int CCs, int DDs){
            this.playerAScore = playerAScore;
            this.playerBScore = playerBScore;
            this.cumulativeScore = cumulativeScore;
            this.CCs = CCs;
            this.DDs = DDs;
        }

        public int getPlayerAScore(){ return playerAScore; }
        public int getPlayerBScore(){ return playerBScore; }
        public int getcumulativeScore(){ return cumulativeScore; }
        public int getCCs(){ return CCs; }
        public int getDDs(){ return DDs; }

        public MatchResult createMatchResult(int playerAScore, int playerBScore, int cumulativeScore, int CCs, int DDs){
            return new MatchResult(playerAScore, playerBScore, cumulativeScore, CCs, DDs);
        }
        @Override
        public String toString(){
            return "" + playerAScore + ", " + playerBScore + ", " + cumulativeScore + ", " + CCs + ", " + DDs;
        }
    }
    public static MatchResult prisonersDilemma(Player A, Player B, int rounds){
        //System.out.println("Game Started");
        //System.out.println("----------------------------------------------------------------------------------");

        String prevAState = "";
        String prevBState = "";
        int CCs = 0;
        int DDs = 0;
        int cumulative = 0;
        int aPoints = 0;
        int bPoints = 0;

        for (int round=0; round < rounds; round++){

            A.setTurn(true);
            boolean moveA = A.play();
            String Amove = moveA ? "C" : "D";
            A.setTurn(false);

            B.setTurn(true);
            boolean moveB = B.play();
            String Bmove = moveB ? "C" : "D";
            B.setTurn(false);

            if (moveA && moveB){
                CCs++;
            }
            if (moveA == false && moveB == false){
                DDs++;
            }
            int rewardA, rewardB;

            if (moveA && moveB){
                rewardA = 3;
                rewardB = 3;
            } 
            else if (moveA){
                rewardA = 0;
                rewardB = 5;
            } 
            else if (moveB){
                rewardA = 5;
                rewardB = 0;
            }
            else {
                rewardA = 1;
                rewardB = 1;
            }
            
            A.setPoints(A.getPoints() + rewardA);
            B.setPoints(B.getPoints() + rewardB);

            if (A instanceof ReinforcementLearningPlayer){
                ((ReinforcementLearningPlayer) A).updateQValues(prevAState, moveA, Bmove, rewardA);
                prevAState = Bmove;
            }
            if (B instanceof ReinforcementLearningPlayer){
                ((ReinforcementLearningPlayer) B).updateQValues(prevBState, moveB, Amove, rewardB);
                prevBState = Amove;
            }
            if (A instanceof PatternLearningPlayer){
                ((PatternLearningPlayer) A).updateHistoryAndCounts(Bmove);
            }
            if (B instanceof PatternLearningPlayer){
                ((PatternLearningPlayer) B).updateHistoryAndCounts(Amove);
            }
            if (A instanceof BayesianInferencePlayer){
                ((BayesianInferencePlayer) A).updateBeliefs(moveB);
            }
            if (B instanceof BayesianInferencePlayer){
                ((BayesianInferencePlayer) B).updateBeliefs(moveA);
            }
            if (A instanceof UCBPlayer){
                ((UCBPlayer) A).updateVals(moveB, moveA);
            }
            if (B instanceof UCBPlayer){
                ((UCBPlayer) B).updateVals(moveA, moveB);
            }
            if (A instanceof JointPQLearningPlayer){
                ((JointPQLearningPlayer) A).updateJQValues(moveA, moveB, rewardA, rewardB);
            }
            if (B instanceof JointPQLearningPlayer){
                ((JointPQLearningPlayer) B).updateJQValues(moveB, moveA, rewardB, rewardA);
            }
            if (A instanceof titForTat){
                ((titForTat) A).updateMoves(moveB);
            }
            if (B instanceof titForTat){
                ((titForTat) B).updateMoves(moveA);
            }
            if (A instanceof grudgePlayer){
                ((grudgePlayer) A).detectDefection(moveB);
            }
            if (B instanceof grudgePlayer){
                ((grudgePlayer) B).detectDefection(moveA);
            }
            if (A instanceof revtitForTat){
                ((revtitForTat) A).updateMoves(moveB);
            }
            if (B instanceof revtitForTat){
                ((revtitForTat) B).updateMoves(moveA);
            }
            cumulative = A.getPoints() + B.getPoints();
            System.out.println("Round " + (round+1) + " | " + A.name + ": " + Amove + " | " + B.name + ": " + Bmove + " | " + A.name + "'s points: " + A.getPoints() + " | " + B.name + "'s points: " + B.getPoints() + " | Cumulative: " + cumulative + " | CCs: " + CCs + " | DDs: " + DDs);
        }
        //System.out.println("----------------------------------------------------------------------------------");

        //System.out.println("ReinforcementLearnerPoints,AlwaysCollaboratePoints,TotalPoints,CCs, DDs");
        aPoints = A.getPoints();
        bPoints = B.getPoints();

        //reset points
        A.setPoints(0);
        B.setPoints(0);

        return new MatchResult(aPoints, bPoints, cumulative, CCs, DDs);
    }
    //generate CSV
    public static void generateResultsCSV(Player playerA, List<Player> allPlayers, int rounds, String fileName) {
        System.out.println("Playing: " + playerA.name);
        try (FileWriter csvWriter = new FileWriter(fileName)) {
            csvWriter.append("Game,Opponent,PlayerA_Score,PlayerB_Score,Cumulative_Score,CCs,DDs,Winner\n");
    
            for (Player opponent : allPlayers) {
                int round = 0;
                int pilot = 10;
                int maxGames = 11;
                List<Integer> cumulativeScores = new ArrayList<>();
                
                System.out.println("Playing against " + opponent.name);
                

                while (round < maxGames) {
                    MatchResult result = prisonersDilemma(playerA, opponent, rounds);
                    String winner = "";
                    if (result.getPlayerAScore() > result.getPlayerBScore()){
                        winner = "Player A";
                    }
                    else if (result.getPlayerAScore() < result.getPlayerBScore()){
                        winner = opponent.name;
                    }
                    else {
                        winner = "Tie";
                    }
                    
                    csvWriter.append(String.format("%d,%s,%d,%d,%d,%d,%d,%s\n", 
                        round, opponent.name, result.getPlayerAScore(), result.getPlayerBScore(),
                        result.getcumulativeScore(), result.getCCs(), result.getDDs(), winner));
                    
                    //data collection for pilot study
                    if (round < pilot) {
                        cumulativeScores.add(result.getcumulativeScore());

                    //calculate and resize 
                    } else if (round == pilot) {
                        int requiredN = calcN(cumulativeScores, 1.96, 1);
                        //System.out.println("Required N for " + opponent.name + ": " + requiredN);
                        if (requiredN <= pilot) {
                            break;
                        }
                        //maxGames = Math.min(requiredN, maxGames);
                        maxGames = requiredN;
                        System.out.println("Playing " + maxGames + " games against " + opponent.name);
                    }
                    round++;
                }
                System.out.println("Finished playing against " + opponent.name + ". Games played: " + round);
            }
            System.out.println("File generated");
        } catch (IOException e) {
            System.out.println("Error occured: " + e.getMessage());
        }
    }
 
    //calculate stdev to automate data collection sample size process
    public static int calcN(List<Integer> cumuScores, double z, double E){
        double sum = 0.0;
        for (int num : cumuScores){
            sum += num;
        }
        double mean = sum/cumuScores.size();

        //calc stdev
        double squaredSum = 0.0;
        for (int num : cumuScores){
            squaredSum += Math.pow(num - mean, 2);
        }

        double stDev = Math.sqrt(squaredSum / (cumuScores.size()-1));

        //confidence of 95% and error of 1
        int n = (int) Math.ceil(Math.pow(z*stDev/E, 2));

        return n;
    }
    public static void main(String[] args){

        List<Player> allPlayers = new ArrayList<>();
        
        allPlayers.add(new ReinforcementLearningPlayer("QLearningPlayer"));
        allPlayers.add(new PatternLearningPlayer("PatternLearningPlayer"));
        allPlayers.add(new titForTat("TitForTat"));
        allPlayers.add(new BayesianInferencePlayer("Bayesian"));
        allPlayers.add(new UCBPlayer("UCBPlayer"));
        allPlayers.add(new JointPQLearningPlayer("JointPQPlayer"));
        allPlayers.add(new revtitForTat("revTitforTat"));
        allPlayers.add(new patternPlayer("PatternPlayer"));
        allPlayers.add(new grudgePlayer("GrudgePlayer"));
        allPlayers.add(new alwaysCooperate("Gullible"));
        allPlayers.add(new alwaysDefect("Sly Fox"));
        allPlayers.add(new alternateEven("Evan"));
        allPlayers.add(new random("Randy"));
        
        // Testing player
        Player playerA = new UCBPlayer("UCBPlayer");
        Player playerB = new grudgePlayer("grudgePlayer");

        prisonersDilemma(playerA, playerB, 100);

        // Generate the CSV and run simulation
        //generateResultsCSV(playerA, allPlayers, 100, "randomVsWorld.csv");
    }
}