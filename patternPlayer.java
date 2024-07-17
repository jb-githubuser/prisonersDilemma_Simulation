public class patternPlayer extends Player {

    private boolean swap;
    private int index = 0;

    public patternPlayer(String name) {
        super(name);
        this.swap = false;
    }
    // numerical sequence 01121220
    // 0 initially means C
    // 1 initially means D
    // 2 means the roles for 0 and 1 switch
    @Override
    public boolean play() {
        int[] seq = new int[]{0, 1, 1, 2, 1, 2, 2, 0};
        boolean move = false;
        if (seq[index] == 2){
            swap = !swap;
            index++;
        }
        if (seq[index] == 0){
            if (swap){
                move = false;
            }
            else {
                move = true;
            }
        }
        if (seq[index] == 1){
            if (swap){
                move = true;
            }
            else {
                move = false;
            }
        }
        if (index < seq.length-1){
            index++;
        }
        else {
            index = 0;
        }
        return move;
    }
}
