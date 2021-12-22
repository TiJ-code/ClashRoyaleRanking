package V1;

public class Player {

    String name;
    int timesLost;

    Player(String name) {
        this.name = name;
        timesLost = 0;
    }

    public String getName() {
        return name;
    }
}
