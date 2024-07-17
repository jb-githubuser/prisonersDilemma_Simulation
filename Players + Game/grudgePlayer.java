public class grudgePlayer extends Player {

    private int grudgeCount;
    private boolean oppDefect;

    public grudgePlayer(String name) {
        super(name);
        this.grudgeCount = 0;
    }
    @Override
    public boolean play(){
        // sets the count for defections
        if (oppDefect){
            grudgeCount = 2;
        }
        if (grudgeCount > 0){
            grudgeCount--;
            return false;
        }
        else {
            return true;
        }
    }
    public void detectDefection(boolean oppMove){
        oppDefect = !oppMove;
    }
}

