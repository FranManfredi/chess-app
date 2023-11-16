package checkers.logic;

import chess.logic.MovementCalculator;
import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;
import common.models.SideColor;

public class CheckWinNoMoreMoves {
    private final MovementCalculator possibleMovements = new MovementCalculator();

    public boolean check(Piece piece, Board board, Coordinate initialSquare) {
        SideColor opponentColor = (piece.getColor() == SideColor.White) ? SideColor.Black : SideColor.White;

        for (Piece opponentPiece : board.getPieces()) {
            if (opponentPiece.getColor() == opponentColor) {
                Coordinate pieceCoordinate = board.getSquareOfPiece(opponentPiece).successfulResult().orElse(null);

                if (pieceCoordinate != null && !possibleMovements.calculatePossibleMovements(board, opponentPiece, pieceCoordinate).isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }
}

