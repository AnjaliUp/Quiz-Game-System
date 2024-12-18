// Player.java
public class Player {
    private String name;
    private String identifier;
    private int score;

    public Player(String name, String identifier) {
        this.name = name;
        this.identifier = identifier;
        this.score = 0;
    }

    // Increase score when player answers correctly
    public void increaseScore(int points) {
        this.score += points;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "Player: " + name + " (" + identifier + ") - Score: " + score;
    }
}
