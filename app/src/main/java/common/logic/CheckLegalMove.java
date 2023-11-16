package common.logic;

import chess.logic.classic.PieceMover;
import common.models.Board;
import common.models.Coordinate;
import common.moves.Move;
import common.models.Piece;
import common.results.MoveResults;

import java.util.List;

public class CheckLegalMove {

    private final PieceMover pieceMover = new PieceMover();

    /**
     * Checks if a move is legal, considering the rules of the game and win conditions.
     *
     * @param winCondition The win condition to check after the move.
     * @param piece        The piece making the move.
     * @param toSquare     The destination square for the move.
     * @param board        The current game board.
     * @param initial      The initial position of the piece.
     * @param moves        The list of possible moves for the piece.
     * @return A result indicating if the move is legal and any additional information.
     */
    public MoveResults<Board, Boolean> checkIfMoveIsLegal(WinCondition winCondition, Piece piece, Coordinate toSquare, Board board, Coordinate initial, List<Move> moves) {
        // Add null checks if needed

        MoveResults<Board, Boolean> moveResult = pieceMover.check(board, initial, toSquare, moves, piece, board.getSquare(toSquare).getPiece());

        if (moveResult.errorResult()) {
            return moveResult;
        } else {
            return winCondition.checkWin(board, piece, moveResult, toSquare);
        }
    }
}
