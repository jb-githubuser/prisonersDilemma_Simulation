// abandoned player because it would just defect every time
public class RegretMatchingPlayer extends Player {
    private double regretC;
    private double regretD;

    public RegretMatchingPlayer(String name) {
        super(name);
        this.regretC = 0;
        this.regretD = 0;
    }

    @Override
    public boolean play() {
        double totalreg = regretC + regretD;
        double probD;
        if (totalreg > 0){
            probD = regretC / totalreg;
        } else {
            probD = 0.5;
        }
        return Math.random() >= probD;

    }
    public void updateRegret(boolean myMove, boolean oppMove){
        if (myMove == true){
            if (oppMove == true){
                regretC += 2;
            }
            else {
                regretC += 1;
            }
        }
    }
}
