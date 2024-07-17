import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ReinforcementLearningPlayer extends Player{

    private Map<String, Double[]> qTable; // Q-values
    private double alpha; // Learning rate
    private double gamma; // Discount factor
    private double epsilon = 0.6; // Exploration rate
    private String prevState;
    private Random rand;
    private int iteration = 0;
    private int movesInCurrentGame = 0;
    private int movesPerGame = 100;

    public ReinforcementLearningPlayer(String name){
        super(name);
        this.qTable = new HashMap<>();
        this.alpha = 0.1;
        this.gamma = 0.9;
        this.rand = new Random();
        this.prevState = "";
    }

    @Override
    public boolean play(){

        if (movesInCurrentGame == movesPerGame) {
            epsilon = 0.6;
            movesInCurrentGame = 0;
            this.qTable.clear();
        }

        //define state as the previous move from the opponent
        String state = prevState; //first move N/A
        
        //Initialize Q-values for new states
        if (!qTable.containsKey(state)){
            qTable.put(state, new Double[]{0.0, 0.0}); // [Q(C), Q(D)]
        }
        
        //decide move based on epsilon-greedy policy
        //epsilon is the exploration rate - higher values lead to more exploration
        //its common to start with a higher epsilon and gradually reduce it as learning progresses
        boolean move;
        if (rand.nextDouble() < epsilon || iteration == 0){
            //Explore: choose random action
            move = rand.nextBoolean();
        }
        else {
            //exploit: choose the best action based on Q-values
            //System.out.println("[" + qTable.get(state)[0] + "] [" + qTable.get(state)[1] + "]");
            move = qTable.get(state)[0] >= qTable.get(state)[1];
            //If the Q-value for "Cooperate" is greater than or equal to the 
            //Q-value for "Defect", action will be true (indicating "Cooperate"). 
            //Otherwise, action will be false (indicating "Defect").
        }
        movesInCurrentGame++;
        return move;
        
    }
    public void updateQValues(String prevState, boolean move, String nextState, int reward) {
        // put prev state in q table
        if (!qTable.containsKey(prevState)) {
            qTable.put(prevState, new Double[]{0.0, 0.0});
        }
    
        // add nextstate to Qtable
        if (!qTable.containsKey(nextState)) {
            qTable.put(nextState, new Double[]{0.0, 0.0});
        }
    
        // get current Q-value
        int moveIndex = move ? 0 : 1;
        double currentQ = qTable.get(prevState)[moveIndex];
    
        // calc max Q-value for the next state
        double maxNextQ = Math.max(qTable.get(nextState)[0], qTable.get(nextState)[1]);
    
        // update Q-table
        double updatedQ = currentQ + alpha * (reward + gamma * maxNextQ - currentQ);
        qTable.get(prevState)[moveIndex] = updatedQ;
    
        //System.out.println(qTable.get(nextState)[0] + " | " + qTable.get(nextState)[1]);
    
        this.prevState = nextState;
        epsilon = Math.max(epsilon * 0.99, 0.05); // decay 
    }
}