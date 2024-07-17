import java.util.HashMap;
import java.util.Map;

public class BayesianInferencePlayer extends Player {

    private Map<String, Double> beliefs;
    private double initC = 0.5;
    private double initD = 0.5;
    // Initialize counts with 1 for Laplace smoothing
    private int countC = 1;
    private int countD = 1;
    private int iteration = 0;

    public BayesianInferencePlayer(String name){
        super(name);
        beliefs = new HashMap<>();
        beliefs.put("C", initC);
        beliefs.put("D", initD);
    }
    @Override
    public boolean play() {
        //System.out.println(beliefs);
        if (iteration == 100){
            this.beliefs.clear();
        }
        double draw = Math.random();

        if (draw < beliefs.get("C")){
            return true;
        }
        else {
            return false;
        }
    }
    public void updateBeliefs(boolean oppMove){
        if (oppMove == true){
            countC++;
        }
        else {
            countD++;
        }
        double total = countC + countD;
        double pC = countC / total;
        double pD = countD /total;

        beliefs.put("C", pC);
        beliefs.put("D", pD);

    }
}