package commands.lavaplayer;

public enum PlayerMode {
    REGULAR("Regular"),
    LOOP("Loop"),
    LOOP_TRACK("Loop Track"),
    SHUFFLE("Shuffle");

    public final String name;

    PlayerMode(String name) {this.name = name;}
}
