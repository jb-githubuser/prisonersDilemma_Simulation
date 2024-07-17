import java.util.HashMap;
import java.util.Random;

public class JointPQLearningPlayer extends Player {
    private double epsilon = 0.6;
    private Random rand;
    private HashMap<String, Double[]> qTable;
    private double alpha;
    private double gamma;
    private String prevState;
    private int iteration = 0;
    private String strOppMoveCurr;
    private String strOppMovePrev;
    private int movesPerGame = 100;

    public JointPQLearningPlayer(String name) {
        super(name);
        this.qTable = new HashMap<>();
        this.alpha = 0.1;
        this.gamma = 0.9;
        this.rand = new Random();
        this.prevState = "";
        this.iteration = 0;
        this.strOppMoveCurr = "";
        this.strOppMovePrev = strOppMoveCurr;
    }
    public void resetQValues(){
        this.qTable.clear();
    }
    @Override
    public boolean play() {
        //reset vals
        if (iteration == movesPerGame) {
            epsilon = 0.6;
            iteration = 0;
            this.qTable.clear();
        }
        //System.out.println(qTable);
        //add states
        String state = prevState;
        if (!qTable.containsKey(state)){
            qTable.put(state, new Double[]{0.0, 0.0});
        }

        //collect states
        boolean move;
        if (rand.nextDouble() < epsilon || iteration == 0 || iteration == 1){
            move = rand.nextBoolean();
            //System.out.println("Explore");
        }
        else {
            move = qTable.get(state)[0] >= qTable.get(state)[1];
        }
        iteration++;
        return move;
    }
    public void updateJQValues(boolean myMove, boolean oppMove, int myReward, int oppReward){
        strOppMoveCurr = oppMove ? "C" : "D";
        String currState = strOppMovePrev + strOppMoveCurr;
        int totalRew = myReward + oppReward;

        if (!qTable.containsKey(currState)){
            qTable.put(currState, new Double[]{0.0, 0.0});
        }

        int moveIndex = myMove ? 0 : 1;
        double currentQ = qTable.get(prevState)[moveIndex];
        double maxNextQ = Math.max(qTable.get(currState)[0], qTable.get(currState)[1]);
        double updatedQ = currentQ + alpha * (totalRew + gamma * maxNextQ - currentQ);
        qTable.get(currState)[moveIndex] = updatedQ;
        //System.out.println(currState);
        //System.out.println(qTable.get(currState)[0] + " | " + qTable.get(currState)[1]);

        this.prevState = currState;
        this.strOppMovePrev = strOppMoveCurr;

        //decay rate
        //epsilon = epsilon * 0.99;
    }
}
