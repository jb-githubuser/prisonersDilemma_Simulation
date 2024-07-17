
public class UCBPlayer extends Player {
    private int countC;
    private int countD;
    private int totalrewC;
    private int totalrewD;
    private int totalPlays;

    public UCBPlayer(String name){
        super(name);
        this.countC = 0;
        this.countD = 0;
        this.totalrewC = 0;
        this.totalrewD = 0;
        this.totalPlays = 0;
    }
    @Override
    public boolean play() {
        //reset values after 100 games
        if (totalPlays == 100){
            this.countC = 0;
            this.countD = 0;
            this.totalrewC = 0;
            this.totalrewD = 0;
            this.totalPlays = 0;
        }
        

        if (countC == 0) return true; // make sure each move is explored first and no division by zero
        if (countD == 0) return false;

        //finding UCB values
        double avgRewC = (double) totalrewC / countC;
        double avgRewD = (double) totalrewD / countD;
        double ucbC = avgRewC + Math.sqrt((2*Math.log(totalPlays)/(countC)));
        double ucbD = avgRewD + Math.sqrt((2*Math.log(totalPlays)/(countD)));
        //System.out.println(ucbC + " | " + ucbD);
        // this player correctly identifies that to get the highest point total, defection everytime is key
        totalPlays++;
        return ucbC >= ucbD;
    }
    public void updateVals(boolean opponentMove, boolean myMove){
        if (myMove == true){
            countC++;
            if (opponentMove == true){
                totalrewC += 3;
            }
        }
        else {
            countD++;
            if (opponentMove == true){
                totalrewD += 5;
            }
            else {
                totalrewD += 1;
            }
        }
    }
}
