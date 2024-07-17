import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PatternLearningPlayer extends Player{

    private Map<String, Integer> moveCounts; 
    private StringBuilder history;
    private Random rand;
    private int historyLength = 3;
    private int movesInCurrentGame = 0;
    private int movesPerGame = 100;

    public PatternLearningPlayer(String name){
        super(name);
        this.moveCounts = new HashMap<>();
        this.history = new StringBuilder();
        this.rand = new Random();
    }
    @Override
    public boolean play(){
        //System.out.println(movesInCurrentGame);
        if (movesInCurrentGame == movesPerGame){
            this.movesInCurrentGame = 0;
            this.history.setLength(0);
            this.moveCounts.clear();
        }
        if (history.length() < historyLength){
            movesInCurrentGame++;
            return rand.nextBoolean();
        }

        //state is the current dealing which updates as the last historyLength characters
        String state = history.substring(history.length() - historyLength);
        //creates the predictive states - possible moves by the opponent
        String predictCState = state + "C";
        String predictDState = state + "D";

        //tracks the amount of times that the next move has been C or D
        int countC = moveCounts.getOrDefault(predictCState, 0);
        int countD = moveCounts.getOrDefault(predictDState, 0);

        //this incentive is to create the most amount of points, not to defect everytime
        if (countC >= countD){
            movesInCurrentGame++;
            return true;
        }
        else {
            movesInCurrentGame++;
            return false;
        }
    }
    public void updateHistoryAndCounts(String opponentMove){
        history.append(opponentMove);

        if (history.length() > historyLength){
            String state = history.substring(history.length() - historyLength - 1);
            moveCounts.put(state, moveCounts.getOrDefault(state, 0) + 1);
        }
    }
}