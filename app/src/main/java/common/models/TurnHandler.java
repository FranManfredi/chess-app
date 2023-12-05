package common.models;

public record TurnHandler(SideColor turn) {
        public TurnHandler setTurn(SideColor turn) {
        return new TurnHandler(turn);
    }

    public SideColor getTurn() {
        return turn;
    }
}
