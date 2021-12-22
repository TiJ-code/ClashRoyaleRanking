package V2;

public class Player {

    public Player(String name) {
        this.playerName = name;
    }

    public String name() {
        return playerName;
    }

    public void rename(String newName) {
        this.playerName = newName;
    }

    public void rescore(int value) {
        this.seed = value;
    }

    public int score() {
        return seed;
    }

    private String playerName;
    private int seed;
}
