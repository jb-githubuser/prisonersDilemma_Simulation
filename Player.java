public abstract class Player {
    int points;
    private boolean isTurn;
    String name;

    public Player(String name) {
        this.points = 0;
        this.isTurn = false;
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }

    public abstract boolean play();
}
