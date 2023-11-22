package checkers.logic.rules;

import common.models.Board;
import common.models.SideColor;

public class CheckForWinByNoMorePieces {
    public Boolean check(Board board, SideColor turn){
        return board.getCurrentPieces().stream().noneMatch(piece -> piece.getColor() != turn);
    }
}
