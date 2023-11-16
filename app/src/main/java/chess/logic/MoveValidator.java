package chess.logic;

import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.moves.Move;

public class MoveValidator {

    public boolean isValidMove(Piece chessPiece, Board chessBoard, Coordinate possibleMove, Coordinate initialSquare) {
        for (Move move : chessPiece.getMovements()) {
            if (move.checkMove(initialSquare, possibleMove, chessBoard, chessPiece.getColor()).outputResult()
                    && CommonMoveValidator.isValidMove(chessBoard, chessPiece, possibleMove)) {
                return true;
            }
        }
        return false;
    }
}