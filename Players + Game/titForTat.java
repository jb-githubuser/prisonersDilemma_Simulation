public class titForTat extends Player{

    //this strategy begins with a collab and then does the opposite of what the opponent previously did
    private int iteration = 0;
    private boolean prevOppMove = false;

    public titForTat(String name){
        super(name);
    }
    @Override
    public boolean play(){
        if (iteration == 0){
            iteration++;
            return true;
        }
        return prevOppMove;
    }
    public void updateMoves(boolean oppMove){
        prevOppMove = oppMove;
    }
}