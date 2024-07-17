public class alternateOdd extends Player{

    private int iteration = 0;
    public alternateOdd(String name){
        super(name);
    }
    @Override
    public boolean play(){
        iteration++;
        return iteration % 2 != 0;
    }
}