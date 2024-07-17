public class alwaysCooperate extends Player {
    public alwaysCooperate(String name) {
        super(name);
    }

    @Override
    public boolean play() {
        return true;
    }
}