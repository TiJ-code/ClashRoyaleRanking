package V2;

public class Player
{
    public Player(String aName)
    {
        playerName = aName;
    }

    public String name()
    {
        return playerName;
    }

    public void rename(String newName)
    {
        playerName = newName;
    }

    public void rescore(int value)
    {
        seed = value;
    }

    public int score()
    {
        return seed;
    }

    private String playerName;
    private int seed;
}