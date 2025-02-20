package common.logic;

import common.models.Board;
import common.models.Coordinate;
import common.moves.Move;
import common.models.Piece;

import java.util.Objects;

public class MoveValidation {
    private final CommonRule commonRule = new CommonRule();
    public Boolean validateMove(Piece piece, Board board, Coordinate possibleMove, Coordinate initialSquare) {
        if (isNotNull(board, possibleMove)) {
            for (Move move : piece.getMovements()) {
                if (move.checkMove(initialSquare, possibleMove, board, piece.getColor()).outputResult() && commonRule.checkRule(board, piece, possibleMove)) {
                    return true;
                }
            }
        } else {
            for (Move move : piece.getEatMovements()) {
                if (move.checkMove(initialSquare, possibleMove, board, piece.getColor()).outputResult() && commonRule.checkRule(board, piece, possibleMove)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isNotNull(Board board, Coordinate possibleMove) {
        return Objects.equals(board.getPiece(possibleMove).successfulResult().get().getName(), "null");
    }
}
