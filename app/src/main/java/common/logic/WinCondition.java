package common.logic;

import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.results.MoveResults;

/**
 * Interface for defining win conditions in a game.
 */
public interface WinCondition {

    /**
     * Checks if a win condition is met after a move.
     *
     * @param board               The current game board.
     * @param piece               The piece making the move.
     * @param moveResults         The result of the move.
     * @param destinationCoordinate The destination coordinate of the move.
     * @return A result indicating if the win condition is met and any additional information.
     */
    MoveResults<Board, Boolean> checkWin(Board board, Piece piece, MoveResults<Board, Boolean> moveResults, Coordinate destinationCoordinate);
}

