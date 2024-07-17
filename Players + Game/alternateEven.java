public class alternateEven extends Player{

    private int iteration = 0;
    public alternateEven(String name){
        super(name);
    }
    @Override
    public boolean play(){
        iteration++;
        return iteration % 2 == 0;
    }
}