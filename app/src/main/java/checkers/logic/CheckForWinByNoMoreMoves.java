package checkers.logic;

import common.logic.PossibleMovements;
import common.models.Board;
import common.models.Piece;
import common.models.SideColor;


public class CheckForWinByNoMoreMoves {
    PossibleMovements possibleMovements = new PossibleMovements();
    public Boolean check(Board board, SideColor color){
        for (Piece p : board.getCurrentPieces()) {
            if (color != getOpositeColor(p.getColor())) {
                continue;
            }
            if (!possibleMovements.getPossibleMovements(board, p, board.getCoordOfPiece(p).successfulResult().get()).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private SideColor getOpositeColor(SideColor color) {
        if (color == SideColor.White)
            return SideColor.Black;
        if (color == SideColor.Black)
            return SideColor.White;
        return color;
    }
}
