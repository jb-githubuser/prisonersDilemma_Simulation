public class alwaysDefect extends Player{
    public alwaysDefect(String name){
        super(name);
    }
    @Override
    public boolean play(){
        return false;
    }
}