public class random extends Player {
    public random(String name) {
        super(name);
    }

    @Override
    public boolean play() {
        int input = (int) (Math.random()*100);
        return input % 2 == 0;
    }
}