package chess.rules;

import common.models.ChessBoard;
import common.models.Coordinate;
import common.moves.Move;
import common.models.ChessPiece;

public class MoveValidator {

    public boolean isValidMove(ChessPiece chessPiece, ChessBoard chessBoard, Coordinate possibleMove, Coordinate initialSquare) {
        for (Move move : chessPiece.getMovements()) {
            if (move.checkMove(initialSquare, possibleMove, chessBoard, chessPiece.getColor()).outputResult()
                    && CommonRule.checkRule(chessBoard, chessPiece, possibleMove)) {
                return true;
            }
        }
        return false;
    }
}