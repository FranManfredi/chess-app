package common.logic;

import common.models.Board;
import common.models.Coordinate;
import common.moves.Move;
import common.models.Piece;

import java.util.Objects;

public class MoveValidation {
    public Boolean validateMove(Piece piece, Board board, Coordinate possibleMove, Coordinate initialSquare) {
        if (Objects.equals(board.getSquare(possibleMove).getPiece().getName(), "null")) {
            for (Move move : piece.getMovements()) {
                if (move.checkMove(initialSquare, possibleMove, board, piece.getColor()).outputResult() && CommonRule.checkRule(board, piece, possibleMove)) {
                    return true;
                }
            }
        } else {
            for (Move move : piece.getEatMovements()) {
                if (move.checkMove(initialSquare, possibleMove, board, piece.getColor()).outputResult() && CommonRule.checkRule(board, piece, possibleMove)) {
                    return true;
                }
            }
        }
        return false;
    }
}
