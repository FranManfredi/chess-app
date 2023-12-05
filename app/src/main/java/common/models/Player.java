package common.models;

public class Player {
    private final SideColor color;

    public Player(SideColor color) {
        this.color = color;
    }

    public Player setColor(SideColor color) {
        return new Player(color);
    }

    public SideColor getColor() {
        return color;
    }
}
