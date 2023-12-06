package common.results;

import common.models.Game;

public record MoveResult<T, R,SideColor>(T successfulResult, R errorResult, SideColor nextTurn, String message) {
    public MoveResult<T, R, SideColor> withNewGame(T newGame) {
        return new MoveResult<>(newGame, this.errorResult, this.nextTurn, this.message);
    }

    public T getBoard() {
        return successfulResult;
    }
}
