package checkers.logic;

import chess.logic.PossibleMovements;
import common.models.Board;
import common.models.Coordinate;
import common.models.Piece;



public class CheckForWinByNoMoreMoves {
    PossibleMovements possibleMovements = new PossibleMovements();
    public Boolean check(Piece piece, Board board, Coordinate initialSquare){
        
        for (Piece p : board.getPieces()) {
            if (!possibleMovements.getPossibleMovements(board, p, board.getSquareOfPiece(p).successfulResult().get()).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
