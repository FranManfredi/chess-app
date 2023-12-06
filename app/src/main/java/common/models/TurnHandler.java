package common.models;

public record TurnHandler(SideColor turn) {
    public TurnHandler setTurn(SideColor turn) {
        return new TurnHandler(turn);
    }

    public TurnHandler changeTurn() {
        return new TurnHandler(turn == SideColor.White ? SideColor.Black : SideColor.White);
    }

    public SideColor getTurn() {
        return turn;
    }
}
